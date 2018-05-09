package me.zuhr.demo.wxapp.exception;

import me.zuhr.demo.basis.constants.IMessage;
import me.zuhr.demo.basis.exception.BusinessException;

/**
 * @author zurun
 * @date 2018/5/9 13:41:07
 */
public class WxAppException extends BusinessException {
    public WxAppException(String errMsg) {
        super(errMsg);
    }

    public WxAppException(IMessage errCode) {
        super(errCode);
    }

    public WxAppException(IMessage errCode, String errMsg) {
        super(errCode, errMsg);
    }
}
