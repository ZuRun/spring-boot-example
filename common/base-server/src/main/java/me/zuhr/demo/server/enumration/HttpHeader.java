package me.zuhr.demo.server.enumration;


import me.zuhr.demo.server.constants.HttpHeaderException;
import me.zuhr.demo.server.exception.RestBusinessException;
import me.zuhr.demo.server.exception.RestUnhandledException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

/**
 * 当response返回http状态码为400或500的时候
 * 可以在header中指定异常类型,接受方根据类型做不同处理
 *
 * @author zurun
 * @date 2018/3/22 23:17:28
 */
public enum HttpHeader {
    /**
     * 自定义的HttpHeader的属性名
     */
    EXCEPTION_TYPE_HEADER("Exception-Type");
    private String value;

    HttpHeader(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * response请求头名为:EXCEPTION-Type,记录异常类型
     *
     * @author zurun
     */
    public enum ExceptionType implements HttpHeaderException {
        /**
         * 业务异常
         */
        BUSINESS {
            /**
             * 直接抛出RestBusinessException异常,内容不做处理
             * @param statusCode
             * @param msg
             * @return
             */
            @Override
            public boolean handleError(HttpStatus statusCode, String msg) {
                throw new RestBusinessException(statusCode, this, msg);
            }
        },
        /**
         * 一般在exception中接的异常,也就是未单独处理的异常
         */
        UNHANDLED {
            @Override
            public boolean handleError(HttpStatus statusCode, String msg) {
                throw new RestUnhandledException(statusCode, this, msg);
            }
        },
        /**
         * 被调用方返回的404，直接原封不动返回
         */
        NOT_FOUND {
            @Override
            public boolean handleError(HttpStatus statusCode, String msg) {
                throw new RestBusinessException(statusCode, this, msg);
            }
        },
        SERVLET_EXCEPTION {
            @Override
            public boolean handleError(HttpStatus statusCode, String msg) {
                throw new RestBusinessException(statusCode, this, msg);
            }
        },
        /**
         * 未知异常,一般是第三方接口？
         * response中没有Exception-Type请求头
         */
        UNKNOWN {
            @Override
            public boolean handleError(HttpStatus statusCode, String msg) {
                throw new RestUnhandledException(statusCode, this, msg);
            }
        };

        @Override
        public String getHeaderName() {
            return EXCEPTION_TYPE_HEADER.getValue();
        }

        @Override
        public String getHeaderValue() {
            return this.name();
        }

        /**
         * 根据name获取枚举常量
         * 不要用valueOf方法,valueOf方法中如果没找到会抛异常
         *
         * @param name
         * @return
         */
        public static ExceptionType getByName(String name) {
            if (StringUtils.isEmpty(name)) {
                return null;
            }
            for (ExceptionType exceptionType : values()) {
                if (exceptionType.getHeaderValue().equals(name)) {
                    return exceptionType;
                }
            }
            return null;
        }

    }

    public static void main(String[] args) {
        System.out.println(ExceptionType.valueOf("ss"));
        System.out.println(ExceptionType.valueOf("UNKNOWN"));
    }

}
