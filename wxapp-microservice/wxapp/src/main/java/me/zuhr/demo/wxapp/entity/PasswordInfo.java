package me.zuhr.demo.wxapp.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import me.zuhr.demo.basis.utils.AesUtils;
import me.zuhr.demo.mybatisplus.base.BaseEntity;
import me.zuhr.demo.wxapp.vo.PassWordInfoVo;

/**
 * 密码管理之密码信息
 *
 * @author zurun
 * @date 2018/5/9 00:22:13
 */
@Data
@TableName("t_wxapp_password_info")
public class PasswordInfo extends BaseEntity {

    private String openid;

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

    /**
     * 解密
     *
     * @param userSecret
     * @return
     */
    public String decryption(String userSecret) {
        return AesUtils.decrypt(this.cipherText, userSecret);
    }

    /**
     * 将密文解密,并转为vo返回
     *
     * @param userSecret
     * @return
     */
    public PassWordInfoVo decryptionToVo(String userSecret) {
        return JSONObject.parseObject(decryption(userSecret), PassWordInfoVo.class);
    }

}
