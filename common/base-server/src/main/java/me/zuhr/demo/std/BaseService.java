package me.zuhr.demo.std;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.zuhr.demo.server.restful.MyRestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;

/**
 * @author zurun
 * @date 2018/3/11 12:31:23
 */
public class BaseService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    protected static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    @Qualifier("restTemplate")
    protected RestTemplate defaultRestTemplate;

    @Autowired
    protected MyRestTemplate restTemplate;


}
