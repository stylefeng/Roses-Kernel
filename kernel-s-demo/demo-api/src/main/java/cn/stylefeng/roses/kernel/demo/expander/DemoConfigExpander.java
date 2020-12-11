package cn.stylefeng.roses.kernel.demo.expander;

import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;

/**
 * 演示环境相关的配置拓展
 *
 * @author fengshuonan
 * @date 2020/10/17 16:10
 */
public class DemoConfigExpander {

    /**
     * 获取演示环境是否开启，默认是关闭的
     *
     * @return true-开启，false-关闭
     * @author fengshuonan
     * @date 2020/10/17 16:12
     */
    public static Boolean getDemoEnvFlag() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_DEMO_ENV_FLAG", Boolean.class, false);
    }

}
