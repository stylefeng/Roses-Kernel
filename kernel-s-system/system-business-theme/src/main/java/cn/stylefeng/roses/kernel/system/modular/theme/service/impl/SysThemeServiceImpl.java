package cn.stylefeng.roses.kernel.system.modular.theme.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.file.api.pojo.request.SysFileInfoRequest;
import cn.stylefeng.roses.kernel.file.modular.service.SysFileInfoService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.api.ThemeServiceApi;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.theme.SysThemeExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.theme.SysThemeRequest;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysTheme;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysThemeTemplate;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysThemeTemplateField;
import cn.stylefeng.roses.kernel.system.modular.theme.mapper.SysThemeMapper;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeService;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeTemplateFieldService;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeTemplateService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统主题service接口实现类
 *
 * @author xixiaowei
 * @date 2021/12/17 16:17
 */
@Service
public class SysThemeServiceImpl extends ServiceImpl<SysThemeMapper, SysTheme> implements SysThemeService, ThemeServiceApi {

    @Resource
    private SysThemeTemplateService sysThemeTemplateService;

    @Resource
    private SysThemeTemplateFieldService sysThemeTemplateFieldService;

    @Resource
    private SysFileInfoService sysFileInfoService;

    @Override
    public void add(SysThemeRequest sysThemeRequest) {
        // 查询模板状态
        SysThemeTemplate sysThemeTemplate = sysThemeTemplateService.getById(sysThemeRequest.getTemplateId());

        // 判断模板启用状态：如果为禁用状态不允许使用
        if (YesOrNotEnum.N.getCode().equals(sysThemeTemplate.getStatusFlag().toString())) {
            throw new SystemModularException(SysThemeExceptionEnum.THEME_TEMPLATE_IS_DISABLE);
        }

        SysTheme sysTheme = new SysTheme();

        // 拷贝属性
        BeanUtil.copyProperties(sysThemeRequest, sysTheme);

        // 设置默认启用状态-禁用N
        sysTheme.setStatusFlag(YesOrNotEnum.N.getCode().charAt(0));

        this.save(sysTheme);
    }

    @Override
    public void del(SysThemeRequest sysThemeRequest) {
        SysTheme sysTheme = this.querySysThemeById(sysThemeRequest);

        // 已启用的主题不允许删除
        if (YesOrNotEnum.Y.getCode().equals(sysTheme.getStatusFlag().toString())) {
            throw new SystemModularException(SysThemeExceptionEnum.THEME_NOT_ALLOW_DELETE);
        }

        // 删除保存的图片
        String themeValue = sysTheme.getThemeValue();
        Map<String, String> themeMap = JSON.parseObject(themeValue, Map.class);

        // 获取map的key
        List<String> themeKeys = new ArrayList<>(themeMap.keySet());

        // 获取图片文件的编码
        LambdaQueryWrapper<SysThemeTemplateField> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysThemeTemplateField::getFieldName, themeKeys).eq(SysThemeTemplateField::getFieldType, "file")
                    .select(SysThemeTemplateField::getFieldName);
        List<SysThemeTemplateField> sysThemeTemplateFields = sysThemeTemplateFieldService.list(queryWrapper);
        List<String> fileNames = sysThemeTemplateFields.stream().map(SysThemeTemplateField::getFieldName).collect(Collectors.toList());

        // 删除图片
        if (fileNames.size() > 0) {
            for (String themeKey : themeKeys) {
                String themeValueStr = themeMap.get(themeKey);
                for (String fileName : fileNames) {
                    if (StrUtil.isNotBlank(themeKey) && StrUtil.isNotBlank(fileName) && themeKey.equals(fileName)) {
                        SysFileInfoRequest sysFileInfoRequest = new SysFileInfoRequest();
                        sysFileInfoRequest.setFileCode(Long.parseLong(themeValueStr));
                        sysFileInfoService.deleteReally(sysFileInfoRequest);
                    }
                }
            }
        }

        this.removeById(sysTheme);
    }

    @Override
    public void edit(SysThemeRequest sysThemeRequest) {
        SysTheme sysTheme = this.querySysThemeById(sysThemeRequest);

        // 拷贝属性
        BeanUtil.copyProperties(sysThemeRequest, sysTheme);

        this.updateById(sysTheme);
    }

    @Override
    public PageResult<SysTheme> findPage(SysThemeRequest sysThemeRequest) {
        LambdaQueryWrapper<SysTheme> queryWrapper = new LambdaQueryWrapper<>();
        // 通过主题名称模糊查询
        queryWrapper.like(StrUtil.isNotBlank(sysThemeRequest.getThemeName()),SysTheme::getThemeName, sysThemeRequest.getThemeName());

        Page<SysTheme> page = page(PageFactory.defaultPage(), queryWrapper);

        return PageResultFactory.createPageResult(page);
    }

    @Override
    public SysTheme detail(SysThemeRequest sysThemeRequest) {
        return this.querySysThemeById(sysThemeRequest);
    }

    @Override
    public void updateThemeStatus(SysThemeRequest sysThemeRequest) {
        SysTheme sysTheme = this.querySysThemeById(sysThemeRequest);

        // 已经启用系统主题不允许禁用
        if (YesOrNotEnum.Y.getCode().equals(sysTheme.getStatusFlag().toString())) {
            throw new SystemModularException(SysThemeExceptionEnum.UNIQUE_ENABLE_NOT_DISABLE);
        } else {
            // 如果当前系统禁用，启用该系统主题，同时禁用已启用的系统主题
            sysTheme.setStatusFlag(YesOrNotEnum.Y.getCode().charAt(0));

            LambdaQueryWrapper<SysTheme> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysTheme::getStatusFlag, YesOrNotEnum.Y.getCode().charAt(0));

            if (this.list().size() > 1) {
                SysTheme theme = getOne(queryWrapper, true);
                theme.setStatusFlag(YesOrNotEnum.N.getCode().charAt(0));
                this.updateById(theme);
            }
        }
        this.updateById(sysTheme);
    }

    /**
     * 查询单个系统主题
     *
     * @author xixiaowei
     * @date 2021/12/17 16:30
     */
    private SysTheme querySysThemeById(SysThemeRequest sysThemeRequest) {
        SysTheme sysTheme = this.getById(sysThemeRequest.getThemeId());
        if (ObjectUtil.isNull(sysTheme)) {
            throw new SystemModularException(SysThemeExceptionEnum.THEME_NOT_EXIST);
        }
        return sysTheme;
    }
}
