package me.zuhr.demo.server.exception;

import me.zuhr.demo.server.enumration.HttpHeader;

/**
 * 发送rest请求后,服务端返回业务异常(状态码为自定义的业务异常码),接收方需要直接抛此异常,body为接收到的body
 * <p>
 * 此异常去掉异常栈构造,减少开销
 *
 * @author zurun
 * @date 2018/2/25 13:19:15
 */
public class RestException extends AbstractRestException {
    private HttpHeader.ExceptionType exceptionType;

    public RestException(HttpHeader.ExceptionType exceptionType, String message) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public HttpHeader.ExceptionType getExceptionType() {
        return exceptionType;
    }
}
