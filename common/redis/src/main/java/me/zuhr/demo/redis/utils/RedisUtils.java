package me.zuhr.demo.redis.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.zuhr.demo.redis.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类,由{@link RedisConfig} 中的@bean注解实例化,默认为单例模式
 * <p>
 * 约定下,所有的key都为string
 * <p>
 *
 * @param <V>  value
 *             // * @param <T> 序列化的实现方式
 * @param <HM> hash的map
 * @param <HV> 哈希表中指定 key 的值
 * @author zurun
 * @date 2018/2/10 00:37:06
 */
@Component
public class RedisUtils<V, HM, HV> {
    private final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 使用场景为key,val都是字符的情况下
     */
    @Qualifier("stringRedisTemplate")
    public final RedisTemplate<String, String> stringRedisTemplate;
    /**
     * 原生序列化
     * 优点:高效,记录了对象与属性的类型,
     * 缺点:序列化的结果字符串是最长,吃内存/硬盘,
     * 序列化的对象必须实现Serializable接口
     */
    @Qualifier("jdkRedisTemplate")
    public final RedisTemplate jdkRedisTemplate;
    /**
     * 对象存为json
     * 优点: 序列化的结果清晰，容易阅读，而且存储字节少，速度快
     * 序列化的对象不用实现Serializable接口
     * <p>
     * 缺点:不记录对象与属性的类型,需要类型转换
     * value中如果同一个对象有多个,反序列化后是多个不同的对象(因为序列化的是值不是引用)
     */
    @Qualifier("jackson2RedisTemplate")
    public final RedisTemplate jackson2RedisTemplate;
    /**
     * 默认序列化的实现方式
     */
    private final RedisTemplate defaultRedisTemplate;

    /**
     * 不同序列化实现方式对redis的操作,一般在提供的常用方法无法满足的情况下使用
     */
    public final Jackson2 jackson2;
    public final Jdk jdk;

    /**
     * 推荐对构造函数进行注解
     * Java类会先执行构造方法，然后再给注解了@Autowired 的对象注入值
     *
     * @param stringRedisTemplate
     * @param jdkRedisTemplate
     * @param jackson2RedisTemplate
     */
    @Autowired
    public RedisUtils(@Qualifier("stringRedisTemplate") RedisTemplate stringRedisTemplate,
                      @Qualifier("jdkRedisTemplate") RedisTemplate jdkRedisTemplate,
                      @Qualifier("jackson2RedisTemplate") RedisTemplate jackson2RedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.jdkRedisTemplate = jdkRedisTemplate;
        this.jackson2RedisTemplate = jackson2RedisTemplate;
        this.defaultRedisTemplate = jdkRedisTemplate;

        jackson2 = new Jackson2();
        jdk = new Jdk();

//        redisTemplateUtil =  RedisTemplateUtil.INSTANCE;
    }


    /** ----------常用的操作----------- */

    /**
     * key过期时间,默认为秒
     *
     * @param key
     * @param second
     * @return
     */
    public Boolean expire(String key, Long second) {
        return expire(key, second, TimeUnit.SECONDS);
    }

    @SuppressWarnings("unchecked")
    public Boolean expire(String key, Long second, TimeUnit timeUnit) {
        return defaultRedisTemplate.expire(key, second, timeUnit);
    }

    /**
     * 删除指定key
     *
     * @param key
     */
    @SuppressWarnings("unchecked")
    public void delete(String key) {
        defaultRedisTemplate.delete(key);
    }

    public void del(String key) {
        delete(key);
    }


    /**
     * 默认value为基本类型
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public V get(String key) {
        //TODO-zurun
        return (V) defaultRedisTemplate.opsForValue().get(key);
    }

    /**
     * value为对象
     *
     * @param key
     * @param c   返回指定类型的对象
     * @return
     */
    public HM get(String key, Class<HM> c) {
        return jackson2.entries(key, c);
    }

    /**
     * value为对象,返回map
     *
     * @param key
     * @return
     */
    public Map getMap(String key) {
        return jackson2.entries(key);
    }


    @SuppressWarnings("unchecked")
    public void set(String key, V value) {
        defaultRedisTemplate.opsForValue().set(key, value);
    }

    @SuppressWarnings("unchecked")
    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void setValue(String key, Object value) {
        defaultRedisTemplate.opsForValue().set(key, value);
    }

    public void setHashMap(String key, Map map) {
        defaultRedisTemplate.opsForHash().putAll(key, map);
    }

    public void setHashMap(String key, V v) {
        defaultRedisTemplate.opsForHash().putAll(key, (Map<?, ?>) v);
    }

