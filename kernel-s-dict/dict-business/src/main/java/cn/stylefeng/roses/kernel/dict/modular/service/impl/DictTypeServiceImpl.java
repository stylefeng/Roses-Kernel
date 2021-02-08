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
    public void add(DictTypeRequest dictTypeRequest) {

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
    @Transactional(rollbackFor = Exception.class)
    public void del(DictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        // 获取字典类型
        SysDictType oldSysDictType = querySysDictType(dictTypeRequest);

        // 字典类型删除
        oldSysDictType.setDelFlag(YesOrNotEnum.Y.getCode());
        this.baseMapper.updateById(oldSysDictType);

        // 逻辑删除所有改类型下的字典
        LambdaUpdateWrapper<SysDict> lambdaQueryWrapper = new LambdaUpdateWrapper<>();
        lambdaQueryWrapper.eq(SysDict::getDictTypeCode, oldSysDictType.getDictTypeCode());
        lambdaQueryWrapper.set(SysDict::getDelFlag, YesOrNotEnum.Y.getCode());
        dictService.update(lambdaQueryWrapper);
    }

    @Override
    public void edit(DictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        // 获取字典类型
        SysDictType oldSysDictType = querySysDictType(dictTypeRequest);

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
    public void editStatus(DictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        // 获取字典类型
        SysDictType oldSysDictType = querySysDictType(dictTypeRequest);

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
    public SysDictType detail(DictTypeRequest dictTypeRequest) {
        List<SysDictType> list = this.findList(dictTypeRequest);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<SysDictType> findList(DictTypeRequest dictTypeRequest) {
        LambdaQueryWrapper<SysDictType> queryWrapper = this.createWrapper(dictTypeRequest);
        return this.list(queryWrapper);
    }

    @Override
    public PageResult<SysDictType> findPage(DictTypeRequest dictTypeRequest) {
        LambdaQueryWrapper<SysDictType> queryWrapper = this.createWrapper(dictTypeRequest);
        Page<SysDictType> page = this.page(PageFactory.defaultPage(), queryWrapper);
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

    /**
     * 根据主键id获取对象
     *
     * @author chenjinlong
     * @date 2021/1/26 13:28
     */
    private SysDictType querySysDictType(DictTypeRequest dictTypeRequest) {
        SysDictType sysDictType = this.getById(dictTypeRequest.getDictTypeId());
        if (ObjectUtil.isEmpty(sysDictType)) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_NOT_EXISTED, dictTypeRequest.getDictTypeId());
        }
        return sysDictType;
    }

    /**
     * 实体构建queryWrapper
     *
     * @author fengshuonan
     * @date 2021/1/24 22:03
     */
    private LambdaQueryWrapper<SysDictType> createWrapper(DictTypeRequest translationRequest) {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();

        // 查询未删除的
        queryWrapper.eq(SysDictType::getDelFlag, YesOrNotEnum.N.getCode());

        if (ObjectUtil.isEmpty(translationRequest)) {
            return queryWrapper;
        }

        Long dictTypeId = translationRequest.getDictTypeId();
        String dictTypeCode = translationRequest.getDictTypeCode();
        String dictTypeName = translationRequest.getDictTypeName();

        queryWrapper.eq(ObjectUtil.isNotNull(dictTypeId), SysDictType::getDictTypeId, dictTypeId);
        queryWrapper.eq(ObjectUtil.isNotNull(dictTypeCode), SysDictType::getDictTypeCode, dictTypeCode);
        queryWrapper.like(ObjectUtil.isNotNull(dictTypeName), SysDictType::getDictTypeName, dictTypeName);

        return queryWrapper;
    }

}
