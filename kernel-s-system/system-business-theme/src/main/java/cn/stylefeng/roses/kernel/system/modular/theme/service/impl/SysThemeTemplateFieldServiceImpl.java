package cn.stylefeng.roses.kernel.system.modular.theme.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.theme.SysThemeTemplateFieldExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.theme.SysThemeTemplateFieldRequest;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysThemeTemplateField;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysThemeTemplateRel;
import cn.stylefeng.roses.kernel.system.modular.theme.mapper.SysThemeTemplateFieldMapper;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeTemplateFieldService;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeTemplateRelService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 系统主题模板属性service接口实现类
 *
 * @author xixiaowei
 * @date 2021/12/17 10:34
 */
@Service
public class SysThemeTemplateFieldServiceImpl extends ServiceImpl<SysThemeTemplateFieldMapper, SysThemeTemplateField> implements SysThemeTemplateFieldService {

    @Resource
    private SysThemeTemplateRelService sysThemeTemplateRelService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest) {
        SysThemeTemplateField sysThemeTemplateField = new SysThemeTemplateField();

        // 拷贝属性
        BeanUtil.copyProperties(sysThemeTemplateFieldRequest, sysThemeTemplateField);

        // 设置主题模板属性编码
        sysThemeTemplateField.setFieldCode(IdWorker.getIdStr());

        // 设置是否必填：如果请求参数为空，默认设置为非必填
        sysThemeTemplateField.setFieldRequired(StringUtils.isBlank(sysThemeTemplateFieldRequest.getFieldType()) ? YesOrNotEnum.N.getCode().charAt(0) : sysThemeTemplateFieldRequest.getFieldRequired());

        // 添加关联关系
        SysThemeTemplateRel sysThemeTemplateRel = new SysThemeTemplateRel();
        sysThemeTemplateRel.setTemplateId(sysThemeTemplateFieldRequest.getTemplateId());
        sysThemeTemplateRel.setFieldCode(sysThemeTemplateField.getFieldCode());

        // 保存关联关系
        sysThemeTemplateRelService.save(sysThemeTemplateRel);

        this.save(sysThemeTemplateField);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest) {
        SysThemeTemplateField sysThemeTemplateField = this.queryThemeTemplateFieldById(sysThemeTemplateFieldRequest);

        // 删除关联关系
        LambdaQueryWrapper<SysThemeTemplateRel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysThemeTemplateRel::getFieldCode, sysThemeTemplateField.getFieldCode());
        sysThemeTemplateRelService.remove(queryWrapper);

        this.removeById(sysThemeTemplateField.getFieldId());
    }

    @Override
    public void edit(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest) {
        SysThemeTemplateField sysThemeTemplateField = this.queryThemeTemplateFieldById(sysThemeTemplateFieldRequest);

        // 更新属性
        BeanUtil.copyProperties(sysThemeTemplateFieldRequest, sysThemeTemplateField);

        // 设置是否必填：如果请求参数为空，默认设置为非必填
        sysThemeTemplateField.setFieldRequired(StringUtils.isBlank(sysThemeTemplateFieldRequest.getFieldType()) ? YesOrNotEnum.N.getCode().charAt(0) : sysThemeTemplateFieldRequest.getFieldRequired());

        this.updateById(sysThemeTemplateField);
    }

    @Override
    public SysThemeTemplateField detail(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest) {
        return this.queryThemeTemplateFieldById(sysThemeTemplateFieldRequest);
    }

    /**
     * 获取主题模板属性
     *
     * @param sysThemeTemplateFieldRequest 请求参数
     * @return 主题模板属性
     * @author xixiaowei
     * @date 2021/12/17 11:03
     */
    private SysThemeTemplateField queryThemeTemplateFieldById(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest) {
        SysThemeTemplateField sysThemeTemplateField = this.getById(sysThemeTemplateFieldRequest.getFieldId());
        if (ObjectUtil.isNull(sysThemeTemplateField)) {
            throw new SystemModularException(SysThemeTemplateFieldExceptionEnum.FIELD_NOT_EXIST);
        }
        return sysThemeTemplateField;
    }
}
