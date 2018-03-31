package me.zuhr.demo.redisson;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @author ZuRun
 * @date 2018/03/31 11:42:42
 */
public class SingleDistributedLockTemplate implements DistributedLockTemplate {
    private RedissonClient redisson;

    public SingleDistributedLockTemplate(RedissonClient redisson) {
        this.redisson = redisson;
    }

    @Override
    public <T> T lock(String lockName, DistributedLockCallback<T> callback, boolean fairLock) {
        return lock(lockName, callback, DEFAULT_TIMEOUT, DEFAULT_TIME_UNIT, fairLock);
    }

    @Override
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

    @Override
    public <T> T tryLock(String lockName, DistributedLockCallback<T> callback, boolean fairLock) {
        return tryLock(lockName, callback, DEFAULT_WAIT_TIME, DEFAULT_TIMEOUT, DEFAULT_TIME_UNIT, fairLock);
    }

    @Override
    public <T> T tryLock(String lockName, DistributedLockCallback<T> callback, long waitTime, long leaseTime, TimeUnit timeUnit, boolean fairLock) {
        RLock lock = getLock(lockName, fairLock);
        try {
            boolean isLocked = lock.tryLock(waitTime, leaseTime, timeUnit);
            if(isLocked){
                lock.unlock();
            }
            return callback.process(isLocked);

        } catch (InterruptedException e) {

        }
        return null;
    }

    private RLock getLock(String lockName, boolean fairLock) {
        RLock lock;
        if (fairLock) {
            lock = redisson.getFairLock(lockName);
        } else {
            lock = redisson.getLock(lockName);
        }
        return lock;
    }

    public void setRedisson(RedissonClient redisson) {
        this.redisson = redisson;
    }

}
