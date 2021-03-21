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
package cn.stylefeng.roses.kernel.sms.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.sms.modular.entity.SysSms;
import cn.stylefeng.roses.kernel.sms.modular.enums.SmsSendStatusEnum;
import cn.stylefeng.roses.kernel.sms.modular.param.SysSmsInfoParam;
import cn.stylefeng.roses.kernel.sms.modular.param.SysSmsSendParam;
import cn.stylefeng.roses.kernel.sms.modular.param.SysSmsVerifyParam;

/**
 * 系统短信service接口
 *
 * @author fengshuonan
 * @date 2020/10/26 22:16
 */
public interface SysSmsInfoService extends IService<SysSms> {

    /**
     * 发送短信
     *
     * @param sysSmsSendParam 短信发送参数
     * @return true-成功，false-失败
     * @author fengshuonan
     * @date 2020/10/26 22:16
     */
    boolean sendShortMessage(SysSmsSendParam sysSmsSendParam);

    /**
     * 存储短信验证信息
     *
     * @param sysSmsSendParam 发送参数
     * @param validateCode    验证码
     * @return 短信记录id
     * @author fengshuonan
     * @date 2020/10/26 22:16
     */
    Long saveSmsInfo(SysSmsSendParam sysSmsSendParam, String validateCode);

    /**
     * 更新短息发送状态
     *
     * @param smsId             短信记录id
     * @param smsSendStatusEnum 发送状态枚举
     * @author fengshuonan
     * @date 2020/10/26 22:16
     */
    void updateSmsInfo(Long smsId, SmsSendStatusEnum smsSendStatusEnum);

    /**
     * 校验验证码是否正确
     * <p>
     * 如果校验失败，或者短信超时，则会抛出异常
     *
     * @param sysSmsVerifyParam 短信校验参数
     * @author fengshuonan
     * @date 2020/10/26 22:16
     */
    void validateSmsInfo(SysSmsVerifyParam sysSmsVerifyParam);

    /**
     * 短信发送记录查询
     *
     * @param sysSmsInfoParam 查询参数
     * @return 查询分页结果
     * @author fengshuonan
     * @date 2020/10/26 22:17
     */
    PageResult<SysSms> page(SysSmsInfoParam sysSmsInfoParam);

}
