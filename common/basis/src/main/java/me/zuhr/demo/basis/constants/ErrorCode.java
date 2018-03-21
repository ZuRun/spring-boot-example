package me.zuhr.demo.basis.constants;

/**
 * 错误码枚举类,不同模块中各自建立此类型接口并继承此接口
 * 此接口为通用错误码
 * <p>
 * 应用的错误码 约定为 4位
 * <p>
 * 4位错误码定义：
 * 首位，应用标识；
 * 二号位  ，应用模 erprise：2 |块标识（common：0 | core：1 | ent file-manage：3 | project：4 | requirement-component：5 | work-flow：6 | architecture：7）；
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
        InvalidErrorCode(10, "无效的错误码"),

        DefaultErrorCode(1, "默认错误信息");

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
