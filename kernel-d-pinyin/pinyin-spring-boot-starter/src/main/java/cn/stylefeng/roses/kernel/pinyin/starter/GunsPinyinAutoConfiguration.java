package cn.stylefeng.roses.kernel.pinyin.starter;

import cn.stylefeng.roses.kernel.pinyin.PinyinServiceImpl;
import cn.stylefeng.roses.kernel.pinyin.api.PinYinApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 拼音的自动配置
 *
 * @author fengshuonan
 * @date 2020/12/4 15:28
 */
@Configuration
public class GunsPinyinAutoConfiguration {

    /**
     * 拼音工具接口的封装
     *
     * @author fengshuonan
     * @date 2020/12/4 15:29
     */
    @Bean
    @ConditionalOnMissingBean(PinYinApi.class)
    public PinYinApi pinYinApi() {
        return new PinyinServiceImpl();
    }

}
