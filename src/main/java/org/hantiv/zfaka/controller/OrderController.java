package org.hantiv.zfaka.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.lang3.StringUtils;
import org.hantiv.zfaka.entity.User;
import org.hantiv.zfaka.exception.BusinessException;
import org.hantiv.zfaka.exception.ErrorCode;
import org.hantiv.zfaka.response.ResponseModel;
import org.hantiv.zfaka.service.OrderService;
import org.hantiv.zfaka.service.PromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author Zhikun Han
 * @Date Created in 15:24 2022/7/13
 * @Description:
 */
@Controller
@RequestMapping("/order")
@CrossOrigin(origins = "{server.addresss}", allowedHeaders = "*", allowCredentials = "true")
public class OrderController implements ErrorCode {
    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    private RateLimiter rateLimiter = RateLimiter.create(1000);

    @Autowired
    private OrderService orderService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @RequestMapping(path = "/captcha", method = RequestMethod.GET)
    public void getCaptcha(String token, HttpServletResponse response) {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        if (token!=null){
            User user = (User) redisTemplate.opsForValue().get(token);
            if (user!=null){
                String key = "captcha:"+user.getId();
                redisTemplate.opsForValue().set(key, specCaptcha.text(), 1, TimeUnit.MINUTES);
            }
        }
        response.setContentType("image/png");
        try{
            OutputStream os = response.getOutputStream();
            specCaptcha.out(os);
        } catch (Exception e) {
            logger.error("发送验证码失败", e.getMessage());
        }
    }

    @RequestMapping(path = "/token", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel generateToken(int itemId, int promotionId, String token, String captcha){
        User user = (User) redisTemplate.opsForValue().get(token);
        if(StringUtils.isEmpty(captcha)){
            throw new BusinessException(PARAMETER_ERROR, "验证码不能为空！");
        }
        String key = "captcha:"+user.getId();
        String realCaptcha = (String) redisTemplate.opsForValue().get(key);
        if(!realCaptcha.equalsIgnoreCase(captcha)){
            throw new BusinessException(PARAMETER_ERROR, "请输入正确的验证码！");
        }
        String promotionToken = promotionService.generateToken(user.getId(), itemId, promotionId);
        if (StringUtils.isEmpty(promotionToken)){
            throw new BusinessException(CREATE_ORDER_FAILURE, "下单失败！");
        }
        return new ResponseModel(promotionToken);
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel create(/*HttpSession session,  */
            int itemId, int amount, Integer promotionId, String promotionToken, String token){
//        User user = (User) session.getAttribute("loginUser");
//        if (!rateLimiter.tryAcquire()) {
//            throw new BusinessException(OUT_OF_LIMIT, "服务器繁忙，请稍后再试！");
//        }
        User user = (User) redisTemplate.opsForValue().get(token);
        logger.debug("登录用户 [" + token + ": " + user + "]");
        if(promotionId != null){
            String key = "promotion:token:"+ user.getId() + ":" + itemId + ":" + promotionId;
            String realPromotionToken = (String) redisTemplate.opsForValue().get(key);
            if(StringUtils.isEmpty(promotionToken) || !promotionToken.equals(realPromotionToken)){
                throw new BusinessException(CREATE_ORDER_FAILURE, "下单失败！");
            }
        }
        Future future = taskExecutor.submit(() -> {
//            orderService.createOrder(user.getId(), itemId, amount, promotionId);
            orderService.createOrderAsync(user.getId(), itemId, amount, promotionId);
            return null;
        });
        try {
            future.get();
        } catch (Exception e) {
            logger.error("下单失败", e.getMessage());
            throw new BusinessException(CREATE_ORDER_FAILURE, "下单失败！");
        }
        return new ResponseModel();
    }
}
