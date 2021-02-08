package cn.stylefeng.roses.kernel.system.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserDataScope;
import cn.stylefeng.roses.kernel.system.modular.user.mapper.SysUserDataScopeMapper;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserDataScopeService;
import cn.stylefeng.roses.kernel.system.pojo.user.request.SysUserRequest;
import cn.stylefeng.roses.kernel.system.pojo.user.request.UserDataScopeRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统用户数据范围service接口实现类
 *
 * @author luojie
 * @date 2020/11/6 10:28
 */
@Service
public class SysUserDataScopeServiceImpl extends ServiceImpl<SysUserDataScopeMapper, SysUserDataScope> implements SysUserDataScopeService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignData(SysUserRequest sysUserRequest) {

        // 获取用户id
        Long userId = sysUserRequest.getUserId();

        // 删除用户所有授权范围
        this.delByUserId(userId);

        List<Long> orgIds = sysUserRequest.getGrantOrgIdList();

        // 授权组织机构数据范围给用户
        List<SysUserDataScope> sysUserDataScopeList = CollUtil.newArrayList();

        // 批量添加数据范围
        orgIds.forEach(orgId -> {
            SysUserDataScope sysUserDataScope = new SysUserDataScope();
            sysUserDataScope.setUserId(userId);
            sysUserDataScope.setOrgId(orgId);
            sysUserDataScopeList.add(sysUserDataScope);

        });

        this.saveBatch(sysUserDataScopeList);
    }


    @Override
    public void add(UserDataScopeRequest userDataScopeRequest) {
        SysUserDataScope sysUserDataScope = new SysUserDataScope();
        BeanUtil.copyProperties(userDataScopeRequest, sysUserDataScope);
        this.save(sysUserDataScope);
    }

    @Override
    public void del(UserDataScopeRequest userDataScopeRequest) {
        SysUserDataScope sysUserDataScope = this.querySysUserRoleById(userDataScopeRequest);
        this.removeById(sysUserDataScope.getUserDataScopeId());
    }

    @Override
    public void delByUserId(Long userId) {
        UserDataScopeRequest userDataScopeRequest = new UserDataScopeRequest();
        userDataScopeRequest.setUserId(userId);
        LambdaQueryWrapper<SysUserDataScope> queryWrapper = this.createQueryWrapper(userDataScopeRequest);
        this.remove(queryWrapper);
    }

    @Override
    public void edit(UserDataScopeRequest userDataScopeRequest) {
        SysUserDataScope sysUserDataScope = this.querySysUserRoleById(userDataScopeRequest);
        BeanUtil.copyProperties(sysUserDataScope, userDataScopeRequest);
        this.updateById(sysUserDataScope);
    }

    @Override
    public SysUserDataScope detail(UserDataScopeRequest userDataScopeRequest) {
        LambdaQueryWrapper<SysUserDataScope> queryWrapper = this.createQueryWrapper(userDataScopeRequest);
        return this.getOne(queryWrapper, false);
    }

    @Override
    public List<Long> findOrgIdsByUserId(Long uerId) {
        UserDataScopeRequest userDataScopeRequest = new UserDataScopeRequest();
        userDataScopeRequest.setUserId(uerId);
        List<SysUserDataScope> sysUserDataScopeList = this.findList(userDataScopeRequest);
        return sysUserDataScopeList.stream().map(SysUserDataScope::getOrgId).collect(Collectors.toList());
    }

    @Override
    public List<SysUserDataScope> findList(UserDataScopeRequest userDataScopeRequest) {
        LambdaQueryWrapper<SysUserDataScope> queryWrapper = this.createQueryWrapper(userDataScopeRequest);
        return this.list(queryWrapper);
    }

    /**
     * 根据主键查询
     *
     * @param userDataScopeRequest dto实体
     * @author chenjinlong
     * @date 2021/2/3 15:02
     */
    private SysUserDataScope querySysUserRoleById(UserDataScopeRequest userDataScopeRequest) {
        return this.getById(userDataScopeRequest.getUserDataScopeId());
    }

    /**
     * 构建 QueryWrapper
     *
     * @param userDataScopeRequest dto实体
     * @author chenjinlong
     * @date 2021/2/3 14:54
     */
    private LambdaQueryWrapper<SysUserDataScope> createQueryWrapper(UserDataScopeRequest userDataScopeRequest) {
        LambdaQueryWrapper<SysUserDataScope> queryWrapper = new LambdaQueryWrapper<>();
        // SQL拼接
        queryWrapper.eq(ObjectUtil.isNotNull(userDataScopeRequest.getOrgId()), SysUserDataScope::getOrgId, userDataScopeRequest.getOrgId());
        queryWrapper.eq(ObjectUtil.isNotNull(userDataScopeRequest.getUserId()), SysUserDataScope::getUserId, userDataScopeRequest.getUserId());
        queryWrapper.eq(ObjectUtil.isNotNull(userDataScopeRequest.getUserDataScopeId()), SysUserDataScope::getUserDataScopeId, userDataScopeRequest.getUserDataScopeId());

        return queryWrapper;
    }

}
