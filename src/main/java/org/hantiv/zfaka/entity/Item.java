package org.hantiv.zfaka.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author Zhikun Han
 * @Date Created in 8:54 2022/7/13
 * @Description: 商品实体类
 */
public class Item {
    private Integer id;
    @NotBlank(message = "商品标题不能为空")
    private String title;
    @NotBlank(message = "商品价格不能为空")
    @Min(value = 0, message = "商品价格不能小于0")
    private BigDecimal price;
    private Integer sales;
    @NotBlank(message = "商品主图不能为空")
    private String imageUrl;
    @NotBlank(message = "商品详情不能为空")
    private String description;
    @NotNull(message = "商品库存不能为空")
    private ItemStock itemStock;
    private Promotion promotion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title==null?null:title.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImageUrl() {
        return imageUrl==null?null:imageUrl.trim();
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description==null?null:description.trim();
    }

    public ItemStock getItemStock() {
        return itemStock;
    }

    public void setItemStock(ItemStock itemStock) {
        this.itemStock = itemStock;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", sales=" + sales +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", itemStock=" + itemStock +
                ", promotion=" + promotion +
                '}';
    }
}
