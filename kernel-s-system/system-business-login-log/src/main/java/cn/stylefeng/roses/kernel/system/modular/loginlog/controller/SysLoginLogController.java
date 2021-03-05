package cn.stylefeng.roses.kernel.system.modular.loginlog.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.system.api.pojo.loginlog.SysLoginLogRequest;
import cn.stylefeng.roses.kernel.system.modular.loginlog.service.SysLoginLogService;
import cn.stylefeng.roses.kernel.system.modular.loginlog.wrapper.SysLoginLogWrapper;
import cn.stylefeng.roses.kernel.wrapper.api.annotation.Wrapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登陆日志控制器
 *
 * @author chenjinlong
 * @date 2021/1/13 17:51
 */
@RestController
@ApiResource(name = "登录日志")
public class SysLoginLogController {

    @Resource
    private SysLoginLogService sysLoginLogService;

    /**
     * 清空登录日志
     *
     * @author chenjinlong
     * @date 2021/1/13 17:51
     */
    @GetResource(name = "清空登录日志", path = "/loginLog/deleteAll")
    public ResponseData deleteAll() {
        sysLoginLogService.delAll();
        return new SuccessResponseData();
    }

    /**
     * 查询登录日志详情
     *
     * @author chenjinlong
     * @date 2021/1/13 17:51
     */
    @GetResource(name = "查看详情登录日志", path = "/loginLog/detail")
    public ResponseData detail(@Validated(SysLoginLogRequest.detail.class) SysLoginLogRequest sysLoginLogRequest) {
        return new SuccessResponseData(sysLoginLogService.detail(sysLoginLogRequest));
    }

    /**
     * 分页查询登录日志
     *
     * @author chenjinlong
     * @date 2021/1/13 17:51
     */
    @GetResource(name = "分页查询登录日志", path = "/loginLog/page")
    @Wrapper(SysLoginLogWrapper.class)
    public ResponseData page(SysLoginLogRequest sysLoginLogRequest) {
        return new SuccessResponseData(sysLoginLogService.findPage(sysLoginLogRequest));
    }

}
