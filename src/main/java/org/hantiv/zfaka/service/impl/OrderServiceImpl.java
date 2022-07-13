package org.hantiv.zfaka.service.impl;

import org.hantiv.zfaka.entity.Order;
import org.hantiv.zfaka.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @Author Zhikun Han
 * @Date Created in 10:51 2022/7/13
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {


    @Override
    public Order createOrder(int userId, int itemId, int amount, int promotionId, String itemStockLogId) {
        return null;
    }

    @Override
    public void createOrderAsync(int userId, int itemId, int amount, int promotionId) {

    }
}
