package cn.stylefeng.roses.kernel.i18n.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.i18n.api.context.TranslationContext;
import cn.stylefeng.roses.kernel.i18n.api.enums.TranslationEnum;
import cn.stylefeng.roses.kernel.i18n.api.exception.TranslationException;
import cn.stylefeng.roses.kernel.i18n.api.exception.enums.TranslationExceptionEnum;
import cn.stylefeng.roses.kernel.i18n.api.pojo.TranslationDict;
import cn.stylefeng.roses.kernel.i18n.api.pojo.request.TranslationRequest;
import cn.stylefeng.roses.kernel.i18n.modular.entity.Translation;
import cn.stylefeng.roses.kernel.i18n.modular.factory.TranslationDictFactory;
import cn.stylefeng.roses.kernel.i18n.modular.mapper.TranslationMapper;
import cn.stylefeng.roses.kernel.i18n.modular.service.TranslationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public void add(TranslationRequest translationRequest) {
        Translation translation = new Translation();
        BeanUtil.copyProperties(translationRequest, translation);
        this.save(translation);
        // 更新对应常量
        this.saveContext(translation);
    }


    @Override
    public void edit(TranslationRequest translationRequest) {
        Translation translation = this.queryTranslation(translationRequest);
        BeanUtil.copyProperties(translationRequest, translation);
        this.updateById(translation);
        // 更新对应常量
        this.saveContext(translation);
    }

    @Override
    public void del(TranslationRequest translationRequest) {
        Translation translation = this.queryTranslation(translationRequest);
        this.removeById(translationRequest.getTranId());
        // 更新对应常量
        this.saveContext(translation);
    }

    @Override
    public Translation detail(TranslationRequest translationRequest) {
        return this.queryTranslation(translationRequest);
    }

    @Override
    public Translation detailBy(TranslationRequest translationRequest) {
        List<Translation> list = this.listBy(translationRequest);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Translation> listBy(TranslationRequest translationRequest) {
        LambdaQueryWrapper<Translation> queryWrapper = this.createWrapper(translationRequest);
        return this.list(queryWrapper);
    }

    @Override
    public PageResult<Translation> getPage(TranslationRequest translationRequest) {
        LambdaQueryWrapper<Translation> wrapper = createWrapper(translationRequest);
        Page<Translation> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<TranslationDict> getAllTranslationDict() {
        List<Translation> list = this.list();
        ArrayList<TranslationDict> translationDictList = new ArrayList<>();
        for (Translation translation : list) {
            TranslationEnum translationEnum = TranslationEnum.getValue(translation.getTranLanguageCode());
            TranslationDict translationDict = TranslationDictFactory.createTranslationDict(translationEnum, translation);
            translationDictList.add(translationDict);
        }
        return translationDictList;
    }

    /**
     * 根据主键id获取对象
     *
     * @param
     * @return
     * @author chenjinlong
     * @date 2021/1/26 13:28
     */
    private Translation queryTranslation(TranslationRequest translationRequest) {
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

        String tranCode = translationRequest.getTranCode();
        String tranName = translationRequest.getTranName();
        String tranLanguageCode = translationRequest.getTranLanguageCode();
        // SQL条件拼接
        queryWrapper.like(ObjectUtil.isNotEmpty(tranCode), Translation::getTranCode, tranCode);
        queryWrapper.like(ObjectUtil.isNotEmpty(tranName), Translation::getTranName, tranName);
        queryWrapper.eq(ObjectUtil.isNotEmpty(tranLanguageCode), Translation::getTranLanguageCode, tranLanguageCode);
        // 排序
        queryWrapper.orderByDesc(Translation::getTranCode);

        return queryWrapper;
    }


    /**
     * 更新对应常量
     *
     * @param translation
     * @author chenjinlong
     * @date 2021/1/26 13:45
     */
    private void saveContext(Translation translation) {
        TranslationEnum translationEnum = TranslationEnum.getValue(translation.getTranLanguageCode());
        TranslationDict translationDict = TranslationDictFactory.createTranslationDict(translationEnum, translation);
        TranslationContext.me().addTranslationDict(translationDict);
    }

}
