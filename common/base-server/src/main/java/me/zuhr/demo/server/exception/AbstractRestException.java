package me.zuhr.demo.server.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.springframework.http.HttpStatus;

/**
 * 所有REST请求,返回错误http状态码的,抛的异常需要继承自HystrixBadRequestException,
 * 这样可以直接处理异常之后进行抛出（这里不会触发熔断机制），而不是进入回调方法。
 * @see com.netflix.hystrix.AbstractCommand#executeCommandAndObserve
 *
 * 此异常去掉异常栈构造,减少开销
 *
 * @author zurun
 * @date 2018/3/20 23:02:25
 */
public abstract class AbstractRestException extends HystrixBadRequestException {

    protected final HttpStatus statusCode;


    public AbstractRestException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    /**
     * 减少开销
     *
     * @return
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
