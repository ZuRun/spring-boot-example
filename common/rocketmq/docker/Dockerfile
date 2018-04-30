FROM jdk
MAINTAINER zurun
# 自启动脚本
ADD start /etc/rc.d/init.d/
# 自启动 时区  
RUN chmod 777  /etc/rc.d/init.d/start \
	&& ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime 
# CMD命令只能有一个，用来在容器启动时，执行命令。也可以是脚本。
ENTRYPOINT ["sh","-c","/etc/rc.d/init.d/start"]