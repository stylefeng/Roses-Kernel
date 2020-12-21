package cn.stylefeng.roses.kernel.system.modular.user.service.impl;

import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserDataScope;
import cn.stylefeng.roses.kernel.system.modular.user.mapper.SysUserDataScopeMapper;
import cn.stylefeng.roses.kernel.system.modular.user.pojo.request.SysUserRequest;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserDataScopeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void grantData(SysUserRequest sysUserRequest) {

        // 获取用户id
        Long userId = sysUserRequest.getUserId();

        // 删除该用户的数据范围集合，sys_user_data_scope表中
        LambdaQueryWrapper<SysUserDataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDataScope::getUserId, userId);
        this.remove(queryWrapper);

        List<Long> orgIdList = sysUserRequest.getGrantOrgIdList();

        // 授权组织机构数据范围给用户
        ArrayList<SysUserDataScope> sysUserDataScopes = new ArrayList<>();
        for (Long orgId : orgIdList) {
            SysUserDataScope sysUserDataScope = new SysUserDataScope();
            sysUserDataScope.setUserId(userId);
            sysUserDataScope.setOrgId(orgId);
            sysUserDataScopes.add(sysUserDataScope);
        }
        this.saveBatch(sysUserDataScopes);
    }

    @Override
    public List<Long> getUserDataScopeIdList(Long uerId) {
        LambdaQueryWrapper<SysUserDataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDataScope::getUserId, uerId);
        queryWrapper.select(SysUserDataScope::getOrgId);

        List<SysUserDataScope> list = this.list(queryWrapper);
        return list.stream().map(SysUserDataScope::getOrgId).collect(Collectors.toList());
    }

    @Override
    public void deleteUserDataScopeListByUserId(Long userId) {
        LambdaQueryWrapper<SysUserDataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDataScope::getUserId, userId);

        this.remove(queryWrapper);
    }
}
