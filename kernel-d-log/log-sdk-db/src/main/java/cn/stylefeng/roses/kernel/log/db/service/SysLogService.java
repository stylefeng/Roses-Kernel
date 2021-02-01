package cn.stylefeng.roses.kernel.log.db.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerRequest;
import cn.stylefeng.roses.kernel.log.db.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 日志记录 service接口
 *
 * @author luojie
 * @date 2020/11/2 17:44
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 新增
     *
     * @param logManagerRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void add(LogManagerRequest logManagerRequest);

    /**
     * 删除
     *
     * @param logManagerRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void del(LogManagerRequest logManagerRequest);

    /**
     * 删除所有数据
     *
     * @param logManagerRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    void delAll(LogManagerRequest logManagerRequest);

    /**
     * 查看日志详情
     *
     * @author TSQ
     * @date 2021/1/11 17:51
     */
    SysLog detail(LogManagerRequest logManagerParam);


    /**
     * 查询-列表-按实体对象
     *
     * @param logManagerParam 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    List<SysLog> findList(LogManagerRequest logManagerParam);

    /**
     * 查询-列表-分页-按实体对象
     *
     * @param logManagerParam 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    PageResult<SysLog> findPage(LogManagerRequest logManagerParam);

}
