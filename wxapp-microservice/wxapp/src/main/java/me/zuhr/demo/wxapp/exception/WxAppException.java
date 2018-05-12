package me.zuhr.demo.wxapp.exception;

import me.zuhr.demo.basis.constants.IMessage;
import me.zuhr.demo.basis.exception.BusinessException;
import me.zuhr.demo.wxapp.constants.ErrorCode;

/**
 * @author zurun
 * @date 2018/5/9 13:41:07
 */
public class WxAppException extends BusinessException {
    public WxAppException(String errMsg) {
        super(ErrorCode.WxAppErrorCode.DEFAULT_WXAPP_ERROR);
    }

    public WxAppException(IMessage errCode) {
        super(errCode);
    }

    public WxAppException(IMessage errCode, String errMsg) {
        super(errCode, errMsg);
    }
}
