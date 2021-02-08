package cn.stylefeng.roses.kernel.system.modular.user.service;

import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserDataScope;
import cn.stylefeng.roses.kernel.system.pojo.UserDataScopeRequest;
import cn.stylefeng.roses.kernel.system.pojo.user.request.SysUserRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统用户数据范围service接口
 *
 * @author luojie
 * @date 2020/11/6 10:28
 */
public interface SysUserDataScopeService extends IService<SysUserDataScope> {

    /**
     * 新增
     *
     * @param userDataScopeRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void add(UserDataScopeRequest userDataScopeRequest);

    /**
     * 删除
     *
     * @param userDataScopeRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void del(UserDataScopeRequest userDataScopeRequest);

    /**
     * 根据 用户id 删除
     *
     * @param userId 用户id
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void delByUserId(Long userId);

    /**
     * 修改
     *
     * @param userDataScopeRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void edit(UserDataScopeRequest userDataScopeRequest);

    /**
     * 查询-详情-根据主键id
     *
     * @param userDataScopeRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    SysUserDataScope detail(UserDataScopeRequest userDataScopeRequest);

    /**
     * 获取用户的数据范围id集合
     *
     * @param uerId 用户id
     * @return 数据范围id集合
     * @author luojie
     * @date 2020/11/6 15:01
     */
    List<Long> findOrgIdsByUserId(Long uerId);

    /**
     * 查询-列表-按实体对象
     *
     * @param userDataScopeRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    List<SysUserDataScope> findList(UserDataScopeRequest userDataScopeRequest);

    /**
     * 分配数据范围
     *
     * @author chenjinlong
     * @date 2021/2/3 15:49
     */
    void assignData(SysUserRequest sysUserRequest);

}
