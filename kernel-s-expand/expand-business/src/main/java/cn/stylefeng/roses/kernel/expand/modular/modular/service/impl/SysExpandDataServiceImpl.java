package cn.stylefeng.roses.kernel.expand.modular.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.expand.modular.modular.entity.SysExpandData;
import cn.stylefeng.roses.kernel.expand.modular.modular.enums.SysExpandDataExceptionEnum;
import cn.stylefeng.roses.kernel.expand.modular.modular.mapper.SysExpandDataMapper;
import cn.stylefeng.roses.kernel.expand.modular.modular.pojo.request.SysExpandDataRequest;
import cn.stylefeng.roses.kernel.expand.modular.modular.service.SysExpandDataService;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务拓展-具体数据业务实现层
 *
 * @author fengshuonan
 * @date 2022/03/29 23:47
 */
@Service
public class SysExpandDataServiceImpl extends ServiceImpl<SysExpandDataMapper, SysExpandData> implements SysExpandDataService {

	@Override
    public void add(SysExpandDataRequest sysExpandDataRequest) {
        SysExpandData sysExpandData = new SysExpandData();
        BeanUtil.copyProperties(sysExpandDataRequest, sysExpandData);
        this.save(sysExpandData);
    }

    @Override
    public void del(SysExpandDataRequest sysExpandDataRequest) {
        SysExpandData sysExpandData = this.querySysExpandData(sysExpandDataRequest);
        this.removeById(sysExpandData.getExpandDataId());
    }

    @Override
    public void edit(SysExpandDataRequest sysExpandDataRequest) {
        SysExpandData sysExpandData = this.querySysExpandData(sysExpandDataRequest);
        BeanUtil.copyProperties(sysExpandDataRequest, sysExpandData);
        this.updateById(sysExpandData);
    }

    @Override
    public SysExpandData detail(SysExpandDataRequest sysExpandDataRequest) {
        return this.querySysExpandData(sysExpandDataRequest);
    }

    @Override
    public PageResult<SysExpandData> findPage(SysExpandDataRequest sysExpandDataRequest) {
        LambdaQueryWrapper<SysExpandData> wrapper = createWrapper(sysExpandDataRequest);
        Page<SysExpandData> sysRolePage = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(sysRolePage);
    }

    @Override
    public List<SysExpandData> findList(SysExpandDataRequest sysExpandDataRequest) {
        LambdaQueryWrapper<SysExpandData> wrapper = this.createWrapper(sysExpandDataRequest);
        return this.list(wrapper);
    }

    /**
     * 获取信息
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    private SysExpandData querySysExpandData(SysExpandDataRequest sysExpandDataRequest) {
        SysExpandData sysExpandData = this.getById(sysExpandDataRequest.getExpandDataId());
        if (ObjectUtil.isEmpty(sysExpandData)) {
            throw new ServiceException(SysExpandDataExceptionEnum.SYS_EXPAND_DATA_NOT_EXISTED);
        }
        return sysExpandData;
    }

    /**
     * 创建查询wrapper
     *
     * @author fengshuonan
     * @date 2022/03/29 23:47
     */
    private LambdaQueryWrapper<SysExpandData> createWrapper(SysExpandDataRequest sysExpandDataRequest) {
        LambdaQueryWrapper<SysExpandData> queryWrapper = new LambdaQueryWrapper<>();

        Long expandDataId = sysExpandDataRequest.getExpandDataId();
        Long expandId = sysExpandDataRequest.getExpandId();
        Long primaryFieldValue = sysExpandDataRequest.getPrimaryFieldValue();
        String expandData = sysExpandDataRequest.getExpandData();

        queryWrapper.eq(ObjectUtil.isNotNull(expandDataId), SysExpandData::getExpandDataId, expandDataId);
        queryWrapper.eq(ObjectUtil.isNotNull(expandId), SysExpandData::getExpandId, expandId);
        queryWrapper.eq(ObjectUtil.isNotNull(primaryFieldValue), SysExpandData::getPrimaryFieldValue, primaryFieldValue);
        queryWrapper.like(ObjectUtil.isNotEmpty(expandData), SysExpandData::getExpandData, expandData);

        return queryWrapper;
    }

}
