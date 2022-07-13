package org.hantiv.zfaka.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Author Zhikun Han
 * @Date Created in 8:58 2022/7/13
 * @Description: 活动实体类
 */
public class Promotion {
    private Integer id;
    private String name;
    private Integer itemId;
    private Timestamp startTime;
    private Timestamp endTime;
    private BigDecimal promotionPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name==null?null:name.trim();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    /**
     * 获取活动状态
     *
     * @param
     * @return int -1:活动未开始，0:活动进行中，1:活动已结束
     */
    public int getStatus() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(now.before(startTime)){
            return -1;
        } else if (now.after(endTime)){
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", itemId=" + itemId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", promotionPrice=" + promotionPrice +
                '}';
    }
}
