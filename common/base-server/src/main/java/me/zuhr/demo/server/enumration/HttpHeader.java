package me.zuhr.demo.server.enumration;


import me.zuhr.demo.server.constants.HttpHeaderException;
import me.zuhr.demo.server.exception.RestBusinessException;

/**
 * 当response返回http状态码为400或500的时候
 * 可以在header中指定异常类型,接受方根据类型做不同处理
 *
 * @author zurun
 * @date 2018/3/22 23:17:28
 */
public interface HttpHeader {
    /**
     * 自定义的HttpHeader的属性名
     */
    enum HeaderName {
        ExceptionType("Exception-Type");

        private String value;

        HeaderName(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 头名为:Exception-Type,记录异常类型
     */
    enum ExceptionType implements HttpHeaderException {
        /**
         * 业务异常
         */
        BUSINESS {
            /**
             * 直接抛出RestBusinessException异常,内容不做处理
             * @param msg
             * @return
             */
            @Override
            public boolean handleError(String msg) {
                throw new RestBusinessException(this, msg);
            }
        },
        RestBusiness {
            @Override
            public boolean handleError(String msg) {
                throw new RestBusinessException(this, msg);
            }
        },
        /**
         * 未知异常
         */
        UNKNOWN {
            @Override
            public boolean handleError(String msg) {
                return false;
            }
        };

        @Override
        public String getHeaderName() {
            return HeaderName.ExceptionType.value;
        }

        @Override
        public String getHeaderValue() {
            return this.name();
        }

    }


}
