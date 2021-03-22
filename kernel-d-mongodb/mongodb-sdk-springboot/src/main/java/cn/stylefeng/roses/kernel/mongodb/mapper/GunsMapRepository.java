package cn.stylefeng.roses.kernel.mongodb.mapper;

import cn.stylefeng.roses.kernel.mongodb.entity.GunsMapEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author huziyang
 * @create 2021-03-20 16:24
 */
@Configuration
public interface GunsMapRepository extends MongoRepository<GunsMapEntity,String> {
}