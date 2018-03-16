package me.zuhr.demo.server.action;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zurun
 * @date 2018/3/9 00:59:04
 */
@RestController
public class SystemAction {
    @Value("${spring.application.name}")
    private String applicationName;

    @RequestMapping("info")
    public JSONObject info(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("HostName", SystemInfo.getInstance().getHostName());
//        jsonObject.put("Ip", SystemInfo.getInstance().getIP());
//        jsonObject.put("Mac", SystemInfo.getInstance().getMac());
//        jsonObject.put("SystemName", SystemInfo.getInstance().getSystemName());
        jsonObject.put("localPort", request.getLocalPort());
        jsonObject.put("applicationName", applicationName);
        return jsonObject;
    }
}
