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
package cn.stylefeng.roses.kernel.dsctn.modular.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dsctn.api.pojo.request.DatabaseInfoRequest;
import cn.stylefeng.roses.kernel.dsctn.modular.entity.DatabaseInfo;
import cn.stylefeng.roses.kernel.dsctn.modular.service.DatabaseInfoService;
import cn.stylefeng.roses.kernel.rule.annotation.BusinessLog;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 数据库信息表控制器
 *
 * @author fengshuonan
 * @date 2020/11/1 22:15
 */
@RestController
@ApiResource(name = "数据源信息管理")
public class DatabaseInfoController {

    @Resource
    private DatabaseInfoService databaseInfoService;

    /**
     * 新增数据源
     *
     * @author fengshuonan
     * @date 2020/11/1 22:16
     */
    @PostResource(name = "新增数据源", path = "/databaseInfo/add")
    @BusinessLog
    public ResponseData<?> add(@RequestBody @Validated(BaseRequest.add.class) DatabaseInfoRequest databaseInfoRequest) {
        databaseInfoService.add(databaseInfoRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除数据源
     *
     * @author fengshuonan
     * @date 2020/11/1 22:18
     */
    @PostResource(name = "删除数据源", path = "/databaseInfo/delete")
    @BusinessLog
    public ResponseData<?> del(@RequestBody @Validated(DatabaseInfoRequest.delete.class) DatabaseInfoRequest databaseInfoRequest) {
        databaseInfoService.del(databaseInfoRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑数据源
     *
     * @author fengshuonan
     * @date 2020/11/1 22:16
     */
    @PostResource(name = "编辑数据源", path = "/databaseInfo/edit")
    @BusinessLog
    public ResponseData<?> edit(@RequestBody @Validated(DatabaseInfoRequest.edit.class) DatabaseInfoRequest databaseInfoRequest) {
        databaseInfoService.edit(databaseInfoRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查询数据源列表（带分页）
     *
     * @author fengshuonan
     * @date 2020/11/1 22:18
     */
    @GetResource(name = "查询数据源列表（带分页）", path = "/databaseInfo/page")
    public ResponseData<PageResult<DatabaseInfo>> findPage(DatabaseInfoRequest databaseInfoRequest) {
        PageResult<DatabaseInfo> pageResult = databaseInfoService.findPage(databaseInfoRequest);
        return new SuccessResponseData<>(pageResult);
    }

    /**
     * 查询所有数据源列表
     *
     * @author fengshuonan
     * @date 2020/11/1 22:18
     */
    @GetResource(name = "查询所有数据源列表", path = "/databaseInfo/list")
    public ResponseData<List<DatabaseInfo>> findList(DatabaseInfoRequest databaseInfoRequest) {
        List<DatabaseInfo> databaseInfos = databaseInfoService.findList(databaseInfoRequest);
        return new SuccessResponseData<>(databaseInfos);
    }

    /**
     * 查询数据源详情
     *
     * @author fengshuonan
     * @date 2021/1/23 20:29
     */
    @GetResource(name = "查询数据源详情", path = "/databaseInfo/detail")
    public ResponseData<DatabaseInfo> detail(@Validated(BaseRequest.detail.class) DatabaseInfoRequest databaseInfoRequest) {
        DatabaseInfo databaseInfo = databaseInfoService.detail(databaseInfoRequest);
        return new SuccessResponseData<>(databaseInfo);
    }

}


