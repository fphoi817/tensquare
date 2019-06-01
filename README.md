
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
    
    /* 注意: UPDATE Article SET Article.state = '1' WHERE Article.id = ?1 这样写会报空指针异常 
    update Article set state='1' where id=?1 正确的也可以写成这样
    自己分析 应该是 Article SET 后面 再次出现 算是新的对象 并不是要修改的对象 也就是说是 SET 前的那个 Article */
 ```
 + 注意的问题
 ```shell
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
    
### SpringCloud 微服务管理

###### 服务发现组件 Eureka

 + 添加 Eureka server 服务端
    1. 创建 Eureka 模块 作为微服务的管理端
    2. 导入 SpringCloud Eureka 服务端的依赖 spring-cloud-starter-netflix-eureka-server
    3. 启动类 添加 @EnableEurekaServer 注解
    4. 配置文件 添加如下配置
        ```yml
        server:
          port: 6868        # 自定义
        eureka:
          client:
            register-with-eureka: false     # 是否将自己注册到Eureka服务中, 模块本身作为服务器, 则无需注册  默认值为 true
            fetch-registry: false
            service-url:
              defaultZone: http://127.0.0.1:${server.port}/eureka/
        ```
    5. Eureka 管理信息UI界面 浏览器链接地址 IP地址:6868, 

 + 添加 Eureka client 客服端

    1. 在需要注册到服务端的模块添加依赖    spring-cloud-starter-netflix-eureka-client

    2. 配置文件中
       ```yml
       eureka:
         client:
           service-url:	# Eureka客户端与Eureka服务端进行交互的地址
             defaultZone: http://127.0.0.1:6868/eureka/
         instance:
           prefer-ip-address: true
       ```
    3. 在启动类中添加注解 @EnableEurekaClient

###### 实现服务间的调用 Fegin

 + 在需要调用的模块中 

   1. 添加依赖 spring‐cloud‐starter‐openfeign

   2. 在需要调用模块的启动类上添加注解 @EnableDiscoveryClient  @EnableFeignClients
   3. 创建client 包, 添加一个interface 接口
   4. 接口添加注解@FeignClient(value = "需要被调用的模块项目名称")
   5. 添加和被调用模块的同名方法   注意   @PathVariable注 解一定要指定参数名称，否则出错 , requestmapping 的URL 需要带上被调用的完整路径
   6. 在调用模块的controller 中注入接口, 并调用

######  熔断器 Hystrix
 + 在需要调用的模块中

   1. 添加配置 

      ```yml
      feign:
        hystrix:
          enabled: true
      ```

   2. 在调用的模块中的client包的接口 创建impl 包 ,添加接口的实现类 重新接口的方法 实现由于被调用的模块如果出错后, 执行的逻辑, 避免自己出错而引起的雪崩效应 注意添加@component 注解 将实现类添加的Spring容器中

   3. 修改client 中接口的注解 @FeignClient(name = "tensquare-base", fallback = BaseLabelClientImpl.class)  注意 接口也需要添加一个 @component 注解

      

