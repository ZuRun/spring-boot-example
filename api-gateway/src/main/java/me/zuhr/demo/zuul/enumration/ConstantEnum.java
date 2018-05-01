package me.zuhr.demo.zuul.enumration;

/**
 * @author zurun
 * @date 2018/4/30 22:46:45
 */
public enum ConstantEnum {

    LOGIN("/login");

    private String value;

    ConstantEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
