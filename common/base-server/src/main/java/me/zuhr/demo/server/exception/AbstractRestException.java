package me.zuhr.demo.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientResponseException;

/**
 * @author zurun
 * @date 2018/3/20 23:02:25
 * @see RestClientResponseException
 */
public abstract class AbstractRestException extends RuntimeException {

    protected final HttpStatus statusCode;


    public AbstractRestException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
