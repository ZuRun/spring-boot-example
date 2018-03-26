package me.zuhr.demo.server.exception;

import me.zuhr.demo.server.enumration.HttpHeader;
import org.springframework.http.HttpStatus;

/**
 * 发送rest请求后,服务端返回异常且Exception-Type请求头为Unhandled,接收方需要直接抛此异常,body为接收到的body
 * <p>
 * 此异常去掉异常栈构造,减少开销
 *
 * @author zurun
 * @date 2018/2/25 13:19:15
 */
public class RestUnhandledException extends AbstractRestServerException {

    public RestUnhandledException(HttpStatus statusCode, HttpHeader.ExceptionType exceptionType, String message) {
        super(statusCode, exceptionType, message);
    }

}
