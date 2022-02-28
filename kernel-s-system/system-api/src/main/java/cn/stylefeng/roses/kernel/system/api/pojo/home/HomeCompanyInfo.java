package cn.stylefeng.roses.kernel.system.api.pojo.home;

import lombok.Data;

/**
 * 首页系统企业或公司信息封装
 *
 * @author xixiaowei
 * @date 2022/1/25 15:06
 */
@Data
public class HomeCompanyInfo {

    /**
     * 所有组织机构数
     */
    private Integer organizationNum;

    /**
     * 所有企业人员总数
     */
    private Integer enterprisePersonNum;

    /**
     * 所有职位总数
     */
    private Integer positionNum;

    /**
     * 当前登录用户，所在公司的部门数量
     */
    private Integer currentDeptNum;

    /**
     * 当前登录用户，所在公司的总人数
     */
    private Integer currentCompanyPersonNum;
}
