package me.zuhr.demo.wxapp.vo;

import lombok.Data;

/**
 * @author zurun
 * @date 2018/4/29 18:24:39
 */
@Data
public class SessionVo extends WxError{

    private String openid;
    private String session_key;
    private String unionid;

}
