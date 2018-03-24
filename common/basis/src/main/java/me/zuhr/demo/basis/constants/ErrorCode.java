package me.zuhr.demo.basis.constants;

/**
 * 错误码枚举类,不同模块中各自建立此类型接口并继承此接口
 * 此接口为通用错误码
 * <p>
 * 应用的错误码 约定为 4位
 * <p>
 * 4位错误码定义：
 * 首位，应用标识； 1
 * 二号位  ，应用模块标识（common：0 | wxapp : 1 | ecc : 8 | ecp : 9）；
 * 三四位为错误码
 * 详见:{@link http://blog.csdn.net/huangwenyi1010/article/details/51581906}
 *
 * @author zurun
 * @date 2018/3/20 23:34:24
 */
public interface ErrorCode {
    enum common implements IMessage {
        /**
         * 无效的错误码
         */
        INVALID_ERROR_CODE(2, "无效的错误码"),

        DEFAULT_ERROR_CODE(1, "默认错误信息");

        private int errCode;
        private String errMsg;


        common(int errCode, String errMsg) {
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
