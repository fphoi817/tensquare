
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
 
 + JPA 带条件的查询
    1. findAll(new Specification() xxx)   需要重写 Specification类的toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)方法
    2. root 根对象,也就是要把条件封装到哪个对象中, where 列名 = label.getid
    3. criteriaQuery 封装的都是查询关键字, 比如 Group by 或 Order by
    4. criteriaBuilder 用来封装条件对象的(类似于mybatis中 example) 如果直接返回 null 表示不需要任何条件
    5. 因为查询的条件不确定, 可能是多个或是一个, 所以 criteriaBuilder.and(必须是初始化长度的数组), 但是条件的个数不知道, 数组的长度无法确认, 所以需要用集合来存放
    6. list.toArray(predicates); 和 predicates = list.toArray(); 是一样的 , 都将集合的值转成了数组后 赋值给了参数数组
 
 + JPA 带条件的分页查询
    1. 带条件的分页查询和只带条件的查询差不多, 主要区别于, 分页的findAll() 方法需要两个参数, 第二个参数是Pageable 分页对象的方法 PageRequest.of(page-1, size);
    2. 主要JPA的分页查询中 传入的page 页 必须是从零开始的, 所以前端传过来的参数必须减1, 返回的对象不推荐是Page, Page对象封装了很多对象, 我们只需要通过page.getTotalElements(), page.getContent() 取出总记录和每页显示条数
    
    
### 添加 tensquare_recruit 模块

 + 配置recruit 模块的一些参数, 连接数据库名称, POM文件, 工程名, 端口号, 运行主类
 + JPA 命名规则生成SQL语句
 
 
         