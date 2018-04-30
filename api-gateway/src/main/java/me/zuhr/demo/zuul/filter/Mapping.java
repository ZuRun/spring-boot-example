package me.zuhr.demo.zuul.filter;

import me.zuhr.demo.zuul.filter.service.Common;
import me.zuhr.demo.zuul.filter.service.ServiceFilter;
import me.zuhr.demo.zuul.filter.service.WxApp;

/**
 * @author zurun
 * @date 2018/4/30 22:20:52
 */
public enum Mapping {
    WXAPP("", new WxApp()),
    COMMON("", new Common());

    private String value;
    private ServiceFilter check;

    Mapping(String value, ServiceFilter check) {
        this.value = value;
        this.check = check;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ServiceFilter getCheck() {
        return check;
    }

    public void setCheck(ServiceFilter check) {
        this.check = check;
    }

    public static Mapping getByName(String name) {
        for (Mapping mapping : Mapping.values()) {
            if (mapping.name().equalsIgnoreCase(name)) {
                return mapping;
            }
        }
        return Mapping.COMMON;
    }

}
