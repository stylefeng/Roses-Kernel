package cn.stylefeng.roses.kernel.app.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.app.modular.entity.SysApp;
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
import cn.stylefeng.roses.kernel.system.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.exception.enums.AppExceptionEnum;
import cn.stylefeng.roses.kernel.system.pojo.app.SysAppRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

        SysApp sysApp = new SysApp();

        // 设置名称和编码
        sysApp.setAppName(sysAppRequest.getAppName());
        sysApp.setAppCode(sysAppRequest.getAppCode());

        // 默认不激活
        sysApp.setActiveFlag(YesOrNotEnum.N.getCode());

        // 设为启用
        sysApp.setStatusFlag(StatusEnum.ENABLE.getCode());

        this.save(sysApp);
    }

    @Override
    public void del(SysAppRequest sysAppRequest) {
        SysApp sysApp = this.querySysApp(sysAppRequest);
        String code = sysApp.getAppCode();

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
    public void edit(SysAppRequest sysAppRequest) {

        SysApp sysApp = this.querySysApp(sysAppRequest);
        BeanUtil.copyProperties(sysAppRequest, sysApp);

        // 不能修改编码
        sysApp.setAppCode(null);

        // 不能修改状态，修改状态接口修改状态
        sysApp.setStatusFlag(null);

        // 不能修改激活，激活接口激活应用
        sysApp.setActiveFlag(null);

        this.updateById(sysApp);
    }

    @Override
    public void editStatus(SysAppRequest sysAppParam) {
        SysApp currentApp = this.querySysApp(sysAppParam);

        // 激活状态的不能被禁用
        if (YesOrNotEnum.Y.getCode().equals(currentApp.getActiveFlag())
                && StatusEnum.DISABLE.getCode().equals(sysAppParam.getStatusFlag())) {
            throw new SystemModularException(AppExceptionEnum.CANT_DISABLE);
        }

        currentApp.setStatusFlag(sysAppParam.getStatusFlag());
        this.updateById(currentApp);
    }

    @Override
    public SysApp detail(SysAppRequest sysAppRequest) {
        return this.querySysApp(sysAppRequest);
    }

    @Override
    public List<SysApp> findList(SysAppRequest sysAppRequest) {
        LambdaQueryWrapper<SysApp> wrapper = createWrapper(sysAppRequest);
        return this.list(wrapper);
    }

    @Override
    public PageResult<SysApp> findPage(SysAppRequest sysAppRequest) {
        LambdaQueryWrapper<SysApp> wrapper = createWrapper(sysAppRequest);
        Page<SysApp> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateActiveFlag(SysAppRequest sysAppRequest) {
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
        queryWrapper.in(SysApp::getAppCode, appCodes);
        queryWrapper.select(SysApp::getAppId, SysApp::getAppCode, SysApp::getAppName);

        List<SysApp> list = this.list(queryWrapper);
        for (SysApp sysApp : list) {
            SimpleDict simpleDict = new SimpleDict();
            simpleDict.setId(sysApp.getAppId());
            simpleDict.setCode(sysApp.getAppCode());
            simpleDict.setName(sysApp.getAppName());
            simpleDicts.add(simpleDict);
        }

        return simpleDicts;
    }

    @Override
    public String getAppNameByAppCode(String appCode) {

        String emptyName = "空应用";

        if (ObjectUtil.isEmpty(appCode)) {
            return emptyName;
        }

        LambdaQueryWrapper<SysApp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysApp::getAppCode, appCode);
        lambdaQueryWrapper.select(SysApp::getAppName);
        SysApp sysApp = this.getOne(lambdaQueryWrapper);

        if (sysApp == null || ObjectUtil.isEmpty(sysApp.getAppName())) {
            return emptyName;
        } else {
            return sysApp.getAppName();
        }
    }

    @Override
    public String getActiveAppCode() {
        LambdaQueryWrapper<SysApp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysApp::getAppCode);
        queryWrapper.eq(SysApp::getActiveFlag, YesOrNotEnum.Y.getCode());
        queryWrapper.eq(SysApp::getDelFlag, YesOrNotEnum.N.getCode());
        List<SysApp> list = this.list(queryWrapper);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0).getAppCode();
        }
    }

    /**
     * 获取系统应用
     *
     * @author fengshuonan
     * @date 2020/3/26 9:56
     */
    private SysApp querySysApp(SysAppRequest sysAppRequest) {
        SysApp sysApp = this.getById(sysAppRequest.getAppId());
        if (ObjectUtil.isNull(sysApp)) {
            throw new ServiceException(AppExceptionEnum.APP_NOT_EXIST);
        }
        return sysApp;
    }

    /**
     * 创建wrapper
     *
     * @author fengshuonan
     * @date 2020/11/6 10:16
     */
    private LambdaQueryWrapper<SysApp> createWrapper(SysAppRequest sysAppRequest) {
        LambdaQueryWrapper<SysApp> queryWrapper = new LambdaQueryWrapper<>();

        // 查询未删除状态的
        queryWrapper.eq(SysApp::getDelFlag, YesOrNotEnum.N.getCode());

        if (ObjectUtil.isEmpty(sysAppRequest)) {
            return queryWrapper;
        }

        // 根据id查询
        queryWrapper.eq(ObjectUtil.isNotEmpty(sysAppRequest.getAppId()), SysApp::getAppId, sysAppRequest.getAppId());

        // 根据名称模糊查询
        queryWrapper.like(ObjectUtil.isNotEmpty(sysAppRequest.getAppName()), SysApp::getAppName, sysAppRequest.getAppName());

        // 根据编码模糊查询
        queryWrapper.like(ObjectUtil.isNotEmpty(sysAppRequest.getAppCode()), SysApp::getAppCode, sysAppRequest.getAppCode());

        return queryWrapper;
    }

}
