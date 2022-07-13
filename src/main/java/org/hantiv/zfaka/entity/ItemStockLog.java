package org.hantiv.zfaka.entity;

/**
 * @Author Zhikun Han
 * @Date Created in 9:00 2022/7/13
 * @Description: 库存流水实体类
 */
public class ItemStockLog {
    private String id;
    private Integer itemId;
    private Integer amount;
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id==null?null:id.trim();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ItemStockLog{" +
                "id='" + id + '\'' +
                ", itemId=" + itemId +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}
