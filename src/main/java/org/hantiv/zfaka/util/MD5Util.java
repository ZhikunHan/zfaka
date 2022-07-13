package org.hantiv.zfaka.util;

import org.apache.commons.lang3.StringUtils;
import org.hantiv.zfaka.exception.BusinessException;
import org.hantiv.zfaka.exception.ErrorCode;
import org.springframework.util.DigestUtils;

/**
 * @Author Zhikun Han
 * @Date Created in 10:01 2022/7/13
 * @Description:
 */
public class MD5Util implements ErrorCode {
    private static final String salt = "欢迎使用zfaka";

    public static String getMD5(String str) {
        if(StringUtils.isEmpty(str)) {
            throw new BusinessException(PARAMETER_ERROR, "参数不合法！");
        }
        return DigestUtils.md5DigestAsHex((str + salt).getBytes());
    }
}
