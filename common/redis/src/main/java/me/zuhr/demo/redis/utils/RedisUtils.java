package me.zuhr.demo.redis.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.zuhr.demo.redis.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类,由{@link RedisConfig} 中的@bean注解实例化,默认为单例模式
 * <p>
 * 约定下,所有的key和hashKey都为string
 * <p>
 * 不同使用场景下使用不同方法:
 * 1. 保存的是对象,且此对象的获取一般全部获取所有属性
 * - 保存:set(String key, V value)方法
 * 2. 保存的是对象,但是:
 * a.此对象经常需要修改部分字段;
 * b.此对象有多并发的使用场景
 * - 保存:
 * a.{@link RedisUtils#hashSet<V>(String key, String hashKey, V v)}
 * b.{@link RedisUtils#hashSet<V>(String key, V v)}
 *
 *
 * @author zurun
 * @date 2018/2/10 00:37:06
 */
@Component
public class RedisUtils {
    private final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 使用场景为key,val都是字符串的情况下
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
    public final PrimitiveType primitive;

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
        primitive = new PrimitiveType();
//        redisTemplateUtil =  RedisTemplateUtil.INSTANCE;
    }


    /** ----------常用的操作----------- */

    /**
     * 为给定 key 设置过期时间,默认为秒
     *
     * @param key
     * @param second
     * @return
     */
    public Boolean expire(String key, Long second) {
        return expire(key, second, TimeUnit.SECONDS);
    }

    /**
     * 为给定 key 设置过期时间。
     *
     * @param key
     * @param timeout
     * @param timeUnit
     * @return
     */
    public Boolean expire(String key, Long timeout, TimeUnit timeUnit) {
        return jdk.expire(key, timeout, timeUnit);
    }

    /**
     * 获取过期时间,单位为秒
     *
     * @param key
     * @return
     */
    public Long getExpireTime(String key) {
        return jdk.getExpireTime(key);
    }

    /**
     * 获取过期时间,单位为秒
     *
     * @param key
     * @param timeUnit
     * @return
     */
    public Long getExpireTime(String key, TimeUnit timeUnit) {
        return jdk.getExpireTime(key, timeUnit);
    }


    /**
     * 删除指定key
     *
     * @param key
     */
    @SuppressWarnings("unchecked")
    public void delete(String key) {
        jdk.delete(key);
    }

    /**
     * 检查给定 key 是否存在。
     *
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        return jdk.hasKey(key);
    }


    /**
     * 常规获取基本数据类型(int,string,long,double,boolean)
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return (String) primitive.get(key);
    }

    public Integer getInt(String key) {
        return (Integer) jackson2.get(key);
    }

    public Long getLong(String key) {
        return (Long) jackson2.get(key);
    }

    public Boolean getBoolean(String key) {
        return (Boolean) jackson2.get(key);
    }

    public Double getDouble(String key) {
        return (Double) jackson2.get(key);
    }

    /**
     * 如果value是对象,此处返回的是map
     * list返回list
     * 基本类型则返回基本类型
     *
     * @param key
     * @return
     */
    public <V> V get(String key) {
        return (V) jackson2.get(key);
    }

    /**
     * value为对象,且 返回指定类型的对象
     *
     * @param key
     * @param c   返回指定类型的对象
     * @return
     */
    public <HM> HM get(String key, Class<HM> c) {
        return (HM) jackson2.get(key, c);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 seconds (以秒为单位)。
     * 貌似是SETEX key seconds value命令,不太确定
     *
     * @param key
     * @param value
     * @param second
     */
    public <V> void set(String key, V value, Long... second) {
        jackson2.set(key, value, second);
    }

    /**
     * 将值 value 关联到 key ，并设置 key 的过期时间
     * 貌似是SETEX key seconds value命令,不太确定
     *
     * @param key
     * @param value
     * @param second
     * @param timeUnit
     */
    public <V> void set(String key, V value, Long second, TimeUnit timeUnit) {
        jackson2.set(key, value, second, timeUnit);
    }

    @SuppressWarnings("unchecked")
    public void set(String key, String value) {
        primitive.set(key, value);
    }

    @SuppressWarnings("unchecked")
    public void set(String key, Integer value) {
        jackson2.set(key, value);
    }

    @SuppressWarnings("unchecked")
    public void set(String key, Long value) {
        jackson2.set(key, value);
    }

    @SuppressWarnings("unchecked")
    public void set(String key, Double value) {
        jackson2.set(key, value);
    }

    @SuppressWarnings("unchecked")
    public void set(String key, Boolean value) {
        jackson2.set(key, value);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
     *
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getAndSet(String key, String value) {
        return (String) primitive.getAndSet(key, value);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
     *
     * @param key
     * @param v
     * @return
     */
    public <V> V getAndSet(String key, V v) {
        return (V) jackson2.getAndSet(key, v);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     * 此方法不具有原子性
     *
     * @param key
     * @param v
     * @param second
     * @return
     */
    public <V> V getAndSet(String key, V v, Long second) {
        return (V) jackson2.getAndSet(key, v, second);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     * 此方法不具有原子性
     *
     * @param key
     * @param v
     * @param second
     * @param timeUnit
     * @return
     */
    public <V> V getAndSet(String key, V v, Long second, TimeUnit timeUnit) {
        return (V) jackson2.getAndSet(key, v, second, timeUnit);
    }

    /**
     * 只有在 key 不存在时设置 key 的值。
     *
     * @param key
     * @param v
     * @return
     */
    public <V> Boolean setNx(String key, V v) {
        return jackson2.setNx(key, v);
    }

    /**
     * 只有在 key 不存在时设置 key 的值。
     *
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public Boolean setNx(String key, String value) {
        return primitive.setNx(key, value);
    }

    /**      **   HASH 部分   **       **/

    /**
     * 将对象保存到redis中
     *
     * @param key
     * @param v   对象
     */
    public <V> void hashSet(String key, V v) {
        jackson2.hashSet(key, v);
    }

    /**
     * 将对象保存到redis中,并加入过期时间
     *
     * @param key
     * @param v
     * @param second
     */
    public <V> void hashSet(String key, V v, Long second) {
        jackson2.hashSet(key, v, second);
    }

    public <V> void hashSet(String key, V v, Long timeout, TimeUnit timeUnit) {
        jackson2.hashSet(key, v, timeout, timeUnit);
    }

    /**
     * 将map保存到redis中
     *
     * @param key
     * @param map
     */
    public void hashSet(String key, Map map) {
        jackson2.hashSet(key, map);
    }

    /**
     * 将map保存到redis中,并加入过期时间,默认过期时间单位为秒
     *
     * @param key
     * @param map
     * @param second
     */
    public void hashSet(String key, Map map, Long second) {
        jackson2.hashSet(key, map, second, TimeUnit.SECONDS);
    }

    /**
     * 将map保存到redis中,并加入过期时间
     *
     * @param key
     * @param map
     * @param timeout
     * @param timeUnit
     */
    public void hashSet(String key, Map map, Long timeout, TimeUnit timeUnit) {
        jackson2.hashSet(key, map, timeout, timeUnit);
    }

    /**
     * 将哈希表 key 中的字段 field 的值设为 value 。
     * HSET key field value
     *
     * @param key
     * @param hashKey
     * @param obj
     */
    public void hashSet(String key, String hashKey, Object obj) {
        jackson2.hashSet(key, hashKey, obj);
    }

    /**
     * 只有在字段 field 不存在时，设置哈希表字段的值。
     * HSETNX key field value
     *
     * @param key
     * @param hashKey
     * @param v       true表示是设置成功,之前没有这个hashKey
     */
    public <V> Boolean hashSetNx(String key, String hashKey, V v) {
        return jackson2.hashSetNx(key, hashKey, v);
    }

    /**
     * 获取存储在哈希表中指定字段的值。
     * HGET key field
     *
     * @param key
     * @param hashKey
     * @return
     */
    public <V> V hashGet(String key, String hashKey) {
        return (V) jackson2.hashGet(key, hashKey);
    }

    public <HV> HV hashGet(String key, String hashKey, Class<HV> c) {
        return (HV) jackson2.hashGet(key, hashKey, c);
    }

    /**
     * 根据key获取map.
     * 不是太建议使用.有这种使用场景的建议直接对象转为json存起来:jackson2.set(key,v)
     *
     * @param key
     * @return
     */
    @Deprecated
    public Map hashGet(String key) {
        return jackson2.hashGet(key);
    }

    /**
     * 获取对象,返回指定类型的对象
     * 不是太建议使用.有这种使用场景的建议直接对象转为json存起来:jackson2.set(key,v)
     *
     * @param key
     * @param c
     * @return
     */
    @Deprecated
    public <HM> HM hashGet(String key, Class<HM> c) {
        return (HM) jackson2.hashGet(key, c);
    }

    /**
     * 生产环境不建议使用
     * 获取指定key的所有hashkey.  Get key set (fields) of hash at {@code key}.
     * HKEYS key
     *
     * @param key
     * @return
     */
    @Deprecated
    public Set hashKeys(String key) {
        return jackson2.hashKeys(key);
    }

    /**
     * 获取指定key的所有value的集合
     * HVALS key
     *
     * @param key
     * @return
     */
    public List hashValues(String key) {
        return jackson2.hashValues(key);
    }

    /**
     * 获取指定key的hash数量
     * HLEN key
     *
     * @param key
     * @return
     */
    public Long hashSize(String key) {
        return jackson2.hashSize(key);
    }

    /**
     * 删除一个或多个哈希表字段
     * HDEL key field1 [field2]
     *
     * @param key
     * @param hashKeys
     * @return
     */
    public Long delete(String key, String... hashKeys) {
        return jackson2.delete(key, hashKeys);
    }


    /**
     * 查看哈希表 key 中，指定的字段是否存在。
     * HEXISTS key field
     *
     * @param key
     * @param hashKey
     */
    public Boolean hasKey(String key, String hashKey) {
        return jackson2.hasKey(key, hashKey);
    }

    private abstract class AbstractTemplate<V> {
        public RedisTemplate redisTemplate;
        HashOperations hashOperations;
        ValueOperations<String, V> valueOperations;

        AbstractTemplate(RedisTemplate redisTemplate) {
            this.redisTemplate = redisTemplate;
            hashOperations = redisTemplate.opsForHash();
            valueOperations = redisTemplate.opsForValue();
        }

        /**
         * 为给定 key 设置过期时间,默认为秒
         *
         * @param key
         * @param second
         * @return
         */
        public Boolean expire(String key, Long second) {
            return expire(key, second, TimeUnit.SECONDS);
        }

        @SuppressWarnings("unchecked")
        public Boolean expire(String key, Long timeout, TimeUnit timeUnit) {
            return redisTemplate.expire(key, timeout, timeUnit);
        }

        /**
         * 获取过期时间,默认为秒
         *
         * @param key
         * @return
         */
        public Long getExpireTime(String key) {
            return getExpireTime(key, TimeUnit.SECONDS);
        }

        /**
         * 获取过期时间
         *
         * @param key
         * @param timeUnit
         * @return
         */
        @SuppressWarnings("unchecked")
        public Long getExpireTime(String key, TimeUnit timeUnit) {
            return redisTemplate.getExpire(key, timeUnit);
        }

        @SuppressWarnings("unchecked")
        public V get(String key) {
            return valueOperations.get(key);
        }

        /**
         * 检查给定 key 是否存在。
         * EXISTS key
         *
         * @param key
         * @return
         */
        @SuppressWarnings("unchecked")
        public Boolean hasKey(String key) {
            return redisTemplate.hasKey(key);
        }

        @SuppressWarnings("unchecked")
        public void set(String key, V v) {
            valueOperations.set(key, v);
        }

        /**
         * 超时选填,默认为秒
         * 将值 value 关联到 key ，并将 key 的过期时间设为 seconds (以秒为单位)。
         *
         * @param key
         * @param v
         * @param second
         */
        @SuppressWarnings("unchecked")
        public void set(String key, V v, Long... second) {
            if (second.length == 0) {
                valueOperations.set(key, v);
            } else {
                set(key, v, second[0], TimeUnit.SECONDS);
            }
        }

        /**
         * 将值 value 关联到 key ，并设置 key 的过期时间
         * 貌似是SETEX key seconds value命令,不太确定
         *
         * @param key
         * @param v
         * @param second
         */
        @SuppressWarnings("unchecked")
        public void set(String key, V v, Long second, TimeUnit timeUnit) {
            valueOperations.set(key, v, second, timeUnit);
        }

        /**
         * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
         * GETSET key value
         *
         * @param key
         * @param v
         * @return
         */
        @SuppressWarnings("unchecked")
        public V getAndSet(String key, V v) {
            return (V) valueOperations.getAndSet(key, v);
        }

        /**
         * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
         * 此方法不具有原子性
         *
         * @param key
         * @param v
         * @param second
         * @return
         */
        public V getAndSet(String key, V v, Long second) {
            return getAndSet(key, v, second, TimeUnit.SECONDS);
        }

        /**
         * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
         * 此方法不具有原子性
         *
         * @param key
         * @param v
         * @param timeout
         * @param timeUnit
         * @return
         */
        @SuppressWarnings("unchecked")
        public V getAndSet(String key, V v, Long timeout, TimeUnit timeUnit) {
            Object obj = valueOperations.getAndSet(key, v);
            expire(key, timeout, timeUnit);
            return (V) obj;
        }

        /**
         * 只有在 key 不存在时设置 key 的值。
         * SETNX key value
         *
         * @param key
         * @param v
         * @return true表示设置成功, 之前没有这个key
         */
        @SuppressWarnings("unchecked")
        public Boolean setNx(String key, V v) {
            return valueOperations.setIfAbsent(key, v);
        }

        /**
         * 删除指定key
         * DEL key
         *
         * @param key
         */
        @SuppressWarnings("unchecked")
        public void delete(String key) {
            redisTemplate.delete(key);
        }


        /**      **   HASH 部分   **       **/


        /**
         * 将对象保存到redis中
         *
         * @param key
         * @param v   对象
         */
        public void hashSet(String key, V v) {
            hashSet(key, objectMapper.convertValue(v, Map.class));
        }

        /**
         * 将对象保存到redis中,并加入过期时间
         *
         * @param key
         * @param v
         * @param second
         */
        public void hashSet(String key, V v, Long second) {
            hashSet(key, v, second, TimeUnit.SECONDS);
        }

        public void hashSet(String key, V v, Long timeout, TimeUnit timeUnit) {
            hashSet(key, objectMapper.convertValue(v, Map.class), timeout, timeUnit);
        }

        /**
         * 将map保存到redis中
         *
         * @param key
         * @param map
         */
        @SuppressWarnings("unchecked")
        public void hashSet(String key, Map map) {
            hashOperations.putAll(key, map);
        }

        /**
         * 将map保存到redis中,并加入过期时间,默认过期时间单位为秒
         *
         * @param key
         * @param map
         * @param second
         */
        public void hashSet(String key, Map map, Long second) {
            hashSet(key, map, second, TimeUnit.SECONDS);
        }

        /**
         * 将map保存到redis中,并加入过期时间
         *
         * @param key
         * @param map
         * @param timeout
         * @param timeUnit
         */
        public void hashSet(String key, Map map, Long timeout, TimeUnit timeUnit) {
            hashSet(key, map);
            expire(key, timeout, timeUnit);
        }

        /**
         * 将哈希表 key 中的字段 field 的值设为 value 。
         * HSET key field value
         *
         * @param key
         * @param hashKey
         * @param obj
         */
        @SuppressWarnings("unchecked")
        public void hashSet(String key, String hashKey, Object obj) {
            hashOperations.put(key, hashKey, obj);
        }

        /**
         * 只有在字段 field 不存在时，设置哈希表字段的值。
         * HSETNX key field value
         *
         * @param key
         * @param hashKey
         * @param v       true表示是设置成功,之前没有这个hashKey
         */
        @SuppressWarnings("unchecked")
        public Boolean hashSetNx(String key, String hashKey, V v) {
            return hashOperations.putIfAbsent(key, hashKey, v);
        }


        /**
         * 获取存储在哈希表中指定字段的值。
         * HGET key field
         *
         * @param key
         * @param hashKey
         * @return
         */
        @SuppressWarnings("unchecked")
        public V hashGet(String key, String hashKey) {
            return (V) hashOperations.get(key, hashKey);
        }

        /**
         * 根据key获取map.
         * 不是太建议使用.有这种使用场景的建议直接对象转为json存起来:jackson2.set(key,v)
         *
         * @param key
         * @return
         */
        @Deprecated
        @SuppressWarnings("unchecked")
        public Map hashGet(String key) {
            return hashOperations.entries(key);
        }

        /**
         * 获取指定key的所有hashkey.  Get key set (fields) of hash at {@code key}.
         * HKEYS key
         *
         * @param key
         * @return
         */
        @SuppressWarnings("unchecked")
        @Deprecated
        public Set hashKeys(String key) {
            return hashOperations.keys(key);
        }

        /**
         * 获取指定key的所有value的集合
         * HVALS key
         *
         * @param key
         * @return
         */
        @SuppressWarnings("unchecked")
        public List hashValues(String key) {
            return hashOperations.values(key);
        }

        /**
         * 获取指定key的hash数量
         * HLEN key
         *
         * @param key
         * @return
         */
        @SuppressWarnings("unchecked")
        public Long hashSize(String key) {
            return hashOperations.size(key);
        }

        /**
         * 删除一个或多个哈希表字段
         * HDEL key field1 [field2]
         *
         * @param key
         * @param hashKeys
         * @return
         */
        @SuppressWarnings("unchecked")
        public Long delete(String key, String... hashKeys) {
            return hashOperations.delete(key, hashKeys);
        }

        /**
         * 查看哈希表 key 中，指定的字段是否存在。
         * HEXISTS key field
         *
         * @param key
         * @param hashKey
         */
        @SuppressWarnings("unchecked")
        public Boolean hasKey(String key, String hashKey) {
            return hashOperations.hasKey(key, hashKey);
        }
    }

    /**
     * 基本类型,还没想好这个地方怎么处理
     */
    @Deprecated
    public class PrimitiveType<V> extends AbstractTemplate<V> {
        PrimitiveType() {
            super(stringRedisTemplate);
        }

    }

    /**
     * jackson2序列化方式
     */
    public class Jackson2<V> extends AbstractTemplate<V> {
        Jackson2() {
            super(jackson2RedisTemplate);
        }

        /**
         * 对象用jackson直接转json
         *
         * @param key
         * @param c
         * @return
         */
        @SuppressWarnings("unchecked")
        public <HM> HM get(String key, Class<HM> c) {
            V v = (V) primitive.get(key);
            try {
                return objectMapper.readValue((String) v, c);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("转换失败:" + e.getMessage());
            }
        }

        /**
         * 当value是一个对象的时候(非map),需要类型转换
         *
         * @param key
         * @param hashKey
         * @param c
         * @return
         */
        @SuppressWarnings("unchecked")
        public <HV> HV hashGet(String key, String hashKey, Class<HV> c) {
            return objectMapper.convertValue(hashGet(key, hashKey), c);
        }

        /**
         * 获取对象,返回指定类型的对象
         * 不是太建议使用.有这种使用场景的建议直接对象转为json存起来:jackson2.set(key,v)
         *
         * @param key
         * @param c
         * @return
         */
        @Deprecated
        public <HM> HM hashGet(String key, Class<HM> c) {
            return objectMapper.convertValue(hashGet(key), c);
        }
    }

    /**
     * jdk原生序列化
     */
    public class Jdk extends AbstractTemplate {
        Jdk() {
            super(jdkRedisTemplate);
        }
    }

}
