package cn.stylefeng.roses.kernel.menu.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.menu.modular.entity.SysMenuButton;
import cn.stylefeng.roses.kernel.menu.modular.mapper.SysMenuButtonMapper;
import cn.stylefeng.roses.kernel.menu.modular.service.SysMenuButtonService;
import cn.stylefeng.roses.kernel.menu.modular.service.SysMenuService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.exception.enums.SysMenuButtonExceptionEnum;
import cn.stylefeng.roses.kernel.system.pojo.menu.SysMenuButtonRequest;
import cn.stylefeng.roses.kernel.system.pojo.menu.SysMenuRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    @Transactional(rollbackFor = Exception.class)
    public void add(SysMenuButtonRequest sysMenuButtonRequest) {

        // 判断菜单是否存在
        findMenuExist(sysMenuButtonRequest.getMenuId());

        SysMenuButton sysMenuButton = new SysMenuButton();
        BeanUtil.copyProperties(sysMenuButtonRequest, sysMenuButton);

        // 设置未删除
        sysMenuButton.setDelFlag(YesOrNotEnum.N.getCode());

        this.save(sysMenuButton);

    }

    @Override
    public SysMenuButton detail(SysMenuButtonRequest sysMenuButtonRequest) {
        return this.queryButton(sysMenuButtonRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(SysMenuButtonRequest sysMenuButtonRequest) {

        // 查询按钮所属菜单是否存在
        findMenuExist(sysMenuButtonRequest.getMenuId());

        SysMenuButton button = this.queryButton(sysMenuButtonRequest);
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
    @Transactional(rollbackFor = Exception.class)
    public void delete(SysMenuButtonRequest sysMenuButtonRequest) {

        // 查询按钮
        SysMenuButton button = this.queryButton(sysMenuButtonRequest);

        // 设置为删除状态
        button.setDelFlag(YesOrNotEnum.Y.getCode());

        this.updateById(button);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(SysMenuButtonRequest sysMenuButtonRequest) {
        Set<Long> buttonIds = sysMenuButtonRequest.getButtonIds();
        if (ArrayUtil.isNotEmpty(buttonIds)) {
            // 查询条件
            LambdaQueryWrapper<SysMenuButton> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(SysMenuButton::getButtonId, buttonIds);
            lambdaQueryWrapper.eq(SysMenuButton::getDelFlag, YesOrNotEnum.N.getCode());

            // 设置为删除状态
            SysMenuButton entity = new SysMenuButton();
            entity.setDelFlag(YesOrNotEnum.Y.getCode());

            this.update(entity, lambdaQueryWrapper);
        }
    }

    @Override
    public PageResult<SysMenuButton> pageList(SysMenuButtonRequest sysMenuButtonRequest) {
        String buttonName = sysMenuButtonRequest.getButtonName();

        // 构造查询条件 根据菜单id查询菜单按钮列表
        LambdaQueryWrapper<SysMenuButton> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenuButton::getMenuId, sysMenuButtonRequest.getMenuId());
        if (StrUtil.isNotBlank(buttonName)) {
            wrapper.like(SysMenuButton::getButtonName, buttonName);
        }
        wrapper.eq(SysMenuButton::getDelFlag, YesOrNotEnum.N.getCode());
        Page<SysMenuButton> page = this.page(PageFactory.defaultPage(), wrapper);

        return PageResultFactory.createPageResult(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenuButtonByMenuId(Long menuId) {
        if (ObjectUtil.isNotEmpty(menuId)) {
            // 查询菜单是否存在
            findMenuExist(menuId);

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
     * 查询菜单是否存在，为空会抛出异常
     *
     * @param menuId 菜单id
     * @author luojie
     * @date 2021/1/9 12:10
     */
    private void findMenuExist(Long menuId) {
        SysMenuRequest sysMenuRequest = new SysMenuRequest();
        sysMenuRequest.setMenuId(menuId);
        sysMenuService.detail(sysMenuRequest);
    }

    /**
     * 获取按钮
     *
     * @author fengshuonan
     * @date 2020/3/27 9:13
     */
    private SysMenuButton queryButton(SysMenuButtonRequest sysMenuButtonRequest) {
        SysMenuButton button = this.getById(sysMenuButtonRequest.getButtonId());
        if (ObjectUtil.isNull(button) || YesOrNotEnum.Y.getCode().equals(button.getDelFlag())) {
            throw new SystemModularException(SysMenuButtonExceptionEnum.MENU_BUTTON_NOT_EXIST);
        }
        return button;
    }

}
