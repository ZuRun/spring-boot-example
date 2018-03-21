package me.zuhr.demo.server.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientResponseException;

import java.nio.charset.Charset;

/**
 * @author zurun
 * @date 2018/3/20 23:02:25
 * @see RestClientResponseException
 */
public abstract class AbstractRestException extends RuntimeException {
    private static final String DEFAULT_CHARSET = "ISO-8859-1";

    protected final HttpStatus statusCode;

    protected final int rawStatusCode;

    protected final String statusText;

    protected final byte[] responseBody;

    protected final HttpHeaders responseHeaders;

    protected final String responseCharset;

    public AbstractRestException(HttpStatus statusCode, String statusText,
                                 HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(new String(responseBody, responseCharset));
        this.statusCode = statusCode;
        this.rawStatusCode = statusCode.value();
        this.statusText = statusText;
        this.responseHeaders = responseHeaders;
        this.responseBody = (responseBody != null ? responseBody : new byte[0]);
        this.responseCharset = (responseCharset != null ? responseCharset.name() : DEFAULT_CHARSET);

    }


}
