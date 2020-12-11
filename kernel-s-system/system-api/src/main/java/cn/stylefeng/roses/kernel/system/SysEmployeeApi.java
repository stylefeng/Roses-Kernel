package cn.stylefeng.roses.kernel.system;

import cn.stylefeng.roses.kernel.system.pojo.organization.SysEmployeeRequest;
import cn.stylefeng.roses.kernel.system.pojo.organization.SysEmployeeResponse;

import java.util.List;

/**
 * 企业员工的管理API
 * <p>
 * 企业员工管理就是用户和组织架构的管理
 *
 * @author luojie
 * @date 2020/11/5 16:23
 */
public interface SysEmployeeApi {

    /**
     * 添加或修改员工信息
     * <p>
     * 业务步骤都是，先删除该用户的所有员工信息，再添加参数上的员工信息
     *
     * @param userId             用户id
     * @param sysEmployeeRequest 企业员工信息
     * @author fengshuonan
     * @date 2020/11/04 11:05
     */
    void updateEmployee(Long userId, List<SysEmployeeRequest> sysEmployeeRequest);

    /**
     * 根据用户id删除员工信息
     *
     * @param userId 用户id
     * @author luojie
     * @date 2020/11/14 14:21
     */
    void deleteEmployeeByUserId(Long userId);

    /**
     * 获取用户主任职信息
     *
     * @param userId 用户id
     * @return 用户的主任职信息
     * @author fengshuonan
     * @date 2020/11/19 21:42
     */
    SysEmployeeResponse getUserMainEmployee(Long userId);

    /**
     * 获取用户所有的任职信息，包含主任职信息和附属任职信息
     *
     * @param userId 用户id
     * @return 用户所有的任职信息
     * @author fengshuonan
     * @date 2020/11/19 21:43
     */
    List<SysEmployeeResponse> getUserAllEmployee(Long userId);

}
