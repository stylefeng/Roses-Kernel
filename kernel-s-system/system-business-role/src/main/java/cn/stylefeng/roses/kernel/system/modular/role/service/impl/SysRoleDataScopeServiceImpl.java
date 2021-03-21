/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
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
