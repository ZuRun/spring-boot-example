package me.zuhr.demo;

import me.zuhr.demo.redis.config.RedisConfig;
import me.zuhr.demo.redis.utils.RedisUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zurun
 * @date 2018/2/11 16:31:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class TTest {

    @Autowired
    RedisUtils redisUtils;

//    private StringRedisTemplate stringRedisTemplate;

//    @Autowired
//    RedisConfig redisConfig;
    @Test
    public void d(){
        redisUtils.set("t1","-----------");
        System.out.println(redisUtils.get("t1"));
        redisUtils.get("test");
        System.out.println(redisUtils.get("test"));
// 保存字符串
//        stringRedisTemplate.opsForValue().set("aaa", "111");
//        System.out.println(stringRedisTemplate.opsForValue().get("aaa"));
//        System.out.println(stringRedisTemplate.opsForValue().get("test"));
//        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));

    }

    @Test
    public void config(){
//        System.out.println(redisConfig);
    }
}
