package cn.stylefeng.roses.kernel.system.modular.organization.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.HrPosition;
import cn.stylefeng.roses.kernel.system.pojo.organization.HrPositionRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 职位信息服务
 *
 * @author fengshuonan
 * @date 2020/11/04 11:07
 */
public interface HrPositionService extends IService<HrPosition> {

    /**
     * 添加职位
     *
     * @param hrPositionRequest 请求参数
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    void add(HrPositionRequest hrPositionRequest);

    /**
     * 编辑职位
     *
     * @param hrPositionRequest 请求参数
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    void edit(HrPositionRequest hrPositionRequest);

    /**
     * 删除职位
     *
     * @param hrPositionRequest 请求参数
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    void delete(HrPositionRequest hrPositionRequest);

    /**
     * 更新装填
     *
     * @param hrPositionRequest 请求参数
     * @author fengshuonan
     * @date 2020/11/18 23:00
     */
    void updateStatus(HrPositionRequest hrPositionRequest);

    /**
     * 查看详情职位
     *
     * @param hrPositionRequest 请求参数
     * @return 职位详情
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    HrPosition detail(HrPositionRequest hrPositionRequest);

    /**
     * 分页查询职位
     *
     * @param hrPositionRequest 请求参数
     * @return 职位详情分页列表
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    PageResult<HrPosition> page(HrPositionRequest hrPositionRequest);

    /**
     * 查询所有职位
     *
     * @param hrPositionRequest 请求参数
     * @return 职位详情列表
     * @author fengshuonan
     * @date 2020/11/04 11:07
     */
    List<HrPosition> list(HrPositionRequest hrPositionRequest);

    /**
     * 通过职位id列表，获取对应的名称列表
     *
     * @param positionIds 职位id列表
     * @return 职位名称列表
     * @author fengshuonan
     * @date 2020/11/19 23:22
     */
    List<String> getPositionNamesByPositionIds(List<Long> positionIds);

}