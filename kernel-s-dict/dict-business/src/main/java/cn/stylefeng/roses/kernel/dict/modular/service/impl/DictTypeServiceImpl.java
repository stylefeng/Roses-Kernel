package cn.stylefeng.roses.kernel.dict.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.api.exception.DictException;
import cn.stylefeng.roses.kernel.dict.api.exception.enums.DictExceptionEnum;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDictType;
import cn.stylefeng.roses.kernel.dict.modular.mapper.DictTypeMapper;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictTypeRequest;
import cn.stylefeng.roses.kernel.dict.modular.service.DictTypeService;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 字典类型表 服务实现类
 *
 * @author majianguo
 * @version 1.0
 * @date 2020/10/28 上午9:58
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, SysDictType> implements DictTypeService {

    @Override
    public void addDictType(DictTypeRequest dictTypeRequest) {

        // 模型转化
        SysDictType sysDictType = new SysDictType();
        BeanUtil.copyProperties(dictTypeRequest, sysDictType);

        // 设置初始状态
        sysDictType.setStatusFlag(StatusEnum.ENABLE.getCode());

        this.baseMapper.insert(sysDictType);
    }

    @Override
    public void updateDictType(DictTypeRequest dictTypeRequest) {

        // 获取字典类型是否存在
        SysDictType oldSysDictType = this.baseMapper.selectById(dictTypeRequest.getDictTypeId());
        if (oldSysDictType == null) {
            String userTip = StrUtil.format(DictExceptionEnum.DICT_TYPE_NOT_EXISTED.getUserTip(), dictTypeRequest.getDictTypeId());
            throw new DictException(DictExceptionEnum.DICT_TYPE_NOT_EXISTED, userTip);
        }

        // 模型转化
        BeanUtil.copyProperties(dictTypeRequest, oldSysDictType);

        // 不能修改字典编码
        oldSysDictType.setDictTypeCode(null);

        this.updateById(oldSysDictType);
    }

    @Override
    public void updateDictTypeStatus(DictTypeRequest dictTypeRequest) {

        // 获取字典类型是否存在
        SysDictType oldSysDictType = this.baseMapper.selectById(dictTypeRequest.getDictTypeId());
        if (oldSysDictType == null) {
            String userTip = StrUtil.format(DictExceptionEnum.DICT_TYPE_NOT_EXISTED.getUserTip(), dictTypeRequest.getDictTypeId());
            throw new DictException(DictExceptionEnum.DICT_TYPE_NOT_EXISTED, userTip);
        }

        // 判断状态是否正确
        StatusEnum statusEnum = StatusEnum.codeToEnum(dictTypeRequest.getStatusFlag());
        if (statusEnum == null) {
            String userTip = StrUtil.format(DictExceptionEnum.WRONG_DICT_STATUS.getUserTip(), dictTypeRequest.getStatusFlag());
            throw new DictException(DictExceptionEnum.WRONG_DICT_STATUS, userTip);
        }

        // 修改状态
        oldSysDictType.setStatusFlag(dictTypeRequest.getStatusFlag());

        this.updateById(oldSysDictType);
    }

    @Override
    public void deleteDictType(DictTypeRequest dictTypeRequest) {
        LambdaUpdateWrapper<SysDictType> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysDictType::getDelFlag, YesOrNotEnum.Y.getCode());
        updateWrapper.eq(SysDictType::getDictTypeId, dictTypeRequest.getDictTypeId());
        this.update(updateWrapper);
    }

    @Override
    public List<SysDictType> getDictTypeList(DictTypeRequest dictTypeRequest) {
        return this.baseMapper.findList(null, dictTypeRequest);
    }

    @Override
    public PageResult<SysDictType> getDictTypePageList(Page<SysDictType> page, DictTypeRequest dictTypeRequest) {
        if (page == null) {
            page = PageFactory.defaultPage();
        }

        if (dictTypeRequest == null) {
            dictTypeRequest = new DictTypeRequest();
        }

        this.baseMapper.findList(page, dictTypeRequest);

        return PageResultFactory.createPageResult(page);
    }

    @Override
    public boolean validateCodeAvailable(DictTypeRequest dictTypeRequest) {

        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(SysDictType::getDictTypeCode, dictTypeRequest.getDictTypeCode());

        if (ObjectUtil.isNotEmpty(dictTypeRequest.getDictTypeId())) {
            wrapper.and(i -> i.ne(SysDictType::getDictTypeId, dictTypeRequest.getDictTypeId()));
        }

        Integer selectCount = this.baseMapper.selectCount(wrapper);

        return selectCount <= 0;
    }

}
