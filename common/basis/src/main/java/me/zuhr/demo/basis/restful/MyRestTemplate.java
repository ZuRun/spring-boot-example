package me.zuhr.demo.basis.restful;

import org.springframework.web.client.RestTemplate;

/**
 * RESTful 设计规范下,根据实际情况对RestTemplate的封装
 *
 * @author zurun
 * @date 2018/2/24 17:42:56
 */
public class MyRestTemplate extends RestTemplate {

    public MyRestTemplate() {
        // 自定义状态码相应策略
        this.setErrorHandler(new MyResponseErrorHandler());
    }

    public <T> T post(String url) {
//        postForEntity();
        return null;
    }
}
