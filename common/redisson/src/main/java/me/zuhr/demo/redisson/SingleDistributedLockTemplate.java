package me.zuhr.demo.redisson;

import org.redisson.api.RedissonClient;


/**
 * @author ZuRun
 * @date 2018/03/31 11:42:42
 */
public class SingleDistributedLockTemplate extends AbstractDistributedLockTemplate {

    public SingleDistributedLockTemplate(RedissonClient redissonClient) {
        super(redissonClient);
    }

}
