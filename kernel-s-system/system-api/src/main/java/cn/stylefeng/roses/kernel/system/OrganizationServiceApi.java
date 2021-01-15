package cn.stylefeng.roses.kernel.system;

import cn.stylefeng.roses.kernel.system.pojo.organization.HrOrganizationResponse;

import java.util.List;

/**
 * 组织机构api
 *
 * @author liuhanqing
 * @date 2021/1/15 10:40
 */
public interface OrganizationServiceApi {

    /**
     * 查询系统组织机构
     *
     * @return 组织机构列表
     * @author liuhanqing
     * @date 2021/1/15 10:41
     */
    List<HrOrganizationResponse> orgList();
}
