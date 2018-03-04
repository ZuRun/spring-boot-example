package me.zuhr.demo.server.restful;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.zuhr.demo.basis.model.Json;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

/**
 * RESTful 设计规范下,根据实际情况对RestTemplate的封装
 *
 * @author zurun
 * @date 2018/2/24 17:42:56
 */
public class MyRestTemplate extends RestTemplate {
    private ObjectMapper objectMapper = new ObjectMapper();

    public MyRestTemplate() {
        // 自定义状态码相应策略
        this.setErrorHandler(new MyResponseErrorHandler());

    }

    public <T> Json post(String url, Object request, Class<T> responseType, Object... uriVariables) {
        Json json = postForObject(url, request, Json.class, uriVariables);
        Object obj = json.getObj();
        T t = objectMapper.convertValue(obj, responseType);

        json.setObj(t);
        return json;
    }

    public <T> Json post(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) {
        Json json = postForObject(url, request, Json.class, uriVariables);
        json.setObj(objectMapper.convertValue(json.getObj(), responseType));
        return json;
    }

    public <T> Json post(URI url, Object request, Class<T> responseType) {
        Json json = postForObject(url, request, Json.class);
        json.setObj(objectMapper.convertValue(json.getObj(), responseType));
        return json;
    }

}
