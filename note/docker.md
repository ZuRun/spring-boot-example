## 创建镜像 环境为jdk8+jce
- 用途
  启用加密功能,此镜像用于config-server
### 步骤
- 准备工作,准备文件,见docker文件夹

- 创建镜像
`docker build -t jdk-jce:latest .`