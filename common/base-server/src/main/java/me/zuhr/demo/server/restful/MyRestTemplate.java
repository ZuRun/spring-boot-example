package me.zuhr.demo.server.restful;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.zuhr.demo.basis.enumration.ServiceNameEnum;
import me.zuhr.demo.basis.model.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * RESTful 设计规范下,根据实际情况对RestTemplate的封装
 *
 * @param <T>
 * @author zurun
 * @date 2018/2/24 17:42:56
 */
public class MyRestTemplate<T> extends RestTemplate {
    private ObjectMapper objectMapper = new ObjectMapper();

    public MyRestTemplate() {
        // 自定义状态码相应策略
        this.setErrorHandler(new MyResponseErrorHandler());

    }

    public Result<T> postForObject(ServiceNameEnum serviceNameEnum, String uri, Object requestBody, Class<T> c, Object... uriVariables) {
        Result<T> result = super.postForObject(createUrl(serviceNameEnum, uri), requestBody, Result.class, uriVariables);
        result.addResult(objectMapper.convertValue(result.getData(), c));
        return result;
    }

    public Result<T> postForObject(ServiceNameEnum serviceNameEnum, String uri, Object requestBody, Class<T> c, Map<String, ?> map) {
        Result<T> result = super.postForObject(createUrl(serviceNameEnum, uri), requestBody, Result.class, map);
        result.addResult(objectMapper.convertValue(result.getData(), c));
        return result;
    }

    public Result<T> getForObject(ServiceNameEnum serviceNameEnum, String uri, Class<T> c, Object... uriVariables) {
        Result<T> result = super.getForObject(createUrl(serviceNameEnum, uri), Result.class, uriVariables);
        result.addResult(objectMapper.convertValue(result.getData(), c));
        return result;
    }

    public Result<T> getForObject(ServiceNameEnum serviceNameEnum, String uri, Class<T> c, Map<String, ?> map) {
        Result<T> result = super.getForObject(createUrl(serviceNameEnum, uri), Result.class, map);
        result.addResult(objectMapper.convertValue(result.getData(), c));
        return result;
    }

    protected String createUrl(ServiceNameEnum serviceNameEnum, String uri) {
        Assert.notNull(serviceNameEnum.getValue(), "ServiceName不能为null");
        if (StringUtils.isNotBlank(uri) && !uri.startsWith("/")) {
            uri = "/" + uri;
        }
        return "http://" + serviceNameEnum.getValue() + uri;
    }

}
