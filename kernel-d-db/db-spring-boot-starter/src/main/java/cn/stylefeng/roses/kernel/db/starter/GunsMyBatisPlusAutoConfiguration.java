package cn.stylefeng.roses.kernel.db.starter;

import cn.stylefeng.roses.kernel.db.mp.dbid.CustomDatabaseIdProvider;
import cn.stylefeng.roses.kernel.db.mp.fieldfill.CustomMetaObjectHandler;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus的插件配置
 *
 * @author fengshuonan
 * @date 2020/11/30 22:40
 */
@Configuration
@AutoConfigureBefore(MybatisPlusAutoConfiguration.class)
public class GunsMyBatisPlusAutoConfiguration {

    /**
     * 新的分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 使用分页插插件
        interceptor.addInnerInterceptor(paginationInterceptor());
        return interceptor;
    }

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
