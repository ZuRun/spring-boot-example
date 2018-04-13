package me.zuhr.demo.server.feign;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import feign.Response;
import feign.codec.ErrorDecoder;
import me.zuhr.demo.server.enumration.HttpHeader;
import me.zuhr.demo.server.exception.RestUnhandledException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 处理response返回的异常
 *
 * @author ZuRun
 * @date 2018/04/13 00:01:32
 */
public class RestErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus httpStatus = HttpStatus.valueOf(response.status());

        String body;
        try {
            // 默认utf-8
            body = CharStreams.toString(new InputStreamReader(response.body().asInputStream(), Charsets.UTF_8));
        } catch (IOException e) {
            body = "body解析失败!";
        }
        List<String> list;
        if (response.headers().containsKey(HttpHeader.EXCEPTION_TYPE_HEADER.getValue())) {
            list = (List<String>) response.headers().get(HttpHeader.EXCEPTION_TYPE_HEADER.getValue());
            String exceptionTypeValue = list != null ? list.get(0) : null;
            HttpHeader.ExceptionType exceptionType = HttpHeader.ExceptionType.getByName(exceptionTypeValue);
            if (exceptionType != null) {
                // 如果是项目中定义的异常类型,执行相应的策略,如果返回true,不在执行后续检查
                if (exceptionType.handleError(httpStatus, body)) {
                    return null;
                }
            }
        }

        return new RestUnhandledException(httpStatus, HttpHeader.ExceptionType.UNKNOWN, "解析异常失败:" + body);
    }
}
