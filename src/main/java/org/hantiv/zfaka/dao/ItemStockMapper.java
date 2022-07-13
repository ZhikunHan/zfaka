package org.hantiv.zfaka.dao;

import org.apache.ibatis.annotations.Mapper;
import org.hantiv.zfaka.entity.ItemStock;

import java.util.List;

/**
 * @Author Zhikun Han
 * @Date Created in 9:25 2022/7/13
 * @Description:
 */
@Mapper
public interface ItemStockMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(ItemStock record);
    ItemStock selectByPrimaryKey(Integer id);
    List<ItemStock> selectAll();
    int updateByPrimaryKey(ItemStock record);
    /**
     * 根据商品ID查询库存
     *
     * @param itemId
     * @return org.hantiv.zfaka.entity.ItemStock
     */
    ItemStock selectByItemId(Integer itemId);
    /**
     * 扣减库存
     *
     * @param id
	 * @param amount
     * @return int
     */
    int decreaseStock(Integer id, Integer amount);
}
