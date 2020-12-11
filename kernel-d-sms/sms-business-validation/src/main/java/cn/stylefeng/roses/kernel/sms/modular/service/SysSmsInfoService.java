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
