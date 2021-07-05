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

import cn.hutool.core.codec.Base64;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.security.api.DragCaptchaApi;
import cn.stylefeng.roses.kernel.security.api.exception.SecurityException;
import cn.stylefeng.roses.kernel.security.api.exception.enums.SecurityExceptionEnum;
import cn.stylefeng.roses.kernel.security.api.pojo.DragCaptchaImageDTO;
import cn.stylefeng.roses.kernel.security.captcha.util.DragCaptchaImageUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 拖拽验证码实现
 *
 * @author fengshuonan
 * @date 2021/7/5 11:34
 */
@Slf4j
public class DragCaptchaService implements DragCaptchaApi {

    private final CacheOperatorApi<String> cacheOperatorApi;

    public DragCaptchaService(CacheOperatorApi<String> cacheOperatorApi) {
        this.cacheOperatorApi = cacheOperatorApi;
    }

    public DragCaptchaImageDTO createCaptcha() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode(DragCaptchaImageUtil.IMAGE_BASE64));
        try {
            DragCaptchaImageDTO dragCaptchaImageDTO = DragCaptchaImageUtil.getVerifyImage(byteArrayInputStream);

            // 缓存x轴坐标
            String verKey = IdUtil.simpleUUID();
            Integer verValue = dragCaptchaImageDTO.getLocationX();
            cacheOperatorApi.put(verKey, verValue.toString());

            // 清空x轴坐标
            dragCaptchaImageDTO.setKey(verKey);
//            dragCaptchaImageDTO.setLocationX(null);

            return dragCaptchaImageDTO;
        } catch (IOException e) {
            throw new SecurityException(SecurityExceptionEnum.CAPTCHA_ERROR);
        } finally {
            IoUtil.close(byteArrayInputStream);
        }
    }

    public boolean validateCaptcha(String verKey, Integer verScope) {
        if (StrUtil.isEmpty(verKey)) {
            return false;
        }
        if (verScope == null) {
            return false;
        }

        // 获取缓存中正确的locationX的值
        String locationXString = cacheOperatorApi.get(verKey);
        if (StrUtil.isEmpty(locationXString)) {
            throw new SecurityException(SecurityExceptionEnum.CAPTCHA_INVALID_ERROR);
        }

        // 获取缓存中存储的范围
        Integer locationX = Convert.toInt(locationXString);
        int beginScope = locationX - 5;
        int endScope = locationX + 5;

        // 每次验证不管成功和失败都剔除掉key
        cacheOperatorApi.remove(verKey);

        // 验证缓存中的范围值
        return verScope >= beginScope && verScope <= endScope;
    }

}
