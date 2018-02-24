package me.zuhr.demo.basis.restful;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 自定义状态码相应策略
 * RestTemplate默认对4和5开头的状态码 抛异常处理
 * 现在约定项目中微服务之间的通信,使用restful规范,对于非2开头的状态码统一处理
 * <p>
 * hasError()方法不变
 * handleError()中不做处理
 *
 * @author zurun
 * @date 2018/2/24 22:50:00
 */
public class MyResponseErrorHandler implements ResponseErrorHandler {

    /**
     * 4开头和5开头的是请求错误的
     *
     * @param response
     * @return
     * @throws IOException
     * @see org.springframework.web.client.DefaultResponseErrorHandler#hasError(ClientHttpResponse)
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        try {
            return hasError(getHttpStatusCode(response));
        } catch (UnknownHttpStatusCodeException ex) {
            return false;
        }
    }


    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

    }

    /**
     * Template method called from {@link #hasError(ClientHttpResponse)}.
     * <p>The default implementation checks if the given status code is
     * {@link HttpStatus.Series#CLIENT_ERROR CLIENT_ERROR} or
     * {@link HttpStatus.Series#SERVER_ERROR SERVER_ERROR}.
     * Can be overridden in subclasses.
     *
     * @param statusCode the HTTP status code
     * @return {@code true} if the response has an error; {@code false} otherwise
     * @see #getHttpStatusCode(ClientHttpResponse)
     */
    protected boolean hasError(HttpStatus statusCode) {
        return (statusCode.series() == HttpStatus.Series.CLIENT_ERROR ||
                statusCode.series() == HttpStatus.Series.SERVER_ERROR);
    }

    protected HttpStatus getHttpStatusCode(ClientHttpResponse response) throws IOException {
        try {
            return response.getStatusCode();
        } catch (IllegalArgumentException ex) {
            throw new UnknownHttpStatusCodeException(response.getRawStatusCode(), response.getStatusText(),
                    response.getHeaders(), getResponseBody(response), getCharset(response));
        }
    }

    protected byte[] getResponseBody(ClientHttpResponse response) {
        try {
            return FileCopyUtils.copyToByteArray(response.getBody());
        } catch (IOException ex) {
            // ignore
        }
        return new byte[0];
    }

    protected Charset getCharset(ClientHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        MediaType contentType = headers.getContentType();
        return (contentType != null ? contentType.getCharset() : null);
    }
}
