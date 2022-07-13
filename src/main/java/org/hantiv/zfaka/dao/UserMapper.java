package org.hantiv.zfaka.dao;

import org.apache.ibatis.annotations.Mapper;
import org.hantiv.zfaka.entity.User;

import java.util.List;

/**
 * @Author Zhikun Han
 * @Date Created in 23:21 2022/7/11
 * @Description:
 */
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(User record);
    User selectByPrimaryKey(Integer id);
    List<User> selectAll();
    int updateByPrimaryKey(User record);
    /**
     * 根据手机号查询用户
     *
     * @param phone
     * @return org.hantiv.zfaka.entity.User
     */
    User selectByPhone(String phone);
}
