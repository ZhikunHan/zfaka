package org.hantiv.zfaka.service;

import org.hantiv.zfaka.entity.Item;
import org.hantiv.zfaka.entity.ItemStockLog;

import java.util.List;

/**
 * @Author Zhikun Han
 * @Date Created in 9:32 2022/7/13
 * @Description:
 */
public interface ItemService {
    List<Item> findItemOnPromotion();
    Item findItemById(int id);
    Item findItemInCache(int id);
    void increaseSales(int itemId, int amount);
    boolean decreaseStock(int itemId, int amount);
    boolean decreaseStockInCache(int itemId, int amount);
    boolean increaseStockInCache(int itemId, int amount);
    ItemStockLog createItemStockLog(int itemId, int amount);
    void updateItemStockLogStatus(String id, int status);
    ItemStockLog findItemStockLogById(String id);
}
