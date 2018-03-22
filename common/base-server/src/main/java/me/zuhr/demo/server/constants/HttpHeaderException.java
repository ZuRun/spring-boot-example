package me.zuhr.demo.server.constants;

import me.zuhr.demo.server.restful.MyResponseErrorHandler;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author zurun
 * @date 2018/3/23 00:34:04
 */
public interface HttpHeaderException extends MyHttpHeader {
    /**
     * 处理异常
     *
     * @param msg
     * @return 返回true表示不再拦截, 直接return, 不执行后面的判断语句了
     * @see MyResponseErrorHandler#handleError(ClientHttpResponse)
     */
    boolean handleError(String msg);
}
