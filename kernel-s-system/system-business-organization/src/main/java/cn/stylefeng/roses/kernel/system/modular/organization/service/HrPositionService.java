package cn.stylefeng.roses.kernel.system.modular.organization.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.HrPosition;
import cn.stylefeng.roses.kernel.system.pojo.HrPositionRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 职位信息服务
 *
 * @author chenjinlong
 * @date 2020/11/04 11:07
 */
public interface HrPositionService extends IService<HrPosition> {

    /**
     * 添加职位
     *
     * @param hrPositionRequest 请求参数
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    void add(HrPositionRequest hrPositionRequest);

    /**
     * 删除职位
     *
     * @param hrPositionRequest 请求参数
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    void del(HrPositionRequest hrPositionRequest);

    /**
     * 编辑职位
     *
     * @param hrPositionRequest 请求参数
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    void edit(HrPositionRequest hrPositionRequest);

    /**
     * 更新装填
     *
     * @param hrPositionRequest 请求参数
     * @author chenjinlong
     * @date 2020/11/18 23:00
     */
    void updateStatus(HrPositionRequest hrPositionRequest);

    /**
     * 查看详情
     *
     * @param hrPositionRequest 请求参数
     * @return 职位详情
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    HrPosition detail(HrPositionRequest hrPositionRequest);

    /**
     * 查询职位详情列表
     *
     * @param hrPositionRequest 请求参数
     * @return 职位详情列表
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    List<HrPosition> findList(HrPositionRequest hrPositionRequest);

    /**
     * 分页查询职位详情列表
     *
     * @param hrPositionRequest 请求参数
     * @return 职位详情分页列表
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    PageResult<HrPosition> findPage(HrPositionRequest hrPositionRequest);

}