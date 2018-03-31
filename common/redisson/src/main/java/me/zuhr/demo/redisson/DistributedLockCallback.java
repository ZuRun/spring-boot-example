package me.zuhr.demo.redisson;

/**
 * 分布式锁回调接口
 *
 * @author ZuRun
 * @date 2018/03/31 11:02:53
 */
public interface DistributedLockCallback<T> {
    /**
     * 调用者必须在此方法中实现需要加分布式锁的业务逻辑
     *
     * @param isLocked 是否上锁成功
     * @return
     */
    public T process(boolean isLocked);
//
//    /**
//     * 得到分布式锁名称
//     *
//     * @return
//     */
//    public String getLockName();

}
