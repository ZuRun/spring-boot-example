package me.zuhr.demo.wxapp.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 如果ercode为null,表示腾讯那边服务器返回无错误
 *
 * @author zurun
 * @date 2018/4/29 18:18:55
 */
@Data
public class WxError {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Integer errcode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String errmsg;

    /**
     * 是否成功
     *
     * @return
     */
    public boolean success() {
        return errcode == null || errcode == 0;
    }
}
