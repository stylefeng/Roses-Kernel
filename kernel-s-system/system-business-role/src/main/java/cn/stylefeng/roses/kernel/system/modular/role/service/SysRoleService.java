package cn.stylefeng.roses.kernel.system.modular.role.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.system.RoleServiceApi;
import cn.stylefeng.roses.kernel.system.modular.role.entity.SysRole;
import cn.stylefeng.roses.kernel.system.pojo.role.dto.SysRoleDTO;
import cn.stylefeng.roses.kernel.system.pojo.role.request.SysRoleRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统角色service接口
 *
 * @author majianguo
 * @date 2020/11/5 上午11:12
 */
public interface SysRoleService extends IService<SysRole>, RoleServiceApi {

    /**
     * 添加系统角色
     *
     * @param sysRoleRequest 添加参数
     * @author majianguo
     * @date 2020/11/5 上午11:13
     */
    void add(SysRoleRequest sysRoleRequest);

    /**
     * 删除系统角色
     *
     * @param sysRoleRequest 删除参数
     * @author majianguo
     * @date 2020/11/5 上午11:14
     */
    void del(SysRoleRequest sysRoleRequest);

    /**
     * 编辑系统角色
     *
     * @param sysRoleRequest 编辑参数
     * @author majianguo
     * @date 2020/11/5 上午11:14
     */
    void edit(SysRoleRequest sysRoleRequest);

    /**
     * 查看系统角色
     *
     * @param sysRoleRequest 查看参数
     * @return 系统角色
     * @author majianguo
     * @date 2020/11/5 上午11:14
     */
    SysRoleDTO detail(SysRoleRequest sysRoleRequest);

    /**
     * 查询系统角色
     *
     * @param sysRoleRequest 查询参数
     * @return 查询分页结果
     * @author majianguo
     * @date 2020/11/5 上午11:13
     */
    PageResult<SysRole> findPage(SysRoleRequest sysRoleRequest);

    /**
     * 根据角色名模糊搜索系统角色列表
     *
     * @param sysRoleRequest 查询参数
     * @return 增强版hashMap，格式：[{"id":456, "name":"总经理(zjl)"}]
     * @author majianguo
     * @date 2020/11/5 上午11:13
     */
    List<SimpleDict> findList(SysRoleRequest sysRoleRequest);

    /**
     * 授权菜单和按钮
     *
     * @author majianguo
     * @date 2021/1/9 18:13
     */
    void grantMenuAndButton(SysRoleRequest sysRoleMenuButtonRequest);

    /**
     * 授权数据范围（组织机构）
     *
     * @param sysRoleRequest 授权参数
     * @author majianguo
     * @date 2020/11/5 上午11:14
     */
    void grantDataScope(SysRoleRequest sysRoleRequest);

    /**
     * 系统角色下拉（用于授权角色时选择）
     *
     * @return 增强版hashMap，格式：[{"id":456, "code":"zjl", "name":"总经理"}]
     * @author majianguo
     * @date 2020/11/5 上午11:13
     */
    List<SimpleDict> dropDown();

    /***
     * 查询角色拥有数据
     *
     * @param sysRoleRequest 查询参数
     * @return 数据范围id集合
     * @author majianguo
     * @date 2020/11/5 上午11:15
     */
    List<Long> getRoleDataScope(SysRoleRequest sysRoleRequest);

}
