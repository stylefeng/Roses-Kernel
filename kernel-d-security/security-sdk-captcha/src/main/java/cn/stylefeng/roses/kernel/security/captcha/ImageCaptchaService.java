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
package cn.stylefeng.roses.kernel.security.captcha;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.security.api.ImageCaptchaApi;
import cn.stylefeng.roses.kernel.security.api.pojo.ImageCaptcha;
import com.wf.captcha.SpecCaptcha;

/**
 * 图形验证码实现
 *
 * @author chenjinlong
 * @date 2021/1/15 13:44
 */
public class ImageCaptchaService implements ImageCaptchaApi {

    private final CacheOperatorApi<String> cacheOperatorApi;

    public ImageCaptchaService(CacheOperatorApi<String> cacheOperatorApi) {
        this.cacheOperatorApi = cacheOperatorApi;
    }

    @Override
    public ImageCaptcha captcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String verKey = IdUtil.simpleUUID();
        cacheOperatorApi.put(verKey, verCode);
        return ImageCaptcha.builder().verImage(specCaptcha.toBase64()).verKey(verKey).build();
    }

    @Override
    public boolean validateCaptcha(String verKey, String verCode) {
        if (StrUtil.isAllEmpty(verKey, verCode)) {
            return false;
        }

        if (!verCode.trim().toLowerCase().equals(cacheOperatorApi.get(verKey))) {
            return false;
        }

        //删除缓存中验证码
        cacheOperatorApi.remove(verKey);

        return true;
    }

    @Override
    public String getVerCode(String verKey) {
        return cacheOperatorApi.get(verKey);
    }

}
