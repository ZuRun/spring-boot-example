/*
 * Copyright WelinkSoft Ltd. (c) 2013.
 */

package me.zuhr.demo.basis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * JSON模型
 * <p/>
 * 用户后台向前台返回的JSON
 * 对象
 */
public class Json implements Serializable {
    private static final long serialVersionUID = 111L;

    private String logInfo;//错误信息

    @JsonIgnore//作用是json序列化时将java bean中的一些属性忽略掉,序列化和反序列化都受影响。
    public String getLogInfo() {
        return logInfo;
    }

    protected boolean success = false;

    protected int code = 0;

    public boolean isSuccess() {
        return success;
    }


    private String msg = "";

    private Object obj = null;

    public Map<String, Object> getOthers() {
        return others;
    }

    public void setOthers(Map<String, Object> others) {
        this.others = others;
    }

    public Json addOthers(String key, Object other) {
        if (this.others == null)
            this.others = new HashMap<String, Object>();
        this.others.put(key, other);
        return this;
    }

    private Map<String, Object> others;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Json addObj(Object obj) {
        this.obj = obj;
        return this;
    }

    /**
     * 设置提示信息
     *
     * @param logInfo
     * @return
     */
    public Json setLogInfo(String logInfo) {//这个是明确显示友好信息的。
        this.logInfo = logInfo;
        return this;
    }

    public static Json ok() {
        Json j = new Json();
        j.setSuccess(true);
        return j;
    }

    public static Json ok(String msg) {
        Json j = new Json();
        j.setSuccess(true);
        j.setMsg(msg);
        return j;
    }

    public static Json fail(String msg) {
        Json j = new Json();
        j.setSuccess(false);
        j.setMsg(msg);
        return j;
    }

    public static Map<String, Object> failForMobile(String msg) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("success", "error");
        ret.put("msg", msg);
        return ret;
    }

    public static Map<String, Object> okForMobile(String msg) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("success", "success");
        ret.put("msg", msg);
        return ret;
    }

    public static Map<String, Object> success(String msg) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("success", true);
        ret.put("msg", msg);
        return ret;
    }

    public static Map<String, Object> breakDown(String msg) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("success", true);
        ret.put("msg", msg);
        return ret;
    }

    public int getCode() {
        return code;
    }

    public Json setCode(int code) {
        this.code = code;
        return this;
    }
}
