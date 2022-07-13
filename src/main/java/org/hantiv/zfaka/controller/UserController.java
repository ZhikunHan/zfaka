package org.hantiv.zfaka.controller;

import org.hantiv.zfaka.exception.ErrorCode;
import org.hantiv.zfaka.response.ResponseModel;
import org.hantiv.zfaka.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Random;
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
}
