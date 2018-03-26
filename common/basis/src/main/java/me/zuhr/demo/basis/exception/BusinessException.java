package me.zuhr.demo.basis.exception;

import me.zuhr.demo.basis.constants.ErrorCode;
import me.zuhr.demo.basis.constants.IMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 业务异常
 * <p>
 * 此异常去掉异常栈构造,减少开销
 * 将由全局异常处理做业务处理
 *
 * @author zurun
 * @date 2018/3/11 12:59:07
 */
public class BusinessException extends RuntimeException {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 异常码/错误码
     */
    protected IMessage errCode;


    public BusinessException(String errMsg) {
        super(errMsg);
        errCode = ErrorCode.common.DEFAULT_BUSINESS_EXCEPTION_CODE;
    }

    public BusinessException(IMessage errCode) {
        super(errCode.getErrMsg());
        this.errCode = errCode;
    }

    public Integer getErrCode() {
        return errCode.getErrCode();
    }


    @Override
    public String getMessage() {
        return StringUtils.isEmpty(super.getMessage()) ? errCode.getErrMsg() : super.getMessage();
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
