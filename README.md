mybatis-generator-demo
==
> 使用 mybatis-generator 简化 curd 操作的开发，让技术更关注业务

#### 版本要求
- JDK 1.8+
- Maven 3.2+ 
- MyBatis 3
- Spring Boot 2.0.3


#### 使用步骤

1. cd dao && mvn clean install
2. 执行 mvn mybatis-generator:generate
3. 在 target/generated-sources/mybatis-generator/ 路径下查找生成的文件

#### Road Map

1. 生成 model & xml. √ 
2. 定制 枚举类 TypeHandler，在 row mapper 中直接映射为枚举. √
3. 生成 dao -> 定制通用 dao, 加入 page-helper 分页插件 √
4. 集成 spring boot. √
5. 接入自成长 SnowFlakeId, 实现不同机器不同配置（词表法）. √
6. 设置事务. √
7. 定制带超时时间的 local cache.
8. 引入 redis,设置通用集中式缓存.
9. 定制校验 enum 类型 validator.
11. 定制配置式 excel 导入/导出控件.
12. 引入通用后台页面框架，实现列表、编辑页.
13. 引入 shiro，控制权限，细化到权限点，即页面内按钮的控制.
14. 配置支持 zookeeper, 集中式配置.
15. 配置 dubbo. 
16. 最终成为一个 web 项目的通用底层架构.

当前进度: 6

#### 妥协
1. 暂不添加分库分表支持，因为现在很多解决方案都是屏蔽业务层感知的，在 web 和 DB 中间加一层，如 MyCat。