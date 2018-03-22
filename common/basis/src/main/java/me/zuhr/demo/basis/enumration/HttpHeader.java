package me.zuhr.demo.basis.enumration;


import me.zuhr.demo.basis.constants.MyHttpHeader;

/**
 * 当response返回http状态码为400或500的时候
 * 可以在header中指定异常类型,接受方根据类型做不同处理
 *
 * @author zurun
 * @date 2018/3/22 23:17:28
 */
public interface HttpHeader {
    enum ExceptionType implements MyHttpHeader {
        /**
         * 业务异常
         */
        BUSINESS,
        /**
         * 未知异常
         */
        UNKNOWN;

        @Override
        public String getHeaderName() {
            return "Exception-Type";
        }

        @Override
        public String getHeaderValue() {
            return this.name();
        }
    }




}
