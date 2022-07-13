package org.hantiv.zfaka.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.hantiv.zfaka.dao.ItemMapper;
import org.hantiv.zfaka.dao.ItemStockLogMapper;
import org.hantiv.zfaka.dao.ItemStockMapper;
import org.hantiv.zfaka.dao.PromotionMapper;
import org.hantiv.zfaka.entity.Item;
import org.hantiv.zfaka.entity.ItemStock;
import org.hantiv.zfaka.entity.ItemStockLog;
import org.hantiv.zfaka.entity.Promotion;
import org.hantiv.zfaka.exception.BusinessException;
import org.hantiv.zfaka.exception.ErrorCode;
import org.hantiv.zfaka.service.ItemService;
import org.hantiv.zfaka.validator.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author Zhikun Han
 * @Date Created in 9:43 2022/7/13
 * @Description:
 */
@Service
public class ItemServiceImpl implements ItemService, ErrorCode {
    private Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemStockMapper itemStockMapper;

    @Autowired
    private ItemStockLogMapper itemStockLogMapper;

    @Autowired
    private PromotionMapper promotionMapper;

    @Autowired
    private ObjectValidator validator;

    @Autowired
    private RedisTemplate redisTemplate;

    // 本地缓存
    private Cache<String, Object> cache;

    @PostConstruct
    public void init() {
        cache = CacheBuilder.newBuilder()
                .initialCapacity(10)
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public List<Item> findItemOnPromotion() {
        List<Item> items = itemMapper.selectOnPromotion();
        return items.stream().map(item -> {
            // 查库存
            ItemStock stock = itemStockMapper.selectByItemId(item.getId());
            item.setItemStock(stock);
            // 查秒杀活动
            Promotion promotion = promotionMapper.selectByItemId(item.getId());
            if(promotion != null && promotion.getStatus() == 0) {
                item.setPromotion(promotion);
            }
            return item;
        }).collect(Collectors.toList());
    }

    @Override
    public Item findItemById(int id) {
        if(id<=0){
            throw new BusinessException(PARAMETER_ERROR, "参数不合法！");
        }
        Item item = itemMapper.selectByPrimaryKey(id);
        if(item != null){
            // 查库存
            ItemStock stock = itemStockMapper.selectByItemId(id);
            item.setItemStock(stock);
            // 查秒杀活动
            Promotion promotion = promotionMapper.selectByItemId(id);
            if(promotion != null && promotion.getStatus() == 0) {
                item.setPromotion(promotion);
            }
        }
        return item;
    }

    @Override
    public Item findItemInCache(int id) {
        if(id<=0){
            throw new BusinessException(PARAMETER_ERROR, "参数不合法！");
        }
        Item item = null;
        String key = "item:" + id;
        // 先从本地缓存（guava）中查询
        item = (Item) cache.getIfPresent(key);
        if(item!=null){
            return item;
        }
        // guava中没有，从Redis中查询
        item = (Item) redisTemplate.opsForValue().get(key);
        if(item!=null){
            // 将查询到的数据放入本地缓存（guava）中
            cache.put(key, item);
            return item;
        }
        // Redis中也没有，从数据库中查询
        item = this.findItemById(id);
        if(item!=null){
            // 将查询到的数据放入Redis中
            redisTemplate.opsForValue().set(key, item, 3, TimeUnit.MINUTES);
            // 将查询到的数据放入本地缓存（guava）中
            cache.put(key, item);
        }
        return item;
    }

    @Override
    public void increaseSales(int itemId, int amount) {
        if(itemId<=0 || amount<=0){
            throw new BusinessException(PARAMETER_ERROR, "参数不合法！");
        }
        itemMapper.increaseSales(itemId, amount);
    }

    @Override
    public boolean decreaseStock(int itemId, int amount) {
        if (itemId <= 0 || amount <= 0) {
            throw new BusinessException(PARAMETER_ERROR, "参数不合法！");
        }
        int rows = itemStockMapper.decreaseStock(itemId, amount);
        return rows > 0;
    }

    @Override
    public boolean decreaseStockInCache(int itemId, int amount) {
        if (itemId <= 0 || amount <= 0) {
            throw new BusinessException(PARAMETER_ERROR, "参数不合法！");
        }
        String key = "item:stock:" + itemId;
        long stock = redisTemplate.opsForValue().decrement(key, amount);
        if (stock < 0) {
            // 回补库存
            this.increaseStockInCache(itemId, amount);
            logger.debug("回补库存，itemId={}, amount={}", itemId, amount);
        } else if (stock == 0) {
            // 售罄标识
            redisTemplate.opsForValue().set("item:stock:over:" + itemId, 1);
            logger.debug("售罄标识，itemId={}", itemId);
        }
        return stock >= 0;
    }

    @Override
    public boolean increaseStockInCache(int itemId, int amount) {
        if (itemId <= 0 || amount <= 0) {
            throw new BusinessException(PARAMETER_ERROR, "参数不合法！");
        }
        String key = "item:stock:" + itemId;
        redisTemplate.opsForValue().increment(key, amount);
        return true;
    }

    @Override
    public ItemStockLog createItemStockLog(int itemId, int amount) {
        if (itemId <= 0 || amount <= 0) {
            throw new BusinessException(PARAMETER_ERROR, "参数不合法！");
        }
        ItemStockLog itemStockLog = new ItemStockLog();
        itemStockLog.setId(UUID.randomUUID().toString().replace("-", ""));
        itemStockLog.setItemId(itemId);
        itemStockLog.setAmount(amount);
        itemStockLog.setStatus(0);
        return itemStockLogMapper.insert(itemStockLog) > 0 ? itemStockLog : null;
    }

    @Override
    public void updateItemStockLogStatus(String id, int status) {
        ItemStockLog itemStockLog = itemStockLogMapper.selectByPrimaryKey(id);
        itemStockLog.setStatus(status);
        itemStockLogMapper.updateByPrimaryKey(itemStockLog);
    }

    @Override
    public ItemStockLog findItemStockLogById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new BusinessException(PARAMETER_ERROR, "参数不合法！");
        }
        return itemStockLogMapper.selectByPrimaryKey(id);
    }
}
