package cn.stylefeng.roses.kernel.migration.web.controller;

import cn.stylefeng.roses.kernel.migration.api.pojo.MigrationAggregationPOJO;
import cn.stylefeng.roses.kernel.migration.web.pojo.MigrationRequest;
import cn.stylefeng.roses.kernel.migration.web.service.MigrationService;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据迁移控制器
 *
 * @author majianguo
 * @date 2021/7/6 17:35
 */
@RestController
@ApiResource(name = "数据迁移控制器")
public class MigrationController {

    @Resource
    private MigrationService migrationService;

    /**
     * 获取所有可备份数据列表
     *
     * @return {@link ResponseData}
     * @author majianguo
     * @date 2021/7/6 17:37
     **/
    @GetResource(name = "获取所有可备份数据列表", path = "/dataMigration/getAllMigrationList")
    public ResponseData<List<MigrationRequest>> getAllMigrationList() {
        List<MigrationRequest> migrationRequestList = migrationService.getAllMigrationList();
        return new SuccessResponseData<>(migrationRequestList);
    }

    /**
     * 备份指定数据列表
     *
     * @return {@link cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData}
     * @author majianguo
     * @date 2021/7/7 11:11
     **/
    @GetResource(name = "备份指定数据列表", path = "/dataMigration/migrationSelectData")
    public ResponseData<String> migrationSelectData(@Validated(MigrationAggregationPOJO.export.class) MigrationAggregationPOJO migrationAggregationPOJO) {
        List<String> res = new ArrayList<>();
        for (String s : migrationAggregationPOJO.getAppAndModuleNameList()) {
            try {
                String decode = URLDecoder.decode(s, "UTF-8");
                res.add(decode);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        migrationAggregationPOJO.setAppAndModuleNameList(res);
        String migrationSelectDataStr = migrationService.migrationSelectData(migrationAggregationPOJO);
        return new SuccessResponseData<>(migrationSelectDataStr);
    }

    /**
     * 恢复备份数据
     *
     * @return {@link ResponseData}
     * @author majianguo
     * @date 2021/7/7 11:11
     **/
    @PostResource(name = "恢复备份数据", path = "/dataMigration/restoreData")
    public ResponseData<?> restoreData(@RequestPart("file") MultipartFile file, String type) {
        migrationService.restoreData(file, type);
        return new SuccessResponseData<>();
    }
}
