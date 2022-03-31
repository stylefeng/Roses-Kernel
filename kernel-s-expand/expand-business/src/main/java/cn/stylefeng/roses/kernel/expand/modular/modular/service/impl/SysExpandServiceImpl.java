package cn.stylefeng.roses.kernel.expand.modular.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.expand.modular.modular.entity.SysExpand;
import cn.stylefeng.roses.kernel.expand.modular.modular.entity.SysExpandData;
import cn.stylefeng.roses.kernel.expand.modular.modular.entity.SysExpandField;
import cn.stylefeng.roses.kernel.expand.modular.modular.enums.SysExpandExceptionEnum;
import cn.stylefeng.roses.kernel.expand.modular.modular.mapper.SysExpandMapper;
import cn.stylefeng.roses.kernel.expand.modular.modular.pojo.request.SysExpandFieldRequest;
import cn.stylefeng.roses.kernel.expand.modular.modular.pojo.request.SysExpandRequest;
import cn.stylefeng.roses.kernel.expand.modular.modular.service.SysExpandDataService;
import cn.stylefeng.roses.kernel.expand.modular.modular.service.SysExpandFieldService;
import cn.stylefeng.roses.kernel.expand.modular.modular.service.SysExpandService;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务拓展业务实现层
 *
 * @author fengshuonan
 * @date 2022/03/29 23:47
 */
@Service
public class SysExpandServiceImpl extends ServiceImpl<SysExpandMapper, SysExpand> implements SysExpandService {

    @Resource
    private SysExpandFieldService sysExpandFieldService;

    @Resource
    private SysExpandDataService sysExpandDataService;

    @Override
    public void add(SysExpandRequest sysExpandRequest) {
        SysExpand sysExpand = new SysExpand();
        BeanUtil.copyProperties(sysExpandRequest, sysExpand);

        // 设置启用状态
        sysExpand.setExpandStatus(StatusEnum.ENABLE.getCode());

        this.save(sysExpand);
    }

    @Override
    public void del(SysExpandRequest sysExpandRequest) {
        SysExpand sysExpand = this.querySysExpand(sysExpandRequest);
        this.removeById(sysExpand.getExpandId());
    }

    @Override
    public void edit(SysExpandRequest sysExpandRequest) {
        SysExpand sysExpand = this.querySysExpand(sysExpandRequest);
        BeanUtil.copyProperties(sysExpandRequest, sysExpand);
        this.updateById(sysExpand);
    }

    @Override
    public SysExpand detail(SysExpandRequest sysExpandRequest) {
        return this.querySysExpand(sysExpandRequest);
    }

    @Override
    public PageResult<SysExpand> findPage(SysExpandRequest sysExpandRequest) {
        LambdaQueryWrapper<SysExpand> wrapper = createWrapper(sysExpandRequest);
        Page<SysExpand> sysRolePage = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(sysRolePage);
    }

    @Override
    public void updateStatus(SysExpandRequest sysExpandRequest) {
        SysExpand sysExpand = this.querySysExpand(sysExpandRequest);
        sysExpand.setExpandStatus(sysExpandRequest.getExpandStatus());
        this.updateById(sysExpand);
    }

    @Override
    public SysExpandData getByExpandCode(SysExpandRequest sysExpandRequest) {
        // 根据编码获取拓展信息
        LambdaQueryWrapper<SysExpand> sysExpandLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysExpandLambdaQueryWrapper.eq(SysExpand::getExpandCode, sysExpandRequest.getExpandCode());
        SysExpand sysExpand = this.getOne(sysExpandLambdaQueryWrapper, false);
        if (sysExpand == null) {
            throw new ServiceException(SysExpandExceptionEnum.SYS_EXPAND_NOT_EXISTED);
        }

        // 获取拓展业务的字段信息
        SysExpandFieldRequest sysExpandFieldRequest = new SysExpandFieldRequest();
        sysExpandFieldRequest.setExpandId(sysExpand.getExpandId());
        List<SysExpandField> list = sysExpandFieldService.findList(sysExpandFieldRequest);

        // 如果传了主键id，则查询一下业务表单的数据
        SysExpandData sysExpandData = new SysExpandData();
        if (StrUtil.isNotBlank(sysExpandRequest.getPrimaryFieldValue())) {
            sysExpandData = sysExpandDataService.detailByPrimaryFieldValue(sysExpandRequest.getPrimaryFieldValue());
            if(sysExpandData == null){
                sysExpandData = new SysExpandData();
            }
        }

        //  设置返回信息
        sysExpandData.setFieldInfoList(list);
        sysExpandData.setExpandInfo(sysExpand);

        return sysExpandData;
    }

    @Override
    public List<SysExpand> findList(SysExpandRequest sysExpandRequest) {
        LambdaQueryWrapper<SysExpand> wrapper = this.createWrapper(sysExpandRequest);
        wrapper.select(SysExpand::getExpandId, SysExpand::getExpandName, SysExpand::getExpandCode);
        wrapper.eq(SysExpand::getExpandStatus, StatusEnum.ENABLE.getCode());
        return this.list(wrapper);
    }

    /**
     * 获取信息
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    private SysExpand querySysExpand(SysExpandRequest sysExpandRequest) {
        SysExpand sysExpand = this.getById(sysExpandRequest.getExpandId());
        if (ObjectUtil.isEmpty(sysExpand)) {
            throw new ServiceException(SysExpandExceptionEnum.SYS_EXPAND_NOT_EXISTED);
        }
        return sysExpand;
    }

    /**
     * 创建查询wrapper
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    private LambdaQueryWrapper<SysExpand> createWrapper(SysExpandRequest sysExpandRequest) {
        LambdaQueryWrapper<SysExpand> queryWrapper = new LambdaQueryWrapper<>();

        Long expandId = sysExpandRequest.getExpandId();
        String expandName = sysExpandRequest.getExpandName();
        String expandCode = sysExpandRequest.getExpandCode();

        queryWrapper.eq(ObjectUtil.isNotNull(expandId), SysExpand::getExpandId, expandId);
        queryWrapper.like(ObjectUtil.isNotEmpty(expandName), SysExpand::getExpandName, expandName);
        queryWrapper.like(ObjectUtil.isNotEmpty(expandCode), SysExpand::getExpandCode, expandCode);

        return queryWrapper;
    }

}
