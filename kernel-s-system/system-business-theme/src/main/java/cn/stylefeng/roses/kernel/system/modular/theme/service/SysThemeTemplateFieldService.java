package cn.stylefeng.roses.kernel.system.modular.theme.service;

import cn.stylefeng.roses.kernel.system.api.pojo.theme.SysThemeTemplateFieldRequest;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysThemeTemplateField;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统主题模板属性service接口
 *
 * @author xixiaowei
 * @date 2021/12/17 10:30
 */
public interface SysThemeTemplateFieldService extends IService<SysThemeTemplateField> {

    /**
     * 增加系统主题模板属性
     *
     * @author xixiaowei
     * @date 2021/12/17 10:47
     */
    void add(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest);

    /**
     * 删除系统主题模板属性
     *
     * @author xixiaowei
     * @date 2021/12/17 11:00
     */
    void del(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest);

    /**
     * 修改系统主题模板属性
     *
     * @author xixiaowei
     * @date 2021/12/17 11:29
     */
    void edit(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest);

    /**
     * 查询系统主题模板属性
     *
     * @author xixiaowei
     * @date 2021/12/17 11:47
     */
    SysThemeTemplateField detail(SysThemeTemplateFieldRequest sysThemeTemplateFieldRequest);
}
