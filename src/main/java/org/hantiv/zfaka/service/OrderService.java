package org.hantiv.zfaka.service;

import org.hantiv.zfaka.entity.Order;

/**
 * @Author Zhikun Han
 * @Date Created in 9:36 2022/7/13
 * @Description:
 */
public interface OrderService {
    Order createOrder(int userId, int itemId, int amount, int promotionId, String itemStockLogId);
    void createOrderAsync(int userId, int itemId, int amount, int promotionId);
}
