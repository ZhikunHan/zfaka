package org.hantiv.zfaka.dao;

import org.apache.ibatis.annotations.Mapper;
import org.hantiv.zfaka.entity.Item;

import java.util.List;

/**
 * @Author Zhikun Han
 * @Date Created in 9:19 2022/7/13
 * @Description:
 */
@Mapper
public interface ItemMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(Item record);
    Item selectByPrimaryKey(Integer id);
    List<Item> selectAll();
    int updateByPrimaryKey(Item record);
    /**
     * 增加销量
     *
     * @param id
	* @param amount
     * @return int
     */
    int increaseSales(Integer id, Integer amount);
    /**
     * 查询正在进行秒杀活动的商品
     *
     * @param 
     * @return java.util.List<org.hantiv.zfaka.entity.Item>
     */
    List<Item> selectOnPromotion();
}
