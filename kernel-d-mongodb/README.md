# Mongodb模块

## GridFS文件管理

### 部署的sql，并为角色配置菜单

```sql
INSERT INTO guns.sys_menu (menu_id, menu_parent_id, menu_pids, menu_name, menu_code, app_code, visible, menu_sort, status_flag, remark, layui_path, layui_icon, antdv_router, antdv_icon, antdv_component, antdv_link_open_type, antdv_link_url, del_flag, create_time, create_user, update_time, update_user) VALUES(1377141796257218561, 1339550467939639317, '[-1],[1339550467939639317],', 'Mongodb 文件存储', 'mongo_file', 'system', 'Y', 1000.00, 1, NULL, '/view/mongodb/file', 'layui-icon-star-fill', NULL, 'icon-default', NULL, 0, NULL, 'N', '2021-03-31 14:12:45', 1339550467939639299, NULL, NULL);
```

### application.yml添加mongodb配置

```yml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:8002
      database: test
```

### SpringBoot启动类上添加配置

```java
@EnableMongoRepositories(basePackages = {"cn.stylefeng.roses.kernel.mongodb.file.mapper"})
```

### 添加GridFSBucket配置类

```java
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huziyang
 * @create 2021-03-26 17:28
 */
@Configuration
public class MongodbConfig {
    @Value("${spring.data.mongodb.database}")
    String db;

    @Bean
    public GridFSBucket getGridFSBucket(MongoClient mongoClient){
        MongoDatabase mongoDatabase = mongoClient.getDatabase(db);
        return GridFSBuckets.create(mongoDatabase);
    }
}
```

### pom中引用工作流的依赖

```xml
<!--mongodb文件管理的配置-->
<dependency>
  <groupId>cn.stylefeng.roses</groupId>
  <artifactId>mongodb-integration-beetl</artifactId>
  <version>${current.roses.version}</version>
</dependency>
```

