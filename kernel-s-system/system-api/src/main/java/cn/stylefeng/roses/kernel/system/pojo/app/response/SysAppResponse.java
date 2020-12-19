package cn.stylefeng.roses.kernel.system.pojo.app.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统应用表
 *
 * @author fengshuonan
 * @date 2020/11/24 21:05
 */
@Data
public class SysAppResponse implements Serializable {

    /**
     * 主键id
     */
    private Long appId;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 编码
     */
    private String appCode;

    /**
     * 是否默认激活（Y-是，N-否）
     */
    private String activeFlag;

    /**
     * 状态（字典 1启用 2禁用）
     */
    private Integer statusFlag;

    /**
     * 是否删除（Y-已删除，N-未删除）
     */
    private String delFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private Long updateUser;

}
