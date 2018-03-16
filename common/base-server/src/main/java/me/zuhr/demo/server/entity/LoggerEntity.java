package me.zuhr.demo.server.entity;

import java.io.Serializable;

/**
 * 项目名称：HengYuLogger
 * 项目版本：V1.0
 * 包名称：com.hengyu.logger.entity
 * 创建人：yuqy
 * 创建时间：2017/3/28 15:57
 * 修改人：yuqy
 * 修改时间：2017/3/28 15:57
 * 修改备注：
 */
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
    private Long createTime;
    /**
     * 接口返回时间
     */
    private Long returnTime;
    /**
     * 接口返回数据json
     */
    private String returnData;
    /**
     * 请求时httpStatusCode代码，如：200,400,404等
     */
    private String httpStatusCode;
    /**
     * 请求耗时秒单位
     */
    private Long timeConsuming;


    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Long returnTime) {
        this.returnTime = returnTime;
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public Long getTimeConsuming() {
        return timeConsuming;
    }

    public void setTimeConsuming(Long timeConsuming) {
        this.timeConsuming = timeConsuming;
    }

    @Override
    public String toString() {
        return "LoggerEntity{" +
                "clientIp='" + clientIp + '\'' +
                ", uri='" + uri + '\'' +
                ", type='" + type + '\'' +
                ", method='" + method + '\'' +
                ", paramData='" + paramData + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", createTime=" + createTime +
                ", returnTime='" + returnTime + '\'' +
                ", returnData='" + returnData + '\'' +
                ", httpStatusCode='" + httpStatusCode + '\'' +
                ", timeConsuming=" + timeConsuming +
                '}';
    }
}
