package cn.stylefeng.roses.kernel.app.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.app.modular.entity.SysApp;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.stylefeng.roses.kernel.app.modular.mapper.SysAppMapper;
import cn.stylefeng.roses.kernel.app.modular.service.SysAppService;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.system.AppServiceApi;
import cn.stylefeng.roses.kernel.system.MenuServiceApi;
import cn.stylefeng.roses.kernel.system.exception.enums.AppExceptionEnum;
import cn.stylefeng.roses.kernel.system.pojo.app.request.SysAppRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统应用service接口实现类
 *
 * @author fengshuonan
 * @date 2020/3/13 16:15
 */
@Service
public class SysAppServiceImpl extends ServiceImpl<SysAppMapper, SysApp> implements SysAppService, AppServiceApi {

    @Resource
    private MenuServiceApi menuApi;

    @Override
    public void add(SysAppRequest sysAppRequest) {

        // 检测是否有已经激活的应用，激活了就不能再设置激活了
        checkParamHaveActive(sysAppRequest, false);

        SysApp sysApp = new SysApp();
        BeanUtil.copyProperties(sysAppRequest, sysApp);

        // 设为启用
        sysApp.setStatusFlag(StatusEnum.ENABLE.getCode());

        this.save(sysApp);
    }

    @Override
    public void edit(SysAppRequest sysAppRequest) {

        // 检测是否有已经激活的应用，激活了就不能再设置激活了
        checkParamHaveActive(sysAppRequest, true);

        SysApp sysApp = this.querySysApp(sysAppRequest);
        BeanUtil.copyProperties(sysAppRequest, sysApp);

        //不能修改状态，用修改状态接口修改状态
        sysApp.setStatusFlag(null);

        this.updateById(sysApp);
    }

    @Override
    public void delete(SysAppRequest sysAppRequest) {
        SysApp sysApp = this.querySysApp(sysAppRequest);
        String code = sysApp.getCode();

        // 该应用下有菜单，则不能删除
        boolean hasMenu = menuApi.hasMenu(code);
        if (hasMenu) {
            throw new ServiceException(AppExceptionEnum.APP_CANNOT_DELETE);
        }

        // 逻辑删除
        sysApp.setDelFlag(YesOrNotEnum.Y.getCode());

        this.updateById(sysApp);
    }

    @Override
    public SysApp detail(SysAppRequest sysAppRequest) {
        return this.querySysApp(sysAppRequest);
    }

    @Override
    public PageResult<SysApp> page(SysAppRequest sysAppRequest) {
        LambdaQueryWrapper<SysApp> wrapper = createWrapper(sysAppRequest);
        Page<SysApp> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysApp> list(SysAppRequest sysAppRequest) {
        LambdaQueryWrapper<SysApp> wrapper = createWrapper(sysAppRequest);
        return this.list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setAsDefault(SysAppRequest sysAppRequest) {
        SysApp currentApp = this.querySysApp(sysAppRequest);

        // 所有已激活的改为未激活
        LambdaUpdateWrapper<SysApp> sysAppLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        sysAppLambdaUpdateWrapper.set(SysApp::getActiveFlag, YesOrNotEnum.N.getCode());
        sysAppLambdaUpdateWrapper.eq(SysApp::getActiveFlag, YesOrNotEnum.Y.getCode());
        this.update(sysAppLambdaUpdateWrapper);

        // 当前的设置为已激活
        currentApp.setActiveFlag(YesOrNotEnum.Y.getCode());
        this.updateById(currentApp);
    }

    @Override
    public Set<SimpleDict> getAppsByAppCodes(Set<String> appCodes) {
        HashSet<SimpleDict> simpleDicts = new HashSet<>();

        LambdaQueryWrapper<SysApp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysApp::getCode, appCodes);
        queryWrapper.select(SysApp::getCode, SysApp::getId, SysApp::getName);

        List<SysApp> list = this.list(queryWrapper);
        for (SysApp sysApp : list) {
            SimpleDict simpleDict = new SimpleDict();
            simpleDict.setId(sysApp.getId());
            simpleDict.setCode(sysApp.getCode());
            simpleDict.setName(sysApp.getName());
            simpleDicts.add(simpleDict);
        }

        return simpleDicts;
    }

    /**
     * 获取系统应用
     *
     * @author fengshuonan
     * @date 2020/3/26 9:56
     */
    private SysApp querySysApp(SysAppRequest sysAppRequest) {
        SysApp sysApp = this.getById(sysAppRequest.getId());
        if (ObjectUtil.isNull(sysApp)) {
            throw new ServiceException(AppExceptionEnum.APP_NOT_EXIST);
        }
        return sysApp;
    }

    /**
     * 检测是否有已经激活的应用，激活了就不能再设置激活了
     *
     * @author fengshuonan
     * @date 2020/11/24 21:29
     */
    private void checkParamHaveActive(SysAppRequest sysAppRequest, boolean excludeSelf) {

        // 查询激活状态有无已经有Y的
        LambdaQueryWrapper<SysApp> appQueryWrapperByActive = new LambdaQueryWrapper<>();
        appQueryWrapperByActive.eq(SysApp::getActiveFlag, YesOrNotEnum.Y.getCode())
                .eq(SysApp::getDelFlag, YesOrNotEnum.N.getCode());

        // 排除自己
        if (excludeSelf) {
            appQueryWrapperByActive.ne(SysApp::getId, sysAppRequest.getId());
        }

        int countByActive = this.count(appQueryWrapperByActive);

        // 只判断激活状态为Y时候数量是否大于1了
        if (countByActive >= 1 && YesOrNotEnum.Y.getCode().equals(sysAppRequest.getActiveFlag())) {
            throw new ServiceException(AppExceptionEnum.APP_ACTIVE_REPEAT);
        }
    }

    /**
     * 创建wrapper
     *
     * @author fengshuonan
     * @date 2020/11/6 10:16
     */
    private LambdaQueryWrapper<SysApp> createWrapper(SysAppRequest sysAppRequest) {
        LambdaQueryWrapper<SysApp> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysAppRequest)) {
            // 根据id查询
            if (ObjectUtil.isNotEmpty(sysAppRequest.getId())) {
                queryWrapper.eq(SysApp::getId, sysAppRequest.getId());
            }

            // 根据名称模糊查询
            if (ObjectUtil.isNotEmpty(sysAppRequest.getName())) {
                queryWrapper.like(SysApp::getName, sysAppRequest.getName());
            }

            // 根据编码模糊查询
            if (ObjectUtil.isNotEmpty(sysAppRequest.getCode())) {
                queryWrapper.like(SysApp::getCode, sysAppRequest.getCode());
            }
        }

        // 查询未删除状态的
        queryWrapper.eq(SysApp::getDelFlag, YesOrNotEnum.N.getCode());

        return queryWrapper;
    }

}
