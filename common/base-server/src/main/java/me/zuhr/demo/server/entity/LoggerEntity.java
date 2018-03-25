package me.zuhr.demo.server.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * @author zurun
 */
@Data
public class LoggerEntity implements Serializable {
    /**
     * 客户端请求ip
     */
    private String clientIp;
    /**
     * 客户端请求路径
     */
    private String uri;
    /**
     * 服务端地址,用来区分机器
     */
    private String localAddr;
    /**
     * 服务方端口号LocalPort,用来区分机器
     */
    private int localPort;
    /**
     * 微服务名
     */
    private String serverName;
    /**
     * 终端请求方式,普通请求,ajax请求
     */
    private String type;
    /**
     * 请求方式method,post,get等
     */
    private String method;
    /**
     * 请求参数内容,json
     */
    private String paramData;
    /**
     * 请求接口唯一session标识
     */
    private String sessionId;
    /**
     * 请求时间
     */
    private Long requestCreateTime;
    /**
     * 接口返回时间
     */
    private Long responseReturnTime;

    /**
     * 请求时httpStatusCode代码，如：200,400,404等
     */
    private String httpStatusCode;
    /**
     * 请求耗时秒单位
     */
    private Long timeConsuming;


}
