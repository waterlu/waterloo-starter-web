# waterloo-starter-web
Web服务的基础框架和基础类，基于Spring Boot

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

