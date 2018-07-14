# SSM_Final_V2 - [进阶版]

  不知不觉学了SSM框架已经三个多月了，回顾之前整合的文章，发现了很多不足，有很多可以改进的地方。因此抽出了一天时间重新整理SSM框架整合笔记。  
  
  基础版已满足入门需求，说明也很详细，但实际项目中可能还需要其他插件支持，使我们的工作效率更高，这里重点介绍Mybatis的几款优秀插件，分别是：     通用Mapper 、分页插件PageHelper 和 自动生成代码Mybatis-Generator，并运用到我的项目中。   
  SSM_Final_V2 [详细文档](https://blog.csdn.net/q343509740/article/details/81047184) 
  
## 1.部分代码
  请参考[SSM_Final_V1](https://github.com/q343509740/SSM_Final/tree/master/SSM_Final_V1) 的链接。

## 2.改动代码
### 1.pom.xml 
```
        <!-- Mybatis-Generator 配置(可选) -->
        <plugin>
          <groupId>org.mybatis.generator</groupId>
          <artifactId>mybatis-generator-maven-plugin</artifactId>
          <version>${generator.version}</version>
          <configuration>
            <!-- 允许移动生成的文件 -->
            <verbose>true</verbose>
            <!-- 是否覆盖 -->
            <overwrite>true</overwrite>
            <!-- 配置文件位置-->
            <configurationFile>src/main/resources/mybatis/generatorConfig.xml</configurationFile>
          </configuration>
          <!--
                使用Maven Mybatis插件
                如果不在plugin里面添加依赖包的话，会找不到相关的jar包
          -->
          <dependencies>
            <dependency>
              <groupId>org.mybatis</groupId>
              <artifactId>mybatis</artifactId>
              <version>${mybatis.version}</version>
            </dependency>
            <dependency>
              <groupId>mysql</groupId>
              <artifactId>mysql-connector-java</artifactId>
              <version>${mysql.version}</version>
            </dependency>
            <dependency>
              <groupId>tk.mybatis</groupId>
              <artifactId>mapper</artifactId>
              <version>${mapper.version}</version>
            </dependency>
          </dependencies>
        </plugin>
```

### 2.web.xml
  1）替换404页面，使页面更友善（自行添加404.html）  
  2）Durid 添加登录验证
  
### 3.spring-mybatis.xml   
  改动较多
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
        <property name="mapperLocations" value="classpath:mapper/*.xml"/>
        <!-- 配置分页插件 -->
        <property name="plugins">
            <array>
                <bean class="com.github.pagehelper.PageInterceptor">
                    <property name="properties">
                        <!-- 常用配置 -->
                        <value>
                            helperDialect=mysql
                            reasonable=true
                        </value>
                    </property>
                </bean>
            </array>
        </property>
 
    </bean>
 
    <!-- 5.配置扫描Dao接口包，动态实现Dao接口，注入到spring容器中(适用Mapper) -->
    <bean class="tk.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- 给出需要扫描Dao接口包 -->
        <property name="basePackage" value="com.ray.dao"/>
    </bean>
</beans>
```

## 3.新增代码
### 1.generatorConfig.xml 
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<!-- 配置生成器 -->
<generatorConfiguration>
 
    <!-- 使用数据库配置文件解耦 -->
    <properties resource="jdbc.properties"/>
 
    <!-- context 遵循严格的配置顺序 -->
    <context id="MyBatis" targetRuntime="MyBatis3" defaultModelType="flat">
 
        <!-- 指明数据库的用于标记数据库对象名的符号，比如ORACLE就是"双引号，MYSQL默认是`反引号 -->
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>
 
        <!-- 继承该自定义MyMapper接口(可选配置) -->
        <plugin type="tk.mybatis.mapper.generator.MapperPlugin">
            <!--配置后生成的 Mapper 接口都会自动继承该接口-->
            <property name="mappers" value="com.ray.util.MyMapper" />
            <!--caseSensitive默认false，当数据库表名区分大小写时，可以将该属性设置为true-->
            <property name="caseSensitive" value="true"/>
        </plugin>
 
        <!-- 移除自动生成的注释 -->
        <commentGenerator>
            <property name="javaFileEncoding" value="UTF-8"/>
            <property name="suppressDate" value="true" />
            <!-- 移除自动生成的注释 true|false -->
            <property name="suppressAllComments" value="true" />
        </commentGenerator>
 
        <!-- 配置连接池属性 -->
        <jdbcConnection driverClass="${jdbc.driver}"
                        connectionURL="${jdbc.url}"
                        userId="${jdbc.username}"
                        password="${jdbc.password}">
        </jdbcConnection>
 
        <!-- 处理NUMERIC和DECIMAL类型的策略 -->
        <javaTypeResolver>
            <property name="forceBigDecimals" value="true"/>
        </javaTypeResolver>
 
        <!-- 控制生成的实体类(替换targetPackage) -->
        <javaModelGenerator targetPackage="com.ray.entity"
                            targetProject="src/main/java">
            <property name="enableSubPackages" value="true" />
            <!-- 设置是否在getter方法中，对String类型字段调用trim()方法 -->
            <property name="trimStrings" value="true" />
        </javaModelGenerator>
 
        <!-- 生成映射文件(替换targetPackage) -->
        <sqlMapGenerator targetPackage="mapper"
                         targetProject="src/main/resources">
            <property name="enableSubPackages" value="true" />
        </sqlMapGenerator>
 
        <!-- 生成Mapper接口(替换targetPackage) -->
        <javaClientGenerator type="XMLMAPPER"
                             targetPackage="com.ray.dao"
                             targetProject="src/main/java">
            <property name="enableSubPackages" value="true" />
        </javaClientGenerator>
 
        <!-- 生成对应表名及类名(替换city), %表示生成全部的表-->
        <!-- 去掉Mybatis-Generator生成的一堆example -->
        <table tableName="city"
               enableCountByExample="false"
               enableUpdateByExample="false"
               enableDeleteByExample="false"
               enableSelectByExample="false"
               selectByExampleQueryId="false">
            <!-- 自动生成主键的属性(替换id) -->
            <generatedKey column="id" sqlStatement="Mysql" identity="true"/>
        </table>
    </context>
</generatorConfiguration>
```

### 2.MyMapper.java
```
package com.ray.util;
 
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
 
/**
 * @author Ray
 * @date 2018/7/14 0014
 * 自定义MyMapper,用于生成的Mapper继承该接口
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}

```

---  
前往 [本人博客](https://blog.csdn.net/q343509740) 的链接。
