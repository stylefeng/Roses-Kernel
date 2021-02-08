package cn.stylefeng.roses.kernel.dict.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.api.constants.DictConstants;
import cn.stylefeng.roses.kernel.dict.api.exception.DictException;
import cn.stylefeng.roses.kernel.dict.api.exception.enums.DictExceptionEnum;
import cn.stylefeng.roses.kernel.dict.api.pojo.dict.request.ParentIdsUpdateRequest;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDict;
import cn.stylefeng.roses.kernel.dict.modular.mapper.DictMapper;
import cn.stylefeng.roses.kernel.dict.modular.pojo.TreeDictInfo;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictRequest;
import cn.stylefeng.roses.kernel.dict.modular.service.DictService;
import cn.stylefeng.roses.kernel.pinyin.api.PinYinApi;
import cn.stylefeng.roses.kernel.rule.constants.SymbolConstant;
import cn.stylefeng.roses.kernel.rule.constants.TreeConstants;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.rule.tree.factory.DefaultTreeBuildFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    public void add(DictRequest dictRequest) {

        // 如果父节点为空，则填充为默认的父节点id
        if (dictRequest.getDictParentId() == null) {
            dictRequest.setDictParentId(DictConstants.DEFAULT_DICT_PARENT_ID);
        }

        // 如果父节点不为空，并且不是-1，则判断父节点存不存在，防止脏数据
        else {
            if (!DictConstants.DEFAULT_DICT_PARENT_ID.equals(dictRequest.getDictParentId())) {
                DictRequest tempParam = new DictRequest();
                tempParam.setDictId(dictRequest.getDictParentId());
                this.querySysDict(tempParam);
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
    public void del(DictRequest dictRequest) {
        //删除自己和下级
        dictMapper.deleteSub(dictRequest.getDictId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(DictRequest dictRequest) {

        SysDict oldSysDict = this.querySysDict(dictRequest);

        // 不能修改字典类型和编码
        dictRequest.setDictTypeCode(null);
        dictRequest.setDictCode(null);

        // 赋值pids，如果更新了pid，则字典的子pid都要更新
        setPids(dictRequest);
        if (!oldSysDict.getDictParentId().equals(dictRequest.getDictParentId())) {
            updateSubPids(dictRequest, oldSysDict);
        }

        // model转化为entity
        BeanUtil.copyProperties(dictRequest, oldSysDict, CopyOptions.create().setIgnoreNullValue(true));

        // 设置拼音
        oldSysDict.setDictNamePinyin(pinYinApi.parseEveryPinyinFirstLetter(oldSysDict.getDictName()));

        this.updateById(oldSysDict);
    }

    @Override
    public SysDict detail(Long dictId) {
        SysDict dict = this.baseMapper.detail(dictId);
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

        return baseMapper.findPage(null, dictRequest);
    }

    @Override
    public PageResult<SysDict> findPage(DictRequest dictRequest) {
        if (dictRequest == null) {
            dictRequest = new DictRequest();
        }

        Page<SysDict> page = PageFactory.defaultPage();
        List<SysDict> list = baseMapper.findPage(page, dictRequest);
        page.setRecords(list);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SysDict> getDictListExcludeSub(Long dictId) {
        if (dictId != null) {
            return dictMapper.getDictListExcludeSub(dictId);
        }
        return this.list();
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
            log.error(DictExceptionEnum.DICT_CODE_REPEAT.getUserTip(), "", dictCode);
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
     * 修改pids，如果pids字段发生了变化，则子字典的所有pids都要变化
     *
     * @author fengshuonan
     * @date 2020/12/11 上午9:48
     */
    private void updateSubPids(DictRequest dictRequest, SysDict oldSysDict) {

        // 被替换的表达式，也就是通过这个条件匹配当前修改的字典的所有子字典，包含子字典的字典（ pids + [pid] + , ）
        String beReplacedRegex = oldSysDict.getDictPids()
                + SymbolConstant.LEFT_SQUARE_BRACKETS + oldSysDict.getDictId() + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA;

        // 新的表达式
        String newReplace = dictRequest.getDictPids();

        // 更新所有子字典的pids字段
        ParentIdsUpdateRequest parentIdsUpdateRequest = createParenIdsUpdateRequest(newReplace, beReplacedRegex);
        dictMapper.updateSubPids(parentIdsUpdateRequest);
    }

    /**
     * 给pids赋值
     * <p>
     * 如果pid是顶级节点，pids就是 [-1],
     * <p>
     * 如果pid不是顶级节点，pids就是父菜单的pids + [pid] + ,
     *
     * @author fengshuonan
     * @date 2020/12/11 上午9:48
     */
    private void setPids(DictRequest dictRequest) {
        Long dictParentId = dictRequest.getDictParentId();
        if (DictConstants.DEFAULT_DICT_PARENT_ID.equals(dictParentId)) {
            String pids = SymbolConstant.LEFT_SQUARE_BRACKETS + DictConstants.DEFAULT_DICT_PARENT_ID + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA;
            dictRequest.setDictPids(pids);
        } else {
            SysDict parentSysDict = dictMapper.selectById(dictParentId);
            if (parentSysDict != null) {
                String pids = parentSysDict.getDictPids() + SymbolConstant.LEFT_SQUARE_BRACKETS + dictRequest.getDictParentId() + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA;
                dictRequest.setDictPids(pids);
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
            throw new DictException(DictExceptionEnum.DICT_NOT_EXISTED, dictRequest.getDictId());
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

        queryWrapper.ne(SysDict::getDelFlag, YesOrNotEnum.Y.getCode());
        return queryWrapper;
    }

}
