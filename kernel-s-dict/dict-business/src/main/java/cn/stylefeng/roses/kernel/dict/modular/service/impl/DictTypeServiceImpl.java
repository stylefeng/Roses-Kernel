package cn.stylefeng.roses.kernel.dict.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.dict.modular.entity.DictType;
import cn.stylefeng.roses.kernel.dict.modular.mapper.DictTypeMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.api.exception.DictException;
import cn.stylefeng.roses.kernel.dict.api.exception.enums.DictExceptionEnum;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictTypeRequest;
import cn.stylefeng.roses.kernel.dict.modular.service.DictTypeService;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
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
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements DictTypeService {

    @Override
    public void addDictType(DictTypeRequest dictTypeRequest) {

        //判断有没有相同编码的字典
        LambdaQueryWrapper<DictType> codeWrapper = new LambdaQueryWrapper<>();
        codeWrapper.eq(DictType::getDictTypeCode, dictTypeRequest.getDictTypeCode());

        int dictTypes = this.baseMapper.selectCount(codeWrapper);
        if (dictTypes > 0) {
            String userTip = StrUtil.format(DictExceptionEnum.DICT_TYPE_CODE_REPEAT.getUserTip(), dictTypeRequest.getDictTypeCode());
            throw new DictException(DictExceptionEnum.DICT_TYPE_CODE_REPEAT, userTip);
        }

        // 模型转化
        DictType dictType = new DictType();
        BeanUtil.copyProperties(dictTypeRequest, dictType);

        // 设置初始状态
        dictType.setDictTypeStatus(StatusEnum.ENABLE.getCode());
        this.baseMapper.insert(dictType);
    }

    @Override
    public void updateDictType(DictTypeRequest dictTypeRequest) {

        // 获取字典类型是否存在
        DictType oldDictType = this.baseMapper.selectById(dictTypeRequest.getId());
        if (oldDictType == null) {
            String userTip = StrUtil.format(DictExceptionEnum.DICT_TYPE_NOT_EXISTED.getUserTip(), dictTypeRequest.getId());
            throw new DictException(DictExceptionEnum.DICT_TYPE_NOT_EXISTED, userTip);
        }

        // 判断有没有相同编码的字典
        LambdaQueryWrapper<DictType> codeWrapper = new LambdaQueryWrapper<>();
        codeWrapper
                .eq(DictType::getDictTypeCode, dictTypeRequest.getDictTypeCode())
                .and(i -> i.ne(DictType::getId, dictTypeRequest.getId()));

        int dictTypes = this.baseMapper.selectCount(codeWrapper);
        if (dictTypes > 0) {
            String userTip = StrUtil.format(DictExceptionEnum.DICT_TYPE_CODE_REPEAT.getUserTip(), dictTypeRequest.getDictTypeCode());
            throw new DictException(DictExceptionEnum.DICT_TYPE_CODE_REPEAT, userTip);
        }

        // 模型转化
        BeanUtil.copyProperties(dictTypeRequest, oldDictType);

        // 不能修改字典编码
        oldDictType.setDictTypeCode(null);

        this.updateById(oldDictType);
    }

    @Override
    public void updateDictTypeStatus(DictTypeRequest dictTypeRequest) {

        // 获取字典类型是否存在
        DictType oldDictType = this.baseMapper.selectById(dictTypeRequest.getId());
        if (oldDictType == null) {
            String userTip = StrUtil.format(DictExceptionEnum.DICT_TYPE_NOT_EXISTED.getUserTip(), dictTypeRequest.getId());
            throw new DictException(DictExceptionEnum.DICT_TYPE_NOT_EXISTED, userTip);
        }

        // 判断状态是否正确
        StatusEnum statusEnum = StatusEnum.codeToEnum(dictTypeRequest.getDictTypeStatus());
        if (statusEnum == null) {
            String userTip = StrUtil.format(DictExceptionEnum.WRONG_DICT_STATUS.getUserTip(), dictTypeRequest.getDictTypeStatus());
            throw new DictException(DictExceptionEnum.WRONG_DICT_STATUS, userTip);
        }

        // 修改状态
        oldDictType.setDictTypeStatus(dictTypeRequest.getDictTypeStatus());

        this.updateById(oldDictType);
    }

    @Override
    public void deleteDictType(DictTypeRequest dictTypeRequest) {
        this.baseMapper.deleteById(dictTypeRequest.getId());
    }

    @Override
    public List<DictType> getDictTypeList(DictTypeRequest dictTypeRequest) {
        return this.baseMapper.findList(null, dictTypeRequest);
    }

    @Override
    public PageResult<DictType> getDictTypePageList(Page<DictType> page, DictTypeRequest dictTypeRequest) {
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

        LambdaQueryWrapper<DictType> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(DictType::getDictTypeCode, dictTypeRequest.getDictTypeCode());

        if (ObjectUtil.isNotEmpty(dictTypeRequest.getId())) {
            wrapper.and(i -> i.ne(DictType::getId, dictTypeRequest.getId()));
        }

        Integer selectCount = this.baseMapper.selectCount(wrapper);

        return selectCount <= 0;
    }

}
