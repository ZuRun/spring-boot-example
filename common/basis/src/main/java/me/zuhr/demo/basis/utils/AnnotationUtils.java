package me.zuhr.demo.basis.utils;

/**
 * 注解工具类
 *
 * @author zurun
 * @date 2018/2/13 01:27:16
 */
public class AnnotationUtils {

    /**
     * 类型转换,主要用途为去掉烦人的 unchecked cast warning 提示
     *
     * @param obj
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}
