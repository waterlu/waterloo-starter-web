# waterloo-starter-web
Web服务的基础框架和基础类，基于Spring Boot



## 功能

使用waterloo-starter-web可以快速开发一个微服务。每个服务有很多类似代码，早期思路是自动生成这些源代码，现在调整为提供Controller和Service基类，实现基本的CRUD功能。waterloo-starter-web做了如下事情：

- 拦截HTTP请求，输出日志，包括具体的请求参数和响应时间
- 统一的异常处理
- 通用参数VO、值VO、接口返回数据的定义
- 通用异常的定义
- 参数校验
- CRUD的Mapper基类
- CRUD方法
  - 插入：插入成功返回含自增主键的实体类对象
  - 详情：根据主键读取详情
  - 更新：根据主键更新
  - 删除：根据主键逻辑删除



## 使用

## 实现



### 注意事项

如果创建spring boot项目（非maven项目），那么pom.xml里面默认是

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.13.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter</artifactId>
	</dependency>
</dependencies>
```
这种情况下，会打一个大的jar，但是引用的时候是有问题的

需要改成下面这样，也就是parent的好处是不用再指定version了

```xml
<properties>
	<spring-boot.version>1.5.13.RELEASE</spring-boot.version>
</properties>
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter</artifactId>
		<version>${spring-boot.version}</version>
	</dependency>
</dependencies>  
```