###### 网关 Zuul
 + 创建一个后端管理模块 manage 和一个前端访问模块 web

    1. 添加依赖

       ```xml
           <dependencies>
               <dependency>
                   <groupId>org.springframework.cloud</groupId>
                   <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
               </dependency>
       
               <dependency>
                   <groupId>org.springframework.cloud</groupId>
                   <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
               </dependency>
           </dependencies>
           
           <!-- 注意这里的artifactID 都是带有starter的依赖 -->
       ```

    2. 添加配置文件

       ```yml
       spring:
         # ===================================================================
         # 项目名称
         # ===================================================================
         application:
           name: tensquare-manage
       
       # ===================================================================
       # Spring cloud eureka 配置 客服端
       # ===================================================================
       eureka:
         client:
           service-url:   # Eureka客户端与Eureka服务端进行交互的地址
             defaultZone: http://127.0.0.1:6868/eureka/
         instance:
           prefer-ip-address: true
       # ===================================================================
       # Spring cloud zuul 配置 网关路由
       # ===================================================================
       zuul:
         routes:
           tensquare-gathering:    # 活动
             path: /gathering/**   # 配置请求URL的请求规则
             serviceId: tensquare-gathering    # 指定 eureka 注册中心的服务ID
           tensquare-article:      # 文章
             path: /article/**     # 配置请求URL的请求规则
             serviceId: tensquare-article      # 指定 eureka 注册中心的服务ID
           tensquare-base:         # 基础
             path: /base/**        # 配置请求URL的请求规则
             serviceId: tensquare-base         # 指定 eureka 注册中心的服务ID
           tensquare-friend:       # 交友
             path: /friend/**      # 配置请求URL的请求规则
             serviceId: tensquare-friend       # 指定 eureka 注册中心的服务ID
           tensquare-qa:           # 问答
             path: /qa/**          # 配置请求URL的请求规则
             serviceId: tensquare-qa           # 指定 eureka 注册中心的服务ID
           tensquare-recruit:      # 招聘
             path: /recruit/**     # 配置请求URL的请求规则
             serviceId: tensquare-recruit      # 指定 eureka 注册中心的服务ID
           tensquare-spit:         # 吐槽
             path: /spit/**        # 配置请求URL的请求规则
             serviceId: tensquare-spit         # 指定 eureka 注册中心的服务ID
           tensquare-user:         # 用户
             path: /user/**        # 配置请求URL的请求规则
             serviecId: tensquare-user         # 指定 eureka 注册中心的服务ID
       ```

    3. 给启动类添加注解  @EnableZuulProxy

    4. 注意访问的时候端口号是网关的端口号, 并且端口号后面是网关路由匹配的规则 然后才是访问接口的URL映射地址, 

    5. 还有最后网关好像需要最后启动,不然会报错



 + 配置 Zuul 过滤器

    1. 添加一个过滤器类 并继承 ZuulFilter 

    2. 重写四个方法

       ```java
           @Component
           public class ManageFilter extends ZuulFilter {
               @Override
               public String filterType() {
                   return "pre";       // 前置过滤器
               }
           
               @Override
               public int filterOrder() {
                   return 0;           // 优先级为0 数字越大 优先级越低
               }
           
               @Override
               public boolean shouldFilter() {
                   return true;       // 是否执行过滤器, 此处为true, 说明需要过滤
               }
           
               @Override
               public Object run() throws ZuulException {
                   System.out.println("zuul 过滤器执行了");
                   return null;
               }
           }
       ```

    3. filterType ：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过 滤器类型 

       + pre: 请求路由之前调用
       + route: 在路由请求的时候调用
       + post: router 和 error 过滤器之后被调用
       + error: 处理请求发送错误时调用

    4. filterOrder: 通过int 值来定义过滤器的执行顺序

    5. shouldFilter: 返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可 实现过滤器的开关。在上例中，我们直接返回true，所以该过滤器总是生效

    6. run: 过滤器的具体逻辑

    7. webfilter 并不需要权限校验, 因为未登录的用户也可以浏览信息, 而managefilter 则需要验证, 因为后端管理是需要权限的



###### 配置中心 Spring-cloud-config 配置中心

+ 配置服务端

  1. Git 新建一个仓库, 并将配置文件 改名为 {application}-{profile}.yml 上传

  2. 创建新模块 tensquare_config   添加新的依赖  Spring-cloud-config-server

  3. 创建启动类  添加注解 @EnableConfigServer  

  4. 编写配置文件 

     ```yml
     server:
       # ===================================================================
       # 端口号
       # ===================================================================
       port: 12000
     spring:
       # ===================================================================
       # 项目名称
       # ===================================================================
       application:
         name: tensquare-config
       # ===================================================================
       # spring-cloud-config 配置中心服务
       # ===================================================================  
       cloud:
         config:
           server:
             git:
               uri: https://gitee.com/Freya0016/tensquare-config.git
     ```

  5. 注意服务端：Authentication is required but no CredentialsProvider has been registered 异常信息 需要将Git仓库改成公开的才行



+ 配置客服端

  1. 在需要配置的模块中 添加 spring-cloud-starter-config 依赖

  2. 添加bootstrap.yml 删除application.yml

     ```
     
     ```




### 持续集成

