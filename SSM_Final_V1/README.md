# SSM_Final_V1 - [基础版]

  不知不觉学了SSM框架已经三个多月了，回顾之前整合的文章，发现了很多不足，有很多可以改进的地方。因此抽出了一天时间重新整理SSM框架整合笔记。
  **集成SSM + Druid + Log4j2 满足入门需求！**  
  SSM_Final_V1 [详细说明](https://blog.csdn.net/q343509740/article/details/81044611) 
  
## 1.添加SSM + Druid + Log4j2 依赖
  [详细说明](https://github.com/q343509740/SSM_Final/blob/master/SSM_Final_V1/pom.xml)

## 2.配置web.xml
  [详细说明](https://github.com/q343509740/SSM_Final/blob/master/SSM_Final_V1/src/main/webapp/WEB-INF/web.xml)

## 3.数据库配置文件(解耦)
```
# Mysql
## 数据库驱动
jdbc.driver=com.mysql.jdbc.Driver
## 数据库地址
jdbc.url=jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8
## 数据库名称
jdbc.username=root
## 数据库密码
jdbc.password=root
 
 
# Druid
## 配置初始化大小、最小、最大
druid.initialSize=1
druid.minIdle=1
druid.maxActive=20
## 配置获取连接等待超时的时间
druid.maxWait=60000
## 配置间隔多久才进行一次检测，检测需要关闭的空闲连接
druid.timeBetweenEvictionRunsMillis=60000
## 配置一个连接在池中最小生存的时间
druid.minEvictableIdleTimeMillis=300000
## 验证连接有效与否的SQL，不同的数据配置不同
druid.validationQuery=SELECT 'x'
## 检测连接是否有效
druid.testWhileIdle=true
druid.testOnBorrow=true
druid.testOnReturn=false
## 打开PSCache，mysql可以配置为false
druid.poolPreparedStatements=false
druid.maxPoolPreparedStatementPerConnectionSize=20
## 这里配置提交方式
druid.defaultAutoCommit=true
## 合并多个DruidDataSource的监控数据
druid.useGlobalDataSourceStat=true
## 开启Druid的监控统计功能
druid.filters=stat,wall

```

## 4.spring-mvc.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
	http://www.springframework.org/schema/tx
	http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
	http://www.springframework.org/schema/context
	http://www.springframework.org/schema/context/spring-context-4.0.xsd
	http://www.springframework.org/schema/mvc
	http://www.springframework.org/schema/mvc/spring-mvc.xsd
	http://www.springframework.org/schema/aop
	http://www.springframework.org/schema/aop/spring-aop-4.0.xsd">
 
    <!-- 1.自动扫描包 -->
    <context:component-scan base-package="com.ray.controller"/>
 
    <!-- 2.添加注解驱动 -->
    <mvc:annotation-driven/>
 
    <!-- 3.静态资源由WEB服务器默认的Servlet来处理 -->
    <mvc:default-servlet-handler/>
 
    <!-- 4.定义跳转的文件的前后缀,视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

## 5.spring-mybatis.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="
    http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context-3.0.xsd
    http://www.springframework.org/schema/mvc
    http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd
    http://www.springframework.org/schema/tx
    http://www.springframework.org/schema/tx/spring-tx-3.0.xsd">
 
    <!-- 配置整合mybatis过程 -->
    <!-- 1.开启自动扫描 -->
    <context:component-scan base-package="com.ray"/>
 
    <!-- 2.配置数据库相关参数properties的属性：${url} -->
    <!-- 使用数据库配置文件解耦 -->
    <context:property-placeholder location="classpath:jdbc.properties"/>
 
    <!-- 3.数据库连接池 -->
    <!-- 下面的druid配置都是基本配置,具体优化设置可以上网查询,也可以去github上面直接搜索druid -->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <!-- 配置连接池属性 -->
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
 
        <!-- 配置初始化大小、最小、最大 -->
        <property name="initialSize" value="${druid.initialSize}"/>
        <property name="minIdle" value="${druid.minIdle}"/>
        <property name="maxActive" value="${druid.maxActive}"/>
 
        <!-- 配置获取连接等待超时的时间 -->
        <property name="maxWait" value="${druid.maxWait}"/>
 
        <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
        <property name="timeBetweenEvictionRunsMillis" value="${druid.timeBetweenEvictionRunsMillis}"/>
 
        <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
        <property name="minEvictableIdleTimeMillis" value="${druid.minEvictableIdleTimeMillis}"/>
 
        <!-- 验证连接有效与否的SQL，不同的数据配置不同 -->
        <property name="validationQuery" value="${druid.validationQuery}" />
 
        <!-- 如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效 -->
        <property name="testWhileIdle" value="${druid.testWhileIdle}"/>
 
        <!-- 这里建议配置为TRUE，防止取到的连接不可用 -->
        <property name="testOnBorrow" value="${druid.testOnBorrow}"/>
        <property name="testOnReturn" value="${druid.testOnReturn}"/>
 
        <!-- 打开PSCache，并且指定每个连接上PSCache的大小 -->
        <!-- 如果用Oracle，则把poolPreparedStatements配置为true，mysql可以配置为false。 -->
        <property name="poolPreparedStatements" value="${druid.poolPreparedStatements}"/>
        <property name="maxPoolPreparedStatementPerConnectionSize" value="${druid.maxPoolPreparedStatementPerConnectionSize}"/>
 
        <!-- 这里配置提交方式，默认就是TRUE，可以不用配置 -->
        <property name="defaultAutoCommit" value="${druid.defaultAutoCommit}" />
 
        <!-- 合并多个DruidDataSource的监控数据 -->
        <property name="useGlobalDataSourceStat" value="${druid.useGlobalDataSourceStat}"/>
 
        <!-- 开启Druid的监控统计功能,StatFilter可以和其他的Filter配置使用 -->
        <property name="filters" value="${druid.filters}"/>
 
        <!-- proxyFilters属性配置,通过bean的方式配置 -->
        <property name="proxyFilters">
            <list>
                <ref bean="log4j2-filter"/>
            </list>
        </property>
    </bean>
 
    <!-- 上面的druid的配置 -->
    <bean id="log4j2-filter" class="com.alibaba.druid.filter.logging.Log4j2Filter">
        <!-- 所有连接相关的日志 -->
        <property name="connectionLogEnabled" value="false"/>
        <!-- 所有Statement相关的日志 -->
        <property name="statementLogEnabled" value="false"/>
        <!-- 是否显示结果集 -->
        <property name="resultSetLogEnabled" value="true"/>
        <!-- 是否显示SQL语句 -->
        <property name="statementExecutableSqlLogEnable" value="true"/>
    </bean>
 
    <!-- 4.spring和MyBatis的完美结合，不需要mybatis的配置映射文件 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!-- 配置MyBaties全局配置文件:mybatis-config.xml -->
        <property name="configLocation" value="classpath:mybatis/mybatis-config.xml"/>
        <!-- 注入数据库连接池 -->
        <property name="dataSource" ref="dataSource"/>
        <!-- 扫描entity包,使用别名 -->
        <property name="typeAliasesPackage" value="com.ray.entity"/>
        <!-- 扫描sql配置文件mapeer -->
        <!--<property name="mapperLocations" value="classpath:mapper/*.xml"/>-->
    </bean>
 
     <!--5.配置扫描Dao接口包，动态实现Dao接口，注入到spring容器中(使用Mybatis) -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <!-- 给出需要扫描Dao接口包 -->
    <property name="basePackage" value="com.ray.dao"/>
    </bean>
</beans>
```

## 6.spring-service.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:jee="http://www.springframework.org/schema/jee"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
        http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-4.0.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd">
 
    <!-- 1.配置事务管理 -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
 
    <!--2.配置事务的通知(事务的增强) [提供两种实现方法]-->
 
    <!-- 方法一：
        基于注解的方式需要在业务层上添加一个@Transactional的注解。
    -->
    <!--<tx:annotation-driven transaction-manager="transactionManager"/>-->
 
    <!-- 方法二：
        基于AspectJ的XML声明式事务管理
        在这种方式下Dao和Service的代码也不会改变(推荐)
     -->
 
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <!-- 定义事物传播特性 -->
        <tx:attributes>
            <!--propagation  :事务传播行为-->
            <!--read-only    :只读-->
            <tx:method name="insert" propagation="REQUIRED"/>
            <tx:method name="update" propagation="REQUIRED"/>
            <tx:method name="delete" propagation="REQUIRED"/>
            <tx:method name="select*" propagation="REQUIRED" read-only="true"/>
        </tx:attributes>
    </tx:advice>
 
    <!-- 配置事务切面,使得上面的事务配置对service接口的所有操作有效 -->
    <aop:config>
        <!-- 配置切入点 -->
        <aop:pointcut id="pointcut" expression="execution(* com.ray.service.*.*(..))"/>
        <!-- 配置切面 -->
        <aop:advisor advice-ref="txAdvice" pointcut-ref="pointcut"/>
    </aop:config>
</beans>

```

## 7.其他配置文件
  略.

---  
前往 [本人博客](https://blog.csdn.net/q343509740) 的链接。
