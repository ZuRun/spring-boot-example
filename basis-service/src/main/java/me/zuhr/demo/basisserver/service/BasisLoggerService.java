package me.zuhr.demo.basisserver.service;

import me.zuhr.demo.basisserver.entity.ExceptionLogger;
import me.zuhr.demo.basisserver.entity.RequestLogger;
import me.zuhr.demo.basisserver.jpa.ExceptionLoggerJpa;
import me.zuhr.demo.basisserver.jpa.RequestLoggerJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author zurun
 * @date 2018/3/18 00:55:26
 */
@Service
@Transactional
public class BasisLoggerService {
    @Autowired
    RequestLoggerJpa requestLoggerJpa;
    @Autowired
    ExceptionLoggerJpa exceptionLoggerJpa;

    /**
     * 保存请求日志
     *
     * @param requestLogger
     */
    public void saveRequestLogger(RequestLogger requestLogger) {
        requestLoggerJpa.save(requestLogger);
    }

    /**
     * 保存异常信息
     *
     * @param exceptionLogger
     */
    public void saveExceptionLogger(ExceptionLogger exceptionLogger) {
        exceptionLoggerJpa.save(exceptionLogger);
    }
}
