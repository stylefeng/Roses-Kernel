package cn.stylefeng.roses.kernel.system.modular.user.service;

import cn.stylefeng.roses.kernel.system.UserOrgServiceApi;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserOrg;
import cn.stylefeng.roses.kernel.system.pojo.user.request.UserOrgRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户组织机构关联信息
 *
 * @author fengshuonan
 * @date 2020/12/19 22:17
 */
public interface SysUserOrgService extends IService<SysUserOrg>, UserOrgServiceApi {

    /**
     * 新增
     *
     * @param userOrgResponse 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void add(UserOrgRequest userOrgResponse);

    /**
     * 新增
     *
     * @param userId     用户id
     * @param orgId      机构id
     * @param positionId 部门id
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void add(Long userId, Long orgId, Long positionId);

    /**
     * 删除
     *
     * @param userOrgResponse 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void del(UserOrgRequest userOrgResponse);

    /**
     * 删除
     *
     * @param userId 用户id
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void delByUserId(Long userId);

    /**
     * 修改
     *
     * @param userOrgResponse 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void edit(UserOrgRequest userOrgResponse);

    /**
     * 修改
     *
     * @param userId     用户id
     * @param orgId      机构id
     * @param positionId 部门id
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void edit(Long userId, Long orgId, Long positionId);

    /**
     * 详情
     *
     * @param userOrgResponse 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    SysUserOrg detail(UserOrgRequest userOrgResponse);

    /**
     * 查询-列表
     *
     * @param userOrgResponse 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    List<SysUserOrg> findList(UserOrgRequest userOrgResponse);


}
