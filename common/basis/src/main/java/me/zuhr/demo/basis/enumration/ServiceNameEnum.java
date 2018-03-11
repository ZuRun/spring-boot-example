package me.zuhr.demo.basis.enumration;

/**
 * @author zurun
 * @date 2018/2/24 00:15:24
 */
public enum ServiceNameEnum {
    // eureka-client-service
    ECP("ecp"),
    // eureka-client-consumer
    ECC("ecc");

    private String value;

    ServiceNameEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
