package me.zuhr.demo.redisson.constants;

import me.zuhr.demo.basis.constants.IMessage;

/**
 * @author zurun
 * @date 2018/4/2 15:19:35
 */
public interface ErrorCode extends me.zuhr.demo.basis.constants.ErrorCode {
    /**
     * 分布式锁异常
     */
    enum DistributedLock implements IMessage {
        DEFAULT_LOCK_FAIL(60, "上锁失败!"),
        LOCK_FAIL(61, "上锁失败!");

        private int errCode;
        private String errMsg;


        DistributedLock(int errCode, String errMsg) {
            this.errCode = errCode;
            this.errMsg = errMsg;
        }

        @Override
        public int getErrCode() {
            return this.errCode;
        }

        @Override
        public String getErrMsg() {
            return this.errMsg;
        }
    }
}
