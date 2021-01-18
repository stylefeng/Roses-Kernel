package cn.stylefeng.roses.kernel.db.api.exception.enums;

import cn.stylefeng.roses.kernel.db.api.constants.DbConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import lombok.Getter;

/**
 * Flyway相关异常枚举
 *
 * @author fengshuonan
 * @date 2021/1/18 22:59
 */
@Getter
public enum FlywayExceptionEnum implements AbstractExceptionEnum {

    /**
     * 获取不到application.yml中的数据库配置
     */
    DB_CONFIG_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "01", "获取不到application.yml中的数据库配置，无法为flyway创建数据库链接"),

    /**
     * flyway执行迁移异常
     */
    FLYWAY_MIGRATE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DbConstants.DB_EXCEPTION_STEP_CODE + "02", "脚本错误，flyway执行迁移异常");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FlywayExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