    public void setHashValue(String key, String hashKey, V v) {
        defaultRedisTemplate.opsForHash().put(key, hashKey, v);
    }

    public Map getHashMap(String key) {
        return defaultRedisTemplate.opsForHash().entries(key);
    }

    @SuppressWarnings("unchecked")
    public V getHashValue(String key, String hashKey) {
        return (V) defaultRedisTemplate.opsForHash().get(key, hashKey);

    }


    private abstract class AbstractTemplate<V> {
        RedisTemplate redisTemplate;

        AbstractTemplate(RedisTemplate redisTemplate) {
            this.redisTemplate = redisTemplate;
        }

        /**
         * key过期时间,默认为秒
         *
         * @param key
         * @param second
         * @return
         */
        public Boolean expire(String key, Long second) {
            return expire(key, second, TimeUnit.SECONDS);
        }

        @SuppressWarnings("unchecked")
        public Boolean expire(String key, Long second, TimeUnit timeUnit) {
            return RedisUtils.this.expire(key, second, timeUnit);
        }


        @SuppressWarnings("unchecked")
        public V get(String key) {
            return (V) redisTemplate.opsForValue().get(key);
        }

        @SuppressWarnings("unchecked")
        public void set(String key, V v) {
            redisTemplate.opsForValue().set(key, v);
        }

        /**
         * 加入超时,默认为秒
         *
         * @param key
         * @param v
         * @param second
         */
        @SuppressWarnings("unchecked")
        public void set(String key, V v, Long second) {
            set(key, v, second, TimeUnit.SECONDS);
        }

        /**
         * @param key
         * @param v
         * @param second
         */
        @SuppressWarnings("unchecked")
        public void set(String key, V v, Long second, TimeUnit timeUnit) {
            redisTemplate.opsForValue().set(key, v, second, TimeUnit.SECONDS);
        }

        /**
         * 删除指定key
         *
         * @param key
         */
        @SuppressWarnings("unchecked")
        public void delete(String key) {
            RedisUtils.this.delete(key);
        }

        public void del(String key) {
            delete(key);
        }


        public void hashSet(String key, V v) {
            hashSet(key, objectMapper.convertValue(v, Map.class));
        }

        @SuppressWarnings("unchecked")
        public void hashSet(String key, Map map) {
            redisTemplate.opsForHash().putAll(key, map);
        }

        @SuppressWarnings("unchecked")
        public void hashSet(String key, Map map, Long second) {
            hashSet(key, map);
            expire(key, second);
        }

        @SuppressWarnings("unchecked")
        public void hashSet(String key, String hashKey, Object obj) {
            redisTemplate.opsForHash().put(key, hashKey, obj);
        }

        @SuppressWarnings("unchecked")
        public Map entries(String key) {
            return redisTemplate.opsForHash().entries(key);
        }

        public HM entries(String key, Class<HM> c) {
            return objectMapper.convertValue(entries(key), c);
        }

        @SuppressWarnings("unchecked")
        public HV hashGet(String key, String hashKey, Class<HV> c) {
            return objectMapper.convertValue(redisTemplate.opsForHash().get(key, hashKey), c);
        }

        @SuppressWarnings("unchecked")
        public V hashGet(String key, String hashKey) {
            return (V) redisTemplate.opsForHash().get(key, hashKey);
        }
    }

    public class PrimitiveType extends AbstractTemplate<V> {


        PrimitiveType(RedisTemplate redisTemplate) {
            super(redisTemplate);
        }

        @Override
        public void set(String key, V v) {
//            defaultRedisTemplate.opsForValue().set(key, v);
        }

    }

    public class Jackson2 extends AbstractTemplate<V> {
        Jackson2() {
            super(jackson2RedisTemplate);
        }

        @SuppressWarnings("unchecked")
        public V get(String key, Class<V> c) throws IOException {
            V v = (V) jackson2RedisTemplate.opsForValue().get(key);
            return objectMapper.readValue((String) v, c);
        }

    }

    public class Jdk extends AbstractTemplate<V> {
        Jdk() {
            super(jdkRedisTemplate);
        }
    }


    private enum RedisTemplateUtil {
        INSTANCE;

        RedisTemplateUtil() {

        }

        /**
         * 默认序列化实现方式
         */
//        private final RedisSerializer defaultRedisSerializer = jdkSerializationRedisSerializer;


//        private RedisTemplate<String, V> redisTemplate;
        /**
         * 正在使用的序列化方式
         */
//        private T redisSerializer;


    }
}
