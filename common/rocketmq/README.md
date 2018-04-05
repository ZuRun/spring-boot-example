# 注意事项
- **同一个消费组应该拥有完全一样的订阅关系**

# docker
## 使用docker
`docker pull registry.cn-hangzhou.aliyuncs.com/zurun/rocketmq:last `

- 启动
`docker run -t -i --name rocketmq -p 9876:9876 -p 10911:10911 -p 10909:10909 -d rocketmq sh`

## docker部署过程记录
### [在docker中部署步骤](docker.md)
### [生成docker镜像](docker-images.md)