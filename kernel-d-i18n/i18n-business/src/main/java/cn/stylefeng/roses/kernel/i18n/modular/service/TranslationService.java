package cn.stylefeng.roses.kernel.i18n.modular.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.i18n.api.TranslationPersistenceApi;
import cn.stylefeng.roses.kernel.i18n.api.pojo.request.TranslationRequest;
import cn.stylefeng.roses.kernel.i18n.modular.entity.Translation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 多语言表 服务类
 *
 * @author fengshuonan
 * @date 2021/1/24 19:21
 */
public interface TranslationService extends IService<Translation>, TranslationPersistenceApi {

    /**
     * 新增翻译项
     *
     * @author fengshuonan
     * @date 2021/1/24 19:27
     */
    void add(TranslationRequest param);

    /**
     * 更新翻译项
     *
     * @author fengshuonan
     * @date 2021/1/24 19:27
     */
    void update(TranslationRequest param);

    /**
     * 删除翻译项
     *
     * @author fengshuonan
     * @date 2021/1/24 19:27
     */
    void delete(TranslationRequest param);

    /**
     * 查询详情
     *
     * @author fengshuonan
     * @date 2021/1/24 19:28
     */
    Translation findDetail(TranslationRequest param);

    /**
     * 查询列表
     *
     * @author fengshuonan
     * @date 2021/1/24 19:28
     */
    PageResult<Translation> findPage(TranslationRequest param);

}
