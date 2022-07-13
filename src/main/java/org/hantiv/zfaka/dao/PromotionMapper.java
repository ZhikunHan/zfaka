package org.hantiv.zfaka.dao;

import org.apache.ibatis.annotations.Mapper;
import org.hantiv.zfaka.entity.Promotion;

import java.util.List;

/**
 * @Author Zhikun Han
 * @Date Created in 9:28 2022/7/13
 * @Description:
 */
@Mapper
public interface PromotionMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(Promotion record);
    Promotion selectByPrimaryKey(Integer id);
    List<Promotion> selectAll();
    int updateByPrimaryKey(Promotion record);
    /**
     * 根据商品ID查询活动
     *
     * @param itemId
     * @return org.hantiv.zfaka.entity.Promotion
     */
    Promotion selectByItemId(Integer itemId);
}
