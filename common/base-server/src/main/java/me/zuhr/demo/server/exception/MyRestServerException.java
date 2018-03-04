package me.zuhr.demo.server.exception;

/**
 * 此异常去掉异常栈构造,减少开销
 *
 * @author zurun
 * @date 2018/2/25 13:19:15
 */
public class MyRestServerException extends RuntimeException {
    private static final long serialVersionUID = -19999999999802L;

    public MyRestServerException(String message) {
        super(message);
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
