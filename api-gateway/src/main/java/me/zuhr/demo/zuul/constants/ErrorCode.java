package me.zuhr.demo.zuul.constants;

import me.zuhr.demo.basis.constants.IMessage;

/**
 * @author zurun
 * @date 2018/5/1 00:01:54
 */
public interface ErrorCode extends me.zuhr.demo.basis.constants.ErrorCode {
    enum WxAppErrorCode implements IMessage {
        TOKEN_MISSING(1251,"缺少token"),
        TOKEN_INCORRECT(1252,"token错误");

        private int errCode;
        private String errMsg;

        WxAppErrorCode(int errCode, String errMsg) {
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
