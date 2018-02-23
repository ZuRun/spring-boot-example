package me.zuhr.demo.redis.enumration;

/**
 * @author zurun
 * @date 2018/2/22 18:09:10
 */
public enum SexEnum {
    MAN("男"),
    WOMAN("女");

    SexEnum(String value){}

    private String value;

    public String getValue() {
        return value;
    }
}
