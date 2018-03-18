package me.zuhr.demo.basis.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 业务异常
 * 此异常去掉异常栈构造,减少开销
 * 将由全局异常处理做业务处理
 *
 * @author zurun
 * @date 2018/3/11 12:59:07
 */
public class ServiceException extends RuntimeException {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 异常码/错误码,用于业务
     */
    protected int errCode = 10;

    public ServiceException() {
        super();
    }

    public ServiceException(String errMsg) {
        super(errMsg);
    }

    /**
     * 记录错误码与错误信息
     *
     * @param errCode
     * @param errMsg
     */
    public ServiceException(int errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;

    }

    /**
     * 减少开销
     *
     * @return
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
