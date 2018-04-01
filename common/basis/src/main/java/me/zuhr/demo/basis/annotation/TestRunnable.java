package me.zuhr.demo.basis.annotation;

import java.lang.annotation.*;

/**
 * 多线程测试用
 *
 * @author ZuRun
 * @date 2018/04/01 23:50:50
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestRunnable {
}
