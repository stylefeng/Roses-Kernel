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
package cn.stylefeng.roses.kernel.system.modular.menu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.menu.SysMenuButtonExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.menu.SysMenuButtonRequest;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenu;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenuButton;
import cn.stylefeng.roses.kernel.system.modular.menu.factory.MenuButtonFactory;
import cn.stylefeng.roses.kernel.system.modular.menu.mapper.SysMenuButtonMapper;
import cn.stylefeng.roses.kernel.system.modular.menu.service.SysMenuButtonService;
import cn.stylefeng.roses.kernel.system.modular.menu.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 系统菜单按钮service接口实现类
 *
 * @author luojie
 * @date 2021/1/9 11:05
 */
@Service
public class SysMenuButtonServiceImpl extends ServiceImpl<SysMenuButtonMapper, SysMenuButton> implements SysMenuButtonService {

    @Resource
    private SysMenuService sysMenuService;

    @Override
    public void add(SysMenuButtonRequest sysMenuButtonRequest) {
        SysMenuButton sysMenuButton = new SysMenuButton();
        BeanUtil.copyProperties(sysMenuButtonRequest, sysMenuButton);
        this.save(sysMenuButton);
    }

    @Override
    public void addDefaultButtons(SysMenuButtonRequest sysMenuButtonRequest) {
        Long menuId = sysMenuButtonRequest.getMenuId();

        // 获取菜单的编码
        SysMenu sysMenu = this.sysMenuService.getById(menuId);

        // 构建菜单的系统默认按钮
        List<SysMenuButton> sysMenuButtonList = MenuButtonFactory.createSystemDefaultButton(menuId, sysMenu.getMenuName(), sysMenu.getMenuCode());
        this.saveBatch(sysMenuButtonList);
    }

    @Override
    public void del(SysMenuButtonRequest sysMenuButtonRequest) {
        SysMenuButton button = this.queryButtonById(sysMenuButtonRequest);

        // 设置为删除状态
        button.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(button);
    }

    @Override
    public void delBatch(SysMenuButtonRequest sysMenuButtonRequest) {
        Set<Long> buttonIds = sysMenuButtonRequest.getButtonIds();
        if (ArrayUtil.isNotEmpty(buttonIds)) {
            // 查询条件
            LambdaQueryWrapper<SysMenuButton> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysMenuButton::getButtonId, buttonIds);
            queryWrapper.eq(SysMenuButton::getDelFlag, YesOrNotEnum.N.getCode());

            // 设置为删除状态
            SysMenuButton entity = new SysMenuButton();
            entity.setDelFlag(YesOrNotEnum.Y.getCode());

            this.update(entity, queryWrapper);
        }
    }

    @Override
    public void edit(SysMenuButtonRequest sysMenuButtonRequest) {
        SysMenuButton button = this.queryButtonById(sysMenuButtonRequest);
        BeanUtil.copyProperties(sysMenuButtonRequest, button);

        // 不更新删除状态
        button.setDelFlag(null);

        // 不更新所属菜单id
        button.setMenuId(null);

        // 不更新菜单code
        button.setButtonCode(null);

        this.updateById(button);
    }

    @Override
    public SysMenuButton detail(SysMenuButtonRequest sysMenuButtonRequest) {
        LambdaQueryWrapper<SysMenuButton> queryWrapper = this.createWrapper(sysMenuButtonRequest);
        return this.getOne(queryWrapper, false);
    }

    @Override
    public PageResult<SysMenuButton> findPage(SysMenuButtonRequest sysMenuButtonRequest) {
        LambdaQueryWrapper<SysMenuButton> wrapper = this.createWrapper(sysMenuButtonRequest);
        Page<SysMenuButton> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }


    @Override
    public void deleteMenuButtonByMenuId(Long menuId) {
        if (ObjectUtil.isNotEmpty(menuId)) {
            // 构建查询条件
            LambdaQueryWrapper<SysMenuButton> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysMenuButton::getMenuId, menuId);
            queryWrapper.eq(SysMenuButton::getDelFlag, YesOrNotEnum.N.getCode());

            // 设置假删除
            SysMenuButton sysMenuButton = new SysMenuButton();
            sysMenuButton.setDelFlag(YesOrNotEnum.Y.getCode());

            this.update(sysMenuButton, queryWrapper);
        }
    }

    /**
     * 根据主键id获取对象
     *
     * @author chenjinlong
     * @date 2021/1/26 13:28
     */
    private SysMenuButton queryButtonById(SysMenuButtonRequest sysMenuButtonRequest) {
        SysMenuButton button = this.getById(sysMenuButtonRequest.getButtonId());
        if (ObjectUtil.isNull(button)) {
            throw new SystemModularException(SysMenuButtonExceptionEnum.MENU_BUTTON_NOT_EXIST);
        }
        return button;
    }

    /**
     * 实体构建queryWrapper
     *
     * @author chenjinlong
     * @date 2021/1/24 22:03
     */
    private LambdaQueryWrapper<SysMenuButton> createWrapper(SysMenuButtonRequest sysMenuButtonRequest) {
        LambdaQueryWrapper<SysMenuButton> queryWrapper = new LambdaQueryWrapper<>();

        // 逻辑删除
        queryWrapper.eq(SysMenuButton::getDelFlag, YesOrNotEnum.N.getCode());

        if (ObjectUtil.isEmpty(sysMenuButtonRequest)) {
            return queryWrapper;
        }

        Long buttonId = sysMenuButtonRequest.getButtonId();
        Long menuId = sysMenuButtonRequest.getMenuId();
        String buttonName = sysMenuButtonRequest.getButtonName();
        String buttonCode = sysMenuButtonRequest.getButtonCode();

        // SQL条件拼接
        queryWrapper.eq(ObjectUtil.isNotNull(buttonId), SysMenuButton::getButtonId, buttonId);
        queryWrapper.eq(ObjectUtil.isNotNull(menuId), SysMenuButton::getMenuId, menuId);
        queryWrapper.like(ObjectUtil.isNotNull(buttonName), SysMenuButton::getButtonName, buttonName);
        queryWrapper.like(ObjectUtil.isNotNull(buttonCode), SysMenuButton::getButtonCode, buttonCode);

        return queryWrapper;
    }

}
