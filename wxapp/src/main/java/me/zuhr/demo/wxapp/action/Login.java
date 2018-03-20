package me.zuhr.demo.wxapp.action;

import me.zuhr.demo.wxapp.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/3/19 12:26:54
 */
@RestController
public class Login {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    LoginService loginService;


    @RequestMapping(value = "login")
    public String login(String code) {
        String result= loginService.login(code);
        logger.warn(result);
        return result;
    }
}
