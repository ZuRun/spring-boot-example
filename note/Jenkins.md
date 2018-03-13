# jenkins
## [安装jenkins](https://segmentfault.com/a/1190000007086764)
```
# 拉取库的配置到本地对应文件
sudo wget -O /etc/yum.repos.d/jenkins.repo http://pkg.jenkins-ci.org/redhat/jenkins.repo
# 导入公钥
sudo rpm --import https://jenkins-ci.org/redhat/jenkins-ci.org.key
# 下载
sudo yum -y install jenkins
# 修改配置
cat /etc/sysconfig/jenkins | more
vim /etc/sysconfig/jenkins
# 启动jenkins服务  
sudo service jenkins start 
# 访问,默认端口8080
按提示操作即可
```
- 路径:`/var/lib/jenkins`

## 使用
### 插件
- maven插件
    1. 搜索Maven Integration插件并安装
    2. **全局工具配置**: maven环境变量为 /usr/share/maven
    
- docker 插件
    1. 搜索`docker-build-step`插件
    
### 配置docker
- 配置docker的远程访问(远程API访问)
```
vim /etc/sysconfig/docker
# 增加
OPTIONS="-H unix:///var/run/docker.sock -H 0.0.0.0:5555"
# 重启
systemctl daemon-reload  
systemctl restart docker.service 
# 查看端口
netstat -tnlp |grep 5555  
```
- 进入Jenkins，选择系统管理--》系统设置--》Docker Builder
`tcp://127.0.0.1:5555`
### 建立任务
#### Build
```

# 第1个参数: 模块名
# 第2个参数: 服务名
# 第3个参数: docker内部ip
# 第4个参数: docker外部ip
sbDockerBuild(){
	#删除同名docker容器
	# content=$(docker ps | grep "$2" | awk '{print $1}')
	docker stop $2
	docker rm $2
	docker rmi $2

	# 到jar路径,并拷贝Dockerfile到当前目录
	cd /var/lib/jenkins/workspace/SpringBoot-example/$1/target
	\cp -f ../src/main/docker/Dockerfile .

	#构建docker 镜像
	docker build -t $2 .

	#启动docker 容器 (host网络模式)
	docker run -t -i --net=host --hostname $2 --name $2 -p $4:$3 -d $2 /bin/bash
}

sbDockerBuild server/discovery-microservice discovery-microservice 18001 18001  
sbDockerBuild server/config-server config-server 18031 18031 
sbDockerBuild model-test/eureka-client-provider ecp 19021 19021
sbDockerBuild model-test/eureka-client-consumer ecc 19011 19011
sbDockerBuild api-gateway api-gateway 31001 31001

```