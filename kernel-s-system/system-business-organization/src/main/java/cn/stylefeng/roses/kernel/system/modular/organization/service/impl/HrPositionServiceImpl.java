package cn.stylefeng.roses.kernel.system.modular.organization.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.UserOrgServiceApi;
import cn.stylefeng.roses.kernel.system.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.exception.enums.PositionExceptionEnum;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.HrPosition;
import cn.stylefeng.roses.kernel.system.modular.organization.mapper.HrPositionMapper;
import cn.stylefeng.roses.kernel.system.modular.organization.service.HrPositionService;
import cn.stylefeng.roses.kernel.system.pojo.organization.HrPositionRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统职位表 服务实现类
 *
 * @author fengshuonan
 * @date 2020/11/04 11:07
 */
@Service
public class HrPositionServiceImpl extends ServiceImpl<HrPositionMapper, HrPosition> implements HrPositionService {

    @Resource
    private UserOrgServiceApi userOrgServiceApi;

    @Override
    public void add(HrPositionRequest hrPositionRequest) {
        HrPosition sysPosition = new HrPosition();
        BeanUtil.copyProperties(hrPositionRequest, sysPosition);

        // 设置状态为启用
        sysPosition.setStatusFlag(StatusEnum.ENABLE.getCode());

        this.save(sysPosition);
    }

    @Override
    public void edit(HrPositionRequest hrPositionRequest) {

        HrPosition sysPosition = this.querySysPosition(hrPositionRequest);
        BeanUtil.copyProperties(hrPositionRequest, sysPosition);

        // 不能修改状态，用修改状态接口修改状态
        sysPosition.setStatusFlag(null);

        this.updateById(sysPosition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(HrPositionRequest hrPositionRequest) {

        HrPosition sysPosition = this.querySysPosition(hrPositionRequest);

        // 该职位下是否有员工
        // 职位有绑定员工，不能删除
        Boolean userOrgFlag = userOrgServiceApi.getUserOrgFlag(null, sysPosition.getPositionId());
        if (userOrgFlag) {
            throw new SystemModularException(PositionExceptionEnum.CANT_DELETE_POSITION);
        }

        // 逻辑删除
        sysPosition.setDelFlag(YesOrNotEnum.Y.getCode());

        this.updateById(sysPosition);
    }

    @Override
    public void updateStatus(HrPositionRequest hrPositionRequest) {
        LambdaUpdateWrapper<HrPosition> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(HrPosition::getPositionId, hrPositionRequest.getPositionId());
        updateWrapper.set(HrPosition::getStatusFlag, hrPositionRequest.getStatusFlag());

        this.update(updateWrapper);
    }

    @Override
    public HrPosition detail(HrPositionRequest hrPositionRequest) {
        return this.querySysPosition(hrPositionRequest);
    }

    @Override
    public PageResult<HrPosition> page(HrPositionRequest hrPositionRequest) {
        LambdaQueryWrapper<HrPosition> wrapper = createWrapper(hrPositionRequest);

        Page<HrPosition> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<HrPosition> list(HrPositionRequest hrPositionRequest) {
        LambdaQueryWrapper<HrPosition> wrapper = createWrapper(hrPositionRequest);
        return this.list(wrapper);
    }

    @Override
    public List<String> getPositionNamesByPositionIds(List<Long> positionIds) {

        // 拼接查询条件
        LambdaQueryWrapper<HrPosition> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(HrPosition::getPositionId, positionIds);
        queryWrapper.select(HrPosition::getPositionName);

        // 查询结果
        List<HrPosition> sysPositions = this.list(queryWrapper);

        // 把name组装起来
        return sysPositions.stream().map(HrPosition::getPositionName).collect(Collectors.toList());
    }

    /**
     * 拼接查询条件
     *
     * @author fengshuonan
     * @date 2020/11/6 18:35
     */
    private LambdaQueryWrapper<HrPosition> createWrapper(HrPositionRequest hrPositionRequest) {
        LambdaQueryWrapper<HrPosition> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(hrPositionRequest)) {

            // 拼接职位名称条件
            if (ObjectUtil.isNotEmpty(hrPositionRequest.getPositionName())) {
                queryWrapper.like(HrPosition::getPositionName, hrPositionRequest.getPositionName());
            }

            // 拼接职位编码条件
            if (ObjectUtil.isNotEmpty(hrPositionRequest.getPositionCode())) {
                queryWrapper.eq(HrPosition::getPositionCode, hrPositionRequest.getPositionCode());
            }
        }

        // 查询未删除状态的
        queryWrapper.eq(HrPosition::getDelFlag, YesOrNotEnum.N.getCode());

        // 根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(HrPosition::getPositionSort);

        return queryWrapper;
    }

    /**
     * 获取系统职位表
     *
     * @author fengshuonan
     * @date 2020/11/18 22:59
     */
    private HrPosition querySysPosition(HrPositionRequest hrPositionRequest) {
        HrPosition sysposition = this.getById(hrPositionRequest.getPositionId());
        if (ObjectUtil.isEmpty(sysposition)) {
            String userTip = StrUtil.format(PositionExceptionEnum.CANT_FIND_POSITION.getUserTip(), sysposition.getPositionId());
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, userTip);
        }
        return sysposition;
    }
}