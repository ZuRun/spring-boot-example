package me.zuhr.demo.redisson;

import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.redisson.annotation.DistributedLock;
import me.zuhr.demo.redisson.lock.DistributedLockTemplate;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    int i = 0;

    @Autowired
    DistributedLockTemplate lockTemplate;

    @Test
    public void annotationTest() {
        go(() -> {
            try {
                todo();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @DistributedLock
    private void todo() throws InterruptedException {
//        i++;
//        Thread.sleep(100L);
        i++;
        System.out.println(i);
    }

    @Test
//    @DistributedLock(tryLock = false)
    public void test() {
        go(() -> {
            //测试内容
            Result result = lockTemplate.lockIfAbsent("_lockName", (isLocked) -> {
                System.out.println("锁");
//                try {
//                    Thread.sleep(1L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                if (isLocked) {
                    return Result.ok();
                } else {
                    return Result.fail("shibai");
                }

            }, 20);
            System.out.println(result);
        });


    }


    interface TestMethod {
        void todo();
    }

    private void go(TestMethod method) {
        // 构造一个Runner
        TestRunnable runner = new TestRunnable() {
            @Override
            public void runTest() {
                // 方法内容
                method.todo();
            }
        };

        int runnerCount = 10;
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