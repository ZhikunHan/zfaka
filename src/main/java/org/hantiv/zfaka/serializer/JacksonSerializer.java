package org.hantiv.zfaka.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * @Author Zhikun Han
 * @Date Created in 9:59 2022/7/13
 * @Description:
 */
public class JacksonSerializer implements RedisSerializer<Object> {
    public static final Charset UTF8 = Charset.forName("UTF-8");
    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if(o == null) {
            return null;
        }
        String json = JSON.toJSONString(o, SerializerFeature.WriteClassName);
        return json.getBytes(UTF8);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String json = new String(bytes, UTF8);
        return JSON.parseObject(json, Object.class, Feature.SupportAutoType);
    }
}
