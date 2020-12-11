package cn.stylefeng.roses.kernel.config.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.config.modular.mapper.SysConfigMapper;
import cn.stylefeng.roses.kernel.config.modular.service.SysConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.stylefeng.roses.kernel.config.api.context.ConfigContext;
import cn.stylefeng.roses.kernel.config.api.exception.ConfigException;
import cn.stylefeng.roses.kernel.config.api.exception.enums.ConfigExceptionEnum;
import cn.stylefeng.roses.kernel.config.modular.entity.SysConfig;
import cn.stylefeng.roses.kernel.config.modular.param.SysConfigParam;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.stylefeng.roses.kernel.config.api.exception.enums.ConfigExceptionEnum.CONFIG_SYS_CAN_NOT_DELETE;


/**
 * 系统参数配置service接口实现类
 *
 * @author fengshuonan
 * @date 2020/4/14 11:16
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Override
    public PageResult<SysConfig> page(SysConfigParam sysConfigParam) {
        LambdaQueryWrapper<SysConfig> wrapper = createWrapper(sysConfigParam);
        Page<SysConfig> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysConfig> list(SysConfigParam sysConfigParam) {
        LambdaQueryWrapper<SysConfig> wrapper = createWrapper(sysConfigParam);
        return this.list(wrapper);
    }

    @Override
    public SysConfig detail(SysConfigParam sysConfigParam) {
        return this.querySysConfig(sysConfigParam);
    }

    @Override
    public void add(SysConfigParam sysConfigParam) {

        // 1.构造实体
        SysConfig sysConfig = new SysConfig();
        BeanUtil.copyProperties(sysConfigParam, sysConfig);
        sysConfig.setStatus(StatusEnum.ENABLE.getCode());

        // 2.保存到库中
        this.save(sysConfig);

        // 3.添加对应context
        ConfigContext.me().putConfig(sysConfigParam.getCode(), sysConfigParam.getValue());
    }

    @Override
    public void delete(SysConfigParam sysConfigParam) {

        // 1.根据id获取常量
        SysConfig sysConfig = this.querySysConfig(sysConfigParam);

        // 2.不能删除系统参数
        if (YesOrNotEnum.Y.getCode().equals(sysConfig.getSysFlag())) {
            throw new ConfigException(CONFIG_SYS_CAN_NOT_DELETE);
        }

        // 3.设置状态为已删除
        sysConfig.setStatus(StatusEnum.DISABLE.getCode());
        this.updateById(sysConfig);

        // 4.删除对应context
        ConfigContext.me().deleteConfig(sysConfigParam.getCode());
    }

    @Override
    public void edit(SysConfigParam sysConfigParam) {

        // 1.根据id获取常量信息
        SysConfig sysConfig = this.querySysConfig(sysConfigParam);

        // 2.请求参数转化为实体
        BeanUtil.copyProperties(sysConfigParam, sysConfig);
        // 不能修改状态，用修改状态接口修改状态
        sysConfig.setStatus(null);

        // 3.更新记录
        this.updateById(sysConfig);

        // 4.更新对应常量context
        ConfigContext.me().putConfig(sysConfigParam.getCode(), sysConfigParam.getValue());
    }

    /**
     * 获取系统参数配置
     *
     * @author fengshuonan
     * @date 2020/4/14 11:19
     */
    private SysConfig querySysConfig(SysConfigParam sysConfigParam) {
        SysConfig sysConfig = this.getById(sysConfigParam.getId());
        if (ObjectUtil.isEmpty(sysConfig)) {
            String userTip = StrUtil.format(ConfigExceptionEnum.CONFIG_NOT_EXIST.getUserTip(), "id: " + sysConfigParam.getId());
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

        if (ObjectUtil.isNotNull(sysConfigParam)) {
            // 如果名称不为空，则带上名称搜素搜条件
            if (ObjectUtil.isNotEmpty(sysConfigParam.getName())) {
                queryWrapper.like(SysConfig::getName, sysConfigParam.getName());
            }

            // 如果常量编码不为空，则带上常量编码搜素搜条件
            if (ObjectUtil.isNotEmpty(sysConfigParam.getCode())) {
                queryWrapper.like(SysConfig::getCode, sysConfigParam.getCode());
            }

            // 如果分类编码不为空，则带上分类编码
            if (ObjectUtil.isNotEmpty(sysConfigParam.getGroupCode())) {
                queryWrapper.eq(SysConfig::getGroupCode, sysConfigParam.getGroupCode());
            }
        }

        // 查询未删除的
        queryWrapper.ne(SysConfig::getDelFlag, YesOrNotEnum.Y.getCode());

        // 按类型升序排列，同类型的排在一起
        queryWrapper.orderByDesc(SysConfig::getGroupCode);

        return queryWrapper;
    }

}
