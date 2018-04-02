package me.zuhr.demo.redisson.aspect;

import me.zuhr.demo.redisson.annotation.DistributedLock;
import me.zuhr.demo.redisson.exception.DistributedLockException;
import me.zuhr.demo.redisson.lock.DistributedLockTemplate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author zurun
 * @date 2018/4/2 14:35:01
 */
@Aspect
@Component
public class DistributedLockAspect {
    @Autowired
    DistributedLockTemplate lockTemplate;

    @Pointcut(value = "@annotation(me.zuhr.demo.redisson.annotation.DistributedLock)")
    public void distributedLockAspect() {
    }

    DistributedLockAspect() {
        System.out.println("--------------");
    }

    @Around(value = "distributedLockAspect()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("-------doAround");
        //切点所在的类
        Class targetClass = pjp.getTarget().getClass();
        //使用了注解的方法
        String methodName = pjp.getSignature().getName();

        Class[] parameterTypes = ((MethodSignature) pjp.getSignature()).getMethod().getParameterTypes();

        Method method = targetClass.getMethod(methodName, parameterTypes);

        Object[] arguments = pjp.getArgs();

        return lock(pjp, targetClass, method);
    }

    /**
     * 方法锁,根据类名和方法名作为lockName
     *
     * @param annotation
     * @param targetClass
     * @param method
     * @return
     */
    public String getLockName(DistributedLock annotation, Class targetClass, Method method) {
        if (StringUtils.isEmpty(annotation.lockName())) {
            return targetClass.getName().concat(method.getName());
        }
        return annotation.lockName();

    }

    /**
     * 锁
     *
     * @param pjp
     * @param method
     * @param targetClass
     * @return
     */
    private Object lock(ProceedingJoinPoint pjp, Class targetClass, Method method) {
        DistributedLock annotation = method.getAnnotation(DistributedLock.class);
        final String lockName = getLockName(annotation, targetClass, method);

        if (annotation.tryLock()) {
            return lockTemplate.tryLock(lockName, (isLocked) -> proceed(pjp, isLocked), annotation.waitTime(), annotation.leaseTime(),
                    annotation.timeUnit(), annotation.fairLock());
        } else {
            return lockTemplate.lock(lockName, (isLocked) -> proceed(pjp, isLocked), annotation.leaseTime(),
                    annotation.timeUnit(), annotation.fairLock());
        }
    }

    private Object proceed(ProceedingJoinPoint pjp, boolean isLocked) {
        // 如果没有成功上锁,则不允许执行方法中内容,直接抛异常
        if (!isLocked) {
            throw new DistributedLockException("未能成功上锁!");
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
