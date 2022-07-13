package org.hantiv.zfaka.response;

/**
 * @Author Zhikun Han
 * @Date Created in 10:07 2022/7/13
 * @Description:
 */
public class CommonReturnType {
    // 状态常量
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_FAILURE = 1;

    // 业务状态
    private int status;
    // 业务数据
    private Object data;
    public CommonReturnType() {
        this.status = STATUS_SUCCESS;
    }
    public CommonReturnType(Object data) {
        this.status = STATUS_SUCCESS;
        this.data = data;
    }
    public CommonReturnType(int status, Object data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonReturnType{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
