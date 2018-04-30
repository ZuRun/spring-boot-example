package me.zuhr.demo.redisson.exception;

import me.zuhr.demo.basis.exception.BusinessException;
import me.zuhr.demo.redisson.constants.ErrorCode;

/**
 * 调用分布式锁异常
 *
 * @author zurun
 * @date 2018/4/2 15:17:03
 */
public class DistributedLockException extends BusinessException {

    public DistributedLockException(String errMsg) {
        super(ErrorCode.DistributedLock.DEFAULT_LOCK_FAIL,errMsg);
    }

    public DistributedLockException(ErrorCode.DistributedLock errCode) {
        super(errCode);
    }
}
