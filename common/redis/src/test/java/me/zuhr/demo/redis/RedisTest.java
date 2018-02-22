package me.zuhr.demo.redis;

import me.zuhr.demo.redis.enuma.SexEnum;
import me.zuhr.demo.redis.utils.RedisUtils;
import me.zuhr.demo.redis.vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author zurun
 * @date 2018/2/22 18:06:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RedisApplicationTest.class)
public class RedisTest {
    @Autowired
    RedisUtils redisUtils;

    @Test
    public void test1() throws IOException {
        String key = "userVo";
        UserVo userVo = new UserVo();
        userVo.setAge(25);
        userVo.setName("姓名name");
        userVo.setAmount(999988888777776555L);
        userVo.setWeight(99.0911);
        userVo.setMoney(BigDecimal.valueOf(500000000.11));
        userVo.setSexEnum(SexEnum.WOMAN);
        System.out.println("-----START-----");

        System.out.println(userVo);
        System.out.println("----------");
        redisUtils.set(key, userVo, 30L);
        Object object = redisUtils.get(key);
        System.out.println(object);
        System.out.println("----------");
        UserVo newUserVo = (UserVo) redisUtils.get(key, UserVo.class);

        System.out.println(newUserVo);
        System.out.println("----------");
        System.out.println(redisUtils.getString(key));

        System.out.println("-----getAndSet-----");
        userVo.setAge(26);
        userVo.setSexEnum(SexEnum.MAN);
        System.out.println(redisUtils.getAndSet(key, userVo));
        System.out.println("----------");
        System.out.println(redisUtils.get(key));

        System.out.println("-----END-----");

    }
}
