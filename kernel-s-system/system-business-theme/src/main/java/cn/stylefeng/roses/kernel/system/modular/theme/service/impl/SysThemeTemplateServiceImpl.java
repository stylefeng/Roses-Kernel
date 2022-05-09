package cn.stylefeng.roses.kernel.system.modular.theme.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.theme.SysThemeExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.exception.enums.theme.SysThemeTemplateExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.theme.SysThemeTemplateDataDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.theme.SysThemeTemplateRequest;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysTheme;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysThemeTemplate;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysThemeTemplateRel;
import cn.stylefeng.roses.kernel.system.modular.theme.mapper.SysThemeTemplateMapper;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeService;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeTemplateRelService;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeTemplateService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;

/**
 * 系统主题模板service接口实现类
 *
 * @author xixiaowei
 * @date 2021/12/17 13:58
 */
@Service
public class SysThemeTemplateServiceImpl extends ServiceImpl<SysThemeTemplateMapper, SysThemeTemplate> implements SysThemeTemplateService {

    @Resource
    private SysThemeTemplateMapper sysThemeTemplateMapper;

    @Resource
    private SysThemeService sysThemeService;

    @Resource
    private SysThemeTemplateRelService sysThemeTemplateRelService;

    @Override
    public void add(SysThemeTemplateRequest sysThemeTemplateRequest) {
        SysThemeTemplate sysThemeTemplate = new SysThemeTemplate();

        // 拷贝属性
        BeanUtil.copyProperties(sysThemeTemplateRequest, sysThemeTemplate);

        // 默认启用状态：禁用N
        sysThemeTemplate.setStatusFlag(YesOrNotEnum.N.getCode().charAt(0));

        this.save(sysThemeTemplate);
    }

    @Override
    public void edit(SysThemeTemplateRequest sysThemeTemplateRequest) {
        SysThemeTemplate sysThemeTemplate = this.querySysThemeTemplateById(sysThemeTemplateRequest);

        if (YesOrNotEnum.Y.getCode().equals(sysThemeTemplate.getStatusFlag().toString())) {
            throw new SystemModularException(SysThemeTemplateExceptionEnum.TEMPLATE_IS_USED);
        }

        // 拷贝属性
        BeanUtil.copyProperties(sysThemeTemplateRequest, sysThemeTemplate);

        this.updateById(sysThemeTemplate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysThemeTemplateRequest sysThemeTemplateRequest) {
        SysThemeTemplate sysThemeTemplate = this.querySysThemeTemplateById(sysThemeTemplateRequest);

        // Guns开头的模板字段不能删除，系统内置
        if (sysThemeTemplate.getTemplateCode().toUpperCase(Locale.ROOT).startsWith(SystemConstants.THEME_CODE_SYSTEM_PREFIX)) {
            throw new SystemModularException(SysThemeExceptionEnum.THEME_IS_SYSTEM);
        }

        // 启动的主题模板不能删除
        if (YesOrNotEnum.Y.getCode().equals(sysThemeTemplate.getStatusFlag().toString())) {
            throw new SystemModularException(SysThemeTemplateExceptionEnum.TEMPLATE_IS_ENABLE);
        }

        // 删除关联关系条件
        LambdaQueryWrapper<SysThemeTemplateRel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysThemeTemplateRel::getTemplateId, sysThemeTemplate.getTemplateId());

        // 删除关联关系
        sysThemeTemplateRelService.remove(queryWrapper);

        // 删除模板
        this.removeById(sysThemeTemplate);
    }

    @Override
    public PageResult<SysThemeTemplate> findPage(SysThemeTemplateRequest sysThemeTemplateRequest) {
        LambdaQueryWrapper<SysThemeTemplate> queryWrapper = new LambdaQueryWrapper<>();
        // 根据系统主题模板名称模糊查询
        queryWrapper.like(StrUtil.isNotBlank(sysThemeTemplateRequest.getTemplateName()), SysThemeTemplate::getTemplateName, sysThemeTemplateRequest.getTemplateName());

        Page<SysThemeTemplate> page = page(PageFactory.defaultPage(), queryWrapper);

        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysThemeTemplate> findList(SysThemeTemplateRequest sysThemeTemplateRequest) {
        return this.list();
    }

    @Override
    public void updateTemplateStatus(SysThemeTemplateRequest sysThemeTemplateRequest) {
        SysThemeTemplate sysThemeTemplate = this.querySysThemeTemplateById(sysThemeTemplateRequest);

        // 系统主题模板被使用，不允许禁用
        LambdaQueryWrapper<SysTheme> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysTheme::getTemplateId, sysThemeTemplate.getTemplateId());
        int sysThemeNum = sysThemeService.count(queryWrapper);
        if (sysThemeNum > 0) {
            throw new SystemModularException(SysThemeTemplateExceptionEnum.TEMPLATE_IS_USED);
        }

        // 修改状态
        if (YesOrNotEnum.Y.getCode().equals(sysThemeTemplate.getStatusFlag().toString())) {
            sysThemeTemplate.setStatusFlag(YesOrNotEnum.N.getCode().charAt(0));
        } else {
            // 如果该模板没有属性不允许启用
            LambdaQueryWrapper<SysThemeTemplateRel> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysThemeTemplateRel::getTemplateId, sysThemeTemplate.getTemplateId());

            List<SysThemeTemplateRel> sysThemeTemplateRels = sysThemeTemplateRelService.list(wrapper);

            if (sysThemeTemplateRels.size() <= 0) {
                throw new SystemModularException(SysThemeTemplateExceptionEnum.TEMPLATE_NOT_ATTRIBUTE);
            }

            sysThemeTemplate.setStatusFlag(YesOrNotEnum.Y.getCode().charAt(0));
        }

        this.updateById(sysThemeTemplate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysThemeTemplateDataDTO> detail(SysThemeTemplateRequest sysThemeTemplateRequest) {
        return sysThemeTemplateMapper.sysThemeTemplateDetail(sysThemeTemplateRequest.getTemplateId());
    }

    /**
     * 查询单个系统主题模板
     *
     * @author xixiaowei
     * @date 2021/12/17 14:28
     */
    private SysThemeTemplate querySysThemeTemplateById(SysThemeTemplateRequest sysThemeTemplateRequest) {
        SysThemeTemplate sysThemeTemplate = this.getById(sysThemeTemplateRequest.getTemplateId());
        if (ObjectUtil.isNull(sysThemeTemplate)) {
            throw new SystemModularException(SysThemeTemplateExceptionEnum.TEMPLATE_NOT_EXIT);
        }
        return sysThemeTemplate;
    }
}
