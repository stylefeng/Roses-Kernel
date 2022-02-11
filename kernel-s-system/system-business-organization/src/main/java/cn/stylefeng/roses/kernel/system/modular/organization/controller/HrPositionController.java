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
package cn.stylefeng.roses.kernel.system.modular.organization.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.annotation.BusinessLog;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.system.api.pojo.organization.HrPositionRequest;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.HrPosition;
import cn.stylefeng.roses.kernel.system.modular.organization.service.HrPositionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统职位控制器
 *
 * @author chenjinlong
 * @date 2020/11/18 21:56
 */
@RestController
@ApiResource(name = "系统职位管理")
public class HrPositionController {

    @Resource
    private HrPositionService hrPositionService;

    /**
     * 添加系统职位
     *
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "添加系统职位", path = "/hrPosition/add")
    @BusinessLog
    public ResponseData<?> add(@RequestBody @Validated(HrPositionRequest.add.class) HrPositionRequest hrPositionRequest) {
        hrPositionService.add(hrPositionRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除系统职位
     *
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "删除系统职位", path = "/hrPosition/delete")
    @BusinessLog
    public ResponseData<?> delete(@RequestBody @Validated(HrPositionRequest.delete.class) HrPositionRequest hrPositionRequest) {
        hrPositionService.del(hrPositionRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 批量删除系统职位
     *
     * @author fengshuonan
     * @date 2021/4/8 13:50
     */
    @PostResource(name = "批量删除系统职位", path = "/hrPosition/batchDelete")
    @BusinessLog
    public ResponseData<?> batchDelete(@RequestBody @Validated(HrPositionRequest.batchDelete.class) HrPositionRequest hrPositionRequest) {
        hrPositionService.batchDel(hrPositionRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑系统职位
     *
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "编辑系统职位", path = "/hrPosition/edit")
    @BusinessLog
    public ResponseData<?> edit(@RequestBody @Validated(HrPositionRequest.edit.class) HrPositionRequest hrPositionRequest) {
        hrPositionService.edit(hrPositionRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 更新职位状态
     *
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    @PostResource(name = "更新职位状态", path = "/hrPosition/updateStatus")
    @BusinessLog
    public ResponseData<?> updateStatus(@RequestBody @Validated(BaseRequest.updateStatus.class) HrPositionRequest hrPositionRequest) {
        hrPositionService.changeStatus(hrPositionRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查看详情系统职位
     *
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    @GetResource(name = "查看详情系统职位", path = "/hrPosition/detail")
    public ResponseData<HrPosition> detail(@Validated(HrPositionRequest.detail.class) HrPositionRequest hrPositionRequest) {
        return new SuccessResponseData<>(hrPositionService.detail(hrPositionRequest));
    }

    /**
     * 分页查询系统职位
     *
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    @GetResource(name = "分页查询系统职位", path = "/hrPosition/page")
    public ResponseData<PageResult<HrPosition>> page(HrPositionRequest hrPositionRequest) {
        return new SuccessResponseData<>(hrPositionService.findPage(hrPositionRequest));
    }

    /**
     * 获取全部系统职位
     *
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    @GetResource(name = "获取全部系统职位", path = "/hrPosition/list")
    public ResponseData<List<HrPosition>> list(HrPositionRequest hrPositionRequest) {
        return new SuccessResponseData<>(hrPositionService.findList(hrPositionRequest));
    }

}
