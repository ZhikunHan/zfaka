package org.hantiv.zfaka.controller;

import org.apache.commons.lang3.StringUtils;
import org.hantiv.zfaka.entity.User;
import org.hantiv.zfaka.exception.BusinessException;
import org.hantiv.zfaka.exception.ErrorCode;
import org.hantiv.zfaka.response.ResponseModel;
import org.hantiv.zfaka.service.UserService;
import org.hantiv.zfaka.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author Zhikun Han
 * @Date Created in 15:53 2022/7/13
 * @Description:
 */
@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "{server.addresss}", allowedHeaders = "*", allowCredentials = "true")
public class UserController implements ErrorCode {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    private String generateOTP() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    @RequestMapping(path = "otp/{phone}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel getOTP(@PathVariable("phone") String phone/*, HttpSession session*/) {
        // 生成OTP
        String otp = this.generateOTP();
        // 存储OTP
//        session.setAttribute(phone, otp);
        redisTemplate.opsForValue().set(phone, otp, 5, TimeUnit.MINUTES);
        // 发送OTP短信
        logger.info("尊敬的{}您好, 您的注册验证码是{}, 请注意查收!", phone, otp);
        return new ResponseModel(otp);
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel register(String otp, User user/*, HttpSession session*/) {
//        // 校验OTP
//        String realOTP = (String) session.getAttribute(user.getPhone());
        String realOTP = (String) redisTemplate.opsForValue().get(user.getPhone());
        if(StringUtils.isEmpty(otp) || StringUtils.isEmpty(realOTP) || !StringUtils.equals(otp, realOTP)) {
            throw new BusinessException(PARAMETER_ERROR, "验证码错误!");
        }

        user.setPassword(MD5Util.getMD5(user.getPassword()));
        // 注册用户
        userService.register(user);
        return new ResponseModel();
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel login(String phone, String password/*, HttpSession session*/) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(PARAMETER_ERROR, "参数不合法!");
        }
        User user = userService.login(phone, MD5Util.getMD5(password));
//        session.setAttribute("loginUser", user);
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(token, user, 1, TimeUnit.MINUTES);
        return new ResponseModel(token);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel logout(/*HttpSession session, */String token) {
//        session.invalidate();
        if (StringUtils.isNotEmpty(token)) {
            redisTemplate.delete(token);
        }
        return new ResponseModel();
    }

    @RequestMapping(path = "/status", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel getUser(/*HttpSession session, */String token) {
//        User user = (User) session.getAttribute("loginUser");
        User user = null;
        if (StringUtils.isNoneEmpty(token)) {
            user = (User) redisTemplate.opsForValue().get(token);
        }
        return new ResponseModel(user);
    }
}
