
## 参考
[spring boot 利用redisson实现redis的分布式锁](http://liaoke0123.iteye.com/blog/2375469)

## 使用

### 方法注解使用
方法上加`@DistributedLock`注解

### 直接使用
- 注入DistributedLockTemplate
- 示例
```
Result result = lockTemplate.lockIfAbsent("_lockName", (isLocked) -> {
    System.out.println("锁");
    try {
        Thread.sleep(1L);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    if (isLocked) {
        return Result.ok();
    } else {
        return Result.fail("");
    }

}, 20);
System.out.println(result);

```