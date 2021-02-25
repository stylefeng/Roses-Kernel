package cn.stylefeng.roses.kernel.system.modular.loginlog.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.modular.loginlog.entity.SysLoginLog;
import cn.stylefeng.roses.kernel.system.api.pojo.loginlog.SysLoginLogRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 登录日志service接口
 *
 * @author chenjinlong
 * @date 2021/1/13 10:56
 */
public interface SysLoginLogService extends IService<SysLoginLog> {

    /**
     * 删除
     *
     * @param sysLoginLogRequest 参数
     * @author chenjinlong
     * @date 2021/1/13 10:55
     */
    void del(SysLoginLogRequest sysLoginLogRequest);

    /**
     * 清空登录日志
     *
     * @author chenjinlong
     * @date 2021/1/13 10:55
     */
    void delAll();

    /**
     * 查看相信
     *
     * @param sysLoginLogRequest 参数
     * @author chenjinlong
     * @date 2021/1/13 10:56
     */
    SysLoginLog detail(SysLoginLogRequest sysLoginLogRequest);

    /**
     * 分页查询
     *
     * @param sysLoginLogRequest 参数
     * @author chenjinlong
     * @date 2021/1/13 10:57
     */
    PageResult<SysLoginLog> findPage(SysLoginLogRequest sysLoginLogRequest);

}
