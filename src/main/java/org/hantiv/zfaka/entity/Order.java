package org.hantiv.zfaka.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Author Zhikun Han
 * @Date Created in 9:02 2022/7/13
 * @Description: 订单实体类
 */
public class Order {
    private String id;
    private Integer userId;
    private Integer itemId;
    private Integer promotionId;
    private BigDecimal orderPrice;
    private Integer orderAmount;
    private BigDecimal orderTotal;
    private Timestamp orderTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id==null?null:id.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", promotionId=" + promotionId +
                ", orderPrice=" + orderPrice +
                ", orderAmount=" + orderAmount +
                ", orderTotal=" + orderTotal +
                ", orderTime=" + orderTime +
                '}';
    }
}
