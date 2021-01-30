package cn.stylefeng.roses.kernel.dict.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.api.exception.DictException;
import cn.stylefeng.roses.kernel.dict.api.exception.enums.DictExceptionEnum;
import cn.stylefeng.roses.kernel.dict.api.pojo.dict.request.ParentIdsUpdateRequest;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDict;
import cn.stylefeng.roses.kernel.dict.modular.mapper.DictMapper;
import cn.stylefeng.roses.kernel.dict.modular.pojo.TreeDictInfo;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictRequest;
import cn.stylefeng.roses.kernel.dict.modular.service.DictService;
import cn.stylefeng.roses.kernel.pinyin.api.PinYinApi;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.constants.TreeConstants;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.rule.factory.DefaultTreeBuildFactory;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.rule.pojo.ztree.ZTreeNode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cn.stylefeng.roses.kernel.dict.api.constants.DictConstants.DEFAULT_DICT_PARENT_ID;
import static cn.stylefeng.roses.kernel.dict.api.exception.enums.DictExceptionEnum.DICT_CODE_REPEAT;

/**
 * 基础字典 服务实现类
 *
 * @author fengshuonan
 * @date 2020/12/26 22:36
 */
@Service
@Slf4j
public class DictServiceImpl extends ServiceImpl<DictMapper, SysDict> implements DictService {

    @Resource
    private PinYinApi pinYinApi;

