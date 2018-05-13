package me.zuhr.demo.wxapp.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author zurun
 * @date 2018/5/9 00:33:54
 */
@Data
public class PassWordInfoVo {
    private Long id;
    /**
     * 账号名称
     */
    private String name;

    /**
     * 账号
     */
    private String account;

    /**
     * 登录密码
     */
    private String loginPassword;
    /**
     * 支付密码
     */
    private String paymentPassword;

    /**
     * 备注
     */
    private String remark;

    /**
     * 自定义内容,存为json字符串
     */
    private JSONObject userDefined;

    public String toJsonString() {
        return JSONObject.toJSONString(this);
    }

}
