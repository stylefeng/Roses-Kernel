package cn.stylefeng.roses.kernel.system.modular.theme.service;

import cn.stylefeng.roses.kernel.system.api.pojo.theme.SysThemeTemplateRelRequest;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysThemeTemplateRel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统主题模板属性关系service接口
 *
 * @author xixiaowei
 * @date 2021/12/17 16:13
 */
public interface SysThemeTemplateRelService extends IService<SysThemeTemplateRel> {

    /**
     * 增加系统主题模板属性关系
     *
     * @author xixiaowei
     * @date 2021/12/24 10:56
     */
    void add(SysThemeTemplateRelRequest sysThemeTemplateRelRequest);

    /**
     * 删除系统主题模板属性关系
     *
     * @author xixiaowei
     * @date 2021/12/24 11:18
     */
    void del(SysThemeTemplateRelRequest sysThemeTemplateRelRequest);
}
