import me.zuhr.demo.redis.RedisApplicationTest;
import me.zuhr.demo.redis.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zurun
 * @date 2018/2/12 16:42:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RedisApplicationTest.class)
public class RedisTest {
    @Qualifier("jedisConnectionFactory")
    @Autowired
    JedisConnectionFactory factory;

    @Autowired
    RedisUtils redisUtils;

    @Test
    public void redisUtilTest() {
        String key = "speedTest";
        String key2 = "map";
        String val = "zuuu!!!!!!!!!!!!!!!!!!!!"+System.currentTimeMillis();
        Map map = new HashMap();
        map.put("xxxx", "xxxx1");
        map.put("yyyy", "yyyy1正在");

        redisUtils.setValue(key, val);
        redisUtils.set(key2, map);

        long beginStr = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
//            redisUtils.setValue(key, val.concat("_"+i));
            redisUtils.get(key2);
        }


        long timeStr = System.currentTimeMillis() - beginStr;
        System.out.println("StringRedisSerializer time:" + timeStr);
        String str = redisUtils.getValue(key);
        Map resultMap = (Map) redisUtils.get(key2);
        System.out.println(str);
        System.out.println(resultMap);
        System.out.println("----------------");

    }

    @Test
    public void test() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        String key = "speedTest";
        String val = "zuuu!!!!!!!!!!!!!!!!!!!!";

        long beginStr = System.currentTimeMillis();
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        // 需要执行下,否则默认序列化类型不生效
        redisTemplate.afterPropertiesSet();
        // 先set下,降低误差
        redisTemplate.opsForValue().set(key, val);

        for (int i = 0; i < 1000; i++) {
            redisTemplate.opsForValue().set(key, val.concat("_" + i));
//            redisTemplate.opsForValue().e
            redisTemplate.opsForValue().get(key);
        }
        long timeStr = System.currentTimeMillis() - beginStr;
        System.out.println(redisTemplate.opsForValue().get(key));
        System.out.println("StringRedisSerializer time:" + timeStr);

        long beginJdk = System.currentTimeMillis();
        redisTemplate.setDefaultSerializer(new JdkSerializationRedisSerializer());
        // 需要执行下,否则默认序列化类型不生效
        redisTemplate.afterPropertiesSet();

        for (int i = 0; i < 1000; i++) {
            redisTemplate.opsForValue().set(key, val);
            redisTemplate.opsForValue().get(key);
        }
        long timeJdk = System.currentTimeMillis() - beginJdk;
        System.out.println("JdkSerializationRedisSerializer time:" + timeJdk);


    }
}
