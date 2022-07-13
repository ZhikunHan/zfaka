package org.hantiv.zfaka.controller.advice;

import org.hantiv.zfaka.exception.BusinessException;
import org.hantiv.zfaka.exception.ErrorCode;
import org.hantiv.zfaka.response.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Zhikun Han
 * @Date Created in 16:36 2022/7/13
 * @Description:
 */
@ControllerAdvice
public class ExceptionAdvice implements ErrorCode {
    private Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    public ResponseModel handleException(Exception e) {
        Map<Object, Object> map = new HashMap<>();
        if(e instanceof BusinessException) {
            map.put("code", ((BusinessException) e).getCode());
            map.put("message", ((BusinessException) e).getMessage());
        } else if (e instanceof NoHandlerFoundException) {
            map.put("code", UNDEFINED_ERROR);
            map.put("message", "该资源不存在！");
        } else {
            logger.error("发生未知错误", e);
            map.put("code", UNDEFINED_ERROR);
            map.put("message", "发生未知错误!");
        }
        return new ResponseModel(ResponseModel.STATUS_FAILURE, map);
    }
}
