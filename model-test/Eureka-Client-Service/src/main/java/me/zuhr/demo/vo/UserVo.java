package me.zuhr.demo.vo;

/**
 * @author zurun
 * @date 2018/2/26 17:10:51
 */
public class UserVo {
    private String name;
    private int age;

    public UserVo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
