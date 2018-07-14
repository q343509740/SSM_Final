# SSM_Final_V2 - [进阶版]

  不知不觉学了SSM框架已经三个多月了，回顾之前整合的文章，发现了很多不足，有很多可以改进的地方。因此抽出了一天时间重新整理SSM框架整合笔记。  
  
  基础版已满足入门需求，说明也很详细，但实际项目中可能还需要其他插件支持，使我们的工作效率更高，这里重点介绍Mybatis的几款优秀插件，分别是： 通用Mapper 、 分页插件PageHelper 和 自动生成代码Mybatis-Generator，并运用到我的项目中。 
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


---  
前往 [本人博客](https://blog.csdn.net/q343509740) 的链接。