    @Resource
    private DictMapper dictMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDict(DictRequest dictRequest) {

        // 如果父节点为空，则填充为默认的父节点id
        if (dictRequest.getDictParentId() == null) {
            dictRequest.setDictParentId(DEFAULT_DICT_PARENT_ID);
        }

        // 如果父节点不为空，并且不是-1，则判断父节点存不存在，防止脏数据
        else {
            if (!DEFAULT_DICT_PARENT_ID.equals(dictRequest.getDictParentId())) {
                SysDict parentSysDict = this.getById(dictRequest.getDictParentId());
                if (parentSysDict == null) {
                    throw new DictException(DictExceptionEnum.PARENT_DICT_NOT_EXISTED, dictRequest.getDictParentId());
                }
            }
        }

        // 赋值pids
        setPids(dictRequest);

        // dto转化为实体
        SysDict sysDict = new SysDict();
        BeanUtil.copyProperties(dictRequest, sysDict);

        // dictId是自动生成
        sysDict.setDictId(null);

        // 设置状态启用
        sysDict.setStatusFlag(StatusEnum.ENABLE.getCode());
        sysDict.setDelFlag(YesOrNotEnum.N.getCode());

        // 设置拼音
        sysDict.setDictNamePinyin(pinYinApi.parseEveryPinyinFirstLetter(sysDict.getDictName()));

        this.baseMapper.insert(sysDict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDict(DictRequest dictRequest) {

        // 查询字典是否存在
        SysDict oldSysDict = this.getById(dictRequest.getDictId());
        if (oldSysDict == null) {
            throw new DictException(DictExceptionEnum.DICT_NOT_EXISTED, dictRequest.getDictId());
        }

        // 不能修改字典类型和编码
        dictRequest.setDictTypeCode(null);
        dictRequest.setDictCode(null);

        // 赋值pids
        setPids(dictRequest);
        if (!oldSysDict.getDictParentId().equals(dictRequest.getDictParentId())) {
            updatePids(dictRequest, oldSysDict);
        }

        // model转化为entity
        BeanUtil.copyProperties(dictRequest, oldSysDict, CopyOptions.create().setIgnoreNullValue(true));

        // 设置拼音
        oldSysDict.setDictNamePinyin(pinYinApi.parseEveryPinyinFirstLetter(oldSysDict.getDictName()));

        this.updateById(oldSysDict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictStatus(DictRequest dictRequest) {

        // 查询对应的字典信息
        SysDict sysDict = this.baseMapper.selectById(dictRequest.getDictId());
        if (sysDict == null) {
            throw new DictException(DictExceptionEnum.DICT_NOT_EXISTED, dictRequest.getDictId());
        }

        // 如果是禁用 禁用所有下级状态
        if (StatusEnum.DISABLE.getCode().equals(dictRequest.getStatusFlag())) {
            LambdaUpdateWrapper<SysDict> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.like(SysDict::getDictPids, sysDict.getDictId());
            lambdaUpdateWrapper.set(SysDict::getStatusFlag, dictRequest.getStatusFlag());
            this.update(lambdaUpdateWrapper);
        }

        // 修改状态
        sysDict.setStatusFlag(dictRequest.getStatusFlag());
        this.updateById(sysDict);
    }

    @Override
    public void deleteDict(DictRequest dictRequest) {

        //删除自己和下级
        dictMapper.deleteSub(dictRequest.getDictId());
    }

    @Override
    public SysDict findDetail(Long dictId) {
        SysDict dict = this.baseMapper.findDetail(dictId);
        // 获取父节点字典名称
        if (dict.getDictParentId().equals(TreeConstants.DEFAULT_PARENT_ID)) {
            dict.setParentName("顶级");
        } else {
            SysDict parentDict = this.getById(dict.getDictParentId());
            dict.setParentName(ObjectUtil.isNotEmpty(parentDict) ? parentDict.getDictName() : "");
        }
        return dict;
    }

    @Override
    public List<SysDict> findList(DictRequest dictRequest) {
        if (dictRequest == null) {
            dictRequest = new DictRequest();
        }

        return baseMapper.findList(null, dictRequest);
    }

    @Override
    public PageResult<SysDict> findPageList(DictRequest dictRequest) {
        if (dictRequest == null) {
            dictRequest = new DictRequest();
        }

        Page<SysDict> page = PageFactory.defaultPage();
        List<SysDict> list = baseMapper.findList(page, dictRequest);
        page.setRecords(list);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<TreeDictInfo> getTreeDictList(DictRequest dictRequest) {

        // 获取字典类型下所有的字典
        List<SysDict> sysDictList = this.findList(dictRequest);
        if (sysDictList == null || sysDictList.isEmpty()) {
            return new ArrayList<>();
        }

        // 构造树节点信息
        ArrayList<TreeDictInfo> treeDictInfos = new ArrayList<>();
        for (SysDict sysDict : sysDictList) {
            TreeDictInfo treeDictInfo = new TreeDictInfo();
            treeDictInfo.setDictId(sysDict.getDictId());
            treeDictInfo.setDictCode(sysDict.getDictCode());
            treeDictInfo.setDictParentId(sysDict.getDictParentId());
            treeDictInfo.setDictName(sysDict.getDictName());
            treeDictInfos.add(treeDictInfo);
        }

        // 构建菜单树
        return new DefaultTreeBuildFactory<TreeDictInfo>().doTreeBuild(treeDictInfos);
    }

    @Override
    public boolean validateCodeAvailable(DictRequest dictRequest) {

        // 判断编码是否重复
        LambdaQueryWrapper<SysDict> codeRepeatWrapper = new LambdaQueryWrapper<>();
        codeRepeatWrapper.eq(SysDict::getDictCode, dictRequest.getDictCode());

        // 如果传了字典id代表是编辑字典
        if (ObjectUtil.isNotEmpty(dictRequest.getDictId())) {
            codeRepeatWrapper.and(i -> i.ne(SysDict::getDictId, dictRequest.getDictId()));
        }

        // 如果重复，抛出异常
        int codeCount = this.baseMapper.selectCount(codeRepeatWrapper);

        // 如果是小于等于0，则这个编码可以用
        return codeCount <= 0;
    }

    @Override
    public List<SysDict> getDictListExcludeSub(Long dictId) {
        if (dictId != null) {
            return dictMapper.getDictListExcludeSub(dictId);
        }
        return baseMapper.findList(null, new DictRequest());
    }

    @Override
    public List<ZTreeNode> dictZTree(DictRequest dictRequest) {
        // 根据字典类型编码获取字典
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDict::getDictTypeCode, dictRequest.getDictTypeCode());
        List<SysDict> dictList = this.list(queryWrapper);

        // 构建ztree
        ArrayList<ZTreeNode> zTreeNodes = new ArrayList<>();
        for (SysDict dict : dictList) {
            ZTreeNode zTreeNode = new ZTreeNode();
            zTreeNode.setId(dict.getDictId());
            zTreeNode.setpId(dict.getDictParentId());
            zTreeNode.setName(dict.getDictName());
            zTreeNode.setOpen(true);
            zTreeNodes.add(zTreeNode);
        }

        // 创建顶级节点
        zTreeNodes.add(ZTreeNode.createParent());

        // 构建已选中的状态
        if (ObjectUtil.isNotEmpty(dictRequest.getDictId())) {
            for (ZTreeNode zTreeNode : zTreeNodes) {
                if (zTreeNode.getId().equals(dictRequest.getDictId())) {
                    zTreeNode.setChecked(true);
                }
            }
        }
        return zTreeNodes;
    }

    @Override
    public PageResult<SysDict> page(DictRequest dictRequest) {
        LambdaQueryWrapper<SysDict> wrapper = createWrapper(dictRequest);
        Page<SysDict> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public String getDictNameByDictCode(String dictCode) {
        LambdaQueryWrapper<SysDict> sysDictLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysDictLambdaQueryWrapper.eq(SysDict::getDictCode, dictCode);
        sysDictLambdaQueryWrapper.ne(SysDict::getDelFlag, YesOrNotEnum.Y.getCode());

        List<SysDict> list = this.list(sysDictLambdaQueryWrapper);

        // 如果查询不到字典，则返回空串
        if (list.isEmpty()) {
            return StrUtil.EMPTY;
        }

        // 字典code存在多个重复的，返回空串并打印错误日志
        if (list.size() > 1) {
            log.error(DICT_CODE_REPEAT.getUserTip(), "", dictCode);
            return StrUtil.EMPTY;
        }

        String dictName = list.get(0).getDictName();
        if (dictName != null) {
            return dictName;
        } else {
            return StrUtil.EMPTY;
        }
    }

    @Override
    public String getDictName(String dictTypeCode, String dictCode) {
        LambdaQueryWrapper<SysDict> sysDictLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysDictLambdaQueryWrapper.eq(SysDict::getDictTypeCode, dictTypeCode);
        sysDictLambdaQueryWrapper.eq(SysDict::getDictCode, dictCode);
        sysDictLambdaQueryWrapper.ne(SysDict::getDelFlag, YesOrNotEnum.Y.getCode());

        List<SysDict> list = this.list(sysDictLambdaQueryWrapper);

        // 如果查询不到字典，则返回空串
        if (list.isEmpty()) {
            return StrUtil.EMPTY;
        }

        // 字典code存在多个重复的，返回空串并打印错误日志
        if (list.size() > 1) {
            log.error(DICT_CODE_REPEAT.getUserTip(), "", dictCode);
            return StrUtil.EMPTY;
        }

        String dictName = list.get(0).getDictName();
        if (dictName != null) {
            return dictName;
        } else {
            return StrUtil.EMPTY;
        }
    }

    @Override
    public List<SimpleDict> getDictDetailsByDictTypeCode(String dictTypeCode) {
        DictRequest dictRequest = new DictRequest();
        dictRequest.setDictTypeCode(dictTypeCode);
        LambdaQueryWrapper<SysDict> wrapper = createWrapper(dictRequest);
        List<SysDict> dictList = this.list(wrapper);
        if (dictList.isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<SimpleDict> simpleDictList = new ArrayList<>();
        for (SysDict sysDict : dictList) {
            SimpleDict simpleDict = new SimpleDict();
            simpleDict.setCode(sysDict.getDictCode());
            simpleDict.setName(sysDict.getDictName());
            simpleDictList.add(simpleDict);
        }
        return simpleDictList;
    }

    @Override
    public void deleteByDictId(Long dictId) {
        this.removeById(dictId);
    }

    /**
     * 批量修改pids的请求
     *
     * @author fengshuonan
     * @date 2020/12/26 12:19
     */
    private static ParentIdsUpdateRequest createParenIdsUpdateRequest(String newParentIds, String oldParentIds) {
        ParentIdsUpdateRequest parentIdsUpdateRequest = new ParentIdsUpdateRequest();
        parentIdsUpdateRequest.setNewParentIds(newParentIds);
        parentIdsUpdateRequest.setOldParentIds(oldParentIds);
        parentIdsUpdateRequest.setUpdateTime(new Date());
        parentIdsUpdateRequest.setUpdateUser(LoginContext.me().getLoginUser().getUserId());
        return parentIdsUpdateRequest;
    }

    /**
     * 修改pids
     *
     * @author fengshuonan
     * @date 2020/12/11 上午9:48
     */
    private void updatePids(DictRequest dictRequest, SysDict oldSysDict) {
        String oldPids = oldSysDict.getDictPids();
        oldPids = oldPids + StrUtil.COMMA + oldSysDict.getDictId();
        ParentIdsUpdateRequest parentIdsUpdateRequest = createParenIdsUpdateRequest(
                dictRequest.getDictPids() + StrUtil.COMMA + dictRequest.getDictId(), oldPids);
        dictMapper.updateSubPids(parentIdsUpdateRequest);
    }

    /**
     * 给pids 赋值
     *
     * @author fengshuonan
     * @date 2020/12/11 上午9:48
     */
    private void setPids(DictRequest dictRequest) {
        Long dictParentId = dictRequest.getDictParentId();
        if (RuleConstants.TREE_ROOT_ID.equals(dictParentId)) {
            dictRequest.setDictPids(RuleConstants.TREE_ROOT_ID.toString());
        } else {
            SysDict parentSysDict = dictMapper.selectById(dictParentId);
            if (parentSysDict != null) {
                dictRequest.setDictPids(parentSysDict.getDictPids() + StrUtil.COMMA + dictParentId);
            }
        }
    }


    /**
     * 获取详细信息
     *
     * @author chenjinlong
     * @date 2021/1/13 10:50
     */
    private SysDict querySysDict(DictRequest dictRequest) {
        SysDict sysDict = this.getById(dictRequest.getDictId());
        if (ObjectUtil.isNull(sysDict)) {
            throw new ServiceException(DictExceptionEnum.DICT_TYPE_NOT_EXISTED);
        }
        return sysDict;
    }

    /**
     * 构建wrapper
     *
     * @author chenjinlong
     * @date 2021/1/13 10:50
     */
    private LambdaQueryWrapper<SysDict> createWrapper(DictRequest dictRequest) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(dictRequest.getDictTypeCode()), SysDict::getDictTypeCode, dictRequest.getDictTypeCode());
        queryWrapper.eq(StrUtil.isNotBlank(dictRequest.getDictCode()), SysDict::getDictCode, dictRequest.getDictCode());
        queryWrapper.like(StrUtil.isNotBlank(dictRequest.getDictName()), SysDict::getDictName, dictRequest.getDictName());
        queryWrapper.eq(StrUtil.isNotBlank(dictRequest.getDictTypeCode()), SysDict::getDictTypeCode, dictRequest.getDictTypeCode());
        queryWrapper.ne(SysDict::getDelFlag, YesOrNotEnum.Y.getCode());
        return queryWrapper;
    }

}
