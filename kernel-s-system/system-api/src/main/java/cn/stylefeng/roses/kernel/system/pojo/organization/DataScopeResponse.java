package cn.stylefeng.roses.kernel.system.pojo.organization;

import cn.stylefeng.roses.kernel.auth.api.enums.DataScopeTypeEnum;
import cn.stylefeng.roses.kernel.system.pojo.role.response.SysRoleResponse;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 查询用户的数据范围时候的返回结果封装
 *
 * @author fengshuonan
 * @date 2020/11/6 11:30
 */
@Data
public class DataScopeResponse {

    /**
     * 数据范围类型的响应结果
     */
    private Set<DataScopeTypeEnum> dataScopeTypeEnums;

    /**
     * 用户id数据范围集合
     */
    private Set<Long> userIds;

    /**
     * 组织架构id数据范围集合
     */
    private Set<Long> organizationIds;

    /**
     * 角色信息
     */
    private List<SysRoleResponse> sysRoleResponses;

}
