package cn.stylefeng.roses.kernel.mongodb.starter;

import cn.stylefeng.roses.kernel.mongodb.api.MongodbApi;
import cn.stylefeng.roses.kernel.mongodb.service.impl.GunsMapServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huziyang
 * @create 2021-03-20 16:24
 */
@Configuration
public class GunsMongodbAutoConfiguration {


    @Bean
    public MongodbApi mongodbApi() {
        return new GunsMapServiceImpl();
    }


}

