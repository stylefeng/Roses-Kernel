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
package cn.stylefeng.roses.kernel.sms.modular.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.sms.modular.entity.SysSms;
import cn.stylefeng.roses.kernel.sms.modular.param.SysSmsInfoParam;
import cn.stylefeng.roses.kernel.sms.modular.param.SysSmsSendParam;
import cn.stylefeng.roses.kernel.sms.modular.param.SysSmsVerifyParam;
import cn.stylefeng.roses.kernel.sms.modular.service.SysSmsInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 短信发送控制器
 *
 * @author fengshuonan
 * @date 2020/10/26 18:34
 */
@RestController
@ApiResource(name = "短信发送控制器")
public class SmsSenderController {

    @Resource
    private SysSmsInfoService sysSmsInfoService;

    /**
     * 发送记录查询
     *
     * @author fengshuonan
     * @date 2020/10/26 18:34
     */
    @GetResource(name = "发送记录查询", path = "/sms/page")
    public ResponseData<PageResult<SysSms>> page(SysSmsInfoParam sysSmsInfoParam) {
        return new SuccessResponseData<>(sysSmsInfoService.page(sysSmsInfoParam));
    }

    /**
     * 发送验证码短信
     *
     * @author fengshuonan
     * @date 2020/10/26 18:34
     */
    @PostResource(name = "发送验证码短信", path = "/sms/sendLoginMessage", requiredLogin = false, requiredPermission = false)
    public ResponseData<Boolean> sendMessage(@RequestBody @Validated SysSmsSendParam sysSmsSendParam) {

        // 清空params参数
        sysSmsSendParam.setParams(null);

        // 设置模板中的参数
        HashMap<String, Object> paramMap = MapUtil.newHashMap();
        paramMap.put("code", RandomUtil.randomNumbers(6));
        sysSmsSendParam.setParams(paramMap);

        return new SuccessResponseData<>(sysSmsInfoService.sendShortMessage(sysSmsSendParam));
    }

    /**
     * 验证短信验证码
     *
     * @author fengshuonan
     * @date 2020/10/26 18:35
     */
    @PostResource(name = "验证短信验证码", path = "/sms/validateMessage", requiredLogin = false, requiredPermission = false)
    public ResponseData<?> validateMessage(@RequestBody @Validated SysSmsVerifyParam sysSmsVerifyParam) {
        sysSmsInfoService.validateSmsInfo(sysSmsVerifyParam);
        return new SuccessResponseData<>("短信验证成功");
    }

}
