/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.log.manage.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.log.api.LogManagerApi;
import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerRequest;
import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;
import cn.stylefeng.roses.kernel.log.db.service.SysLogService;
import cn.stylefeng.roses.kernel.log.manage.wrapper.LogInfoWrapper;
import cn.stylefeng.roses.kernel.rule.annotation.BusinessLog;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.wrapper.api.annotation.Wrapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 日志管理控制器
 *
 * @author luojie
 * @date 2020/11/3 12:44
 */
@RestController
@ApiResource(name = "日志管理控制器")
public class LogManagerController {

    /**
     * 日志管理api
     */
    @Resource
    private LogManagerApi logManagerApi;

    /**
     * 日志管理service
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 查询日志列表
     *
     * @author luojie
     * @date 2020/11/3 12:58
     */
    @GetResource(name = "查询日志列表", path = "/logManager/list")
    public ResponseData<List<LogRecordDTO>> list(@RequestBody LogManagerRequest logManagerRequest) {
        return new SuccessResponseData<>(logManagerApi.findList(logManagerRequest));
    }

    /**
     * 查询日志
     *
     * @author tengshuqi
     * @date 2021/1/8 17:36
     */
    @GetResource(name = "查询日志列表", path = "/logManager/page")
    @Wrapper(LogInfoWrapper.class)
    public ResponseData<PageResult<LogRecordDTO>> page(LogManagerRequest logManagerRequest) {
        return new SuccessResponseData<>(logManagerApi.findPage(logManagerRequest));
    }

    /**
     * 删除日志
     *
     * @author luojie
     * @date 2020/11/3 13:47
     */
    @PostResource(name = "删除日志", path = "/logManager/delete")
    @BusinessLog
    public ResponseData<?> delete(@RequestBody @Validated(LogManagerRequest.delete.class) LogManagerRequest logManagerRequest) {
        sysLogService.delAll(logManagerRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查看日志详情
     *
     * @author TSQ
     * @date 2021/1/11 17:36
     */
    @GetResource(name = "查看日志详情", path = "/logManager/detail")
    @Wrapper(LogInfoWrapper.class)
    public ResponseData<LogRecordDTO> detail(@Validated(LogManagerRequest.detail.class) LogManagerRequest logManagerRequest) {
        return new SuccessResponseData<>(logManagerApi.detail(logManagerRequest));
    }

}
