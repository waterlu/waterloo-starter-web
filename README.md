# waterloo-starter-web
Web服务的基础框架和基础类，基于Spring Boot

## 功能

使用waterloo-starter-web可以快速开发一个微服务。每个服务有很多类似代码，早期思路是自动生成这些源代码，现在调整为提供Controller和Service基类，实现基本的CRUD功能。waterloo-starter-web做了如下事情：

- 拦截HTTP请求，输出日志，包括具体的请求参数和响应时间
- 统一的异常处理
- 通用参数VO、值VO、接口返回数据的定义
- 通用异常的定义
- 参数校验
- 实体类基类
- CRUD的Controller和Service基类
- CRUD的Mapper基类
- CRUD方法
  - 插入：插入成功返回含自增主键的实体类对象
  - 详情：根据主键读取详情
  - 更新：根据主键更新
  - 删除：根据主键逻辑删除

## 使用

### @EnableWaterlooWeb

在Application类上添加@EnableWaterlooWeb注解，激活HTTP请求过滤器和统一异常处理

```java
@EnableWaterlooWeb
public class ServiceDemoApplication {
}
```

### 参数、返回值和异常

入参

- 基类ParamDTO，包含callSystemID和traceID。
- 使用@Validated注解进行参数校验
  - @Not Null - Integer等基本类型不能为空
  - @NotBlank - String不为空且长度大于0
  - @NotEmpty - 集合类对象个数大于0
- 预定义了InsertGroup和UpdateGroup，区分场景；

返回值

- 接口返回值ResponseResult继承Spring的ResponseEntity；


- 所有请求返回的HTTP code都是200，真实数据封装在ResponseData中；
- ResponseData包含code、message和data三个字段
  - code：真正的处理结果，200表示成功，400表示参数异常，500表示系统异常，业务错误从1001开始；
  - message：出错时生效，保存具体的错误信息文本；
  - data：有效数据负载payload；


ResponseData默认构造函数表示成功，code=200。

```java
User userInfo = userService.getUser(userId);
ResponseData responseData = new ResponseData();
responseData.setData(userInfo);
ResponseResult responseResult = new ResponseResult(responseData);
return responseResult;
```

抛出异常时，统一异常处理器自动封装ResponseResult并返回（HTTP code是200）。

ListResultVO封装了常用的列表返回数据结构。

业务异常BizException继承RuntimeException

ResponseCode定义了常见的错误编码：

- 200 - 成功
- 400 - 参数异常
- 401 - 未授权
- 500 - 系统内部异常
- 503 - 网络超时，系统暂时不可用，请稍后再试
- 大于1000 - 业务异常（继承BizException）

### 基类

#### BaseEntity

实体类的基类，包含deleteFlag，createTime，updateTime三个属性。这也就意味着数据库表必须有这三个字段。所有实体类都继承BaseEntity，以后可以方便进行扩展。

#### CrudMapper

所有Mapper的基类，实现了常见的CRUD方法，Mapper不用在XML文件中定义这些方法了。

```java
public interface CrudMapper<T> extends BaseMapper<T>, RowBoundsMapper<T> {
  // 逻辑删除，BaseMapper默认物理删除
  @DeleteProvider(type = MybatisProvider.class, method = "dynamicSQL")
  int deleteFlag(Object key);
}
```

基类BaseMapper提供了基础的CRUD方法，RowBoundsMapper提供了分页查询的基础方法。

#### BaseService

所有服务实现类请继承抽象基类BaseService，并实现getMapper()方法返回对应的Mapper类对象。

BaseService默认提供了save()、get()、update()和delete()方法。

```java
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {
  @Autowired
  private UserMapper userMapper;
  @Override
  public CrudMapper<User> getMapper() {
    return userMapper;
  }
}
```

#### BaseController

所有Controller请继承抽象基类BaseController，并实现getService()和setEntityId()方法。

BaseController默认提供了CRUD功能。

**Create功能**

- 首先调用paramToEntity()方法将参数对象转换为实体类对象，Controller子类可以覆盖这个方法；
- 然后将实体类对象写入数据库；
- 如果数据库更新行数为0抛出异常；
- 如果写入成功返回写入的数据，默认返回实体类对象，也可以覆盖entityToVo()方法转换为VO对象。

```java
@PostMapping(value = "")
public ResponseResult create(@RequestBody @Validated P param) throws BizException {
  // 将入参转换为实体类对象，方便Mapper操作
  T entity = paramToEntity(param);

  // 持久化到数据库
  int row = getService().save(entity);
  if (row == 0) {
    // 写入失败，抛出异常
    throw new DBException();
  }

  // 写入成功，返回实体对象；自增ID在save()时回写；
  ResponseData responseData = entityToVo(entity);
  return new ResponseResult(responseData);
}

```

**Retrieve功能**

- 根据传入的ID查询数据库；
- 返回数据同样可以覆盖entityToVo()方法进行VO类转换。

```java
@GetMapping(value = "/{id}")
public ResponseResult get(@PathVariable Long id) throws BizException {
  // 根据主键读取数据
  T entity = getService().get(id);

  if(null == entity) {
    // 读取失败，抛出异常
    throw new DBException();
  }

  ResponseData responseData = entityToVo(entity);
  return new ResponseResult(responseData);
}
```

**Update功能**

- 首先调用paramToEntity()方法将参数对象转换为实体类对象；
- 然后设置主键字段的值；
- 最后更新数据库，并返回更新的行数，更新行数为0不抛出异常，交给上层业务判断是否正确。

```java
@PutMapping(value = "/{id}")
public ResponseResult update(@PathVariable Long id, @RequestBody @Validated P param)
  throws BizException {
  // 将入参转换为实体类对象，方便Mapper操作
  T entity = paramToEntity(param);

  // 设置ID字段值
  setEntityId(entity, id);

  // 更新数据库
  int row = getService().update(entity);

  // 返回更新行数，row=0不抛异常
  SimpleResponseData responseData = new SimpleResponseData(row);
  return new ResponseResult(responseData);
}
```

**Delete功能**

- 执行逻辑删除操作，就是`update set delete_flag = 0` 操作；
- 返回更新的行数，更新行数为0不抛出异常，交给上层业务判断是否正确。

```java
@DeleteMapping(value = "/{id}")
public ResponseResult delete(@PathVariable Long id) throws BizException {
  // 逻辑删除
  int row = getService().delete(id);

  // 返回更新行数，row=0不抛异常
  SimpleResponseData responseData = new SimpleResponseData(row);
  return new ResponseResult(responseData);
}
```

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

