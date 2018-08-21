mybatis-generator-demo
==
> 使用 mybatis-generator 简化 curd 操作的开发，让技术更关注业务

#### 版本要求
- JDK 1.8+
- Maven 3.2+ 
- MyBatis 3

#### 使用步骤

1. cd dao && mvn clean install
2. 执行 mvn mybatis-generator:generate
3. 在 target/generated-sources/mybatis-generator/ 路径下查找生成的文件


#### Road Map

1. 生成 model & xml. √ 
2. 定制 枚举类 TypeHandler，在 row mapper 中直接映射为枚举. √
3. 生成 dao -> 定制通用 dao, 加入 page-helper 分页插件
4. 集成 spring boot. √
5. 接入自成长 id.
6. 定制带超时时间的 local cache.
7. 引入 redis.
8. 引入通用后台页面框架，实现列表、编辑页.
9. 定制 excel 导入/导出控件.
10. 配置 dubbo. 
11. 最终成为一个 web 项目的通用底层架构.

当前进度: 1