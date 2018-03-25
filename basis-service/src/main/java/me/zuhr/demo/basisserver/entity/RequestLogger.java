package me.zuhr.demo.basisserver.entity;

import lombok.Data;
import me.zuhr.demo.jpa.base.AbstractHibernateEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author zurun
 * @date 2018/3/17 22:25:09
 */
@Data
@Entity
@Table(name = "t_logger_request")
public class RequestLogger extends AbstractHibernateEntity {
    /**
     * 客户端请求ip
     */
    @Column(name = "client_ip")
    private String clientIp;
    /**
     * 客户端请求路径
     */
    @Column(name = "uri")
    private String uri;
    /**
     * 服务端地址,用来区分机器
     */
    @Column(name = "local_addr")
    private String localAddr;
    /**
     * 服务方端口号LocalPort,用来区分机器
     */
    @Column(name = "local_port")
    private int localPort;
    /**
     * 微服务名
     */
    @Column(name = "server_name")
    private String serverName;
    /**
     * 终端请求方式,普通请求,ajax请求
     */
    @Column(name = "type")
    private String type;
    /**
     * 请求方式method,post,get等
     */
    @Column(name = "method")
    private String method;
    /**
     * 请求参数内容,json
     */
    @Column(name = "param_data")
    private String paramData;
    /**
     * 请求接口唯一session标识
     */
    @Column(name = "session_id")
    private String sessionId;
    /**
     * 请求时间
     */
    @Column(name = "request_create_time", insertable = true)
    private Timestamp requestCreateTime;
    /**
     * 接口返回时间
     */
    @Column(name = "response_return_time")
    private Timestamp responseReturnTime;

    /**
     * 请求时httpStatusCode代码，如：200,400,404等
     */
    @Column(name = "http_status_code")
    private String httpStatusCode;
    /**
     * 请求耗时单位
     */
    @Column(name = "time_consuming")
    private int timeConsuming;

    /**
     * 忽略修改时间
     */
    @Transient
    private Date lastModifiedTime;
}
