package cn.stylefeng.roses.kernel.sms.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统短信表
 *
 * @author fengshuonan
 * @date 2020/10/26 21:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_sms")
public class SysSms extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "sms_id", type = IdType.ASSIGN_ID)
    private Long smsId;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 短信验证码
     */
    @TableField("validate_code")
    private String validateCode;

    /**
     * 短信模板编号
     */
    @TableField("template_code")
    private String templateCode;

    /**
     * 业务id
     */
    @TableField("biz_id")
    private String bizId;

    /**
     * 发送状态：1-未发送，2-发送成功，3-发送失败，4-失效
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 来源：1-app，2-pc，3-其他
     */
    @TableField("source")
    private Integer source;

    /**
     * 短信失效截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("invalid_time")
    private Date invalidTime;

}
