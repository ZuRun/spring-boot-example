package me.zuhr.demo.wxapp.constants;

import me.zuhr.demo.basis.constants.IMessage;

/**
 * @author zurun
 * @date 2018/5/9 13:46:38
 */
public interface ErrorCode extends me.zuhr.demo.basis.constants.ErrorCode {

    enum WxAppErrorCode implements IMessage{
        TOKEN_MISSING(1251,"缺少token"),
        INVALID_TOKEN(1252,"token错误");

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
