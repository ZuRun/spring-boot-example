package me.zuhr.demo.wxapp.vo;

import lombok.Data;

/**
 * @author zurun
 * @date 2018/5/12 22:12:13
 */
@Data
public class SimplePassWordInfoVo {
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
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String modifiedTime;
}
