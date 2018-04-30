package me.zuhr.demo.redisson.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class DistributedLockTemplate {
    // 默认等待锁时间
    private final long DEFAULT_WAIT_TIME = 30;
    // 默认锁超时时间,如果没有手动解锁则自动解锁
    private final long DEFAULT_TIMEOUT = 5;
    private final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    protected RedissonClient redissonClient;

    public DistributedLockTemplate(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public void lock(String lockName, long leaseTime) {
        lock(lockName, leaseTime, TimeUnit.SECONDS, false);
    }

    public void lock(String lockName, long leaseTime, TimeUnit timeUnit, boolean fairLock) {
        RLock lock = getLock(lockName, fairLock);
        lock.lock(leaseTime, timeUnit);
    }

    /**
     * 使用分布式锁,使用锁默认超时时间,不使用公平锁
     *
     * @param lockName
     * @param callback
     * @param <T>
     * @return
     */
    public <T> T lock(String lockName, DistributedLockCallback<T> callback) {
        return lock(lockName, callback, false);
    }

    /**
     * 使用分布式锁，使用锁默认超时时间。
     *
     * @param callback
     * @param fairLock 是否使用公平锁
     * @return
     */
    public <T> T lock(String lockName, DistributedLockCallback<T> callback, boolean fairLock) {
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
    public <T> T lock(String lockName, DistributedLockCallback<T> callback, long leaseTime, TimeUnit timeUnit, boolean fairLock) {
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
     * 解锁
     *
     * @param lockName
     * @param fairLock
     */
    public void unlock(String lockName, boolean fairLock) {
        RLock lock = getLock(lockName, fairLock);
        lock.unlock();
    }

    /**
     * 解锁
     *
     * @param lock
     */
    public void unlock(RLock lock) {
        lock.unlock();
    }

    /**
     * 尝试分布式锁，使用锁默认等待时间、超时时间。
     *
     * @param callback
     * @param fairLock 是否使用公平锁
     * @param <T>
     * @return
     */
    public <T> T tryLock(String lockName, DistributedLockCallback<T> callback, boolean fairLock) {
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
    public <T> T tryLock(String lockName, DistributedLockCallback<T> callback, long waitTime, long leaseTime, TimeUnit timeUnit, boolean fairLock) {
        RLock lock = getLock(lockName, fairLock);
        try {
            boolean isLocked = lock.tryLock(waitTime, leaseTime, timeUnit);
            T t = callback.process(isLocked);
            if (isLocked) {
                lock.unlock();
            }
            return t;

        } catch (InterruptedException e) {

        }
        return null;
    }

    /**
     * 尝试分布式锁
     *
     * @param lockName
     * @param callback
     * @param leaseTime
     * @param <T>
     * @return
     */
    public <T> T lockIfAbsent(String lockName, DistributedLockCallback<T> callback, long leaseTime) {
        return tryLock(lockName, callback, -1, leaseTime, TimeUnit.SECONDS, false);
    }



    public RLock getLock(String lockName) {
        return getLock(lockName, false);
    }

    public RLock getLock(String lockName, boolean fairLock) {
        RLock lock;
        if (fairLock) {
            lock = redissonClient.getFairLock(lockName);
        } else {
            lock = redissonClient.getLock(lockName);
        }
        return lock;
    }

}
