package me.zuhr.demo.ecc;

/**
 * @author zurun
 * @date 2018/2/26 17:14:23
 */
public class UserVo2 {
    private String name;
    private int age;

    public UserVo2(){

    }

    public UserVo2(String name, int age){
        this.name=name;
        this.age=age;
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

    @Override
    public String toString() {
        return "UserVo2{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
