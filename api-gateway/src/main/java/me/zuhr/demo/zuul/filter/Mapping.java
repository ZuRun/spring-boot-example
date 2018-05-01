package me.zuhr.demo.zuul.filter;

import me.zuhr.demo.zuul.filter.service.Common;
import me.zuhr.demo.zuul.filter.service.ServiceFilter;
import me.zuhr.demo.zuul.filter.service.WxApp;

/**
 * @author zurun
 * @date 2018/4/30 22:20:52
 */
public enum Mapping {
    WXAPP("", WxApp.class),
    COMMON("", Common.class);

    private String value;
    private Class<? extends ServiceFilter> clazz;

    Mapping(String value, Class<? extends ServiceFilter> clazz) {
        this.value = value;
        this.clazz = clazz;
    }

    public String getValue() {
        return value;
    }


    public Class<? extends ServiceFilter> getClazz() {
        return clazz;
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
