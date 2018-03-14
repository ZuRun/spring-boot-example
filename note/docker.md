# config-server 镜像比较特殊,需要加密功能
## 创建镜像 环境为jdk8+jce
- 用途
  启用加密功能
### 步骤
- 准备工作,准备文件,见docker文件夹,并cd进入此文件夹

- 创建镜像
`docker build -t jdk-jce:latest .`
- 登录镜像仓库,此处 用的阿里docker仓库
`sudo docker login --username=阿里云账号 registry.cn-hangzhou.aliyuncs.com`
- tag
` docker tag [ImageId] registry.cn-hangzhou.aliyuncs.com/zurun/jdk-jce:last`
- 推送
` docker push registry.cn-hangzhou.aliyuncs.com/zurun/jdk-jce:last`

### 使用
`docker pull registry.cn-hangzhou.aliyuncs.com/zurun/jdk-jce:last `

注意要加last,不然提示找不到