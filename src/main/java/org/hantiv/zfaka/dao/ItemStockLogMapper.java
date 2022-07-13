package org.hantiv.zfaka.dao;

import org.apache.ibatis.annotations.Mapper;
import org.hantiv.zfaka.entity.ItemStockLog;

import java.util.List;

/**
 * @Author Zhikun Han
 * @Date Created in 9:23 2022/7/13
 * @Description:
 */
@Mapper
public interface ItemStockLogMapper {
    int deleteByPrimaryKey(String id);
    int insert(ItemStockLog record);
    ItemStockLog selectByPrimaryKey(String id);
    List<ItemStockLog> selectAll();
    int updateByPrimaryKey(ItemStockLog record);
}
