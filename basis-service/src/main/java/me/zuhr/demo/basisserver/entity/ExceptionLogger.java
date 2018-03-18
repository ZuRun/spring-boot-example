package me.zuhr.demo.basisserver.entity;

import lombok.Data;
import me.zuhr.demo.jpa.base.AbstractHibernateEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author zurun
 * @date 2018/3/19 00:38:50
 */
@Data
@Entity
@Table(name = "t_logger_exception")
public class ExceptionLogger extends AbstractHibernateEntity {

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
