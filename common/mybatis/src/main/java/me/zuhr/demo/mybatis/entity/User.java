package me.zuhr.demo.mybatis.entity;

import lombok.Data;

/**
 * @author zurun
 */
@Data
public class User {
    private Long id;

    private String userName;

    private String password;

    private String phone;

    private Integer age;


    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }


    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }


    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

}