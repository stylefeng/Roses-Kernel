package cn.stylefeng.roses.kernel.migration.web.pojo;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import lombok.Data;

import java.util.List;

/**
 * 迁移数据请求对象
 *
 * @author majianguo
 * @date 2021/7/7 9:15
 */
@Data
public class MigrationRequest {

    /**
     * 应用名称
     */
    @ChineseDescription("应用名称")
    private String appName;

    /**
     * 模块列表
     */
    @ChineseDescription("模块列表")
    private List<String> moduleNames;

}
