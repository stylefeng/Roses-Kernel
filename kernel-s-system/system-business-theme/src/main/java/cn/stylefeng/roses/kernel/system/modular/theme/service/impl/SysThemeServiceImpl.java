package cn.stylefeng.roses.kernel.system.modular.theme.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.file.api.FileInfoApi;
import cn.stylefeng.roses.kernel.file.api.pojo.AntdvFileInfo;
import cn.stylefeng.roses.kernel.file.api.pojo.request.SysFileInfoRequest;
import cn.stylefeng.roses.kernel.file.modular.service.SysFileInfoService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.api.ThemeServiceApi;
import cn.stylefeng.roses.kernel.system.api.constants.SystemConstants;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.theme.SysThemeExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.theme.SysThemeDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.theme.SysThemeRequest;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysTheme;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysThemeTemplate;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysThemeTemplateField;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysThemeTemplateRel;
import cn.stylefeng.roses.kernel.system.modular.theme.enums.ThemeFieldTypeEnum;
import cn.stylefeng.roses.kernel.system.modular.theme.factory.DefaultThemeFactory;
import cn.stylefeng.roses.kernel.system.modular.theme.mapper.SysThemeMapper;
import cn.stylefeng.roses.kernel.system.modular.theme.pojo.DefaultTheme;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeService;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeTemplateFieldService;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeTemplateRelService;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeTemplateService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
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
@Slf4j
public class SysThemeServiceImpl extends ServiceImpl<SysThemeMapper, SysTheme> implements SysThemeService, ThemeServiceApi {

    @Resource
    private SysThemeTemplateService sysThemeTemplateService;

    @Resource
    private SysThemeTemplateFieldService sysThemeTemplateFieldService;

    @Resource
    private SysFileInfoService sysFileInfoService;

    @Resource
    private FileInfoApi fileInfoApi;

    @Resource(name = "themeCacheApi")
    private CacheOperatorApi<DefaultTheme> themeCacheApi;

    @Resource
    private SysThemeTemplateRelService sysThemeTemplateRelService;

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

