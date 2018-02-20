package me.zuhr.demo.redis.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.zuhr.demo.redis.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * redis工具类,由{@link RedisConfig} 中的@bean注解实例化,默认为单例模式
 * <p>
 * 约定下,所有的key都为string
 *
// * @param <T> 序列化的实现方式
 * @param <V> value
 * @author zurun
 * @date 2018/2/10 00:37:06
 */
@Component
public class RedisUtils<V> {
    private final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 使用场景为key,val都是字符的情况下
     */
    @Qualifier("stringRedisTemplate")
    private final RedisTemplate stringRedisTemplate;
    /**
     * 原生序列化
     * 优点:高效,记录了对象与属性的类型,
     * 缺点:序列化的结果字符串是最长,吃内存/硬盘,
     *      序列化的对象必须实现Serializable接口
     */
    @Qualifier("jdkRedisTemplate")
    private final RedisTemplate jdkRedisTemplate;
    /**
     * 对象存为json
     * 优点: 序列化的结果清晰，容易阅读，而且存储字节少，速度快
     *      序列化的对象不用实现Serializable接口
     *
     * 缺点:不记录对象与属性的类型,需要类型转换
     *      value中如果同一个对象有多个,反序列化后是多个不同的对象(因为序列化的是值不是引用)
     *
     */
    @Qualifier("jackson2RedisTemplate")
    private final RedisTemplate jackson2RedisTemplate;
    /**
     * 默认序列化的实现方式
     */
    private final RedisTemplate defaultRedisTemplate;

    public final JsonType jsonType=new JsonType();

    /**
     * 推荐对构造函数进行注解
     * 因为Java类会先执行构造方法，然后再给注解了@Autowired 的对象注入值
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


//        redisTemplateUtil =  RedisTemplateUtil.INSTANCE;
    }

    public V get(String key) {
        return getRedisTemplate().opsForValue().get(key);
    }

    public  V get(String key,Class<V> c) throws IOException {
        return getRedisTemplate().opsForValue().get(key);

//        V v= getStringRedisTemplate().opsForValue().get(key);
//
//        return objectMapper.readValue((String) v,c);
    }

    public void set(String key, V value) {
        getRedisTemplate().opsForValue().set(key, value);
    }

    @SuppressWarnings("unchecked")
    public String getValue(String key) {
        return (String) getStringRedisTemplate().opsForValue().get(key);
    }

    public void setValue(String key, Object value) {
        getStringRedisTemplate().opsForValue().set(key, (V) value);
    }

    public void setHashMap(String key,Map map){
        getRedisTemplate().opsForHash().putAll(key,map);
    }
    public void setHashMap(String key,V v){
        getRedisTemplate().opsForHash().putAll(key, (Map<?, ?>) v);
    }
    public void setHashValue(String key,String hashKey,V v){
        getRedisTemplate().opsForHash().put(key,hashKey,v);
    }

    public Map getHashMap(String key) {
        return getRedisTemplate().opsForHash().entries(key);
    }

    @SuppressWarnings("unchecked")
    public V getHashValue(String key, String hashKey) {
        return (V) getRedisTemplate().opsForHash().get(key, hashKey);

    }


    public JsonType getJsonType() {
        return new JsonType();
    }

    private RedisTemplate<String, V> getRedisTemplate() {
        return defaultRedisTemplate;
    }
    private RedisTemplate<String, V> getStringRedisTemplate() {
        return stringRedisTemplate;
    }
    private RedisTemplate<String, V> getJdkRedisTemplate() {
        return jdkRedisTemplate;
    }
    private RedisTemplate<String, V> getJackson2RedisTemplate() {
        return jackson2RedisTemplate;
    }


    private class PrimitiveType{
        private PrimitiveType(){}

        private void set(String key,V v){
            getRedisTemplate().opsForValue().set(key, v);
        }
    }
    private class JsonType{
        public JsonType(){}

        public void set(String key,V v){
            getJackson2RedisTemplate().opsForValue().set(key, v);
        }
        public V get(String key,Class<V> c) throws IOException {
            V v= getStringRedisTemplate().opsForValue().get(key);
//
        return objectMapper.readValue((String) v,c);
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
