package me.zuhr.demo.server.action;

import com.alibaba.fastjson.JSONObject;
import me.zuhr.demo.basis.utils.SystemInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zurun
 * @date 2018/3/9 00:59:04
 */
@RestController
public class SystemAction {
    @RequestMapping("sysInfo")
    public JSONObject info(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("HostName", SystemInfo.getInstance().getHostName());
        jsonObject.put("Ip", SystemInfo.getInstance().getIP());
        jsonObject.put("Mac", SystemInfo.getInstance().getIP());
        jsonObject.put("SystemName", SystemInfo.getInstance().getSystemName());
        jsonObject.put("localPort", request.getLocalPort());
        jsonObject.put("serverPort", request.getServerPort());
        return jsonObject;
    }
}
