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
package cn.stylefeng.roses.kernel.sms.modular.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.security.api.ImageCaptchaApi;
import cn.stylefeng.roses.kernel.sms.api.SmsSenderApi;
import cn.stylefeng.roses.kernel.sms.api.exception.SmsException;
import cn.stylefeng.roses.kernel.sms.api.expander.SmsConfigExpander;
import cn.stylefeng.roses.kernel.sms.modular.entity.SysSms;
import cn.stylefeng.roses.kernel.sms.modular.enums.SmsSendStatusEnum;
import cn.stylefeng.roses.kernel.sms.modular.enums.SmsTypeEnum;
import cn.stylefeng.roses.kernel.sms.modular.mapper.SysSmsMapper;
import cn.stylefeng.roses.kernel.sms.modular.param.SysSmsInfoParam;
import cn.stylefeng.roses.kernel.sms.modular.param.SysSmsSendParam;
import cn.stylefeng.roses.kernel.sms.modular.param.SysSmsVerifyParam;
import cn.stylefeng.roses.kernel.sms.modular.service.SysSmsInfoService;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.validator.api.exception.enums.ValidatorExceptionEnum;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.stylefeng.roses.kernel.sms.api.constants.SmsConstants.SMS_CODE_PARAM_NAME;
import static cn.stylefeng.roses.kernel.sms.api.exception.enums.SmsExceptionEnum.*;

/**
 * 系统短信接口实现类
 *
 * @author fengshuonan
 * @date 2020/10/26 22:17
 */
@Service
@Slf4j
public class SysSmsInfoServiceImpl extends ServiceImpl<SysSmsMapper, SysSms> implements SysSmsInfoService {

    @Resource
    private SmsSenderApi smsSenderApi;

    @Resource
    private ImageCaptchaApi captchaApi;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean sendShortMessage(SysSmsSendParam sysSmsSendParam) {
        String verKey = sysSmsSendParam.getVerKey();
        String verCode = sysSmsSendParam.getVerCode();
        if (StrUtil.isEmpty(verKey) || StrUtil.isEmpty(verCode)) {
            throw new SystemModularException(ValidatorExceptionEnum.CAPTCHA_EMPTY);
        }
        if (!captchaApi.validateCaptcha(verKey, verCode)) {
            throw new SystemModularException(ValidatorExceptionEnum.CAPTCHA_ERROR);
        }

        Map<String, Object> params = sysSmsSendParam.getParams();

        // 1. 如果是纯消息发送，直接发送，校验类短信要把验证码存库
        if (SmsTypeEnum.MESSAGE.equals(sysSmsSendParam.getSmsTypeEnum())) {
            smsSenderApi.sendSms(sysSmsSendParam.getPhone(), sysSmsSendParam.getTemplateCode(), params);
        }

        // 2. 如果参数中有code参数，则获取参数param中的code值
        String validateCode;
        if (params != null && params.get(SMS_CODE_PARAM_NAME) != null) {
            validateCode = params.get(SMS_CODE_PARAM_NAME).toString();
        }

        // 3. 如果参数中没有code参数，自动装填一个code参数，用于放随机的验证码
        else {
            validateCode = RandomUtil.randomNumbers(6);
            if (params == null) {
                params = MapUtil.newHashMap();
            }
            params.put(SMS_CODE_PARAM_NAME, validateCode);
        }

        // 4. 存储短信到数据库
        Long smsId = this.saveSmsInfo(sysSmsSendParam, validateCode);

        log.info("开始发送短信：发送的电话号码= " + sysSmsSendParam.getPhone() + ",发送的模板号=" + sysSmsSendParam.getTemplateCode() + "，发送的参数是：" + JSON.toJSONString(params));

        // 5. 发送短信
        smsSenderApi.sendSms(sysSmsSendParam.getPhone(), sysSmsSendParam.getTemplateCode(), params);

        // 6. 更新短信发送状态
        this.updateSmsInfo(smsId, SmsSendStatusEnum.SUCCESS);

        return true;
    }

