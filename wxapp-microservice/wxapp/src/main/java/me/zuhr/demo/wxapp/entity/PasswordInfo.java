package me.zuhr.demo.wxapp.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import me.zuhr.demo.mybatisplus.base.BaseEntity;

/**
 * 密码管理之密码信息
 *
 * @author zurun
 * @date 2018/5/9 00:22:13
 */
@Data
@TableName("t_wxapp_password_info")
public class PasswordInfo extends BaseEntity {

    private String openId;

    /**
     * 账号name
     */
    private String name;

    /**
     * 密文
     */
    private String cipherText;

    /**
     * 随机盐
     */
    private String salt;

}
