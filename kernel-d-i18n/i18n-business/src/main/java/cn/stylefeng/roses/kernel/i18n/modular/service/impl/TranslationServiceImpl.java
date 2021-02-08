package cn.stylefeng.roses.kernel.i18n.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.api.DictApi;
import cn.stylefeng.roses.kernel.i18n.api.context.TranslationContext;
import cn.stylefeng.roses.kernel.i18n.api.exception.TranslationException;
import cn.stylefeng.roses.kernel.i18n.api.exception.enums.TranslationExceptionEnum;
import cn.stylefeng.roses.kernel.i18n.api.pojo.TranslationDict;
import cn.stylefeng.roses.kernel.i18n.api.pojo.request.TranslationRequest;
import cn.stylefeng.roses.kernel.i18n.modular.entity.Translation;
import cn.stylefeng.roses.kernel.i18n.modular.factory.TranslationDictFactory;
import cn.stylefeng.roses.kernel.i18n.modular.mapper.TranslationMapper;
import cn.stylefeng.roses.kernel.i18n.modular.service.TranslationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 多语言管理业务
 *
 * @author fengshuonan
 * @date 2021/1/24 19:38
 */
@Service
public class TranslationServiceImpl extends ServiceImpl<TranslationMapper, Translation> implements TranslationService {

    @Resource
    private DictApi dictApi;

    @Override
    public void add(TranslationRequest translationRequest) {
        Translation translation = new Translation();
        BeanUtil.copyProperties(translationRequest, translation);
        this.save(translation);

        // 更新翻译的缓存
        this.saveContext(translation);
    }

    @Override
    public void edit(TranslationRequest translationRequest) {
        Translation translation = this.queryTranslationById(translationRequest);
        BeanUtil.copyProperties(translationRequest, translation);
        this.updateById(translation);

        // 更新翻译的缓存
        this.saveContext(translation);
    }

    @Override
    public void del(TranslationRequest translationRequest) {
        Translation translation = this.queryTranslationById(translationRequest);
        this.removeById(translationRequest.getTranId());

        // 删除对应缓存
        TranslationContext.me().deleteTranslationDict(translation.getTranLanguageCode(), translation.getTranCode());
    }

    @Override
    public Translation detail(TranslationRequest translationRequest) {
        LambdaQueryWrapper<Translation> queryWrapper = this.createWrapper(translationRequest);
        return this.getOne(queryWrapper, false);
    }

    @Override
    public List<Translation> findList(TranslationRequest translationRequest) {
        LambdaQueryWrapper<Translation> queryWrapper = this.createWrapper(translationRequest);
        return this.list(queryWrapper);
    }

    @Override
    public PageResult<Translation> findPage(TranslationRequest translationRequest) {
        LambdaQueryWrapper<Translation> wrapper = createWrapper(translationRequest);
        Page<Translation> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public void deleteTranLanguage(TranslationRequest translationRequest) {

        // 删除对应的字典信息
        dictApi.deleteByDictId(translationRequest.getDictId());

        // 删除该语言下的所有翻译项
        LambdaUpdateWrapper<Translation> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Translation::getTranLanguageCode, translationRequest.getTranLanguageCode());
        this.remove(wrapper);
    }

    @Override
    public List<TranslationDict> getAllTranslationDict() {
        List<Translation> list = this.list();
        ArrayList<TranslationDict> translationDictList = new ArrayList<>();
        for (Translation translation : list) {
            TranslationDict translationDict = TranslationDictFactory.createTranslationDict(translation.getTranLanguageCode(), translation);
            translationDictList.add(translationDict);
        }
        return translationDictList;
    }

    /**
     * 根据主键id获取对象
     *
     * @author chenjinlong
     * @date 2021/1/26 13:28
     */
    private Translation queryTranslationById(TranslationRequest translationRequest) {
        Translation translation = this.getById(translationRequest.getTranId());
        if (ObjectUtil.isEmpty(translation)) {
            throw new TranslationException(TranslationExceptionEnum.NOT_EXISTED, translationRequest.getTranId());
        }
        return translation;
    }

    /**
     * 实体构建queryWrapper
     *
     * @author fengshuonan
     * @date 2021/1/24 22:03
     */
    private LambdaQueryWrapper<Translation> createWrapper(TranslationRequest translationRequest) {
        LambdaQueryWrapper<Translation> queryWrapper = new LambdaQueryWrapper<>();

        // 按翻译编码倒序排列
        queryWrapper.orderByDesc(Translation::getTranCode);

        if (ObjectUtil.isEmpty(translationRequest)) {
            return queryWrapper;
        }

        Long tranId = translationRequest.getTranId();
        String tranCode = translationRequest.getTranCode();
        String tranName = translationRequest.getTranName();
        String tranLanguageCode = translationRequest.getTranLanguageCode();

        // SQL条件拼接
        queryWrapper.eq(ObjectUtil.isNotEmpty(tranId), Translation::getTranId, tranId);
        queryWrapper.like(ObjectUtil.isNotEmpty(tranCode), Translation::getTranCode, tranCode);
        queryWrapper.like(ObjectUtil.isNotEmpty(tranName), Translation::getTranName, tranName);
        queryWrapper.eq(ObjectUtil.isNotEmpty(tranLanguageCode), Translation::getTranLanguageCode, tranLanguageCode);

        return queryWrapper;
    }

    /**
     * 更新翻译的缓存
     *
     * @author chenjinlong
     * @date 2021/1/26 13:45
     */
    private void saveContext(Translation translation) {

        // 没有对应的语种，不添加到context
        if (translation.getTranLanguageCode() == null) {
            return;
        }

        TranslationDict translationDict = TranslationDictFactory.createTranslationDict(translation.getTranLanguageCode(), translation);
        TranslationContext.me().addTranslationDict(translationDict);
    }

}
