package me.zuhr.demo.ecc.constants;

import me.zuhr.demo.basis.constants.IMessage;

/**
 * @author zurun
 * @date 2018/3/24 17:32:01
 */
public interface ErrorCode extends me.zuhr.demo.basis.constants.ErrorCode {
    enum ecc implements IMessage {
        ECC_DEF_ERROR(1800, "ecc默认错误");


        private int errCode;
        private String errMsg;

        ecc(int errCode, String errMsg) {
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
