# rocketmq-externals
[详情见gitbub](https://github.com/apache/rocketmq-externals/tree/master/rocketmq-console)

## How To Install

### With Docker

* get docker image

```
mvn clean package -Dmaven.test.skip=true docker:build
```

or

```
docker pull styletang/rocketmq-console-ng
```
* run it (change namesvrAddr and port yourself)

```
docker run -e "JAVA_OPTS=-Drocketmq.namesrv.addr=127.0.0.1:9876 -Dcom.rocketmq.sendMessageWithVIPChannel=false" -p 21001:8080 -t styletang/rocketmq-console-ng
```
