package org.hantiv.zfaka.service;

/**
 * @Author Zhikun Han
 * @Date Created in 9:42 2022/7/13
 * @Description:
 */
public interface PromotionService {
    String generateToken(int userId, int itemId, int promotionId);
}
