package me.zuhr.demo.redis;

import me.zuhr.demo.redis.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 速度测试
 *
 * @author zurun
 * @date 2018/2/21 15:01:19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RedisApplicationTest.class)
public class RedisSpeedTest {

    @Autowired
    RedisUtils redisUtils;

    @Test
    public void test() {
        String key1 = "t1";
        String key2 = "t2";
        String key3 = "t3";
        String value = "---------------";
        System.out.println(redisUtils.getString(key1));

        //程序运行时间： 1815ms
        go((i) -> redisUtils.jdk.set(key1, value,10L));
        //程序运行时间： 1820ms
        go((i) -> redisUtils.jackson2.set(key2, value,10L));
        //程序运行时间： 1740ms
        go((i) -> redisUtils.primitive.set(key3, value,10L));

    }

    private void go(MyTodo todo) {
        // 获取开始时间
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            todo.execute(i);
        }
        // 获取结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");

    }

    private interface MyTodo {
        void execute(int i);
    }
}
