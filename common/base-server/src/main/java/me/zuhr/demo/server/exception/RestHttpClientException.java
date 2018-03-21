package me.zuhr.demo.server.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.nio.charset.Charset;

/**
 * 客户端发送rest请求,返回客户端异常(400)
 *
 * @author zurun
 * @date 2018/3/21 00:44:08
 */
public class RestHttpClientException extends AbstractRestException {


    public RestHttpClientException(HttpStatus statusCode, String statusText,
                                   HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(statusCode, statusText, responseHeaders, responseBody, responseCharset);

    }

}
