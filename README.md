# SpringBoot + SpringCloud 实践

## 目录
```
└── common : 被依赖的模块,非单独启动的服务
    └── basis
    └── redis
    └── rocketmq
    └── shiro
    └── web
└── server
    └── discovery : 服务治理(Eureka) 
    └── web
└── model-test : 用来练习和测试的模块
    └── ECS : 模拟被请求的服务
    └── ECC : 模拟发送请求的服务
```
## 总结
- [注解](Annotation.md)

## springBoot版本升级注意事项:
- common/basis模块下的org.springframework.http.HttpStatus文件检查下是否需要更新