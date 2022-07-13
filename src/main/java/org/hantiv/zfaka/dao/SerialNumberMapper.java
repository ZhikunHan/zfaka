package org.hantiv.zfaka.dao;

import org.apache.ibatis.annotations.Mapper;
import org.hantiv.zfaka.entity.SerialNumber;

import java.util.List;

/**
 * @Author Zhikun Han
 * @Date Created in 9:30 2022/7/13
 * @Description:
 */
@Mapper
public interface SerialNumberMapper {
    int deleteByPrimaryKey(String name);
    int insert(SerialNumber record);
    SerialNumber selectByPrimaryKey(String name);
    List<SerialNumber> selectAll();
    int updateByPrimaryKey(SerialNumber record);
}
