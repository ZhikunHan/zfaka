package org.hantiv.zfaka.service;

import org.hantiv.zfaka.entity.User;

/**
 * @Author Zhikun Han
 * @Date Created in 10:17 2022/7/13
 * @Description:
 */
public interface UserService {
    void register(User user);
    User login(String phone, String password);
    User findUserById(int id);
    User findUserFromCache(int id);
}
