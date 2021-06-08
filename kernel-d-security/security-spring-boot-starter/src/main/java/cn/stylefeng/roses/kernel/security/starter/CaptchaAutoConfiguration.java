/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.security.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.roses.kernel.cache.api.constants.CacheConstants;
import cn.stylefeng.roses.kernel.security.api.CaptchaApi;
import cn.stylefeng.roses.kernel.security.captcha.CaptchaService;
import cn.stylefeng.roses.kernel.security.captcha.cache.CaptchaMemoryCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 图形验证码自动配置
 *
 * @author fengshuonan
 * @date 2020/12/1 21:44
 */
@Configuration
public class CaptchaAutoConfiguration {

    /**
     * 图形验证码
     *
     * @author chenjinlong
     * @date 2021/1/15 11:25
     */
    @Bean
    @ConditionalOnMissingBean(CaptchaApi.class)
    public CaptchaApi captchaApi() {
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(CacheConstants.NONE_EXPIRED_TIME);
        CaptchaMemoryCache captchaMemoryCache = new CaptchaMemoryCache(timedCache);
        return new CaptchaService(captchaMemoryCache);
    }

}
