package me.zuhr.demo.wxapp.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import me.zuhr.demo.mybatisplus.base.BaseEntity;

/**
 * @author zurun
 * @date 2018/5/1 15:38:25
 */
@Data
@TableName("t_wxapp_user")
public class WxAppUser extends BaseEntity {
    private String openid;

    private String unionid;

    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 性别 值为1时是男性，值为2时是女性，值为0时是未知
     */
    private Integer gender;
    /**
     * 用户的语言，简体中文为zh_CN
     */
    private String language;
    /**
     * 用户所在省份
     */
    private String province;
    private String city;
    private String country;
    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
    private String avatarUrl;
}
