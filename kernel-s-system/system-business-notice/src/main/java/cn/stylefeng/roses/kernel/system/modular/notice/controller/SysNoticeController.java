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
package cn.stylefeng.roses.kernel.system.modular.notice.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.annotation.BusinessLog;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.notice.SysNoticeRequest;
import cn.stylefeng.roses.kernel.system.modular.notice.entity.SysNotice;
import cn.stylefeng.roses.kernel.system.modular.notice.service.SysNoticeService;
import cn.stylefeng.roses.kernel.system.modular.notice.wrapper.NoticeWrapper;
import cn.stylefeng.roses.kernel.wrapper.api.annotation.Wrapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 通知管理控制器
 *
 * @author liuhanqing
 * @date 2021/1/8 19:47
 */
@RestController
@ApiResource(name = "通知管理")
public class SysNoticeController {

    @Resource
    private SysNoticeService sysNoticeService;

    /**
     * 添加通知管理
     *
     * @author liuhanqing
     * @date 2021/1/9 14:44
     */
    @PostResource(name = "添加通知管理", path = "/sysNotice/add")
    @BusinessLog
    public ResponseData<?> add(@RequestBody @Validated(SysNoticeRequest.add.class) SysNoticeRequest sysNoticeParam) {
        sysNoticeService.add(sysNoticeParam);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑通知管理
     *
     * @author liuhanqing
     * @date 2021/1/9 14:54
     */
    @PostResource(name = "编辑通知管理", path = "/sysNotice/edit")
    @BusinessLog
    public ResponseData<?> edit(@RequestBody @Validated(SysNoticeRequest.edit.class) SysNoticeRequest sysNoticeParam) {
        sysNoticeService.edit(sysNoticeParam);
        return new SuccessResponseData<>();
    }

    /**
     * 删除通知管理
     *
     * @author liuhanqing
     * @date 2021/1/9 14:54
     */
    @PostResource(name = "删除通知管理", path = "/sysNotice/delete")
    @BusinessLog
    public ResponseData<?> delete(@RequestBody @Validated(SysNoticeRequest.delete.class) SysNoticeRequest sysNoticeParam) {
        sysNoticeService.del(sysNoticeParam);
        return new SuccessResponseData<>();
    }

    /**
     * 查看通知管理
     *
     * @author liuhanqing
     * @date 2021/1/9 9:49
     */
    @GetResource(name = "查看通知管理", path = "/sysNotice/detail")
    public ResponseData<SysNotice> detail(@Validated(SysNoticeRequest.detail.class) SysNoticeRequest sysNoticeParam) {
        return new SuccessResponseData<>(sysNoticeService.detail(sysNoticeParam));
    }

    /**
     * 查询通知管理
     *
     * @author liuhanqing
     * @date 2021/1/9 21:23
     */
    @GetResource(name = "查询通知管理", path = "/sysNotice/page")
    @Wrapper(NoticeWrapper.class)
    public ResponseData<PageResult<SysNotice>> page(SysNoticeRequest sysNoticeParam) {
        return new SuccessResponseData<>(sysNoticeService.findPage(sysNoticeParam));
    }

    /**
     * 通知管理列表
     *
     * @author liuhanqing
     * @date 2021/1/9 14:55
     */
    @GetResource(name = "通知管理列表", path = "/sysNotice/list")
    public ResponseData<List<SysNotice>> list(SysNoticeRequest sysNoticeParam) {
        return new SuccessResponseData<>(sysNoticeService.findList(sysNoticeParam));
    }

}
