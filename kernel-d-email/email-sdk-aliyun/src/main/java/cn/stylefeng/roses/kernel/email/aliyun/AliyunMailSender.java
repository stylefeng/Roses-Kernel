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
package cn.stylefeng.roses.kernel.email.aliyun;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.BatchSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import cn.stylefeng.roses.kernel.email.api.MailSenderApi;
import cn.stylefeng.roses.kernel.email.api.exception.MailException;
import cn.stylefeng.roses.kernel.email.api.exception.enums.EmailExceptionEnum;
import cn.stylefeng.roses.kernel.email.api.pojo.SendMailParam;
import cn.stylefeng.roses.kernel.email.api.pojo.aliyun.AliyunMailSenderProperties;
import cn.stylefeng.roses.kernel.email.api.pojo.aliyun.AliyunSendMailParam;

import static cn.stylefeng.roses.kernel.email.api.exception.enums.EmailExceptionEnum.ALIYUN_MAIL_SEND_ERROR;

/**
 * 阿里云邮件发送的实现
 *
 * @author fengshuonan
 * @date 2020/10/30 22:26
 */
public class AliyunMailSender implements MailSenderApi {

    private final AliyunMailSenderProperties aliyunMailSenderProperties;

    private IAcsClient acsClient;

    public AliyunMailSender(AliyunMailSenderProperties aliyunMailSenderProperties) {
        this.aliyunMailSenderProperties = aliyunMailSenderProperties;
    }

    @Override
    public void sendMail(SendMailParam sendMailParam) {
    }

    @Override
    public void sendMailHtml(SendMailParam sendMailParam) {
    }

    /**
     * 发送单个阿里云邮件
     *
     * @author fengshuonan
     * @date 2020/10/30 23:39
     */
    public void sendAliyunMail(AliyunSendMailParam aliyunSendMailParam) {

        // 校验发送邮件的参数
        assertSendMailParams(aliyunSendMailParam);

        // 初始化客户端
        initClient();

        // 创建发送单个邮件的请求
        SingleSendMailRequest singleSendRequest = createSingleSendRequest(aliyunSendMailParam);

        //如果调用成功，正常返回httpResponse；如果调用失败则抛出异常，需要在异常中捕获错误异常码；错误异常码请参考对应的API文档;
        try {
            acsClient.getAcsResponse(singleSendRequest);
        } catch (ClientException e) {
            String userTip = StrUtil.format(ALIYUN_MAIL_SEND_ERROR.getUserTip(), e.getErrCode());
            throw new MailException(ALIYUN_MAIL_SEND_ERROR, userTip);
        }
    }

    /**
     * 批量发送阿里云邮件
     *
     * @author fengshuonan
     * @date 2020/10/30 23:39
     */
    public void sendBatchAliyunMail(AliyunSendMailParam aliyunSendMailParam) {

        // 校验发送邮件的参数
        assertSendMailParams(aliyunSendMailParam);

        // 初始化客户端
        initClient();

        // 创建发送批量邮件的请求
        BatchSendMailRequest batchSendRequest = createBatchSendRequest(aliyunSendMailParam);

        //如果调用成功，正常返回httpResponse；如果调用失败则抛出异常，需要在异常中捕获错误异常码；错误异常码请参考对应的API文档;
        try {
            acsClient.getAcsResponse(batchSendRequest);
        } catch (ClientException e) {
            String userTip = StrUtil.format(ALIYUN_MAIL_SEND_ERROR.getUserTip(), e.getErrCode());
            throw new MailException(ALIYUN_MAIL_SEND_ERROR, userTip);
        }
    }

    /**
     * 阿里云邮箱推送方式发送邮件
     *
     * @author fengshuonan
     * @date 2020/10/30 22:28
     */
    private IAcsClient initClient() {
        if (acsClient != null) {
            return acsClient;
        }

        // 装载配置
        IClientProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou",
                aliyunMailSenderProperties.getAccessKeyId(),
                aliyunMailSenderProperties.getAccessKeySecret());

        acsClient = new DefaultAcsClient(profile);
        return acsClient;
    }

    /**
     * 创建发送一个邮件的请求
     *
     * @author fengshuonan
     * @date 2020/10/30 22:39
     */
    private SingleSendMailRequest createSingleSendRequest(AliyunSendMailParam aliyunSendMailParam) {
        SingleSendMailRequest request = new SingleSendMailRequest();

        // 控制台创建的发信地址
        request.setAccountName(aliyunMailSenderProperties.getAccountName());

        // 发信人昵称
        request.setFromAlias(aliyunSendMailParam.getFromAlias());

        // 地址类型：0-为随机账号，1-为发信地址
        request.setAddressType(1);

        // 控制台创建的标签
        request.setTagName(aliyunSendMailParam.getTagName());

        // 使用管理台配置的回信地址
        request.setReplyToAddress(true);

        // 发信地址
        request.setToAddress(aliyunSendMailParam.getTo());

        // 邮件主题
        request.setSubject(aliyunSendMailParam.getTitle());

        //如果采用byte[].toString的方式的话请确保最终转换成utf-8的格式再放入htmlbody和textbody，若编码不一致则会被当成垃圾邮件。
        request.setHtmlBody(aliyunSendMailParam.getContent());

        //SDK 采用的是http协议的发信方式, 默认是GET方法，有一定的长度限制。
        //若textBody、htmlBody或content的大小不确定，建议采用POST方式提交，避免出现uri is not valid异常
        request.setMethod(MethodType.POST);

        //是否开启追踪功能，开启需要备案，0关闭，1开启
        request.setClickTrace("0");

        return request;
    }

    /**
     * 创建发送批量邮件的请求
     *
     * @author fengshuonan
     * @date 2020/10/30 22:39
     */
    private BatchSendMailRequest createBatchSendRequest(AliyunSendMailParam aliyunSendMailParam) {
        BatchSendMailRequest request = new BatchSendMailRequest();

        // 控制台创建的发信地址
        request.setAccountName(aliyunMailSenderProperties.getAccountName());

        // 预先创建且上传了收件人的收件人列表名称
        request.setReceiversName(aliyunSendMailParam.getReceiversName());

        // 邮件模板，在控制台创建，相当于邮件的内容
        request.setTemplateName(aliyunSendMailParam.getTemplateName());

        // 地址类型：0-为随机账号，1-为发信地址
        request.setAddressType(1);

        // 控制台创建的标签
        request.setTagName(aliyunSendMailParam.getTagName());

        //SDK 采用的是http协议的发信方式, 默认是GET方法，有一定的长度限制。
        //若textBody、htmlBody或content的大小不确定，建议采用POST方式提交，避免出现uri is not valid异常
        request.setMethod(MethodType.POST);

        //是否开启追踪功能，开启需要备案，0关闭，1开启
        request.setClickTrace("0");

        return request;
    }

    /**
     * 校验发送邮件的请求参数
     *
     * @author fengshuonan
     * @date 2018/7/8 下午6:41
     */
    private void assertSendMailParams(AliyunSendMailParam aliyunSendMailParam) {
        if (aliyunSendMailParam == null) {
            String format = StrUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }

        if (ObjectUtil.isEmpty(aliyunSendMailParam.getTo())) {
            String format = StrUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "收件人邮箱");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }

        if (ObjectUtil.isEmpty(aliyunSendMailParam.getTitle())) {
            String format = StrUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "邮件标题");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }

        if (ObjectUtil.isEmpty(aliyunSendMailParam.getContent())) {
            String format = StrUtil.format(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getUserTip(), "邮件内容");
            throw new MailException(EmailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getErrorCode(), format);
        }
    }

}
