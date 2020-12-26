package cn.stylefeng.roses.kernel.system.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.kernel.system.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserOrg;
import cn.stylefeng.roses.kernel.system.modular.user.mapper.SysUserOrgMapper;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserOrgService;
import cn.stylefeng.roses.kernel.system.pojo.user.SysUserOrgResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.stylefeng.roses.kernel.system.exception.enums.SysUserOrgExceptionEnum.EMPLOYEE_MANY_MAIN_NOT_FOUND;

/**
 * 用户组织机构关联信息
 *
 * @author fengshuonan
 * @date 2020/12/19 22:17
 */
@Service
public class SysUserOrgServiceServiceImpl extends ServiceImpl<SysUserOrgMapper, SysUserOrg> implements SysUserOrgService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserOrg(Long userId, Long orgId, Long positionId) {

        // 删除旧的绑定信息
        LambdaUpdateWrapper<SysUserOrg> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUserOrg::getUserId, userId);
        this.remove(updateWrapper);

        // 新增新的绑定信息
        SysUserOrg sysUserOrg = new SysUserOrg();
        sysUserOrg.setUserId(userId);
        sysUserOrg.setOrgId(orgId);
        sysUserOrg.setPositionId(positionId);
        this.save(sysUserOrg);
    }

    @Override
    public SysUserOrgResponse getUserOrgInfo(Long userId) {

        LambdaQueryWrapper<SysUserOrg> sysUserOrgLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserOrgLambdaQueryWrapper.eq(SysUserOrg::getUserId, userId);

        List<SysUserOrg> list = this.list(sysUserOrgLambdaQueryWrapper);
        if (list.size() != 1) {
            throw new SystemModularException(EMPLOYEE_MANY_MAIN_NOT_FOUND);
        } else {
            SysUserOrg sysUserOrg = list.get(0);
            SysUserOrgResponse sysUserOrgResponse = new SysUserOrgResponse();
            BeanUtil.copyProperties(sysUserOrg, sysUserOrgResponse);
            return sysUserOrgResponse;
        }
    }

    @Override
    public void deleteUserOrg(Long userId) {
        LambdaUpdateWrapper<SysUserOrg> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUserOrg::getUserId, userId);
        this.remove(updateWrapper);
    }

    @Override
    public Boolean getUserOrgFlag(Long orgId, Long positionId) {

        // TODO 测试
        LambdaQueryWrapper<SysUserOrg> sysUserOrgLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserOrgLambdaQueryWrapper.eq(true, SysUserOrg::getOrgId, orgId);
        sysUserOrgLambdaQueryWrapper.eq(true, SysUserOrg::getPositionId, positionId);

        List<SysUserOrg> list = this.list(sysUserOrgLambdaQueryWrapper);
        return list.size() > 0;
    }

}