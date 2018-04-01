package me.zuhr.demo.redisson;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @author ZuRun
 * @date 2018/03/31 11:15:48
 */
public interface DistributedLockTemplate {
    // 默认等待锁时间
    long DEFAULT_WAIT_TIME = 30;
    // 默认锁超时时间,如果没有手动解锁则自动解锁
    long DEFAULT_TIMEOUT = 5;
    TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    /**
     * 使用分布式锁,使用锁默认超时时间,不使用公平锁
     *
     * @param lockName
     * @param callback
     * @param <T>
     * @return
     */
    default <T> T lock(String lockName, DistributedLockCallback<T> callback) {
        return lock(lockName, callback, false);
    }

    /**
     * 使用分布式锁，使用锁默认超时时间。
     *
     * @param callback
     * @param fairLock 是否使用公平锁
     * @return
     */
    default <T> T lock(String lockName, DistributedLockCallback<T> callback, boolean fairLock) {
        return lock(lockName, callback, DEFAULT_TIMEOUT, DEFAULT_TIME_UNIT, fairLock);
    }

    /**
     * 使用分布式锁。自定义锁的超时时间
     *
     * @param callback
     * @param leaseTime 锁超时时间。超时后自动释放锁。
     * @param timeUnit
     * @param fairLock  是否使用公平锁
     * @param <T>
     * @return
     */
    default <T> T lock(String lockName, DistributedLockCallback<T> callback, long leaseTime, TimeUnit timeUnit, boolean fairLock) {
        RLock lock = getLock(lockName, fairLock);
        try {
            lock.lock(leaseTime, timeUnit);
            return callback.process(true);
        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }
    }

    /**
     * 尝试分布式锁，使用锁默认等待时间、超时时间。
     *
     * @param callback
     * @param fairLock 是否使用公平锁
     * @param <T>
     * @return
     */
    default <T> T tryLock(String lockName, DistributedLockCallback<T> callback, boolean fairLock) {
        return tryLock(lockName, callback, DEFAULT_WAIT_TIME, DEFAULT_TIMEOUT, DEFAULT_TIME_UNIT, fairLock);
    }

    /**
     * 尝试分布式锁，自定义等待时间、超时时间。
     *
     * @param callback
     * @param waitTime  获取锁最长等待时间
     * @param leaseTime 锁超时时间。超时后自动释放锁。
     * @param timeUnit
     * @param fairLock  是否使用公平锁
     * @param <T>
     * @return
     */
    default <T> T tryLock(String lockName, DistributedLockCallback<T> callback, long waitTime, long leaseTime, TimeUnit timeUnit, boolean fairLock) {
        RLock lock = getLock(lockName, fairLock);
        try {
            boolean isLocked = lock.tryLock(waitTime, leaseTime, timeUnit);
            if (isLocked) {
                lock.unlock();
            }
            return callback.process(isLocked);

        } catch (InterruptedException e) {

        }
        return null;
    }


    RLock getLock(String lockName, boolean fairLock);
}
