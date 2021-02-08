package cn.stylefeng.roses.kernel.app.modular.service;

import cn.stylefeng.roses.kernel.app.modular.entity.SysApp;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.pojo.app.SysAppRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统应用service接口
 *
 * @author fengshuonan
 * @date 2020/3/13 16:14
 */
public interface SysAppService extends IService<SysApp> {

    /**
     * 添加系统应用
     *
     * @param sysAppParam 添加参数
     * @author fengshuonan
     * @date 2020/3/25 14:57
     */
    void add(SysAppRequest sysAppParam);

    /**
     * 删除系统应用
     *
     * @param sysAppParam 删除参数
     * @author fengshuonan
     * @date 2020/3/25 14:57
     */
    void del(SysAppRequest sysAppParam);

    /**
     * 编辑系统应用
     *
     * @param sysAppParam 编辑参数
     * @author fengshuonan
     * @date 2020/3/25 14:58
     */
    void edit(SysAppRequest sysAppParam);

    /**
     * 更新状态
     *
     * @param sysAppParam 请求参数
     * @author fengshuonan
     * @date 2021/1/6 14:30
     */
    void editStatus(SysAppRequest sysAppParam);

    /**
     * 查看系统应用
     *
     * @param sysAppParam 查看参数
     * @return 系统应用
     * @author fengshuonan
     * @date 2020/3/26 9:50
     */
    SysApp detail(SysAppRequest sysAppParam);

    /**
     * 系统应用列表
     *
     * @param sysAppParam 查询参数
     * @return 系统应用列表
     * @author fengshuonan
     * @date 2020/4/19 14:56
     */
    List<SysApp> findList(SysAppRequest sysAppParam);

    /**
     * 查询系统应用
     *
     * @param sysAppParam 查询参数
     * @return 查询分页结果
     * @author fengshuonan
     * @date 2020/3/24 20:55
     */
    PageResult<SysApp> findPage(SysAppRequest sysAppParam);

    /**
     * 将应用设为默认应用，用户进入系统会默认进这个应用的菜单
     *
     * @param sysAppParam 设为默认应用参数
     * @author fengshuonan
     * @date 2020/6/29 16:49
     */
    void updateActiveFlag(SysAppRequest sysAppParam);

}
