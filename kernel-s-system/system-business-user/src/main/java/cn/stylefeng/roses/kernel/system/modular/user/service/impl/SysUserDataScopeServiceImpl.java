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
package cn.stylefeng.roses.kernel.system.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.user.SysUserDataScopeExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.exception.enums.user.SysUserOrgExceptionEnum;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUserDataScope;
import cn.stylefeng.roses.kernel.system.modular.user.mapper.SysUserDataScopeMapper;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserDataScopeService;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.SysUserRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.UserDataScopeRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
        if (ObjectUtil.isNotEmpty(userId)) {
            LambdaQueryWrapper<SysUserDataScope> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUserDataScope::getUserId, userId);
            this.remove(queryWrapper);
        }
    }

    @Override
    public void edit(UserDataScopeRequest userDataScopeRequest) {
        SysUserDataScope sysUserDataScope = this.querySysUserRoleById(userDataScopeRequest);
        BeanUtil.copyProperties(sysUserDataScope, userDataScopeRequest);
        this.updateById(sysUserDataScope);
    }

    @Override
    public SysUserDataScope detail(UserDataScopeRequest userDataScopeRequest) {
        return this.getOne(this.createQueryWrapper(userDataScopeRequest), false);
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
        SysUserDataScope sysUserDataScope = this.getById(userDataScopeRequest.getUserDataScopeId());
        if (ObjectUtil.isEmpty(sysUserDataScope)) {
            throw new SystemModularException(SysUserDataScopeExceptionEnum.USER_DATA_SCOPE_NOT_EXIST, sysUserDataScope.getUserDataScopeId());
        }
        return sysUserDataScope;
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
