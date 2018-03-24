package me.zuhr.demo.server.restful;

import me.zuhr.demo.server.enumration.HttpHeader;
import me.zuhr.demo.server.exception.RestHttpClientException;
import me.zuhr.demo.server.exception.RestHttpServerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 自定义状态码相应策略
 * RestTemplate默认对4和5开头的状态码 抛异常处理
 * 现在约定项目中微服务之间的通信,使用restful规范,对于非2开头的状态码统一处理
 * <p>
 * hasError()方法不变
 * handleError()中自定义异常处理
 *
 * @author zurun
 * @date 2018/2/24 22:50:00
 */
public class MyResponseErrorHandler implements ResponseErrorHandler {

    /**
     * 4开头和5开头的是状态码代表有异常
     *
     * @param response
     * @return
     * @throws IOException
     * @see DefaultResponseErrorHandler#hasError(ClientHttpResponse)
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        try {
            return hasError(getHttpStatusCode(response));
        } catch (UnknownHttpStatusCodeException ex) {
            return false;
        }
    }


    /**
     * 抛出自定义异常,主要为了能将responseBody中的内容作为异常的message
     *
     * @param response
     * @throws IOException
     * @see DefaultResponseErrorHandler#handleError(ClientHttpResponse)
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = getHttpStatusCode(response);
        byte[] responseBodyBytes = getResponseBody(response);
        Charset charset = getCharset(response);
        // body转为String
        String body = responseBodyBytes.length == 0 ? null : new String(responseBodyBytes, charset);

        // 自定义的 异常类型 请求头
        String exceptionTypeValue = response.getHeaders().getFirst(HttpHeader.EXCEPTION_TYPE_HEADER.getValue());
        HttpHeader.ExceptionType exceptionType = HttpHeader.ExceptionType.getByName(exceptionTypeValue);
        if (exceptionType != null) {
            // 如果是项目中定义的异常类型,执行相应的策略,如果返回true,不在执行后续检查
            if (exceptionType.handleError(body)) {
                return;
            }
        }

        switch (statusCode.series()) {
            case CLIENT_ERROR: {
                // 4开头的状态码HttpClientErrorException HttpServerErrorException
                throw new RestHttpClientException(statusCode, response.getStatusText(),
                        response.getHeaders(), responseBodyBytes, charset);
            }
            case SERVER_ERROR: {
                // 5开头的状态码
                throw new RestHttpServerException(statusCode, response.getStatusText(),
                        response.getHeaders(), responseBodyBytes, charset);
            }
            default:
                throw new RestClientException("Unknown status code [" + statusCode + "]");
        }
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
        // 没有使用默认charset,不要返回null
        return (contentType != null ? contentType.getCharset() : Charset.defaultCharset());
    }
}
