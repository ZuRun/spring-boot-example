package me.zuhr.demo.action;

import me.zuhr.demo.basis.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zurun
 * @date 2018/2/11 10:34:37
 */
@Controller
public class MainAction {
    @Autowired
    ConfigBean configBean;

    @RequestMapping("/index")
    @ResponseBody
    public String index() {
        return "Hello!" + configBean.getName() + ";age:" + configBean.getAge();
    }

    @RequestMapping("/main")
    public String main() {
        return "test.html";
    }
}
