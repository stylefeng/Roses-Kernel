package cn.stylefeng.roses.kernel.system.modular.theme.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.system.api.pojo.theme.SysThemeRequest;
import cn.stylefeng.roses.kernel.system.modular.theme.pojo.DefaultTheme;
import cn.stylefeng.roses.kernel.system.modular.theme.service.SysThemeService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 主题开放接口的API
 *
 * @author fengshuonan
 * @date 2022/1/10 18:27
 */
@RestController
@ApiResource(name = "主题开放接口的API")
public class SysThemeApiController {

    @Resource
    private SysThemeService sysThemeService;

    /**
     * 获取当前Guns管理系统的主题数据
     *
     * @author fengshuonan
     * @date 2022/1/10 18:29
     */
    @GetResource(name = "获取当前Guns管理系统的主题数据", path = "/theme/currentThemeInfo", requiredPermission = false, requiredLogin = false)
    public ResponseData<DefaultTheme> currentThemeInfo(SysThemeRequest sysThemeParam) {
        DefaultTheme defaultTheme = sysThemeService.currentThemeInfo(sysThemeParam);
        return new SuccessResponseData<>(defaultTheme);
    }


}
