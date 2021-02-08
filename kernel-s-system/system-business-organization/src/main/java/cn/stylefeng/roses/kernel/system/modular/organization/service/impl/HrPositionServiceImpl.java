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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    public void del(HrPositionRequest hrPositionRequest) {
        HrPosition sysPosition = this.querySysPositionById(hrPositionRequest);

        // 该职位下是否有员工，如果有将不能删除
        Boolean userOrgFlag = userOrgServiceApi.getUserOrgFlag(null, sysPosition.getPositionId());
        if (userOrgFlag) {
            throw new SystemModularException(PositionExceptionEnum.CANT_DELETE_POSITION);
        }

        // 逻辑删除
        sysPosition.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(sysPosition);
    }

    @Override
    public void edit(HrPositionRequest hrPositionRequest) {
        HrPosition sysPosition = this.querySysPositionById(hrPositionRequest);
        BeanUtil.copyProperties(hrPositionRequest, sysPosition);
        this.updateById(sysPosition);
    }

    @Override
    public void updateStatus(HrPositionRequest hrPositionRequest) {
        HrPosition sysPosition = this.querySysPositionById(hrPositionRequest);
        sysPosition.setStatusFlag(hrPositionRequest.getStatusFlag());
        this.updateById(sysPosition);
    }

    @Override
    public HrPosition detail(HrPositionRequest hrPositionRequest) {
        return this.querySysPositionById(hrPositionRequest);
    }

    @Override
    public List<HrPosition> findList(HrPositionRequest hrPositionRequest) {
        LambdaQueryWrapper<HrPosition> wrapper = this.createWrapper(hrPositionRequest);
        return this.list(wrapper);
    }

    @Override
    public PageResult<HrPosition> findPage(HrPositionRequest hrPositionRequest) {
        LambdaQueryWrapper<HrPosition> wrapper = this.createWrapper(hrPositionRequest);

        Page<HrPosition> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    /**
     * 根据主键id获取对象信息
     *
     * @return 实体对象
     * @author chenjinlong
     * @date 2021/2/2 10:16
     */
    private HrPosition querySysPositionById(HrPositionRequest hrPositionRequest) {
        HrPosition hrPosition = this.getById(hrPositionRequest.getPositionId());
        if (ObjectUtil.isEmpty(hrPosition)) {
            throw new SystemModularException(PositionExceptionEnum.CANT_FIND_POSITION, hrPositionRequest.getPositionId());
        }
        return hrPosition;
    }


    /**
     * 实体构建 QueryWrapper
     *
     * @author chenjinlong
     * @date 2021/2/2 10:17
     */
    private LambdaQueryWrapper<HrPosition> createWrapper(HrPositionRequest hrPositionRequest) {
        LambdaQueryWrapper<HrPosition> queryWrapper = new LambdaQueryWrapper<>();

        // 查询未删除状态的
        queryWrapper.eq(HrPosition::getDelFlag, YesOrNotEnum.N.getCode());

        // 根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(HrPosition::getPositionSort);

        if (ObjectUtil.isEmpty(hrPositionRequest)) {
            return queryWrapper;
        }

        Long positionId = hrPositionRequest.getPositionId();
        String positionName = hrPositionRequest.getPositionName();
        String positionCode = hrPositionRequest.getPositionCode();

        // SQL条件拼接
        queryWrapper.eq(ObjectUtil.isNotNull(positionId), HrPosition::getPositionId, positionId);
        queryWrapper.like(StrUtil.isNotEmpty(positionName), HrPosition::getPositionName, positionName);
        queryWrapper.eq(StrUtil.isNotEmpty(positionCode), HrPosition::getPositionCode, positionCode);

        return queryWrapper;
    }

}
