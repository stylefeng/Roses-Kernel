package cn.stylefeng.roses.kernel.db.starter;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import cn.stylefeng.roses.kernel.db.mp.dbid.CustomDatabaseIdProvider;
import cn.stylefeng.roses.kernel.db.mp.fieldfill.CustomMetaObjectHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus的插件配置
 *
 * @author fengshuonan
 * @date 2020/11/30 22:40
 */
@Configuration
@AutoConfigureAfter(MybatisPlusAutoConfiguration.class)
public class GunsMyBatisPlusAutoConfiguration {

    /**
     * 分页插件
     *
     * @author fengshuonan
     * @date 2020/11/30 22:41
     */
    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        return new PaginationInnerInterceptor();
    }

    /**
     * 公共字段填充插件
     *
     * @author fengshuonan
     * @date 2020/11/30 22:41
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new CustomMetaObjectHandler();
    }

    /**
     * 数据库id选择器，兼容多个数据库sql脚本
     *
     * @author fengshuonan
     * @date 2020/11/30 22:42
     */
    @Bean
    public CustomDatabaseIdProvider customDatabaseIdProvider() {
        return new CustomDatabaseIdProvider();
    }

}
