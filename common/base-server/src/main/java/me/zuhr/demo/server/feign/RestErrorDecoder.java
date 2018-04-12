package me.zuhr.demo.server.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import me.zuhr.demo.basis.exception.BusinessException;

/**
 * 处理response返回的异常
 *
 * @author ZuRun
 * @date 2018/04/13 00:01:32
 */
public class RestErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
//        response.headers().get()

        return new BusinessException("feignException");
    }
}
