package cn.stylefeng.roses.kernel.system.modular.loginlog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 登录日志表
 *
 * @author chenjinlong
 * @date 2021/1/13 11:05
 */
@Data
@TableName("sys_login_log")
public class SysLoginLog {

    /**
     * 主键id
     */
    @TableId("llg_id")
    private Long llgId;

    /**
     * 日志名称
     */
    @TableField("llg_name")
    private String llgName;

    /**
     * 是否执行成功
     */
    @TableField("llg_succeed")
    private String llgSucceed;

    /**
     * 具体消息
     */
    @TableField("llg_message")
    private String llgMessage;

    /**
     * 登录ip
     */
    @TableField("llg_ip_address")
    private String llgIpAddress;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

}
