package org.hantiv.zfaka.dao;

import org.apache.ibatis.annotations.Mapper;
import org.hantiv.zfaka.entity.Order;

import java.util.List;

/**
 * @Author Zhikun Han
 * @Date Created in 9:27 2022/7/13
 * @Description:
 */
@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(String id);
    int insert(Order record);
    Order selectByPrimaryKey(String id);
    List<Order> selectAll();
    int updateByPrimaryKey(Order record);

}
