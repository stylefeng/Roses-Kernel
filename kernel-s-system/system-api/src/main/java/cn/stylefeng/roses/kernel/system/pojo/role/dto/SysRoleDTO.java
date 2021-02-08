package cn.stylefeng.roses.kernel.system.pojo.role.dto;

import cn.stylefeng.roses.kernel.auth.api.enums.DataScopeTypeEnum;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author majianguo
 * @date 2020/11/5 下午3:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleDTO extends BaseRequest {

    /**
     * 主键
     */
    private Long roleId;

    /**
     * 名称
     */
    private String roleName;

    /**
     * 编码
     */
    private String roleCode;

    /**
     * 排序
     */
    private Integer roleSort;

    /**
     * 数据范围类型：10-全部数据，20-本部门及以下数据，30-本部门数据，40-仅本人数据，50-自定义数据
     */
    private Integer dataScopeType;

    /**
     * 数据范围类型枚举
     */
    private DataScopeTypeEnum dataScopeTypeEnum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态：1-启用，2-禁用
     */
    private Integer statusFlag;

    /**
     * 是否是系统角色：Y-是，N-否
     */
    private String roleSystemFlag;

    /**
     * 角色类型
     */
    private String roleTypeCode;

}
