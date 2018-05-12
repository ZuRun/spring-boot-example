package me.zuhr.demo.basis.exception;

import me.zuhr.demo.basis.constants.ErrorCode;
import me.zuhr.demo.basis.constants.IMessage;

/**
 * 业务异常
 * <p>
 * 此异常去掉异常栈构造,减少开销
 * 将由全局异常处理做业务处理
 *
 * @author zurun
 * @date 2018/3/11 12:59:07
 */
public class BusinessException extends MyRuntimeException {


    public BusinessException(String errMsg) {
        super(ErrorCode.common.DEFAULT_BUSINESS_EXCEPTION_CODE, errMsg);
    }

    public BusinessException(IMessage errCode) {
        super(errCode);
    }

    public BusinessException(IMessage errCode, String errMsg) {
        super(errCode, errMsg);
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


}
