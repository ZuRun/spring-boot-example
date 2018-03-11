package me.zuhr.demo.basis.model;

import com.alibaba.fastjson.JSON;

/**
 * @author zurun
 * @date 2018/3/11 12:51:57
 */
public abstract class BaseResult<T> {
    private int code = 0;
    private String message;
    private T result;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
