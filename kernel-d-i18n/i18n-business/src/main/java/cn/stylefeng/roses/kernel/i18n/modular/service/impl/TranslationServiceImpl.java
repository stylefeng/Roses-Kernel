package cn.stylefeng.roses.kernel.i18n.modular.service.impl;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.i18n.api.pojo.TranslationDict;
import cn.stylefeng.roses.kernel.i18n.api.pojo.request.TranslationRequest;
import cn.stylefeng.roses.kernel.i18n.modular.entity.Translation;
import cn.stylefeng.roses.kernel.i18n.modular.mapper.TranslationMapper;
import cn.stylefeng.roses.kernel.i18n.modular.service.TranslationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    }

    @Override
    public void update(TranslationRequest param) {

    }

    @Override
    public void delete(TranslationRequest param) {

    }

    @Override
    public Translation findDetail(TranslationRequest param) {
        return null;
    }

    @Override
    public PageResult<Translation> findPage(TranslationRequest param) {
        return null;
    }

    @Override
    public List<TranslationDict> getAllTranslationDict() {
        return null;
    }

}
