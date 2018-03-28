package me.zuhr.demo.server.constants;

import me.zuhr.demo.server.restful.MyResponseErrorHandler;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author zurun
 * @date 2018/3/22 23:38:38
 */
public interface MyHttpHeader {
    /**
     * 获取header的name
     *
     * @return
     */
    String getHeaderName();

    /**
     * 获取header的属性
     *
     * @return
     */
    String getHeaderValue();


}