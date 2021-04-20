package cn.stylefeng.roses.kernel.log.api.pojo.loginlog;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 登录日志的dto
 *
 * @author fengshuonan
 * @date 2021/3/30 20:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLoginLogDto extends BaseRequest {

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

    /**
     * 登录姓名
     */
    private String userName;

    /**
     * 开始时间
     */
    private Date createTime;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

}
