# 制作镜像
## docker镜像
- 启动jdk镜像
`docker run -t -i --name jdk -p 9876:9876 -p 10911:10911 -p 10909:10909 -d registry.cn-hangzhou.aliyuncs.com/zurun/jdk-jce:last `
- 进入docker
`docker exec -ti jdk sh`
- cd至~
`cd ~`

## 配置
- maven依赖
```
wget http://mirrors.shu.edu.cn/apache/maven/maven-3/3.5.3/binaries/apache-maven-3.5.3-bin.tar.gz
tar -zxvf apache-maven-3.5.3-bin.tar.gz
vi /etc/profile
# 环境变量
M2_HOME=/root/apache-maven-3.5.3
export PATH=${M2_HOME}/bin:${PATH}
# 重载/etc/profile这个文件
source /etc/profile
```
- maven 阿里镜像
修改 settings.xml `vi /root/apache-maven-3.5.3/conf/settings.xml`
`<mirrors>`标签中增加以下内容
```
<mirror>
  <id>alimaven</id>
  <mirrorOf>central</mirrorOf>
  <name>aliyun maven</name>
  <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
</mirror>
```
- 下载rocketmq
```
wget https://github.com/apache/rocketmq/archive/master.zip
unzip -oq master.zip
mv rocketmq-master rocketmq
cd rocketmq
# 打包 打包过程可能会比较久
mvn -Prelease-all -DskipTests clean install -U
cd ~/rocketmq/distribution/target/apache-rocketmq
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
### 环境变量
- 系统环境变量
`vi ~/.bash_profile`
- 设置环境变量：NAMESRV_ADDR  ,机器的ip,非容器ip
```
export NAMESRV_ADDR=127.0.0.1:9876
source ~/.bash_profile
```

### **重点:修改RocketMQ的 broker 的地址**

`echo "brokerIP1=127.0.0.1" > broker.properties`

### 启动服务
- 启动服务Broker
`nohup sh bin/mqnamesrv &`
- 启动服务mqbroker
`nohup sh bin/mqbroker -n 127.0.0.1:9876 -c broker.properties &`

# build
- cd到[docker](docker)目录下执行命令:
`docker build -t rocketmq:latest .`

# 启动
`docker run -t -i --name rocketmq -p 9876:9876 -p 10911:10911 -p 10909:10909 -d rocketmq sh`

- 进入docker
`docker exec -ti rocketmq sh`