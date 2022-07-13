package org.hantiv.zfaka.exception;

/**
 * @Author Zhikun Han
 * @Date Created in 9:54 2022/7/13
 * @Description:
 */
public class BusinessException extends RuntimeException {
    private int code;
    private String message;
    public BusinessException(int code) {
        super();
        this.code = code;
    }
    public BusinessException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
