package me.zuhr.demo.redis.utils;

import me.zuhr.demo.utils.AnnotationUtils;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;

/**
 * redis工具类
 * <p>
 * 约定下,所有的key都为string
 *
 * @author zurun
 * @date 2018/2/10 00:37:06
 */
public class RedisUtils<V> {

    private JedisConnectionFactory connectionFactory;
    private RedisTemplateUtil redisTemplateUtil;

    public RedisUtils(JedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        RedisTemplate redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        redisTemplateUtil = new RedisTemplateUtil(redisTemplate);
    }

    public V get(String key) {
        return getRedisTemplate().opsForValue().get(key);
    }

    public void set(String key, V value) {
        getRedisTemplate().opsForValue().set(key, value);
    }

    public String getValue(String key) {
        return AnnotationUtils.cast(getRedisTemplate(StringRedisSerializer.class).opsForValue().get(key));
    }

    public void setValue(String key, String value) {
        getRedisTemplate(StringRedisSerializer.class).opsForValue().set(key, (V) value);
//        redisTemplate.getConnectionFactory().getConnection().set(objectToByte(key), objectToByte(value));
    }

//    public V getBy

    public Map opsForHashMap(String key) {
        return getRedisTemplate().opsForHash().entries(key);
    }

    public V opsForHashValue(String key, Object hashKey) {
        return AnnotationUtils.cast(getRedisTemplate().opsForHash().get(key, hashKey));

    }


    private <T extends RedisSerializer> RedisTemplate<String, V> getRedisTemplate(Class<T> c) {
        return redisTemplateUtil.getRedisTemplate(c);
    }

    private RedisTemplate<String, V> getRedisTemplate() {
        return redisTemplateUtil.getRedisTemplate();
    }


    private class RedisTemplateUtil<T> {
        private final Class defaultRedisSerializer = JdkSerializationRedisSerializer.class;
        private final StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        private final JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();

        private RedisTemplate<String, V> redisTemplate;
        private RedisSerializer redisSerializer;

        public RedisTemplateUtil(RedisTemplate redisTemplate) {
            // 设置key的序列化规则
            redisTemplate.setKeySerializer(stringRedisSerializer);
            redisTemplate.setHashKeySerializer(stringRedisSerializer);

            this.redisTemplate = redisTemplate;
            this.redisSerializer = redisTemplate.getDefaultSerializer() == null ? getDefaultBean() : redisTemplate.getDefaultSerializer();
        }

        public <T extends RedisSerializer> RedisTemplate<String, V> getRedisTemplate(Class<T> c) {
            if (c == null) {
                c = defaultRedisSerializer;
            } else if (c.equals(redisSerializer.getClass())) {
                return redisTemplate;
            }
            redisSerializer = getBean(c);
//        new Jackson2JsonRedisSerializer(Object.class)

            return changeDefaultSerializer();
        }

        private RedisSerializer getDefaultBean() {
            return getBean(JdkSerializationRedisSerializer.class);
        }

        private <T extends RedisSerializer> RedisSerializer getBean(Class<T> c) {
            try {
                return c.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return jdkSerializationRedisSerializer;
        }

        /**
         * 默认jdkSerializer实现序列化
         *
         * @return
         */
        public RedisTemplate<String, V> getRedisTemplate() {
            if (JdkSerializationRedisSerializer.class.equals(redisSerializer.getClass())) {
                return redisTemplate;
            }
            return getRedisTemplate(null);
        }

        private RedisTemplate changeDefaultSerializer() {
            redisTemplate.setDefaultSerializer(redisSerializer);
            redisTemplate.setValueSerializer(redisSerializer);
            // 需要执行下,否则默认序列化类型不生效
            redisTemplate.afterPropertiesSet();

            return redisTemplate;
        }


    }
}
