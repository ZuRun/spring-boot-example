package me.zuhr.demo.std;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zurun
 * @date 2018/5/9 10:24:40
 */
public abstract class AbstractControllerOrService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 看起来是线程不安全的,其实是安全的,可以研究下
     */
    @Autowired
    protected HttpServletRequest request;

}
