
### init projecit

### 添加 tensquare_common 模块
 + 添加数据返回包装类
 + 添加 IdWorker谷歌雪花算法的ID生成类
 
### 添加 tensquare_base 模块
 + 修改 POM.XML文件
    1. 添加 MySQL数据库连接驱动依赖
    2. 添加 DAO层解决方案 JPA依赖
    3. 添加 tensquare_commom 模块依赖
 + 注入 IdWoeker 到容器内 (可以用@service注解, 但是, 很多没有用到, 所以推荐使用@bean) 
 + 添加 controller 层代码 (CRUD)
 + 添加 pojo 层 
    1. jpa 规定实体类上需要添加 @Entity @Table(name = "tb_xxx") 主键@id 
    2. 在分布式 微服务的架构中 实体类在各模块中通信 需要将实体类实现 Serializable 接口
 + 添加 dao 层
    1. jpa 规定在dao接口中 需要继承 JpaRepository<Clazz, id的类型>, 同时如果是复杂类型(比如分页) 还要需要继承 JpaSpecificationExecutor<Clazz> 
 + 添加 service 层
    1. jpa findById() 返回值是一个 容器对象 Optional (JDK1.8的新特性, Optional 类主要解决的问题是臭名昭著的空指针异常（NullPointerException）)  可以依靠 Optional.get() 取值 
    2. jap save() 可以新增也可以修改 如果没有ID就是新增, 如果参数有ID 就会去数据库查询 并修改参数ID的数据
 + 添加 BaseExceptionHandler 异常处理 
    1. 注解 类上 @RestControllerAdvice 是controller层的Advice 是 Spring AOP切面增加
    2. 注解 方法上 @ExceptionHandler(value = Exception.class) 表示处理异常为 Exception类型的异常 助手类
    3. logger.error(request.getRequestURI()); 日志记录 发生异常的请求   logger.error(e.toString(), e);  日志记录 发生异常的值栈信息