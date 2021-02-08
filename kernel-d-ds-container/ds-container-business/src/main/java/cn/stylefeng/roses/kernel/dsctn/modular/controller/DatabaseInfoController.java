package cn.stylefeng.roses.kernel.dsctn.modular.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dsctn.api.pojo.request.DatabaseInfoRequest;
import cn.stylefeng.roses.kernel.dsctn.modular.entity.DatabaseInfo;
import cn.stylefeng.roses.kernel.dsctn.modular.service.DatabaseInfoService;
import cn.stylefeng.roses.kernel.resource.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.resource.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


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
    public ResponseData add(@RequestBody @Validated(BaseRequest.add.class) DatabaseInfoRequest databaseInfoRequest) {
        databaseInfoService.add(databaseInfoRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除数据源
     *
     * @author fengshuonan
     * @date 2020/11/1 22:18
     */
    @PostResource(name = "删除数据源", path = "/databaseInfo/delete")
    public ResponseData del(@RequestBody @Validated(DatabaseInfoRequest.delete.class) DatabaseInfoRequest databaseInfoRequest) {
        databaseInfoService.del(databaseInfoRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑数据源
     *
     * @author fengshuonan
     * @date 2020/11/1 22:16
     */
    @PostResource(name = "编辑数据源", path = "/databaseInfo/edit")
    public ResponseData edit(@RequestBody @Validated(DatabaseInfoRequest.edit.class) DatabaseInfoRequest databaseInfoRequest) {
        databaseInfoService.edit(databaseInfoRequest);
        return new SuccessResponseData();
    }

    /**
     * 查询数据源列表（带分页）
     *
     * @author fengshuonan
     * @date 2020/11/1 22:18
     */
    @GetResource(name = "查询数据源列表（带分页）", path = "/databaseInfo/page")
    public ResponseData findPage(DatabaseInfoRequest databaseInfoRequest) {
        PageResult<DatabaseInfo> pageResult = databaseInfoService.findPage(databaseInfoRequest);
        return new SuccessResponseData(pageResult);
    }

    /**
     * 查询数据源详情
     *
     * @author fengshuonan
     * @date 2021/1/23 20:29
     */
    @GetResource(name = "查询数据源详情", path = "/databaseInfo/detail")
    public ResponseData detail(@Validated(BaseRequest.detail.class) DatabaseInfoRequest databaseInfoRequest) {
        DatabaseInfo databaseInfo = databaseInfoService.detail(databaseInfoRequest);
        return new SuccessResponseData(databaseInfo);
    }

}


