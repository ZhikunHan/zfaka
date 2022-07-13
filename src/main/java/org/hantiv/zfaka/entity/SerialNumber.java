package org.hantiv.zfaka.entity;

/**
 * @Author Zhikun Han
 * @Date Created in 9:15 2022/7/13
 * @Description: 序列号实体类
 */
public class SerialNumber {
    /**
     * 业务名称
     *
     * @param null
     * @return
     */
    private String name;
    /**
     * 当前序号
     *
     * @param null
     * @return
     */
    private Integer value;
    /**
     * 增加步长
     *
     * @param null
     * @return
     */
    private Integer step;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name==null?null:name.trim();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "SerialNumber{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", step=" + step +
                '}';
    }
}
