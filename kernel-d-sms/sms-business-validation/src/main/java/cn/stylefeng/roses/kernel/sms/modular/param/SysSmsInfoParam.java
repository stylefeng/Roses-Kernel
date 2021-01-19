package cn.stylefeng.roses.kernel.sms.modular.param;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统短信表
 *
 * @author fengshuonan
 * @date 2020/10/26 22:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysSmsInfoParam extends BaseRequest {

    /**
     * 主键
     */
    private Long smsId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 短信验证码
     */
    private String validateCode;

    /**
     * 短信模板编号
     */
    private String templateCode;

    /**
     * 业务id
     */
    private String bizId;

    /**
     * 发送状态：1-未发送，2-发送成功，3-发送失败，4-失效
     */
    private Integer statusFlag;

    /**
     * 来源：1-app，2-pc，3-其他
     */
    private Integer source;

    /**
     * 短信失效截止时间
     */
    private Date invalidTime;

}
