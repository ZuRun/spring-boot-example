
# docker环境下搭建单机rocketmq服务-详细
## **前期准备 **
### **下载docker镜像并启动**
- 下载镜像
`docker pull centos`
- 查看镜像
`docker images`
- 启动centos 镜像
`docker run -t -i --net=host --hostname centos --name centos -p 9876:9876 -p 10911:10911 -p 10909:10909 -d centos /bin/bash`
- 查看是否启动成功
`docker ps -a`
- 进入docker
`docker exec -ti centos /bin/bash`

### **依赖**
- wget 
 `yum install wget`
- maven依赖
```
wget http://mirrors.shu.edu.cn/apache/maven/maven-3/3.5.3/binaries/apache-maven-3.5.3-bin.tar.gz
tar -zxvf apache-maven-3.5.3-bin.tar.gz
vim /etc/profile
#环境变量
M2_HOME=/root/apache-maven-3.5.3
export PATH=${M2_HOME}/bin:${PATH}
#重载/etc/profile这个文件
 source /etc/profile
```
- jdk1.8
```
# 下载
# 解压
tar -zxvf jdk-8u161-linux-x64.tar.gz
# 环境变量 vim /etc/profile
 JAVA_HOME=/root/jdk1.8.0_161
 JRE_HOME=$JAVA_HOME/jre
 PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin
 CLASSPATH=:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:$JRE_HOME/lib/dt.jar
#重载/etc/profile这个文件
source /etc/profile
```

- maven 阿里镜像
修改 settings.xml,`<mirrors>`标签中增加以下内容
```
<mirror>
  <id>alimaven</id>
  <mirrorOf>central</mirrorOf>
  <name>aliyun maven</name>
  <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
</mirror>
```
### **[更改yum源为阿里云](http://blog.csdn.net/inslow/article/details/54177191)**

- 首先备份系统自带yum源配置文件/etc/yum.repos.d/CentOS-Base.repo

    `mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.backup`

- 下载ailiyun的yum源配置文件到/etc/yum.repos.d/

`wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo`

- 运行yum makecache生成缓存

`yum makecache`

- `yum -y update`

### **安装依赖**

- vim 
 `yum install vim`

- 安装git
`yum install git -y`

### **克隆和打包**
```
cd /opt
# 比较慢
git clone https://github.com/apache/rocketmq.git
cd rocketmq
# 打包过程可能会比较久
mvn -Prelease-all -DskipTests clean install -U
cd distribution/target/apache-rocketmq
```

### **内存参数配置**
```
#由于docker分配内存比较小，所以启动前需要调节一下，启动的虚拟内存参数配置
#nameserver 内存
vim bin/runserver.sh   
#broke内存
vim bin/runbroker.sh  

# 参考你自己的机器内存修改内存
JAVA_OPT_1="-server -Xms256m -Xmx256m -Xmn128m -XX:PermSize=64m -XX:MaxPermSize=128m"
```

### **环境变量**
- 设置环境变量：NAMESRV_ADDR  ,机器的ip,非容器ip

`export NAMESRV_ADDR=127.0.0.1:9876`

### **重点:修改RocketMQ的 broker 的地址**

`echo "brokerIP1=127.0.0.1" > broker.properties`

## **启动**
- cd

`cd /opt/rocketmq/distribution/target/apache-rocketmq`
### **启动服务mqnamesrv**

- 启动
`nohup sh bin/mqnamesrv &`
- 查看日志

`tail -f ~/logs/rocketmqlogs/namesrv.log`

### **启动服务Broker**

- 查看hosts解析

```
cat /etc/hosts
# 127.0.0.1   localhost localhost.localdomain 
```
- 启动

`nohup sh bin/mqbroker -n localhost:9876 -c broker.properties &`
- 查看日志

`tail -f ~/logs/rocketmqlogs/broker.log`

### **测试发送与接收**
```
# producer测试
sh bin/tools.sh org.apache.rocketmq.example.quickstart.Producer
# 输出:SendResult [sendStatus=SEND_OK, msgId= ...
sh bin/tools.sh org.apache.rocketmq.example.quickstart.Consumer
# 输出:ConsumeMessageThread_%d Receive New Messages: [MessageExt...
```
### **停止服务**

`sh bin/mqshutdown broker`

`sh bin/mqshutdown namesrv`

## **docker镜像**
- 提交一个运行中的容器为镜像
`docker commit centos rocketmq`
- 运行
`docker run -t -i --net=host --hostname rocketmq --name rocketmq -p 9876:9876 -p 10911:10911 -p 10909:10909 -d rocketmq /bin/bash 
`

## 参考
[腾讯云社区](https://cloud.tencent.com/developer/article/1010217)