    @Override
    public Long saveSmsInfo(SysSmsSendParam sysSmsSendParam, String validateCode) {

        // 获取当前时间
        Date nowDate = new Date();

        // 计算短信失效时间
        Integer invalidedSeconds = SmsConfigExpander.getSmsValidateExpiredSeconds();
        long invalidateTime = nowDate.getTime() + invalidedSeconds * 1000;
        Date invalidate = new Date(invalidateTime);

        SysSms sysSms = new SysSms();
        sysSms.setCreateTime(nowDate);
        sysSms.setInvalidTime(invalidate);
        sysSms.setPhoneNumber(sysSmsSendParam.getPhone());
        sysSms.setStatusFlag(SmsSendStatusEnum.WAITING.getCode());
        sysSms.setSource(sysSmsSendParam.getSmsSendSourceEnum().getCode());
        sysSms.setTemplateCode(sysSmsSendParam.getTemplateCode());
        sysSms.setValidateCode(validateCode);

        this.save(sysSms);

        log.info("发送短信，存储短信到数据库，数据为：" + JSON.toJSONString(sysSms));

        return sysSms.getSmsId();
    }

    @Override
    public void updateSmsInfo(Long smsId, SmsSendStatusEnum smsSendStatusEnum) {
        SysSms sysSms = this.getById(smsId);
        sysSms.setStatusFlag(smsSendStatusEnum.getCode());
        this.updateById(sysSms);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void validateSmsInfo(SysSmsVerifyParam sysSmsVerifyParam) {

        // 查询有没有这条记录
        LambdaQueryWrapper<SysSms> smsQueryWrapper = new LambdaQueryWrapper<>();
        smsQueryWrapper.eq(SysSms::getPhoneNumber, sysSmsVerifyParam.getPhone())
                .and(f -> f.eq(SysSms::getSource, sysSmsVerifyParam.getSmsSendSourceEnum().getCode()))
                .and(f -> f.eq(SysSms::getTemplateCode, sysSmsVerifyParam.getTemplateCode()));
        smsQueryWrapper.orderByDesc(SysSms::getCreateTime);

        List<SysSms> sysSmsList = this.list(smsQueryWrapper);

        log.info("验证短信Provider接口，查询到sms记录：" + JSON.toJSONString(sysSmsList));

        // 如果找不到记录，提示验证失败
        if (ObjectUtil.isEmpty(sysSmsList)) {
            throw new SmsException(SMS_VALIDATE_ERROR_NOT_EXISTED_RECORD);
        }

        // 获取最近发送的第一条
        SysSms sysSms = sysSmsList.get(0);

        // 先判断状态是不是失效的状态
        if (SmsSendStatusEnum.INVALID.getCode().equals(sysSms.getStatusFlag())) {
            throw new SmsException(SMS_VALIDATE_ERROR_INVALIDATE_STATUS);
        }

        // 如果验证码和传过来的不一致
        if (!sysSmsVerifyParam.getCode().equals(sysSms.getValidateCode())) {
            throw new SmsException(SMS_VALIDATE_ERROR_INVALIDATE_CODE);
        }

        // 判断是否超时
        Date invalidTime = sysSms.getInvalidTime();
        if (ObjectUtil.isEmpty(invalidTime) || new Date().after(invalidTime)) {
            throw new SmsException(SMS_VALIDATE_ERROR_INVALIDATE_TIME);
        }

        // 验证成功把短信设置成失效
        sysSms.setStatusFlag(SmsSendStatusEnum.INVALID.getCode());
        this.updateById(sysSms);
    }

    @Override
    public PageResult<SysSms> page(SysSmsInfoParam sysSmsInfoParam) {
        LambdaQueryWrapper<SysSms> queryWrapper = new LambdaQueryWrapper<>();

        if (ObjectUtil.isNotNull(sysSmsInfoParam)) {

            // 根据手机号模糊查询
            if (ObjectUtil.isNotEmpty(sysSmsInfoParam.getPhone())) {
                queryWrapper.like(SysSms::getPhoneNumber, sysSmsInfoParam.getPhone());
            }

            // 根据发送状态查询（字典 0 未发送，1 发送成功，2 发送失败，3 失效）
            if (ObjectUtil.isNotEmpty(sysSmsInfoParam.getStatusFlag())) {
                queryWrapper.eq(SysSms::getStatusFlag, sysSmsInfoParam.getStatusFlag());
            }

            // 根据来源查询（字典 1 app， 2 pc， 3 其他）
            if (ObjectUtil.isNotEmpty(sysSmsInfoParam.getSource())) {
                queryWrapper.eq(SysSms::getSource, sysSmsInfoParam.getSource());
            }
        }

        // 查询分页结果
        Page<SysSms> page = this.page(PageFactory.defaultPage(), queryWrapper);
        return PageResultFactory.createPageResult(page);
    }


}
