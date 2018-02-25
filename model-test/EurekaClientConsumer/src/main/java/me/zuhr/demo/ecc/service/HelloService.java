package me.zuhr.demo.ecc.service;

import me.zuhr.demo.basis.enumration.ServiceNameEnum;
import me.zuhr.demo.basis.restful.MyRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zurun
 * @date 2018/2/24 11:55:15
 */
@Service
public class HelloService {
    @Autowired
    MyRestTemplate restTemplate;

    public String test() {
        ResponseEntity<Map> rmap = restTemplate.getForEntity("http://" + ServiceNameEnum.ECS.getValue() + "/handle", Map.class);


        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://" + ServiceNameEnum.ECS.getValue() + "/hello", String.class);
        String body = responseEntity.getBody();
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int statusCode = responseEntity.getStatusCodeValue();
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        Map<String, String> map = responseEntity.getHeaders().toSingleValueMap();
        return body;
    }
}
