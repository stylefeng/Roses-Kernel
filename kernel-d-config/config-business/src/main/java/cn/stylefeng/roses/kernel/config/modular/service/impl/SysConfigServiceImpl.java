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
package cn.stylefeng.roses.kernel.config.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.config.api.ConfigInitCallbackApi;
import cn.stylefeng.roses.kernel.config.api.ConfigInitStrategyApi;
import cn.stylefeng.roses.kernel.config.api.constants.ConfigConstants;
import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import cn.stylefeng.roses.kernel.config.api.exception.ConfigException;
import cn.stylefeng.roses.kernel.config.api.exception.enums.ConfigExceptionEnum;
import cn.stylefeng.roses.kernel.config.api.pojo.ConfigInitItem;
import cn.stylefeng.roses.kernel.config.api.pojo.ConfigInitRequest;
import cn.stylefeng.roses.kernel.config.modular.entity.SysConfig;
import cn.stylefeng.roses.kernel.config.modular.mapper.SysConfigMapper;
import cn.stylefeng.roses.kernel.config.modular.param.SysConfigParam;
import cn.stylefeng.roses.kernel.config.modular.service.SysConfigService;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.file.api.constants.FileConstants;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统参数配置service接口实现类
 *
 * @author fengshuonan
 * @date 2020/4/14 11:16
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysConfigParam sysConfigParam) {

        // 1.构造实体
        SysConfig sysConfig = new SysConfig();
        BeanUtil.copyProperties(sysConfigParam, sysConfig);
        sysConfig.setStatusFlag(StatusEnum.ENABLE.getCode());

        // 2.保存到库中
        this.save(sysConfig);

        // 3.添加对应context
        ConfigContext.me().putConfig(sysConfigParam.getConfigCode(), sysConfigParam.getConfigValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysConfigParam sysConfigParam) {

        // 1.根据id获取常量
        SysConfig sysConfig = this.querySysConfig(sysConfigParam);
        if (sysConfig == null) {
            String userTip = StrUtil.format(ConfigExceptionEnum.CONFIG_NOT_EXIST.getUserTip(), "id: " + sysConfigParam.getConfigId());
            throw new ConfigException(ConfigExceptionEnum.CONFIG_NOT_EXIST, userTip);
        }

        // 2.不能删除系统参数
        if (YesOrNotEnum.Y.getCode().equals(sysConfig.getSysFlag())) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_SYS_CAN_NOT_DELETE);
        }

        // 3.设置状态为已删除
        sysConfig.setStatusFlag(StatusEnum.DISABLE.getCode());
        sysConfig.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(sysConfig);

        // 4.删除对应context
        ConfigContext.me().deleteConfig(sysConfigParam.getConfigCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(SysConfigParam sysConfigParam) {

        // 1.根据id获取常量信息
        SysConfig sysConfig = this.querySysConfig(sysConfigParam);

        // 2.请求参数转化为实体
        BeanUtil.copyProperties(sysConfigParam, sysConfig);
        // 不能修改状态，用修改状态接口修改状态
        sysConfig.setStatusFlag(null);

        // 3.更新记录
        this.updateById(sysConfig);

        // 4.更新对应常量context
        ConfigContext.me().putConfig(sysConfigParam.getConfigCode(), sysConfigParam.getConfigValue());
    }

    @Override
    public SysConfig detail(SysConfigParam sysConfigParam) {
        return this.querySysConfig(sysConfigParam);
    }

    @Override
    public PageResult<SysConfig> findPage(SysConfigParam sysConfigParam) {
        LambdaQueryWrapper<SysConfig> wrapper = createWrapper(sysConfigParam);
        Page<SysConfig> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysConfig> findList(SysConfigParam sysConfigParam) {
        LambdaQueryWrapper<SysConfig> wrapper = createWrapper(sysConfigParam);
        return this.list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initConfig(ConfigInitRequest configInitRequest) {

        if (configInitRequest == null || configInitRequest.getSysConfigs() == null) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_INIT_ERROR);
        }

        // 如果当前已经初始化过配置，则不能初始化
        LambdaQueryWrapper<SysConfig> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysConfig::getConfigCode, RuleConstants.SYSTEM_CONFIG_INIT_FLAG_NAME);
        SysConfig tempSysConfig = this.getOne(lambdaQueryWrapper, false);
        String alreadyInit = tempSysConfig.getConfigValue();
        if (Convert.toBool(alreadyInit)) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_INIT_ALREADY);
        }

        // 获取初始化回调接口的所有实现类
        Map<String, ConfigInitCallbackApi> beans = SpringUtil.getBeansOfType(ConfigInitCallbackApi.class);

        // 调用初始化之前回调
        if (ObjectUtil.isNotNull(beans)) {
            for (ConfigInitCallbackApi initCallbackApi : beans.values()) {
                initCallbackApi.initBefore();
            }
        }

        // 添加系统已经初始化的配置
        Map<String, String> sysConfigs = configInitRequest.getSysConfigs();
        sysConfigs.put(RuleConstants.SYSTEM_CONFIG_INIT_FLAG_NAME, "true");

        // 针对每个配置执行更新库和刷新缓存的操作
        for (Map.Entry<String, String> entry : sysConfigs.entrySet()) {
            String configCode = entry.getKey();
            String configValue = entry.getValue();

            // 获取库数据库这条记录
            LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysConfig::getConfigCode, configCode);
            SysConfig sysConfig = this.getOne(wrapper, false);
            if (sysConfig == null) {
                continue;
            }
            sysConfig.setConfigValue(configValue);
            this.updateById(sysConfig);

            // 更新缓存
            ConfigContext.me().putConfig(configCode, configValue);
        }

        // 调用初始化之后回调
        if (ObjectUtil.isNotNull(beans)) {
            for (ConfigInitCallbackApi initCallbackApi : beans.values()) {
                initCallbackApi.initAfter();
            }
        }
    }

    @Override
    public Boolean getInitConfigFlag() {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getConfigCode, RuleConstants.SYSTEM_CONFIG_INIT_FLAG_NAME);
        SysConfig sysConfig = this.getOne(wrapper, false);

        // 配置为空，还没初始化
        if (sysConfig == null) {
            return true;
        } else {
            String configValue = sysConfig.getConfigValue();
            if (StrUtil.isEmpty(configValue)) {
                return true;
            } else {
                return Convert.toBool(sysConfig.getConfigValue());
            }
        }
    }

    @Override
    public List<ConfigInitItem> getInitConfigs() {
        List<ConfigInitItem> configInitItemList = new ArrayList<>();
        Map<String, ConfigInitStrategyApi> beans = SpringUtil.getBeansOfType(ConfigInitStrategyApi.class);
        for (ConfigInitStrategyApi value : beans.values()) {
            configInitItemList.addAll(value.getInitConfigs());
        }
        return configInitItemList;
    }

    @Override
    public String getServerDeployHost() {

        // 获取后端部署的服务器
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getConfigCode, ConfigConstants.SYS_SERVER_DEPLOY_HOST);
        SysConfig sysConfig = this.getOne(wrapper, false);

        if (sysConfig != null) {
            return sysConfig.getConfigValue();
        } else {
            return FileConstants.DEFAULT_SERVER_DEPLOY_HOST;
        }
    }

    /**
     * 获取系统参数配置
     *
     * @author fengshuonan
     * @date 2020/4/14 11:19
     */
    private SysConfig querySysConfig(SysConfigParam sysConfigParam) {
        SysConfig sysConfig = this.getById(sysConfigParam.getConfigId());
        if (ObjectUtil.isEmpty(sysConfig) || sysConfig.getDelFlag().equals(YesOrNotEnum.Y.getCode())) {
            String userTip = StrUtil.format(ConfigExceptionEnum.CONFIG_NOT_EXIST.getUserTip(), "id: " + sysConfigParam.getConfigId());
            throw new ConfigException(ConfigExceptionEnum.CONFIG_NOT_EXIST, userTip);
        }
        return sysConfig;
    }

    /**
     * 创建wrapper
     *
     * @author fengshuonan
     * @date 2020/11/6 10:16
     */
    private LambdaQueryWrapper<SysConfig> createWrapper(SysConfigParam sysConfigParam) {
        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<>();

        // 查询未删除的
        queryWrapper.ne(SysConfig::getDelFlag, YesOrNotEnum.Y.getCode());

        // 按类型升序排列，同类型的排在一起
        queryWrapper.orderByDesc(SysConfig::getGroupCode);

        if (ObjectUtil.isEmpty(sysConfigParam)) {
            return queryWrapper;
        }

        String configName = sysConfigParam.getConfigName();
        String configCode = sysConfigParam.getConfigCode();
        String groupCode = sysConfigParam.getGroupCode();

        // 如果名称不为空，则带上名称搜素搜条件
        queryWrapper.like(ObjectUtil.isNotEmpty(configName), SysConfig::getConfigName, configName);

        // 如果常量编码不为空，则带上常量编码搜素搜条件
        queryWrapper.like(ObjectUtil.isNotEmpty(configCode), SysConfig::getConfigCode, configCode);

        // 如果分类编码不为空，则带上分类编码
        queryWrapper.eq(ObjectUtil.isNotEmpty(groupCode), SysConfig::getGroupCode, groupCode);

        return queryWrapper;
    }

}
