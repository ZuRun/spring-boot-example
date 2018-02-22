package me.zuhr.demo.redis.vo;

import me.zuhr.demo.redis.enuma.SexEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author zurun
 * @date 2018/2/14 23:04:27
 */
public class UserVo implements Serializable{
    private String name;
    private SexEnum sexEnum;
    private Integer age;
    private Double weight;
    private Long amount;
    private BigDecimal money;
    private ClassVo classVo;
    private List<ClassVo> classVos=new ArrayList<>();
    private List<Map> list=new ArrayList<>();


    @Override
    public String toString() {
        return "UserVo{" +
                "name='" + name + '\'' +
                ", sexEnum=" + sexEnum +
                ", age=" + age +
                ", weight=" + weight +
                ", amount=" + amount +
                ", money=" + money +
                ", classVo=" + classVo +
                ", classVos=" + classVos +
                ", list=" + list +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SexEnum getSexEnum() {
        return sexEnum;
    }

    public void setSexEnum(SexEnum sexEnum) {
        this.sexEnum = sexEnum;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public ClassVo getClassVo() {
        return classVo;
    }

    public void setClassVo(ClassVo classVo) {
        this.classVo = classVo;
    }

    public List<Map> getList() {
        return list;
    }

    public void addList(Map map){
        list.add(map);
    }

    public List<ClassVo> getClassVos() {
        return classVos;
    }
    public void addClass(ClassVo classVo){
        classVos.add(classVo);
    }
}
