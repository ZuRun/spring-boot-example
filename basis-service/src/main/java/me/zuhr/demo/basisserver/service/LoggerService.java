package me.zuhr.demo.basisserver.service;

import me.zuhr.demo.basisserver.entity.RequestLogger;
import me.zuhr.demo.basisserver.jpa.RequestLoggerJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author zurun
 * @date 2018/3/18 00:55:26
 */
@Service
public class LoggerService {
    @Autowired
    RequestLoggerJpa requestLoggerJpa;

    /**
     * 保存请求日志
     * 此方法开启事务
     *
     * @param requestLogger
     */
    @Transactional
    public void saveRequestLogger(RequestLogger requestLogger) {
        requestLoggerJpa.save(requestLogger);
    }
}
