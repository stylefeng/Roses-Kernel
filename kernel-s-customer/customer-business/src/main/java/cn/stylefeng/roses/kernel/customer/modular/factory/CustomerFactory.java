package cn.stylefeng.roses.kernel.customer.modular.factory;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordStoredEncryptApi;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleUserInfo;
import cn.stylefeng.roses.kernel.customer.api.expander.CustomerConfigExpander;
import cn.stylefeng.roses.kernel.customer.modular.entity.Customer;
import cn.stylefeng.roses.kernel.customer.modular.request.CustomerRequest;
import cn.stylefeng.roses.kernel.email.api.pojo.SendMailParam;
import cn.stylefeng.roses.kernel.file.api.FileOperatorApi;
import cn.stylefeng.roses.kernel.file.api.constants.FileConstants;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;

import java.util.Collections;
import java.util.Date;

/**
 * C端用户实体组装
 *
 * @author fengshuonan
 * @date 2021/6/7 15:10
 */
public class CustomerFactory {

    /**
     * 创建注册用户实体
     *
     * @author fengshuonan
     * @date 2021/6/7 15:10
     */
    public static Customer createRegCustomer(CustomerRequest customerRequest) {

        Customer customer = new Customer();

        // 账号
        customer.setAccount(customerRequest.getAccount());

        // 密码
        PasswordStoredEncryptApi passwordStoredEncryptApi = SpringUtil.getBean(PasswordStoredEncryptApi.class);
        customer.setPassword(passwordStoredEncryptApi.encrypt(customerRequest.getPassword()));

        // 昵称
        customer.setNickName(customerRequest.getNickName());

        // 邮箱
        customer.setEmail(customerRequest.getEmail());

        // 生成随机邮箱验证码
        customer.setVerifyCode(RandomUtil.randomString(29).toUpperCase());

        // 设置是否已经验证，未验证
        customer.setVerifiedFlag(YesOrNotEnum.N.getCode());

        // 设置默认头像
        customer.setAvatar(FileConstants.DEFAULT_AVATAR_FILE_ID);
        customer.setAvatarObjectName(FileConstants.DEFAULT_AVATAR_FILE_OBJ_NAME);

        // 设置默认积分0
        customer.setScore(0);

        // 设置状态
        customer.setStatusFlag(StatusEnum.ENABLE.getCode());

        return customer;
    }

    /**
     * 创建发送注册激活邮件的参数
     *
     * @author fengshuonan
     * @date 2021/6/7 15:27
     */
    public static SendMailParam createRegEmailParam(String mail, String verifyCode) {
        String title = CustomerConfigExpander.getRegMailTitle();
        String template = CustomerConfigExpander.getRegMailContent();

        SendMailParam sendMailParam = new SendMailParam();
        sendMailParam.setTos(Collections.singletonList(mail));
        sendMailParam.setTitle(title);
        sendMailParam.setContent(StrUtil.format(template, verifyCode, verifyCode));
        return sendMailParam;
    }

    /**
     * 创建loginUser对象
     *
     * @author fengshuonan
     * @date 2021/6/7 17:06
     */
    public static LoginUser createLoginUser(Customer customer, FileOperatorApi fileOperatorApi) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(customer.getCustomerId());
        loginUser.setAccount(customer.getAccount());
        loginUser.setLoginTime(new Date());

        SimpleUserInfo simpleUserInfo = new SimpleUserInfo();
        simpleUserInfo.setAvatar(customer.getAvatar());
        simpleUserInfo.setNickName(customer.getNickName());
        simpleUserInfo.setEmail(customer.getEmail());
        simpleUserInfo.setPhone(customer.getTelephone());
        loginUser.setSimpleUserInfo(simpleUserInfo);

        // 设置用户头像url
        String fileAuthUrl = fileOperatorApi.getFileUnAuthUrl(CustomerConfigExpander.getCustomerBucket(), customer.getAvatarObjectName());
        loginUser.setAvatarUrl(fileAuthUrl);

        // 设置用户是C端用户
        loginUser.setCustomerFlag(true);

        return loginUser;
    }

    /**
     * 创建发送注册激活邮件的参数
     *
     * @author fengshuonan
     * @date 2021/6/7 15:27
     */
    public static SendMailParam createResetPwdEmail(String mail, String randomCode) {
        String title = CustomerConfigExpander.getResetPwdMailTitle();
        String template = CustomerConfigExpander.getResetPwdMailContent();

        SendMailParam sendMailParam = new SendMailParam();
        sendMailParam.setTos(Collections.singletonList(mail));
        sendMailParam.setTitle(title);
        sendMailParam.setContent(StrUtil.format(template, randomCode));
        return sendMailParam;
    }

}
