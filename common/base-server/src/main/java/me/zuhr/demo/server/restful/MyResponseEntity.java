package me.zuhr.demo.server.restful;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * @author zurun
 * @date 2018/2/26 17:54:58
 */
public class MyResponseEntity extends ResponseEntity {

    public static ResponseEntity fail(Object obj) {
        return ResponseEntity.status(999).body(obj);
    }

    public MyResponseEntity(HttpStatus status) {
        super(status);
    }

    public MyResponseEntity(Object body, HttpStatus status) {
        super(body, status);
    }

    public MyResponseEntity(MultiValueMap headers, HttpStatus status) {
        super(headers, status);
    }

    public MyResponseEntity(Object body, MultiValueMap headers, HttpStatus status) {
        super(body, headers, status);
    }
}
