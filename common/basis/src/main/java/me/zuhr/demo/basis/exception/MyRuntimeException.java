package me.zuhr.demo.basis.exception;

import me.zuhr.demo.basis.constants.ErrorCode;
import me.zuhr.demo.basis.constants.IMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 项目自定义运行异常
 *
 * @author zurun
 * @date 2018/5/12 12:11:40
 */
public class MyRuntimeException extends RuntimeException {

    /**
     * 异常码/错误码
     */
    protected IMessage errCode;

    protected final Logger logger = LoggerFactory.getLogger(getClass());



    protected MyRuntimeException(String errMsg) {
        super(errMsg);
        errCode = ErrorCode.common.DEFAULT_BUSINESS_EXCEPTION_CODE;
    }

    protected MyRuntimeException(IMessage errCode) {
        super(errCode.getErrMsg());
        this.errCode = errCode;
    }

    protected MyRuntimeException(IMessage errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
    }



    public Integer getErrCode() {
        return errCode.getErrCode();
    }

    /**
     * 慎用，因为实际错误信息不一定对应此枚举的错误信息
     *
     * @return
     */
    @Deprecated
    public IMessage getErrorCode() {
        return errCode;
    }


    @Override
    public String getMessage() {
        return StringUtils.isEmpty(super.getMessage()) ? errCode.getErrMsg() : super.getMessage();
    }

}