        // 获取图片文件的名称
        List<String> fileNames = new ArrayList<>();
        if (themeKeys.size() > 0) {
            LambdaQueryWrapper<SysThemeTemplateField> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysThemeTemplateField::getFieldCode, themeKeys).eq(SysThemeTemplateField::getFieldType, ThemeFieldTypeEnum.FILE.getCode())
                    .select(SysThemeTemplateField::getFieldCode);
            List<SysThemeTemplateField> sysThemeTemplateFields = sysThemeTemplateFieldService.list(queryWrapper);
            fileNames = sysThemeTemplateFields.stream().map(SysThemeTemplateField::getFieldCode).collect(Collectors.toList());
        }


        // 删除图片
        if (fileNames.size() > 0) {
            for (String themeKey : themeKeys) {
                String themeValueStr = themeMap.get(themeKey);
                for (String fileName : fileNames) {
                    if (StrUtil.isNotBlank(themeKey) && StrUtil.isNotBlank(fileName) && themeKey.equals(fileName)) {
                        SysFileInfoRequest sysFileInfoRequest = new SysFileInfoRequest();
                        sysFileInfoRequest.setFileId(Long.parseLong(themeValueStr));
                        sysFileInfoService.deleteReally(sysFileInfoRequest);
                    }
                }
            }
        }

        this.removeById(sysTheme);

        // 清除主题缓存
        this.clearThemeCache();
    }

    @Override
    public void edit(SysThemeRequest sysThemeRequest) {
        SysTheme sysTheme = new SysTheme();

        // 拷贝属性
        BeanUtil.copyProperties(sysThemeRequest, sysTheme);

        this.updateById(sysTheme);

        // 清除主题缓存
        this.clearThemeCache();
    }

    @Override
    public PageResult<SysThemeDTO> findPage(SysThemeRequest sysThemeRequest) {
        LambdaQueryWrapper<SysTheme> queryWrapper = new LambdaQueryWrapper<>();
        // 通过主题名称模糊查询
        queryWrapper.like(StrUtil.isNotBlank(sysThemeRequest.getThemeName()), SysTheme::getThemeName, sysThemeRequest.getThemeName());

        Page<SysTheme> page = page(PageFactory.defaultPage(), queryWrapper);

        List<SysThemeDTO> sysThemeDTOList = new ArrayList<>();
        for (SysTheme record : page.getRecords()) {
            SysThemeDTO sysThemeDTO = new SysThemeDTO();
            BeanUtil.copyProperties(record, sysThemeDTO);
            SysThemeTemplate sysThemeTemplate = sysThemeTemplateService.getById(record.getTemplateId());
            sysThemeDTO.setTemplateName(sysThemeTemplate.getTemplateName());
            sysThemeDTOList.add(sysThemeDTO);
        }

        return PageResultFactory.createPageResult(sysThemeDTOList, page.getTotal(), Integer.valueOf(String.valueOf(page.getSize())), Integer.valueOf(String.valueOf(page.getCurrent())));
    }

    @Override
    public SysTheme detail(SysThemeRequest sysThemeRequest) {
        SysTheme sysTheme = this.querySysThemeById(sysThemeRequest);

        // 设置动态属性表单
        String themeValueJson = sysTheme.getThemeValue();
        JSONObject jsonObject = JSON.parseObject(themeValueJson);
        sysTheme.setDynamicForm(jsonObject.getInnerMap());

        // 遍历表单属性，找到所有文件类型的，组装文件的图片和名称等信息
        HashMap<String, AntdvFileInfo[]> tempFileList = new HashMap<>();
        for (Map.Entry<String, Object> keyValues : jsonObject.entrySet()) {
            String key = keyValues.getKey();
            String value = jsonObject.getString(key);
            // 判断是否是文件类型
            boolean keyFileFlag = sysThemeTemplateFieldService.getKeyFileFlag(key);
            if (keyFileFlag) {
                AntdvFileInfo antdvFileInfo = this.fileInfoApi.buildAntdvFileInfo(Long.valueOf(value));
                tempFileList.put(key, new AntdvFileInfo[]{antdvFileInfo});
            }
        }

        // 设置临时文件的展示
        sysTheme.setTempFileList(tempFileList);
        return sysTheme;
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

        // 清除主题缓存
        this.clearThemeCache();
    }

    @Override
    public DefaultTheme currentThemeInfo(SysThemeRequest sysThemeParam) {

        // 获取缓存中是否有默认主题
        DefaultTheme defaultTheme = themeCacheApi.get(SystemConstants.THEME_GUNS_PLATFORM);
        if (defaultTheme != null) {
            return defaultTheme;
        }

        // 查询系统中激活的主题
        DefaultTheme result = this.querySystemTheme();

        // 将主题信息中的文件id，拼接为文件url的形式
        this.parseFileUrls(result);

        // 缓存系统中激活的主题
        themeCacheApi.put(SystemConstants.THEME_GUNS_PLATFORM, result);

        return result;
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

    /**
     * 查找系统中默认的主题
     *
     * @author fengshuonan
     * @date 2022/1/11 9:44
     */
    private DefaultTheme querySystemTheme() {
        // 查询编码为GUNS_PLATFORM的主题模板id
        Long defaultTemplateId = getDefaultTemplateId();
        if (defaultTemplateId == null) {
            return DefaultThemeFactory.getSystemDefaultTheme();
        }

        // 查找改模板激活的主题，如果没有就返回默认主题
        LambdaQueryWrapper<SysTheme> sysThemeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysThemeLambdaQueryWrapper.eq(SysTheme::getTemplateId, defaultTemplateId);
        sysThemeLambdaQueryWrapper.eq(SysTheme::getStatusFlag, YesOrNotEnum.Y.getCode());
        sysThemeLambdaQueryWrapper.orderByDesc(BaseEntity::getCreateTime);
        SysTheme sysTheme = this.getOne(sysThemeLambdaQueryWrapper, false);
        if (sysTheme == null) {
            log.error("当前系统主题模板编码为GUNS_PLATFORM的主题不存在，请检查数据库数据是否正常！");
            return DefaultThemeFactory.getSystemDefaultTheme();
        }

        // 解析主题中的json字符串
        String themeValue = sysTheme.getThemeValue();
        if (StrUtil.isNotBlank(themeValue)) {
            JSONObject jsonObject = JSONObject.parseObject(themeValue);
            return DefaultThemeFactory.parseDefaultTheme(jsonObject);
        } else {
            return DefaultThemeFactory.getSystemDefaultTheme();
        }
    }

    /**
     * 将属性中所有是文件类型的文件id转化为文件url
     *
     * @author fengshuonan
     * @date 2022/1/11 11:12
     */
    private DefaultTheme parseFileUrls(DefaultTheme theme) {
        // 查询编码为GUNS_PLATFORM的主题模板id
        Long defaultTemplateId = getDefaultTemplateId();
        if (defaultTemplateId == null) {
            return theme;
        }

        // 获取主题模板中所有是文件的字段
        LambdaQueryWrapper<SysThemeTemplateRel> sysThemeTemplateRelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysThemeTemplateRelLambdaQueryWrapper.eq(SysThemeTemplateRel::getTemplateId, defaultTemplateId);
        List<SysThemeTemplateRel> relList = this.sysThemeTemplateRelService.list(sysThemeTemplateRelLambdaQueryWrapper);

        if (ObjectUtil.isEmpty(relList)) {
            return theme;
        }

        // 所有是文件类型的字段编码
        List<String> fieldCodes = relList.stream().map(SysThemeTemplateRel::getFieldCode).collect(Collectors.toList());

        // 查询字段中是文件的字段列表
        LambdaQueryWrapper<SysThemeTemplateField> sysThemeTemplateFieldLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysThemeTemplateFieldLambdaQueryWrapper.in(SysThemeTemplateField::getFieldCode, fieldCodes);
        sysThemeTemplateFieldLambdaQueryWrapper.eq(SysThemeTemplateField::getFieldType, ThemeFieldTypeEnum.FILE.getCode());
        sysThemeTemplateFieldLambdaQueryWrapper.select(SysThemeTemplateField::getFieldCode);
        List<SysThemeTemplateField> fieldInfoList = this.sysThemeTemplateFieldService.list(sysThemeTemplateFieldLambdaQueryWrapper);

        if (ObjectUtil.isEmpty(fieldInfoList)) {
            return theme;
        }

        // 所有文件类型的字段名
        List<String> needToParse = fieldInfoList.stream().map(SysThemeTemplateField::getFieldCode).map(StrUtil::toCamelCase).collect(Collectors.toList());

        // 其他属性
        Map<String, String> otherConfigs = theme.getOtherConfigs();

        for (String fieldName : needToParse) {
            PropertyDescriptor propertyDescriptor = null;
            try {
                propertyDescriptor = new PropertyDescriptor(fieldName, DefaultTheme.class);
                Method readMethod = propertyDescriptor.getReadMethod();
                String fieldValue = (String) readMethod.invoke(theme);
                if (!StrUtil.isEmpty(fieldValue)) {
                    // 将文件id转化为文件url
                    String fileUnAuthUrl = fileInfoApi.getFileUnAuthUrl(Long.valueOf(fieldValue));
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    writeMethod.invoke(theme, fileUnAuthUrl);
                }
            } catch (Exception e) {
                log.error("解析主题的文件id为url时出错", e);
            }

            // 判断其他属性有没有需要转化的
            for (Map.Entry<String, String> otherItem : otherConfigs.entrySet()) {
                if (fieldName.equals(otherItem.getKey())) {
                    String otherFileId = otherItem.getValue();
                    // 将文件id转化为文件url
                    String fileUnAuthUrl = fileInfoApi.getFileUnAuthUrl(Long.valueOf(otherFileId));
                    otherConfigs.put(otherItem.getKey(), fileUnAuthUrl);
                }
            }
        }

        return theme;
    }

    /**
     * 获取默认系统的模板id
     *
     * @author fengshuonan
     * @date 2022/1/11 11:35
     */
    private Long getDefaultTemplateId() {
        // 查询编码为GUNS_PLATFORM的主题模板id
        LambdaQueryWrapper<SysThemeTemplate> sysThemeTemplateLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysThemeTemplateLambdaQueryWrapper.eq(SysThemeTemplate::getTemplateCode, SystemConstants.THEME_GUNS_PLATFORM);
        SysThemeTemplate sysThemeTemplate = this.sysThemeTemplateService.getOne(sysThemeTemplateLambdaQueryWrapper, false);
        if (sysThemeTemplate == null) {
            log.error("当前系统主题模板编码GUNS_PLATFORM不存在，请检查数据库数据是否正常！");
            return null;
        }
        return sysThemeTemplate.getTemplateId();
    }

    /**
     * 清除主题缓存
     *
     * @author fengshuonan
     * @date 2022/1/12 12:49
     */
    private void clearThemeCache() {
        themeCacheApi.remove(SystemConstants.THEME_GUNS_PLATFORM);
    }

}
