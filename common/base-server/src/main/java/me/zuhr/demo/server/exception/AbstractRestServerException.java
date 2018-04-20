package me.zuhr.demo.server.exception;

import me.zuhr.demo.server.enumration.HttpHeader;
import org.springframework.http.HttpStatus;

/**
 * 发送rest请求后,服务端返回异常且有Exception-Type请求头,接收方需要直接抛此异常,body为接收到的body
 * <p>
 *
 *
 * @author zurun
 * @date 2018/2/25 13:19:15
 */
public abstract class AbstractRestServerException extends AbstractRestException {
    private HttpHeader.ExceptionType exceptionType;

    public AbstractRestServerException(HttpStatus statusCode, HttpHeader.ExceptionType exceptionType, String message) {
        super(statusCode, message);
        this.exceptionType = exceptionType;
    }

    public HttpHeader.ExceptionType getExceptionType() {
        return exceptionType;
    }


}
