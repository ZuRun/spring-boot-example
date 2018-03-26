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
public abstract class AbstractRestHttpException extends AbstractRestException {
    private static final String DEFAULT_CHARSET = "ISO-8859-1";


    protected final int rawStatusCode;

    protected final String statusText;

    protected final byte[] responseBody;

    protected final HttpHeaders responseHeaders;

    protected final String responseCharset;

    public AbstractRestHttpException(HttpStatus statusCode, String statusText,
                                     HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(statusCode,new String(responseBody, responseCharset));
        this.rawStatusCode = statusCode.value();
        this.statusText = statusText;
        this.responseHeaders = responseHeaders;
        this.responseBody = (responseBody != null ? responseBody : new byte[0]);
        this.responseCharset = (responseCharset != null ? responseCharset.name() : DEFAULT_CHARSET);

    }


}
