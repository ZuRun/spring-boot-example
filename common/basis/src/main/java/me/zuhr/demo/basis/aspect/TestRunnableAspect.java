package me.zuhr.demo.basis.aspect;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author ZuRun
 * @date 2018/04/02 00:07:22
 */
@Aspect
@Component
public class TestRunnableAspect {
    /**
     * 这里是关键点，把切面的连接点放在了我们的注解上
     */
    @Pointcut("@annotation(me.zuhr.demo.basis.annotation.TestRunnable)")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void before(JoinPoint joinPoint){
//        junit.framework.
//        // 构造一个Runner
//        TestRunnable runner = new TestRunnable() {
//            @Override
//            public void runTest() {
//                // 方法内容
//                joinPoint.getThis();
//            }
//        };
//
//        int runnerCount = 50;
//        //Rnner数组，想当于并发多少个。
//        TestRunnable[] trs = new TestRunnable[runnerCount];
//        for (int i = 0; i < runnerCount; i++) {
//            trs[i] = runner;
//        }
//        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
//        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
//        try {
//            // 开发并发执行数组里定义的内容
//            mttr.runTestRunnables();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
    }
}
