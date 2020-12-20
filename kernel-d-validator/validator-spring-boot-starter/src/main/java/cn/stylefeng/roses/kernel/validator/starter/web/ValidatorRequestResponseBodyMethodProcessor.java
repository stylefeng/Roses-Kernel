package cn.stylefeng.roses.kernel.validator.starter.web;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.validator.context.RequestParamContext;
import cn.stylefeng.roses.kernel.validator.exception.ParamValidateException;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;

import static cn.stylefeng.roses.kernel.validator.exception.enums.ValidatorExceptionEnum.PARAM_VALIDATE_ERROR;

/**
 * 拓展原有RequestResponseBodyMethodProcessor，只为缓存临时参数
 *
 * @author fengshuonan
 * @date 2020/8/21 20:51
 */
public class ValidatorRequestResponseBodyMethodProcessor extends RequestResponseBodyMethodProcessor {

    public ValidatorRequestResponseBodyMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {

        parameter = parameter.nestedIfOptional();
        Object arg = readWithMessageConverters(webRequest, parameter, parameter.getNestedGenericParameterType());

        // 临时缓存一下@RequestBody注解上的参数
        RequestParamContext.setObject(arg);

        String name = Conventions.getVariableNameForParameter(parameter);

        if (binderFactory != null) {
            WebDataBinder binder = binderFactory.createBinder(webRequest, arg, name);
            if (arg != null) {
                validateIfApplicable(binder, parameter);
                if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
                    List<ObjectError> allErrors = binder.getBindingResult().getAllErrors();
                    StringBuilder errTips = new StringBuilder();
                    int index = 1;
                    for (ObjectError error : allErrors) {
                        errTips.append(index++);
                        errTips.append(".");
                        errTips.append(error.getDefaultMessage());
                        errTips.append(";");
                    }
                    String userTip = StrUtil.format(PARAM_VALIDATE_ERROR.getUserTip(), errTips.toString());
                    throw new ParamValidateException(PARAM_VALIDATE_ERROR, userTip);
                }
            }
            if (mavContainer != null) {
                mavContainer.addAttribute(BindingResult.MODEL_KEY_PREFIX + name, binder.getBindingResult());
            }
        }

        return adaptArgumentIfNecessary(arg, parameter);
    }
}
