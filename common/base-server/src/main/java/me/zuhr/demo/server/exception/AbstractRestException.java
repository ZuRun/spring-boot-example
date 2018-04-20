package me.zuhr.demo.server.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientResponseException;

/**
 * 此异常去掉异常栈构造,减少开销
 *
 * @author zurun
 * @date 2018/3/20 23:02:25
 * @see RestClientResponseException
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
