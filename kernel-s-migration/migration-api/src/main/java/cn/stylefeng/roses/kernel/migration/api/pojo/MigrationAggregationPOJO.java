package cn.stylefeng.roses.kernel.migration.api.pojo;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 数据迁移聚合类
 *
 * @author majianguo
 * @date 2021/7/6 16:10
 */
@Data
public class MigrationAggregationPOJO extends BaseRequest {

    /**
     * 应用和模块名称列表
     */
    @NotNull(message = "模块名称不能为空", groups = {export.class, restore.class})
    private List<String> appAndModuleNameList;

    /**
     * 数据集
     */
    @NotNull(message = "数据集不能为空", groups = {restore.class})
    private Map<String, MigrationInfo> data;

    /**
     * 导出
     */
    public @interface export {

    }

    /**
     * 恢复
     */
    public @interface restore {

    }
}
