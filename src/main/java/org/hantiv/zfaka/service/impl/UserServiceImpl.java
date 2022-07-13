package org.hantiv.zfaka.service.impl;

import org.hantiv.zfaka.entity.User;
import org.hantiv.zfaka.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author Zhikun Han
 * @Date Created in 10:52 2022/7/13
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void register(User user) {

    }

    @Override
    public User login(String phone, String password) {
        return null;
    }

    @Override
    public User findUserById(int id) {
        return null;
    }

    @Override
    public User fidnUserFromCache(int id) {
        return null;
    }
}
