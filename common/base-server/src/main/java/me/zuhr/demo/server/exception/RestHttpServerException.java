package me.zuhr.demo.server.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.nio.charset.Charset;

/**
 * 客户端发送rest请求,返回服务端异常(500)
 *
 * @author zurun
 * @date 2018/2/25 12:27:22
 */
public class RestHttpServerException extends AbstractRestException {


    public RestHttpServerException(HttpStatus statusCode, String statusText,
                                   HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(statusCode, statusText, responseHeaders, responseBody, responseCharset);

    }

}
