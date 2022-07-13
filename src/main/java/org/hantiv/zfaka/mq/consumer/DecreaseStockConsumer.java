package org.hantiv.zfaka.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.hantiv.zfaka.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Zhikun Han
 * @Date Created in 17:30 2022/7/13
 * @Description:
 */
@Service
@RocketMQMessageListener(topic = "seckill",
        consumerGroup = "seckill_stock", selectorExpression = "decrease_stock")
public class DecreaseStockConsumer implements RocketMQListener<String> {
    private Logger logger = LoggerFactory.getLogger(DecreaseStockConsumer.class);

    @Autowired
    private ItemService itemService;

    @Override
    public void onMessage(String message) {
        JSONObject param = JSONObject.parseObject(message);
        int itemId = (int) param.get("itemId");
        int amount = (int) param.get("amount");

        try {
            itemService.decreaseStock(itemId, amount);
            logger.debug("最终扣减库存完成 [" + param.get("itemStockLogId") + "]");
        } catch (Exception e) {
            logger.error("从DB扣减库存失败", e);
        }
    }
}
