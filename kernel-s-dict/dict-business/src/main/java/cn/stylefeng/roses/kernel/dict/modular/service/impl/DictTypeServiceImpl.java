package cn.stylefeng.roses.kernel.dict.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.api.enums.DictTypeClassEnum;
import cn.stylefeng.roses.kernel.dict.api.exception.DictException;
import cn.stylefeng.roses.kernel.dict.api.exception.enums.DictExceptionEnum;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDict;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDictType;
import cn.stylefeng.roses.kernel.dict.modular.mapper.DictTypeMapper;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictTypeRequest;
import cn.stylefeng.roses.kernel.dict.modular.service.DictService;
import cn.stylefeng.roses.kernel.dict.modular.service.DictTypeService;
import cn.stylefeng.roses.kernel.pinyin.api.PinYinApi;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static cn.stylefeng.roses.kernel.dict.api.constants.DictConstants.CONFIG_GROUP_DICT_TYPE_CODE;

/**
 * 字典类型表 服务实现类
 *
 * @author fengshuonan
 * @date 2020/12/26 22:36
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, SysDictType> implements DictTypeService {

    @Resource
    private DictService dictService;

    @Resource
    private PinYinApi pinYinApi;

    @Override
    public void addDictType(DictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        // 模型转化
        SysDictType sysDictType = new SysDictType();
        BeanUtil.copyProperties(dictTypeRequest, sysDictType);

        // 设置初始状态
        sysDictType.setStatusFlag(StatusEnum.ENABLE.getCode());
        sysDictType.setDelFlag(YesOrNotEnum.N.getCode());

        // 设置首字母拼音
        sysDictType.setDictTypeNamePinyin(pinYinApi.parseEveryPinyinFirstLetter(sysDictType.getDictTypeName()));
        this.baseMapper.insert(sysDictType);
    }

    @Override
    public void updateDictType(DictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        // 获取字典类型是否存在
        SysDictType oldSysDictType = this.baseMapper.selectById(dictTypeRequest.getDictTypeId());
        if (oldSysDictType == null) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_NOT_EXISTED, dictTypeRequest.getDictTypeId());
        }

        // 模型转化
        BeanUtil.copyProperties(dictTypeRequest, oldSysDictType);

        // 不能修改字典编码
        oldSysDictType.setDictTypeCode(null);

        // 设置首字母拼音
        oldSysDictType.setDictTypeNamePinyin(pinYinApi.parseEveryPinyinFirstLetter(oldSysDictType.getDictTypeName()));
        this.updateById(oldSysDictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictTypeStatus(DictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        // 获取字典类型是否存在
        SysDictType oldSysDictType = this.baseMapper.selectById(dictTypeRequest.getDictTypeId());
        if (oldSysDictType == null) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_NOT_EXISTED, dictTypeRequest.getDictTypeId());
        }

        // 修改状态
        oldSysDictType.setStatusFlag(dictTypeRequest.getStatusFlag());

        // 修改所有本类型下的字典状态
        LambdaUpdateWrapper<SysDict> lambdaQueryWrapper = new LambdaUpdateWrapper<>();
        lambdaQueryWrapper.eq(SysDict::getDictTypeCode, oldSysDictType.getDictTypeCode());
        lambdaQueryWrapper.eq(SysDict::getDelFlag, YesOrNotEnum.N.getCode());
        lambdaQueryWrapper.set(SysDict::getStatusFlag, dictTypeRequest.getStatusFlag());
        dictService.update(lambdaQueryWrapper);

        // 更新字典类型
        this.updateById(oldSysDictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictType(DictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        // 获取字典类型是否存在
        SysDictType sysDictType = this.baseMapper.selectById(dictTypeRequest.getDictTypeId());
        if (sysDictType == null) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_NOT_EXISTED, dictTypeRequest.getDictTypeId());
        }

        // 字典类型删除
        sysDictType.setDelFlag(YesOrNotEnum.Y.getCode());
        this.baseMapper.updateById(sysDictType);

        // 逻辑删除所有改类型下的字典
        LambdaUpdateWrapper<SysDict> lambdaQueryWrapper = new LambdaUpdateWrapper<>();
        lambdaQueryWrapper.eq(SysDict::getDictTypeCode, sysDictType.getDictTypeCode());
        lambdaQueryWrapper.set(SysDict::getDelFlag, YesOrNotEnum.Y.getCode());
        dictService.update(lambdaQueryWrapper);
    }

    @Override
    public List<SysDictType> getDictTypeList(DictTypeRequest dictTypeRequest) {
        return this.baseMapper.findList(null, dictTypeRequest);
    }

    @Override
    public PageResult<SysDictType> getDictTypePageList(DictTypeRequest dictTypeRequest) {

        Page<SysDictType> page = PageFactory.defaultPage();

        if (dictTypeRequest == null) {
            dictTypeRequest = new DictTypeRequest();
        }

        List<SysDictType> list = this.baseMapper.findList(page, dictTypeRequest);

        return PageResultFactory.createPageResult(page.setRecords(list));
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

    @Override
    public SysDictType findDetail(Long dictTypeId) {
        return this.baseMapper.findDetail(dictTypeId);
    }

    @Override
    public SysDictType getConfigDictTypeDetail() {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictType::getDictTypeCode, CONFIG_GROUP_DICT_TYPE_CODE);
        return this.getOne(queryWrapper);
    }

    /**
     * 校验dictTypeClass是否是系统字典，如果是系统字典只能超级管理员操作
     *
     * @author fengshuonan
     * @date 2020/12/25 15:57
     */
    private void validateSystemTypeClassOperate(DictTypeRequest dictTypeRequest) {
        if (DictTypeClassEnum.SYSTEM_TYPE.getCode().equals(dictTypeRequest.getDictTypeClass())) {
            if (!LoginContext.me().getSuperAdminFlag()) {
                throw new DictException(DictExceptionEnum.SYSTEM_DICT_NOT_ALLOW_OPERATION);
            }
        }
    }

}
