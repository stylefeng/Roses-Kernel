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
    public void add(TranslationRequest param) {

        // 1.构造实体
        Translation translation = new Translation();
        BeanUtil.copyProperties(param, translation);

        // 2.保存到库中
        this.save(translation);

        // 3.添加对应context
        TranslationEnum translationEnum = TranslationEnum.valueOf(param.getLanguage());
        TranslationDict translationDict = TranslationDictFactory.createTranslationDict(translationEnum, translation);
        TranslationContext.me().addTranslationDict(translationDict);
    }

    @Override
    public void update(TranslationRequest param) {

        // 1.根据id获取信息
        Translation translation = this.queryTranslation(param);

        // 2.请求参数转化为实体
        BeanUtil.copyProperties(param, translation);

        // 3.更新记录
        this.updateById(translation);

        // 4.更新对应常量context
        TranslationEnum translationEnum = TranslationEnum.valueOf(param.getLanguage());
        TranslationDict translationDict = TranslationDictFactory.createTranslationDict(translationEnum, translation);
        TranslationContext.me().addTranslationDict(translationDict);
    }

    @Override
    public void delete(TranslationRequest param) {

        // 1.根据id获取实体
        Translation translation = this.queryTranslation(param);

        // 2.删除该记录
        this.removeById(param.getTranId());

        // 3.删除对应context
        TranslationEnum translationEnum = TranslationEnum.valueOf(translation.getLanguage());
        TranslationContext.me().deleteTranslationDict(translationEnum, translation.getTranCode());
    }

    @Override
    public Translation findDetail(TranslationRequest param) {
        return queryTranslation(param);
    }

    @Override
    public PageResult<Translation> findPage(TranslationRequest param) {
        LambdaQueryWrapper<Translation> wrapper = createWrapper(param);
        Page<Translation> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<TranslationDict> getAllTranslationDict() {
        List<Translation> list = this.list();
        ArrayList<TranslationDict> translationDictList = new ArrayList<>();
        for (Translation translation : list) {
            TranslationEnum translationEnum = TranslationEnum.valueOf(translation.getLanguage());
            TranslationDict translationDict = TranslationDictFactory.createTranslationDict(translationEnum, translation);
            translationDictList.add(translationDict);
        }
        return translationDictList;
    }

    /**
     * 获取字典项的对象
     *
     * @author fengshuonan
     * @date 2021/1/24 21:54
     */
    private Translation queryTranslation(TranslationRequest param) {
        Translation translation = this.getById(param.getTranId());
        if (ObjectUtil.isEmpty(translation)) {
            throw new TranslationException(TranslationExceptionEnum.NOT_EXISTED, param.getTranId());
        }
        return translation;
    }

    /**
     * 创建多语言的wrapper
     *
     * @author fengshuonan
     * @date 2021/1/24 22:03
     */
    private LambdaQueryWrapper<Translation> createWrapper(TranslationRequest param) {
        LambdaQueryWrapper<Translation> queryWrapper = new LambdaQueryWrapper<>();

        if (ObjectUtil.isNotNull(param)) {

            // 如果编码不为空，则带上名称搜素搜条件
            if (ObjectUtil.isNotEmpty(param.getTranCode())) {
                queryWrapper.like(Translation::getTranCode, param.getTranCode());
            }

            // 如果翻译名称不为空，则带上翻译名称
            if (ObjectUtil.isNotEmpty(param.getTranName())) {
                queryWrapper.eq(Translation::getTranName, param.getTranName());
            }
        }

        // 按时间倒序
        queryWrapper.orderByDesc(Translation::getCreateTime);

        return queryWrapper;
    }

}
