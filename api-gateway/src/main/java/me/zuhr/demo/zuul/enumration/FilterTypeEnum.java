package me.zuhr.demo.zuul.enumration;

/**
 * 该函数需要返回一个字符串来代表过滤器的类型，
 * 而这个类型就是在HTTP请求过程中定义的各个阶段。
 * 在Zuul中默认定义了四种不同生命周期的过滤器类型
 *
 * @author zurun
 * @date 2018/4/21 20:21:19
 */
public enum FilterTypeEnum {
    /**
     * 可以在请求被路由之前调用
     */
    PRE("pre"),
    /**
     * 在路由请求时候被调用
     */
    ROUTING("routing"),
    /**
     * 在routing和error过滤器之后被调用
     */
    POST("post"),
    /**
     * 处理请求时发生错误时被调用
     */
    ERROR("error");

    private String value;

    FilterTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
