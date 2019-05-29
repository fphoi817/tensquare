
# 十次方项目笔记

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
    3. 如果是联合主键 需要在类上添加 @IdClass(Clazz.class)
 
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
 
### 添加 tensquare_qa 模块 
 
 + 多对多 中间表 关联查询 在持久层DAO的方法上 加注解@Query()  里面的字符串是JQL  如果要用SQL语句 需要 加上nativeQuery = ture
 + JQL 的语法 是所有关于SQL中表名 全部换成对象名
 
### 添加 tensquare_article 模块

 + 在JPA 的持久层中除了查询 其他操作都应该加上 注解 @Modifying
 ```
    @Modifying
    @Query("UPDATE Article art SET art.state = '1' WHERE art.id = ?1")
    
    注意: UPDATE Article SET Article.state = '1' WHERE Article.id = ?1 这样写会报空指针异常 
    update Article set state='1' where id=?1 正确的也可以写成这样
    自己分析 应该是 Article SET 后面 再次出现 算是新的对象 并不是要修改的对象 也就是说是 SET 前的那个 Article
 ```
 + 注意的问题
 ```
 2019-05-13 16:51:18.122 ERROR 5320 --- [nio-9004-exec-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : Table 'tensquare_base.tb_article' doesn't exist
 其实是数据库连错了
 ```
 
 + 实现文章的缓存处理
    1. 需要在启动类上 添加注解 @EnableCacheing 开启缓存
    2. 在需要缓存的方法上添加注解 @Cacheable(value="xxx",key="#xxx")  参数 value 表示 cacheManage 的名称 参数 key 表示 缓存的键
    3. 如果修改,添加 需要删除原来的缓存 @CacheEvict(value="xxx",key="#xx.xxx") 

###### public Object getHeader(@RequestHeader("access_token") String accessToken, String id)可以获取到浏览器头信息

### 密码加密与微服务鉴权JWT
 + BCrypt 密码加密
    1. 导入 spring-boot-starter-security 依赖
    2. 添加配置类 配置类继承 WebSecurityConfigurerAdapter 并重写 configure(HttpSecurity http) 方法, 这个类上需添加两个注解 @Configuration @EnableWebSecurity
    3. 在启动类中配置Bean 注入 BCryptPasswordEncoder 
    4. 在MySQL数据库中字段类型为 bigint 对应Java 类型Long, 而不是long
 
 + 添加拦截器
    1. 继承 HandlerInterceptorAdapter 适配器 可以重写三个方法
        > 也可直接实现 implements HandlerInterceptor 重写三个方法
    2. 预处理 perHandle 可以进行编码, 安全控制等处理
    3. 后处理 postHandle 可以修改 ModelAndView
    4. 返回处理 afterCompletion 可以根据exception 是否null 判断是否发生异常, 进行日志记录

 + 添加配置类 注册拦截器
    1. 添加配置类 注解 @Configuration  
    2. 重新 addInterceptors(InterceptorRegistry registry) 添加注册拦截器
    3. 添加 配置类 进行拦截器的注册 该类需要添加 @configuration 注解 并且重写这个接口对象实例 new WebMvcConfigurer(){ ... } 
    4. addCorsMappings(CorsRegistry registry) 重写父类提供的跨域请求处理的接口
    5. addInterceptors(InterceptorRegistry registry) 注册拦截器
    6. 该配置类的方法实例对象需要加上 @Bean 去注入到spring容器中