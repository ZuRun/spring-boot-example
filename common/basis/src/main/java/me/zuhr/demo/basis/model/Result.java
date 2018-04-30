package me.zuhr.demo.basis.model;

import me.zuhr.demo.basis.constants.ErrorCode;
import me.zuhr.demo.basis.constants.IMessage;

/**
 * @author zurun
 * @date 2018/3/11 13:14:18
 */
public class Result<T> extends BaseResult<T> {


    public Boolean isSuccess() {
        return 0 == getCode();
    }

    public Result<T> addResult(T t) {
        this.setData(t);
        return this;
    }

    public static Result ok() {
        Result result = new Result();
        return result;
    }

    public static Result ok(String message) {
        Result result = ok();
        result.setMessage(message);
        return result;
    }

    public static Result fail(String message) {
        return fail(ErrorCode.common.DEFAULT_FAIL_CODE.getErrCode(), message);
    }

    public static Result fail(IMessage errorCode) {
        return fail(errorCode.getErrCode(), errorCode.getErrMsg());
    }

    public static Result fail(int code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
