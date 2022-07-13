package org.hantiv.zfaka.controller.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.hantiv.zfaka.exception.ErrorCode;
import org.hantiv.zfaka.response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Zhikun Han
 * @Date Created in 16:29 2022/7/13
 * @Description:
 */
@Component
public class LoginCheckInterceptor implements HandlerInterceptor, ErrorCode {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("loginUser");
        String token = request.getHeader("token");
        if (token == null || !redisTemplate.hasKey(token)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            Map<Object, Object> map = new HashMap<>();
            map.put("code", USER_NOT_LOGIN);
            map.put("message", "请先登录！");
            ResponseModel responseModel = new ResponseModel(ResponseModel.STATUS_FAILURE, map);
            writer.write(JSONObject.toJSONString(responseModel));
            return false;
        }
        return true;
    }
}
