/*
Copyright [2020] [https://www.stylefeng.cn]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Guns源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns-separation
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns-separation
6.若您的项目无法满足以上几点，可申请商业授权，获取Guns商业授权许可，请在官网购买授权，地址为 https://www.stylefeng.cn
 */
package cn.stylefeng.roses.kernel.role.modular.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.stylefeng.roses.kernel.role.modular.entity.SysRoleDataScope;
import cn.stylefeng.roses.kernel.role.modular.mapper.SysRoleDataScopeMapper;
import cn.stylefeng.roses.kernel.role.modular.service.SysRoleDataScopeService;
import cn.stylefeng.roses.kernel.system.pojo.role.request.SysRoleRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 系统角色数据范围service接口实现类
 *
 * @author majianguo
 * @date 2020/11/5 下午4:32
 */
@Service
public class SysRoleDataScopeServiceImpl extends ServiceImpl<SysRoleDataScopeMapper, SysRoleDataScope> implements SysRoleDataScopeService {

    @Override
    public void grantDataScope(SysRoleRequest sysRoleParam) {
        Long roleId = sysRoleParam.getId();

        LambdaQueryWrapper<SysRoleDataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleDataScope::getRoleId, roleId);

        // 删除所拥该角色已绑定的范围
        this.remove(queryWrapper);

        // 授权该角色数据范围
        sysRoleParam.getGrantOrgIdList().forEach(orgId -> {
            SysRoleDataScope sysRoleDataScope = new SysRoleDataScope();
            sysRoleDataScope.setRoleId(roleId);
            sysRoleDataScope.setOrganizationId(orgId);
            this.save(sysRoleDataScope);
        });
    }

    @Override
    public List<Long> getRoleDataScopeIdList(List<Long> roleIdList) {
        List<Long> resultList = CollectionUtil.newArrayList();
        if (ObjectUtil.isNotEmpty(roleIdList)) {
            LambdaQueryWrapper<SysRoleDataScope> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysRoleDataScope::getRoleId, roleIdList);
            this.list(queryWrapper).forEach(sysRoleDataScope -> resultList.add(sysRoleDataScope.getOrganizationId()));
        }
        return resultList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleDataScopeListByOrgIdList(Set<Long> orgIdList) {
        LambdaQueryWrapper<SysRoleDataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleDataScope::getOrganizationId, orgIdList);
        this.remove(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleDataScopeListByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleDataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleDataScope::getRoleId, roleId);
        this.remove(queryWrapper);
    }
}
