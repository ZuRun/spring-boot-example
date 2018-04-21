package me.zuhr.demo.basis.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @author zurun
 * @date 2018/3/11 12:51:57
 */
@Data
public abstract class BaseResult<T> {
    private int code = 0;
    private String message;
    private T data;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
