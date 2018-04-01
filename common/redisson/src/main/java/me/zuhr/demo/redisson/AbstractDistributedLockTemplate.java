package me.zuhr.demo.redisson;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

public abstract class AbstractDistributedLockTemplate implements DistributedLockTemplate {
    protected RedissonClient redissonClient;

    public AbstractDistributedLockTemplate(RedissonClient redissonClient){
        this.redissonClient=redissonClient;
    }

    @Override
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
