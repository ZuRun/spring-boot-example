package me.zuhr.demo.redis.utils;

import me.zuhr.demo.utils.AnnotationUtils;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;

/**
 * redis工具类,由{@link me.zuhr.demo.redis.config.RedisConfig} 中的@bean注解实例化,默认为单例模式
 * <p>
 * 约定下,所有的key都为string
 *
 * @param <T> 序列化的实现方式
 * @param <V> value
 * @author zurun
 * @date 2018/2/10 00:37:06
 */
public class RedisUtils<T extends RedisSerializer, V> {

    private final StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    private final JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
    private final Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    private final RedisSerializer defaultRedisSerializer = jdkSerializationRedisSerializer;

    /**
     * 默认序列化的实现方式
     */
    private final Class<T> defaultRSClass = (Class<T>) defaultRedisSerializer.getClass();
    private final Class<T> jdkRSClass = (Class<T>) jdkSerializationRedisSerializer.getClass();
    private final Class<T> stringRSClass = (Class<T>) stringRedisSerializer.getClass();
    private final Class<T> jsonRSClass = (Class<T>) jackson2JsonRedisSerializer.getClass();

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

    @SuppressWarnings("unchecked")
    public String getValue(String key) {
        return (String) getRedisTemplate(stringRSClass).opsForValue().get(key);
    }

    public void setValue(String key, String value) {
        getRedisTemplate(stringRSClass).opsForValue().set(key, (V) value);
    }


    public Map opsForHashMap(String key) {
        return getRedisTemplate().opsForHash().entries(key);
    }

    @SuppressWarnings("unchecked")
    public V opsForHashValue(String key, Object hashKey) {
        return (V) getRedisTemplate().opsForHash().get(key, hashKey);

    }


    private RedisTemplate<String, V> getRedisTemplate(Class<T> c) {
        return redisTemplateUtil.getRedisTemplate(c);
    }

    private RedisTemplate<String, V> getRedisTemplate() {
        return redisTemplateUtil.getRedisTemplate();
    }


    private class RedisTemplateUtil {


        private RedisTemplate<String, V> redisTemplate;
        /**
         * 正在使用的序列化方式
         */
        private T redisSerializer;

        public RedisTemplateUtil(RedisTemplate redisTemplate) {
            // 设置key的序列化规则,约定为string
            redisTemplate.setKeySerializer(stringRedisSerializer);
            redisTemplate.setHashKeySerializer(stringRedisSerializer);

            this.redisTemplate = redisTemplate;
            this.redisSerializer = getDefaultBean();
            // 赋值序列化方式
            changeDefaultSerializer();
        }


        private T getDefaultBean() {
            return getBean(defaultRSClass);
        }

        private T getBean(Class<T> c) {
            try {
                return c.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return (T) defaultRedisSerializer;
        }

        /**
         * 指定序列化方式,并返回RedisTemplate
         *
         * @param c 序列化方式,为null表示直接返回当前的序列化方式
         * @return
         */
        public RedisTemplate<String, V> getRedisTemplate(Class<T> c) {
            // 为空或者序列化方式与现有序列化方式一样,直接返回RedisTemplate
            if (c == null || c.equals(redisSerializer.getClass())) {
                return redisTemplate;
            }
            redisSerializer = getBean(c);

            return changeDefaultSerializer();
        }


        /**
         * 默认Serializer实现序列化,并返回RedisTemplate
         *
         * @return
         */
        public RedisTemplate<String, V> getRedisTemplate() {
            if (defaultRSClass.equals(redisSerializer.getClass())) {
                return redisTemplate;
            }
            return getRedisTemplate(defaultRSClass);
        }

        /**
         * 序列化方式更改
         * RedisUtils中调用get方法和getValue方法都会导致序列化方式不一样
         *
         * @return
         */
        private RedisTemplate changeDefaultSerializer() {
            redisTemplate.setDefaultSerializer(redisSerializer);
            redisTemplate.setValueSerializer(redisSerializer);
            // 需要执行下,否则默认序列化类型不生效
            redisTemplate.afterPropertiesSet();

            return redisTemplate;
        }


    }
}
