package me.zuhr.demo.basis.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientResponseException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * 客户端发送rest请求,针对返回状态码的异常
 *
 * @author zurun
 * @date 2018/2/25 12:27:22
 * @see RestClientResponseException
 */
public class MyRestClientResponseException extends RuntimeException {
    private static final long serialVersionUID = -19999999999801L;

    private static final String DEFAULT_CHARSET = "ISO-8859-1";

    private final HttpStatus statusCode;

    private final int rawStatusCode;

    private final String statusText;

    private final byte[] responseBody;

    private final HttpHeaders responseHeaders;

    private final String responseCharset;

    public MyRestClientResponseException(HttpStatus statusCode, String statusText,
                                         HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(new String(responseBody, responseCharset));
        this.statusCode = statusCode;
        this.rawStatusCode = statusCode.value();
        this.statusText = statusText;
        this.responseHeaders = responseHeaders;
        this.responseBody = (responseBody != null ? responseBody : new byte[0]);
        this.responseCharset = (responseCharset != null ? responseCharset.name() : DEFAULT_CHARSET);

    }

    /**
     * ResponseBody中的内容返回字符串
     *
     * @return
     */
    public String getResponseBodyAsString() {
        try {
            return new String(this.responseBody, this.responseCharset);
        } catch (UnsupportedEncodingException ex) {
            // should not occur
            throw new IllegalStateException(ex);
        }
    }
}
