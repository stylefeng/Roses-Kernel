package cn.stylefeng.roses.kernel.file.starter;

import cn.stylefeng.roses.kernel.file.FileOperatorApi;
import cn.stylefeng.roses.kernel.file.expander.FileConfigExpander;
import cn.stylefeng.roses.kernel.file.local.LocalFileOperator;
import cn.stylefeng.roses.kernel.file.pojo.props.LocalFileProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件的自动配置
 *
 * @author fengshuonan
 * @date 2020/12/1 14:34
 */
@Configuration
public class GunsFileAutoConfiguration {

    /**
     * 本地文件操作
     *
     * @author fengshuonan
     * @date 2020/12/1 14:40
     */
    @Bean
    @ConditionalOnMissingBean(FileOperatorApi.class)
    public FileOperatorApi fileOperatorApi() {

        LocalFileProperties localFileProperties = new LocalFileProperties();

        // 从系统配置表中读取配置
        localFileProperties.setLocalFileSavePathLinux(FileConfigExpander.getLocalFileSavePathLinux());
        localFileProperties.setLocalFileSavePathWin(FileConfigExpander.getLocalFileSavePathWindows());

        return new LocalFileOperator(localFileProperties);
    }

}
