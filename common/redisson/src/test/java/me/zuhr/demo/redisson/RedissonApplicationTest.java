package me.zuhr.demo.redisson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author ZuRun
 * @date 2018/03/31 12:45:00
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RedissonApplication.class)
public class RedissonApplicationTest {

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void test() {
        SingleDistributedLockTemplate lockTemplate = new SingleDistributedLockTemplate(redissonClient);
        Boolean result = lockTemplate.lock("", (isLocked) -> {
            System.out.println("锁");

            if (isLocked) {
                return true;
            } else {
                return false;
            }

        }, false);
        System.out.println(result);

    }



}