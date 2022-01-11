package cn.stylefeng.roses.kernel.system.modular.theme.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.pojo.theme.SysThemeDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.theme.SysThemeRequest;
import cn.stylefeng.roses.kernel.system.modular.theme.entity.SysTheme;
import cn.stylefeng.roses.kernel.system.modular.theme.pojo.DefaultTheme;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统主题service接口
 *
 * @author xixiaowei
 * @date 2021/12/17 16:15
 */
public interface SysThemeService extends IService<SysTheme> {

    /**
     * 增加系统主题
     *
     * @author xixiaowei
     * @date 2021/12/17 16:26
     */
    void add(SysThemeRequest sysThemeRequest);

    /**
     * 删除系统主题
     *
     * @author xixiaowei
     * @date 2021/12/17 16:29
     */
    void del(SysThemeRequest sysThemeRequest);

    /**
     * 修改系统主题
     *
     * @author xixiaowei
     * @date 2021/12/17 16:47
     */
    void edit(SysThemeRequest sysThemeRequest);

    /**
     * 查询系统主题
     *
     * @return 分页结果
     * @author xixiaowei
     * @date 2021/12/17 16:52
     */
    PageResult<SysThemeDTO> findPage(SysThemeRequest sysThemeRequest);

    /**
     * 查询系统主题详情
     *
     * @return 系统主题
     * @author xixiaowei
     * @date 2021/12/17 17:01
     */
    SysTheme detail(SysThemeRequest sysThemeRequest);

    /**
     * 修改系统主题启用状态
     *
     * @author xixiaowei
     * @date 2021/12/17 17:06
     */
    void updateThemeStatus(SysThemeRequest sysThemeRequest);

    /**
     * 当前系统主题数据
     *
     * @author fengshuonan
     * @date 2022/1/10 18:30
     */
    DefaultTheme currentThemeInfo(SysThemeRequest sysThemeParam);

}
