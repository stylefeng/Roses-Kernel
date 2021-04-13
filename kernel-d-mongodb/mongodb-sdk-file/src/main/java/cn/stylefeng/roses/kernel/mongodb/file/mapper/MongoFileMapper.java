package cn.stylefeng.roses.kernel.mongodb.file.mapper;

import cn.stylefeng.roses.kernel.mongodb.file.entity.MongoFileEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author huziyang
 * @create 2021-03-26 17:27
 */
@Configuration
public interface MongoFileMapper extends MongoRepository<MongoFileEntity,String> {
}
