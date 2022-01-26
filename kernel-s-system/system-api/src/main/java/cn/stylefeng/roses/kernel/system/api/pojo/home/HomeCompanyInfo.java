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
     * 组织机构数
     */
    private Integer organizationNum;

    /**
     * 企业人员总数
     */
    private Integer enterprisePersonnelNum;

    /**
     * 职位总数
     */
    private Integer positionNum;

    /**
     * 部门总数
     */
    private Integer sectionNum;

    /**
     * 公司人员总数
     */
    private Integer companyPersonnelNum;
}
