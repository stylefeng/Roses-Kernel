package cn.stylefeng.roses.kernel.i18n.listener;

import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.i18n.api.TranslationApi;
import cn.stylefeng.roses.kernel.i18n.api.TranslationPersistenceApi;
import cn.stylefeng.roses.kernel.i18n.api.pojo.TranslationDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.util.List;

/**
 * 初始化系统配置表
 * <p>
 * 当spring装配好配置后，就去数据库读constants
 *
 * @author fengshuonan
 * @date 2021/1/24 19:36
 */
@Slf4j
public class TranslationDictInitListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {

        TranslationPersistenceApi tanTranslationPersistenceApi = SpringUtil.getBean(TranslationPersistenceApi.class);
        TranslationApi translationApi = SpringUtil.getBean(TranslationApi.class);

        // 从数据库读取字典
        List<TranslationDict> allTranslationDict = tanTranslationPersistenceApi.getAllTranslationDict();
        if (allTranslationDict != null) {
            translationApi.init(allTranslationDict);
            log.info("初始化所有的翻译字典" + allTranslationDict.size() + "条！");
        }
    }

}
