package cn.stylefeng.roses.kernel.log.api.pojo.loginlog;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
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
    @ChineseDescription("主键id")
    private Long llgId;

    /**
     * 日志名称
     */
    @ChineseDescription("日志名称")
    private String llgName;

    /**
     * 是否执行成功
     */
    @ChineseDescription("是否执行成功")
    private String llgSucceed;

    /**
     * 具体消息
     */
    @ChineseDescription("具体消息")
    private String llgMessage;

    /**
     * 登录ip
     */
    @ChineseDescription("登录ip")
    private String llgIpAddress;

    /**
     * 用户id
     */
    @ChineseDescription("用户id")
    private Long userId;

    /**
     * 登录姓名
     */
    @ChineseDescription("登录姓名")
    private String userName;

    /**
     * 开始时间
     */
    @ChineseDescription("创建时间")
    private Date createTime;

    /**
     * 开始时间
     */
    @ChineseDescription("开始时间")
    private String beginTime;

    /**
     * 结束时间
     */
    @ChineseDescription("结束时间")
    private String endTime;

}
