package cn.stylefeng.roses.kernel.menu.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.menu.modular.entity.SysMenu;
import cn.stylefeng.roses.kernel.menu.modular.entity.SysMenuButton;
import cn.stylefeng.roses.kernel.menu.modular.mapper.SysMenuButtonMapper;
import cn.stylefeng.roses.kernel.menu.modular.service.SysMenuButtonService;
import cn.stylefeng.roses.kernel.menu.modular.service.SysMenuService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.system.exception.enums.SysMenuButtonExceptionEnum;
import cn.stylefeng.roses.kernel.system.exception.enums.SysMenuExceptionEnum;
import cn.stylefeng.roses.kernel.system.pojo.menu.SysMenuButtonRequest;
import cn.stylefeng.roses.kernel.system.pojo.menu.SysMenuButtonResponse;
import cn.stylefeng.roses.kernel.system.pojo.menu.SysMenuRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统菜单按钮service接口实现类
 *
 * @author q18idc.com QQ993143799
 * @date 2021/1/9 11:05
 */
@Service
public class SysMenuButtonServiceImpl extends ServiceImpl<SysMenuButtonMapper, SysMenuButton> implements SysMenuButtonService {

    @Resource
    private SysMenuService sysMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysMenuButtonRequest sysMenuButtonRequest) {

        Long menuId = sysMenuButtonRequest.getMenuId();

        // 根据传过来的菜单id查询是否存在
        findMenuExist(menuId);

        // 查询菜单按钮code是否存在
        findMenuButtonCodeExist(sysMenuButtonRequest);

        SysMenuButton sysMenuButton = new SysMenuButton();
        BeanUtil.copyProperties(sysMenuButtonRequest, sysMenuButton);
        // 设置未删除
        sysMenuButton.setDelFlag(YesOrNotEnum.N.getCode());
        this.save(sysMenuButton);

    }

    @Override
    public SysMenuButtonResponse detail(SysMenuButtonRequest sysMenuButtonRequest) {
        // 构建查询条件 查询未删除的按钮
        LambdaQueryWrapper<SysMenuButton> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysMenuButton::getButtonId, sysMenuButtonRequest.getButtonId());
        lqw.eq(SysMenuButton::getDelFlag, YesOrNotEnum.N.getCode());
        lqw.last("LIMIT 1");

        SysMenuButton sysMenuButton = this.getOne(lqw);

        if (ObjectUtil.isEmpty(sysMenuButton)) {
            throw new ServiceException(SysMenuButtonExceptionEnum.MENU_BUTTON_NOT_EXIST);
        }

        SysMenuButtonResponse response = new SysMenuButtonResponse();
        BeanUtil.copyProperties(sysMenuButton, response);
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(SysMenuButtonRequest sysMenuButtonRequest) {
        Long menuId = sysMenuButtonRequest.getMenuId();

        // 查询按钮是否存在
        this.detail(sysMenuButtonRequest);

        // 根据传过来的菜单id查询是否存在
        findMenuExist(menuId);

        // 查询菜单按钮code是否存在
        findMenuButtonCodeExist(sysMenuButtonRequest);

        SysMenuButton sysMenuButton = new SysMenuButton();
        BeanUtil.copyProperties(sysMenuButtonRequest, sysMenuButton);
        // 不更新删除状态
        sysMenuButton.setDelFlag(null);
        // 不更新所属菜单id
        sysMenuButton.setMenuId(null);

        this.updateById(sysMenuButton);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(SysMenuButtonRequest sysMenuButtonRequest) {
        // 查询按钮是否存在
        this.detail(sysMenuButtonRequest);

        // 查询条件
        LambdaQueryWrapper<SysMenuButton> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysMenuButton::getButtonId, sysMenuButtonRequest.getButtonId());
        lqw.eq(SysMenuButton::getDelFlag, YesOrNotEnum.N.getCode());

        // 设置为删除状态
        SysMenuButton entity = new SysMenuButton();
        entity.setDelFlag(YesOrNotEnum.Y.getCode());

        this.update(entity, lqw);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(SysMenuButtonRequest sysMenuButtonRequest) {
        Set<Long> buttonIds = sysMenuButtonRequest.getButtonIds();
        if (ArrayUtil.isNotEmpty(buttonIds)) {
            // 查询条件
            LambdaQueryWrapper<SysMenuButton> lqw = new LambdaQueryWrapper<>();
            lqw.in(SysMenuButton::getButtonId, buttonIds);
            lqw.eq(SysMenuButton::getDelFlag, YesOrNotEnum.N.getCode());

            // 设置为删除状态
            SysMenuButton entity = new SysMenuButton();
            entity.setDelFlag(YesOrNotEnum.Y.getCode());

            this.update(entity, lqw);
        }

    }

    /**
     * 分页获取菜单按钮列表
     *
     * @param sysMenuButtonRequest 菜单id
     * @return 菜单按钮列表
     * @author q18idc.com QQ993143799
     * @date 2021/1/9 12:53
     */
    @Override
    public PageResult<SysMenuButtonResponse> pageList(SysMenuButtonRequest sysMenuButtonRequest) {
        String buttonName = sysMenuButtonRequest.getButtonName();

        // 构造查询条件 根据菜单id查询菜单按钮列表
        LambdaQueryWrapper<SysMenuButton> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenuButton::getMenuId, sysMenuButtonRequest.getMenuId());
        if (StrUtil.isNotBlank(buttonName)) {
            wrapper.like(SysMenuButton::getButtonName, buttonName);
        }
        wrapper.eq(SysMenuButton::getDelFlag, YesOrNotEnum.N.getCode());
        Page<SysMenuButton> page = this.page(PageFactory.defaultPage(), wrapper);

        // 实体转换
        List<SysMenuButton> sysButtonList = page.getRecords();
        List<SysMenuButtonResponse> sysButtonResponseList = sysButtonList.stream().map(b -> {
            SysMenuButtonResponse sysButtonResponse = new SysMenuButtonResponse();
            BeanUtil.copyProperties(b, sysButtonResponse);
            return sysButtonResponse;
        }).collect(Collectors.toList());

        PageResult<SysMenuButtonResponse> pageResult = new PageResult<>();
        BeanUtil.copyProperties(PageResultFactory.createPageResult(page), pageResult);
        pageResult.setRows(sysButtonResponseList);
        return pageResult;
    }

    /**
     * 根据菜单id删除该菜单下的所有按钮
     *
     * @param menuId 菜单id
     * @author q18idc.com QQ993143799
     * @date 2021/1/9 14:45
     */
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
     * 查询菜单是否存在
     *
     * @param menuId 菜单id
     * @author q18idc.com QQ993143799
     * @date 2021/1/9 12:10
     */
    public void findMenuExist(Long menuId) {
        // 根据传过来的菜单id查询是否存在
        SysMenuRequest sysMenuRequest = new SysMenuRequest();
        sysMenuRequest.setMenuId(menuId);
        SysMenu sysMenu = sysMenuService.detail(sysMenuRequest);
        if (ObjectUtil.isEmpty(sysMenu)) {
            throw new ServiceException(SysMenuExceptionEnum.MENU_NOT_EXIST);
        }
    }

    /**
     * 查询菜单按钮code是否存在
     *
     * @param sysMenuButtonRequest 菜单按钮请求参数
     * @author q18idc.com QQ993143799
     * @date 2021/1/9 11:38
     */
    public void findMenuButtonCodeExist(SysMenuButtonRequest sysMenuButtonRequest) {
        // 查询菜单按钮code是否存在
        LambdaQueryWrapper<SysMenuButton> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(SysMenuButton::getButtonId);
        wrapper.eq(SysMenuButton::getButtonCode, sysMenuButtonRequest.getButtonCode());
        wrapper.eq(SysMenuButton::getDelFlag, YesOrNotEnum.N.getCode());
        if (ObjectUtil.isNotEmpty(sysMenuButtonRequest.getButtonId())) {
            wrapper.ne(SysMenuButton::getButtonId, sysMenuButtonRequest.getButtonId());
        }
        wrapper.last("LIMIT 1");
        SysMenuButton button = getOne(wrapper);
        if (ObjectUtil.isNotEmpty(button)) {
            throw new ServiceException(SysMenuButtonExceptionEnum.BUTTON_CODE_REPEAT);
        }
    }
}
