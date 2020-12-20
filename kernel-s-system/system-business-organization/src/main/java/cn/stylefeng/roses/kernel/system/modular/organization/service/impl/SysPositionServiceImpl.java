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
import cn.stylefeng.roses.kernel.system.modular.organization.entity.SysPosition;
import cn.stylefeng.roses.kernel.system.modular.organization.mapper.SysPositionMapper;
import cn.stylefeng.roses.kernel.system.modular.organization.service.SysPositionService;
import cn.stylefeng.roses.kernel.system.pojo.organization.SysPositionRequest;
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
public class SysPositionServiceImpl extends ServiceImpl<SysPositionMapper, SysPosition> implements SysPositionService {

    @Resource
    private UserOrgServiceApi userOrgServiceApi;

    @Override
    public void add(SysPositionRequest sysPositionRequest) {
        SysPosition sysPosition = new SysPosition();
        BeanUtil.copyProperties(sysPositionRequest, sysPosition);

        // 设置状态为启用
        sysPosition.setStatusFlag(StatusEnum.ENABLE.getCode());

        this.save(sysPosition);
    }

    @Override
    public void edit(SysPositionRequest sysPositionRequest) {

        SysPosition sysPosition = this.querySysPosition(sysPositionRequest);
        BeanUtil.copyProperties(sysPositionRequest, sysPosition);

        // 不能修改状态，用修改状态接口修改状态
        sysPosition.setStatusFlag(null);

        this.updateById(sysPosition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(SysPositionRequest sysPositionRequest) {

        SysPosition sysPosition = this.querySysPosition(sysPositionRequest);

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
    public void updateStatus(SysPositionRequest sysPositionRequest) {
        LambdaUpdateWrapper<SysPosition> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysPosition::getPositionId, sysPositionRequest.getPositionId());
        updateWrapper.set(SysPosition::getStatusFlag, sysPositionRequest.getStatusFlag());

        this.update(updateWrapper);
    }

    @Override
    public SysPosition detail(SysPositionRequest sysPositionRequest) {
        return this.querySysPosition(sysPositionRequest);
    }

    @Override
    public PageResult<SysPosition> page(SysPositionRequest sysPositionRequest) {
        LambdaQueryWrapper<SysPosition> wrapper = createWrapper(sysPositionRequest);

        Page<SysPosition> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysPosition> list(SysPositionRequest sysPositionRequest) {
        LambdaQueryWrapper<SysPosition> wrapper = createWrapper(sysPositionRequest);
        return this.list(wrapper);
    }

    @Override
    public List<String> getPositionNamesByPositionIds(List<Long> positionIds) {

        // 拼接查询条件
        LambdaQueryWrapper<SysPosition> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysPosition::getPositionId, positionIds);
        queryWrapper.select(SysPosition::getPositionName);

        // 查询结果
        List<SysPosition> sysPositions = this.list(queryWrapper);

        // 把name组装起来
        return sysPositions.stream().map(SysPosition::getPositionName).collect(Collectors.toList());
    }

    /**
     * 拼接查询条件
     *
     * @author fengshuonan
     * @date 2020/11/6 18:35
     */
    private LambdaQueryWrapper<SysPosition> createWrapper(SysPositionRequest sysPositionRequest) {
        LambdaQueryWrapper<SysPosition> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysPositionRequest)) {

            // 拼接职位名称条件
            if (ObjectUtil.isNotEmpty(sysPositionRequest.getPositionName())) {
                queryWrapper.like(SysPosition::getPositionName, sysPositionRequest.getPositionName());
            }

            // 拼接职位编码条件
            if (ObjectUtil.isNotEmpty(sysPositionRequest.getPositionCode())) {
                queryWrapper.eq(SysPosition::getPositionCode, sysPositionRequest.getPositionCode());
            }
        }

        // 查询未删除状态的
        queryWrapper.eq(SysPosition::getDelFlag, YesOrNotEnum.N.getCode());

        // 根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(SysPosition::getPositionSort);

        return queryWrapper;
    }

    /**
     * 获取系统职位表
     *
     * @author fengshuonan
     * @date 2020/11/18 22:59
     */
    private SysPosition querySysPosition(SysPositionRequest sysPositionRequest) {
        SysPosition sysposition = this.getById(sysPositionRequest.getPositionId());
        if (ObjectUtil.isEmpty(sysposition)) {
            String userTip = StrUtil.format(PositionExceptionEnum.CANT_FIND_POSITION.getUserTip(), sysposition.getPositionId());
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, userTip);
        }
        return sysposition;
    }
}