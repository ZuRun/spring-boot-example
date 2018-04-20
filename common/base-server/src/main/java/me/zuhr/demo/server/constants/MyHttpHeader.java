package me.zuhr.demo.server.constants;


/**
 * @author zurun
 * @date 2018/3/22 23:38:38
 */
public interface MyHttpHeader {
    /**
     * 获取header的name
     *
     * @return
     */
    String getHeaderName();

    /**
     * 获取header的属性
     *
     * @return
     */
    String getHeaderValue();


}
