package cn.stylefeng.roses.kernel.system.modular.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.system.api.RoleDataScopeServiceApi;
import cn.stylefeng.roses.kernel.system.modular.role.entity.SysRoleDataScope;
import cn.stylefeng.roses.kernel.system.modular.role.mapper.SysRoleDataScopeMapper;
import cn.stylefeng.roses.kernel.system.modular.role.service.SysRoleDataScopeService;
import cn.stylefeng.roses.kernel.system.api.pojo.role.request.SysRoleDataScopeRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.role.request.SysRoleRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统角色数据范围service接口实现类
 *
 * @author majianguo
 * @date 2020/11/5 下午4:32
 */
@Service
public class SysRoleDataScopeServiceImpl extends ServiceImpl<SysRoleDataScopeMapper, SysRoleDataScope> implements SysRoleDataScopeService, RoleDataScopeServiceApi {

    @Override
    public void grantDataScope(SysRoleRequest sysRoleParam) {
        Long roleId = sysRoleParam.getRoleId();

        // 删除所拥该角色已绑定的范围
        this.delByRoleId(roleId);

        // 批量新增-授权该角色数据范围
        if (ObjectUtil.isNotEmpty(sysRoleParam.getGrantOrgIdList())) {
            List<SysRoleDataScope> sysRoleDataScopeList = CollUtil.newArrayList();
            sysRoleParam.getGrantOrgIdList().forEach(orgId -> {
                SysRoleDataScope sysRoleDataScope = new SysRoleDataScope();
                sysRoleDataScope.setRoleId(roleId);
                sysRoleDataScope.setOrganizationId(orgId);
                sysRoleDataScopeList.add(sysRoleDataScope);
            });
            this.saveBatch(sysRoleDataScopeList);
        }
    }

    @Override
    public List<Long> getRoleDataScopeIdList(List<Long> roleIdList) {
        LambdaQueryWrapper<SysRoleDataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleDataScope::getRoleId, roleIdList);
        return this.list(queryWrapper).stream().map(SysRoleDataScope::getOrganizationId).collect(Collectors.toList());
    }


    @Override
    public void add(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
        SysRoleDataScope sysRoleDataScope = new SysRoleDataScope();
        BeanUtil.copyProperties(sysRoleDataScopeRequest, sysRoleDataScope);
        this.save(sysRoleDataScope);
    }

    @Override
    public void del(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
        SysRoleDataScope sysRoleDataScope = this.querySysRoleDataScopeById(sysRoleDataScopeRequest);
        this.removeById(sysRoleDataScope.getRoleDataScopeId());
    }

    @Override
    public void delByRoleId(Long roleId) {
        SysRoleDataScopeRequest sysRoleDataScopeRequest = new SysRoleDataScopeRequest();
        sysRoleDataScopeRequest.setRoleId(roleId);
        this.remove(this.createQueryWrapper(sysRoleDataScopeRequest));
    }

    @Override
    public void delByOrgIds(Set<Long> orgIds) {
        LambdaQueryWrapper<SysRoleDataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleDataScope::getOrganizationId, orgIds);
        this.remove(queryWrapper);
    }

    @Override
    public void edit(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
        SysRoleDataScope sysRoleDataScope = this.querySysRoleDataScopeById(sysRoleDataScopeRequest);
        BeanUtil.copyProperties(sysRoleDataScopeRequest, sysRoleDataScope);
        this.updateById(sysRoleDataScope);
    }

    @Override
    public SysRoleDataScope detail(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
        return this.getOne(this.createQueryWrapper(sysRoleDataScopeRequest), false);
    }

    @Override
    public List<SysRoleDataScope> findList(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
        return this.list(this.createQueryWrapper(sysRoleDataScopeRequest));
    }


    /**
     * 根据主键查询
     *
     * @param sysRoleDataScopeRequest dto实体
     * @author chenjinlong
     * @date 2021/2/3 15:02
     */
    private SysRoleDataScope querySysRoleDataScopeById(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
        return this.getById(sysRoleDataScopeRequest.getRoleDataScopeId());
    }

    /**
     * 构建 QueryWrapper
     *
     * @param sysRoleDataScopeRequest dto实体
     * @author chenjinlong
     * @date 2021/2/3 14:54
     */
    private LambdaQueryWrapper<SysRoleDataScope> createQueryWrapper(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
        LambdaQueryWrapper<SysRoleDataScope> queryWrapper = new LambdaQueryWrapper<>();
        // SQL拼接
        queryWrapper.eq(ObjectUtil.isNotNull(sysRoleDataScopeRequest.getRoleDataScopeId()), SysRoleDataScope::getRoleDataScopeId, sysRoleDataScopeRequest.getRoleDataScopeId());
        queryWrapper.eq(ObjectUtil.isNotNull(sysRoleDataScopeRequest.getRoleId()), SysRoleDataScope::getRoleId, sysRoleDataScopeRequest.getRoleId());
        queryWrapper.eq(ObjectUtil.isNotNull(sysRoleDataScopeRequest.getOrganizationId()), SysRoleDataScope::getOrganizationId, sysRoleDataScopeRequest.getOrganizationId());

        return queryWrapper;
    }
}
