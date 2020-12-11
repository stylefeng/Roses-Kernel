package cn.stylefeng.roses.kernel.system.modular.organization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.SysEmployee;
import cn.stylefeng.roses.kernel.system.pojo.organization.SysEmployeeRequest;

import java.util.List;

/**
 * 企业员工的管理，用户和组织机构的绑定
 * <p>
 * 用户在添加到sys_user表后没有和组织机构关联，通过employee这个表来关联用户和组织机构
 *
 * @author fengshuonan
 * @date 2020/11/04 11:05
 */
public interface SysEmployeeService extends IService<SysEmployee> {

    /**
     * 分页查询企业员工
     *
     * @param sysEmployeeRequest 企业员工查询条件
     * @return 企业员工详情分页列表
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    PageResult<SysEmployee> page(SysEmployeeRequest sysEmployeeRequest);

    /**
     * 查询所有企业员工
     *
     * @param sysEmployeeRequest 企业员工查询条件
     * @return 企业员工详情列表
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    List<SysEmployee> list(SysEmployeeRequest sysEmployeeRequest);

}