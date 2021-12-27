package cn.stylefeng.roses.kernel.system.modular.theme.service.impl;

import cn.stylefeng.roses.kernel.system.api.pojo.theme.SysThemeTemplateRelRequest;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysThemeTemplateRel;
import cn.stylefeng.roses.kernel.system.modular.theme.mapper.SysThemeTemplateRelMapper;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeTemplateRelService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统主题模板属性关系service接口实现类
 *
 * @author xixiaowei
 * @date 2021/12/17 16:14
 */
@Service
public class SysThemeTemplateRelServiceImpl extends ServiceImpl<SysThemeTemplateRelMapper, SysThemeTemplateRel> implements SysThemeTemplateRelService {

    @Override
    public void add(SysThemeTemplateRelRequest sysThemeTemplateRelRequest) {
        // 获取请求中的所有属性编码
        String[] fieldCodes = sysThemeTemplateRelRequest.getFieldCodes();

        List<SysThemeTemplateRel> sysThemeTemplateRels = new ArrayList<>();

        // 填充对象
        for (String fieldCode : fieldCodes) {
            SysThemeTemplateRel sysThemeTemplateRel = new SysThemeTemplateRel();
            sysThemeTemplateRel.setTemplateId(sysThemeTemplateRelRequest.getTemplateId());
            sysThemeTemplateRel.setFieldCode(fieldCode);

            sysThemeTemplateRels.add(sysThemeTemplateRel);
        }

        // 保存关系
        this.saveBatch(sysThemeTemplateRels);
    }

    @Override
    public void del(SysThemeTemplateRelRequest sysThemeTemplateRelRequest) {
        // 获取请求中的所有属性编码
        String[] fieldCodes = sysThemeTemplateRelRequest.getFieldCodes();

        // 构建删除条件
        LambdaQueryWrapper<SysThemeTemplateRel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysThemeTemplateRel::getFieldCode, fieldCodes);

        this.remove(queryWrapper);
    }
}
