package me.zuhr.demo.std;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.zuhr.demo.server.restful.MyRestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zurun
 * @date 2018/3/11 12:31:23
 */
public class BaseService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    protected static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    protected MyRestTemplate restTemplate;


}
