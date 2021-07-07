package cn.stylefeng.roses.kernel.migration.api.enums;

import lombok.Getter;

/**
 * 迁移类型枚举
 *
 * @author majianguo
 * @date 2021/7/6 16:25
 */
@Getter
public enum MigrationAggregationTypeEnum {

    /**
     * 全量迁移
     */
    MIGRATION_FULL("FULL", "全量迁移"),

    /**
     * 增量迁移
     */
    MIGRATION_INCREMENTAL("INCREMENTAL", "增量迁移");

    private final String code;

    private final String name;

    MigrationAggregationTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
