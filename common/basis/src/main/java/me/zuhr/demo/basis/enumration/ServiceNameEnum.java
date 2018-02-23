package me.zuhr.demo.basis.enumration;

/**
 * @author zurun
 * @date 2018/2/24 00:15:24
 */
public enum ServiceNameEnum {

    ECS("eureka-client-service"),
    ECC("eureka-client-consumer");

    private String value;

    ServiceNameEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
