package cn.stylefeng.roses.kernel.i18n.modular.controller;

import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.dict.api.DictApi;
import cn.stylefeng.roses.kernel.dict.api.constants.DictConstants;
import cn.stylefeng.roses.kernel.i18n.api.context.TranslationContext;
import cn.stylefeng.roses.kernel.i18n.api.pojo.request.TranslationRequest;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户多语言信息控制器
 *
 * @author fengshuonan
 * @date 2021/1/27 21:59
 */
@RestController
@ApiResource(name = "用户多语言信息控制器")
public class UserTranslationController {

    @Resource
    private DictApi dictApi;

    @Resource
    private SessionManagerApi sessionManagerApi;

    /**
     * 获取所有的多语言类型编码
     *
     * @author fengshuonan
     * @date 2021/1/24 19:20
     */
    @GetResource(name = "获取所有的多语言类型编码", path = "/i18n/getAllLanguages", requiredPermission = false)
    public ResponseData getAllLanguages() {
        List<SimpleDict> dictDetailsByDictTypeCode = dictApi.getDictDetailsByDictTypeCode(DictConstants.LANGUAGES_DICT_TYPE_CODE);
        return new SuccessResponseData(dictDetailsByDictTypeCode);
    }

    /**
     * 获取当前用户的多语言字典
     *
     * @author fengshuonan
     * @date 2021/1/27 22:00
     */
    @GetResource(name = "获取当前用户的多语言字典", path = "/i18n/getUserTranslation", requiredPermission = false)
    public ResponseData getUserTranslation() {
        String tranLanguageCode = LoginContext.me().getLoginUser().getTranLanguageCode();
        Map<String, String> translationDictByLanguage = TranslationContext.me().getTranslationDictByLanguage(tranLanguageCode);
        return new SuccessResponseData(translationDictByLanguage);
    }

    /**
     * 修改当前用户的多语言配置
     *
     * @author fengshuonan
     * @date 2021/1/27 22:04
     */
    @PostResource(name = "修改当前用户的多语言配置", path = "/i18n/changeUserTranslation", requiredPermission = false)
    public ResponseData changeUserTranslation(@RequestBody @Validated(TranslationRequest.changeUserLanguage.class) TranslationRequest translationRequest) {

        String token = LoginContext.me().getToken();
        LoginUser loginUser = LoginContext.me().getLoginUser();

        // 更新当前用户的多语言配置
        loginUser.setTranLanguageCode(translationRequest.getTranLanguageCode());
        sessionManagerApi.updateSession(token, loginUser);

        return new SuccessResponseData();
    }

}


