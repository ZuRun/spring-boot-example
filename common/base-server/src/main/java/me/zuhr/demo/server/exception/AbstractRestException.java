package me.zuhr.demo.server.exception;

import org.springframework.web.client.RestClientResponseException;

/**
 * @author zurun
 * @date 2018/3/20 23:02:25
 * @see RestClientResponseException
 */
public abstract class AbstractRestException extends RuntimeException {


    public AbstractRestException(String message) {
        super(message);
    }


}
