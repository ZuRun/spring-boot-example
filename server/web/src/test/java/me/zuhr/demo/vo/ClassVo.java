package me.zuhr.demo.vo;

import java.io.Serializable;

/**
 * @author zurun
 * @date 2018/2/15 00:13:15
 */
public class ClassVo  implements Serializable {
    private String name;
    private Integer num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "ClassVo{" +
                "name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}
