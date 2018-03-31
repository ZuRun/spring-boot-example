package me.zuhr.demo.redisson;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;


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

    @Test
    public void t() {
        SingleDistributedLockTemplate lockTemplate = new SingleDistributedLockTemplate(redissonClient);

        // 构造一个Runner
        TestRunnable runner = new TestRunnable() {
            @Override
            public void runTest() throws Throwable {
                //你的测试内容
                Boolean result = lockTemplate.tryLock("_lockName", (isLocked) -> {
                    System.out.println("锁");

                    if (isLocked) {
                        return true;
                    } else {
                        return false;
                    }

                }, -1, 100, TimeUnit.SECONDS, false);
                System.out.println(result);
            }
        };
        int runnerCount = 50;
        //Rnner数组，想当于并发多少个。
        TestRunnable[] trs = new TestRunnable[runnerCount];
        for (int i = 0; i < runnerCount; i++) {
            trs[i] = runner;
        }
        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        try {
            // 开发并发执行数组里定义的内容
            mttr.runTestRunnables();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


}