package me.zuhr.demo.basis.exception;

/**
 * 业务异常
 * 此异常去掉异常栈构造,减少开销
 * 将由全局异常处理做业务处理
 *
 * @author zurun
 * @date 2018/3/11 12:59:07
 */
public class ServiceException extends RuntimeException {
    /**
     * 异常码/错误码,用于业务
     */
    private int errCode = 1000;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    /**
     * 记录错误码与错误信息
     *
     * @param errCode
     * @param message
     */
    public ServiceException(int errCode, String message) {
        super(message);
        this.errCode = errCode;

    }

    /**
     * 减少开销
     *
     * @return
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