###### DockerMaven 插件

 + 通过Maven插件自动部署

    1. 修改宿主机的docker配置，让其可以远程访问

         ```shell
         vi /lib/systemd/system/docker.service
         
         其中 ExecStart=后添加配置 ‐H tcp://0.0.0.0:2375 ‐H unix:///var/run/docker.sock
         
         具体位置 插入到
         --seccomp-profile ... \
         ‐H tcp://0.0.0.0:2375 ‐H unix:///var/run/docker.sock \
         $OPTIONS \
         
         systemctl daemon‐reload  	// 重新载入docker 守护线程
         systemctl restart docker  	// 重启docker 服务
         docker start registry		// 重启私有仓库服务
         
         注意其中 一定要开启防护墙 的端口 2375  不然无法访问 教训啊
         firewall-cmd --zone=public --add-port=80/tcp --permanent    （--permanent永久生效，没有此参数重启后失效）
         firewall-cmd --reload
         查看所有打开的端口： firewall-cmd --zone=public --list-ports
         ```

    2. 在工程pom.xml 增加配置

         ```xml
            <build>
                 <finalName>app</finalName>  <!-- maven 打包上传后的文件名 -->
                 <plugins>
                     <plugin>
                         <groupId>org.springframework.boot</groupId>
                         <artifactId>spring-boot-maven-plugin</artifactId>
                     </plugin>	<!-- springboot 整合 maven 插件 -->	
                     <plugin>
                         <groupId>com.spotify</groupId>
                         <artifactId>docker-maven-plugin</artifactId>
                         <version>0.4.13</version>
         <!-- docker的maven插件 官网：https://github.com/spotify/docker-maven-plugin -->
                         <configuration>               
                             <imageName>{ip}:5000/${project.artifactId}:${project.version}</imageName>
         <!-- 标记要上传的(docker tag)镜像名 ip地址:端口号/artifactId/version -->                    
                             <baseImage>jdk1.8</baseImage>
         <!-- 集成的基础镜像 springboot 打包的项目 需要依赖 JDK1.8运行环境 -->
                             <entryPoint>["java", "-jar", "/${project.build.finalName}.jar"]</entryPoint>
         <!-- docker把镜像生成容器后 执行该命令 java -jar 项目文件名.jar -->                 
                             <resources>
                                 <resource>
                                     <targetPath>/</targetPath>
                                     <directory>${project.build.directory}</directory>
                                     <include>${project.build.finalName}.jar</include>
                                 </resource>
                             </resources>
                             <dockerHost>http//{ip}:2375</dockerHost>
                             <!-- docker 连接地址 -->
                         </configuration>
                     </plugin>
                 </plugins>
             </build>
         ```
    
###### Jenkins 集成插件

 + 安装之前的 配置

   1. `mkdir /home/jenkins`	       创建jenkins 挂载的文件夹
   2. `mkdir /home/jenkins/java`   创建一个存放 jdk 的文件夹
   3. `mkdir /home/jenkins/maven`  创建一个存放 maven 的文件夹
   4. 将JDK 和 maven 上传到对应文件夹下 解压 tar -zxvf xxxx.tar.gz
   5. vim /home/jenkins/maven/apache-maven-3.6.1/conf/settings.xml
   6. 添加 <localRepository>**/var/jenkins_home/RepMaven**</localRepository> 
    > 注意:  这里的粗体字, 因为maven所在的文件夹是在容器jenkins 容器挂载在宿主机上的文件, 
      所以需要写上jenkins能识别的路径, 如果写成宿主机的路径 /home/jenkins/RepMaven 
      会无法找到文件 ,maven是在jenkins中运行的, 所有的依赖将会下载到jenkins 容器对应路径的
      文件下 /var/jenkins_home/RepMaven        教训啊
   7. 需要给容器jenkins挂载的文件授权chown -R 1000:1000 jenkins/ 给UID为1000的权限 不然容器无法启动
 
 + 安装jenkins
   1. 需要安装 docker.io/jenkins/jenkins 而docker.io/jenkins版本太旧无法下载插件
   2. `docker run -d --name jenkins -p 8888:8080 -p 50000:50000 -v /home/jenkins:/var/jenkins_home --privileged docker.io/jenkins/jenkins`
   3. 注意 挂载的路径对应 并且--privileged 授权
   4. jenkins 需要安装 插件 Maven Integration
   5. 配置全局 JDK 和 maven
    > 注意这里的路径 和上面的引用说明一样 不能直接写宿主机挂载的路径 /home/jenkins/java/jdk1.8.0_201 
      而应该是 /var/jenkins_home/java/jdk1.8.0_201 因为JDK 是在jenkins中运行的 配置的路径也应该是
      容器挂载的映射地址 maven 也一样 不能是 /home/jenkins/maven/apache-maven-3.6.1
      而应该是 /var/jenkins_home/maven/apache-maven-3.6.1
    
            
   
   

  

  

  

  

  

  

  

  