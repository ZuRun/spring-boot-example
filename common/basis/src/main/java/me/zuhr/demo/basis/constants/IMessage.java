package me.zuhr.demo.basis.constants;

/**
 * @author zurun
 * @date 2018/3/20 23:46:11
 */
public interface IMessage {

    /**
     * 获取异常码
     *
     * @return
     */
    int getErrCode();

    /**
     * 获取异常描述信息
     *
     * @return
     */
    String getErrMsg();
}
