package cn.stylefeng.roses.kernel.dict.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.dict.modular.mapper.DictMapper;
import cn.stylefeng.roses.kernel.dict.modular.pojo.TreeDictInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.api.exception.DictException;
import cn.stylefeng.roses.kernel.dict.api.exception.enums.DictExceptionEnum;
import cn.stylefeng.roses.kernel.dict.modular.entity.Dict;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictRequest;
import cn.stylefeng.roses.kernel.dict.modular.service.DictService;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.factory.DefaultTreeBuildFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static cn.stylefeng.roses.kernel.dict.api.constants.DictConstants.DEFAULT_DICT_PARENT_ID;
import static cn.stylefeng.roses.kernel.dict.api.exception.enums.DictExceptionEnum.WRONG_DICT_STATUS;

/**
 * 基础字典 服务实现类
 *
 * @author majianguo
 * @version 1.0
 * @date 2020/10/28 上午9:48
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public void addDict(DictRequest dictRequest) {

        // 判断某个字典类型下，该编码是否重复
        LambdaQueryWrapper<Dict> codeRepeatWrapper = new LambdaQueryWrapper<>();
        codeRepeatWrapper
                .eq(Dict::getDictTypeCode, dictRequest.getDictTypeCode())
                .and(i -> i.eq(Dict::getDictCode, dictRequest.getDictTypeCode()));

        // 如果重复，抛出异常
        int codeCount = this.baseMapper.selectCount(codeRepeatWrapper);
        if (codeCount > 0) {
            String userTip = StrUtil.format(DictExceptionEnum.DICT_CODE_REPEAT.getUserTip(), dictRequest.getDictTypeCode(), dictRequest.getDictCode());
            throw new DictException(DictExceptionEnum.DICT_CODE_REPEAT, userTip);
        }

        // 判断某个字典类型下，字典的名称是否重复
        LambdaQueryWrapper<Dict> nameRepeatWrapper = new LambdaQueryWrapper<>();
        nameRepeatWrapper
                .eq(Dict::getDictTypeCode, dictRequest.getDictTypeCode())
                .and(i -> i.eq(Dict::getDictName, dictRequest.getDictName()));

        // 如果重复，抛出异常
        int nameCount = this.baseMapper.selectCount(nameRepeatWrapper);
        if (nameCount > 0) {
            String userTip = StrUtil.format(DictExceptionEnum.DICT_NAME_REPEAT.getUserTip(), dictRequest.getDictTypeCode(), dictRequest.getDictName());
            throw new DictException(DictExceptionEnum.DICT_NAME_REPEAT, userTip);
        }

        // 如果父节点为空，则填充为默认的父节点id
        if (dictRequest.getParentDictId() == null) {
            dictRequest.setParentDictId(DEFAULT_DICT_PARENT_ID);
        }

        // 如果父节点不为空，并且不是-1，则判断父节点存不存在，防止脏数据
        else {
            if (!DEFAULT_DICT_PARENT_ID.equals(dictRequest.getParentDictId())) {
                Dict parentDict = this.getById(dictRequest.getParentDictId());
                if (parentDict == null) {
                    String userTip = StrUtil.format(DictExceptionEnum.PARENT_DICT_NOT_EXISTED.getUserTip(), dictRequest.getParentDictId());
                    throw new DictException(DictExceptionEnum.PARENT_DICT_NOT_EXISTED, userTip);
                }
            }
        }

        // dto转化为实体
        Dict dict = new Dict();
        BeanUtil.copyProperties(dictRequest, new Dict());

        // dictId是自动生成
        dict.setId(null);

        // 设置状态启用
        dict.setDictStatus(StatusEnum.ENABLE.getCode());

        this.baseMapper.insert(dict);
    }

    @Override
    public void updateDict(DictRequest dictRequest) {

        // 查询字典是否存在
        Dict oldDict = this.getById(dictRequest.getId());
        if (oldDict == null) {
            String userTip = StrUtil.format(DictExceptionEnum.DICT_NOT_EXISTED.getUserTip(), dictRequest.getId());
            throw new DictException(DictExceptionEnum.DICT_NOT_EXISTED, userTip);
        }

        // 判断某个字典类型下，该编码是否重复
        LambdaQueryWrapper<Dict> codeRepeatWrapper = new LambdaQueryWrapper<>();
        codeRepeatWrapper
                .eq(Dict::getDictTypeCode, dictRequest.getDictTypeCode())
                .and(i -> i.eq(Dict::getDictCode, dictRequest.getDictTypeCode()))
                .and(i -> i.ne(Dict::getId, dictRequest.getId()));

        // 如果重复，抛出异常
        int codeCount = this.baseMapper.selectCount(codeRepeatWrapper);
        if (codeCount > 0) {
            String userTip = StrUtil.format(DictExceptionEnum.DICT_CODE_REPEAT.getUserTip(), dictRequest.getDictTypeCode(), dictRequest.getDictCode());
            throw new DictException(DictExceptionEnum.DICT_CODE_REPEAT, userTip);
        }

        // 判断某个字典类型下，字典的名称是否重复
        LambdaQueryWrapper<Dict> nameRepeatWrapper = new LambdaQueryWrapper<>();
        nameRepeatWrapper
                .eq(Dict::getDictTypeCode, dictRequest.getDictTypeCode())
                .and(i -> i.eq(Dict::getDictName, dictRequest.getDictName()))
                .and(i -> i.ne(Dict::getId, dictRequest.getId()));

        // 如果重复，抛出异常
        int nameCount = this.baseMapper.selectCount(nameRepeatWrapper);
        if (nameCount > 0) {
            String userTip = StrUtil.format(DictExceptionEnum.DICT_NAME_REPEAT.getUserTip(), dictRequest.getDictTypeCode(), dictRequest.getDictName());
            throw new DictException(DictExceptionEnum.DICT_NAME_REPEAT, userTip);
        }

        // 不能修改字典类型和编码
        dictRequest.setDictTypeCode(null);
        dictRequest.setDictCode(null);

        // model转化为entity
        BeanUtil.copyProperties(dictRequest, oldDict, CopyOptions.create().setIgnoreNullValue(true));

        this.updateById(oldDict);
    }

    @Override
    public void updateDictStatus(DictRequest dictRequest) {

        // 检查参数状态是否合法
        if (StatusEnum.codeToEnum(dictRequest.getDictStatus()) == null) {
            String userTip = StrUtil.format(WRONG_DICT_STATUS.getUserTip(), dictRequest.getDictStatus());
            throw new DictException(WRONG_DICT_STATUS, userTip);
        }

        // 查询对应的字典信息
        Dict dict = this.baseMapper.selectById(dictRequest.getId());
        if (dict == null) {
            String userTip = StrUtil.format(DictExceptionEnum.DICT_NOT_EXISTED.getUserTip(), dictRequest.getId());
            throw new DictException(DictExceptionEnum.DICT_NOT_EXISTED, userTip);
        }

        // 修改状态
        dict.setDictStatus(dictRequest.getDictStatus());
        this.updateById(dict);
    }

    @Override
    public void deleteDict(DictRequest dictRequest) {
        this.baseMapper.deleteById(dictRequest.getId());
    }

    @Override
    public Dict findDetail(DictRequest dictRequest) {
        return this.baseMapper.findDetail(dictRequest);
    }

    @Override
    public List<Dict> findList(DictRequest dictRequest) {
        if (dictRequest == null) {
            dictRequest = new DictRequest();
        }

        return baseMapper.findList(null, dictRequest);
    }

    @Override
    public PageResult<Dict> findPageList(DictRequest dictRequest) {
        if (dictRequest == null) {
            dictRequest = new DictRequest();
        }

        Page<Dict> page = PageFactory.defaultPage();
        baseMapper.findList(page, dictRequest);

        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<TreeDictInfo> getTreeDictList(DictRequest dictRequest) {

        // 获取字典类型下所有的字典
        List<Dict> dictList = this.findList(dictRequest);
        if (dictList == null || dictList.isEmpty()) {
            return new ArrayList<>();
        }

        // 构造树节点信息
        ArrayList<TreeDictInfo> treeDictInfos = new ArrayList<>();
        for (Dict dict : dictList) {
            TreeDictInfo treeDictInfo = new TreeDictInfo();
            treeDictInfo.setDictId(dict.getId());
            treeDictInfo.setDictCode(dict.getDictCode());
            treeDictInfo.setParentDictId(dict.getParentDictId());
            treeDictInfo.setDictName(dict.getDictName());
            treeDictInfos.add(treeDictInfo);
        }

        // 构建菜单树
        return new DefaultTreeBuildFactory<TreeDictInfo>().doTreeBuild(treeDictInfos);
    }

    @Override
    public boolean validateCodeAvailable(DictRequest dictRequest) {

        // 判断编码是否重复
        LambdaQueryWrapper<Dict> codeRepeatWrapper = new LambdaQueryWrapper<>();
        codeRepeatWrapper
                .eq(Dict::getDictTypeCode, dictRequest.getDictTypeCode())
                .and(i -> i.eq(Dict::getDictCode, dictRequest.getDictCode()));

        // 如果传了字典id代表是编辑字典
        if (ObjectUtil.isNotEmpty(dictRequest.getId())) {
            codeRepeatWrapper.and(i -> i.ne(Dict::getId, dictRequest.getId()));
        }

        // 如果重复，抛出异常
        int codeCount = this.baseMapper.selectCount(codeRepeatWrapper);

        // 如果是小于等于0，则这个编码可以用
        return codeCount <= 0;
    }

}
