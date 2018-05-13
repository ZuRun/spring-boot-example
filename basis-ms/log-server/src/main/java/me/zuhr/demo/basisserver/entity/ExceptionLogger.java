package me.zuhr.demo.basisserver.entity;

import lombok.Data;
import me.zuhr.demo.jpa.base.AbstractHibernateEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author zurun
 * @date 2018/3/19 00:38:50
 */
@Data
@Entity
@Table(name = "t_logger_exception")
@EntityListeners(AuditingEntityListener.class)
public class ExceptionLogger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createTime;

    /**
     * 请求时间
     */
    @Column(name = "time", insertable = true)
    private Timestamp time;
    /**
     * 错误码
     */
    @Column(name = "error_code")
    private String errCode;
    /**
     * 错误信息
     */
    @Column(name = "error_message")
    private String errMsg;
    /**
     * 异常类型
     */
    @Column(name = "exception_name")
    private String exceptionName;
    /**
     * 服务名
     */
    @Column(name = "server_name")
    private String serverName;

    /**
     * 根本导致原因
     */
    @Column(name = "cause_by")
    private String causeBy;
    /**
     * 堆栈跟踪信息
     */
    @Column(name = "stack_trace")
    private String stackTrace;
}
