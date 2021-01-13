package cn.stylefeng.roses.kernel.system.pojo.loginlog.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录日志表
 *
 * @author chenjinlong
 * @date 2021/1/13 11:06
 */
@Data
public class SysLoginLogResponse implements Serializable {

    /**
     * 主键id
     */
    private Long llgId;

    /**
     * 日志名称
     */
    private String llgName;

    /**
     * 是否执行成功
     */
    private String llgSucceed;

    /**
     * 具体消息
     */
    private String llgMessage;

    /**
     * 登录ip
     */
    private String llgIpAddress;

    /**
     * 用户id
     */
    private Long userId;

}
