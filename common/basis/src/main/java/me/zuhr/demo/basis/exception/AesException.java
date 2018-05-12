package me.zuhr.demo.basis.exception;

import me.zuhr.demo.basis.constants.IMessage;

/**
 * @author zurun
 * @date 2018/5/12 12:10:21
 */
public class AesException extends MyRuntimeException {

    public AesException(IMessage errCode) {
        super(errCode);
    }
}
