package cn.stylefeng.roses.kernel.config.api.exception.enums;

import cn.stylefeng.roses.kernel.config.api.constants.ConfigConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import lombok.Getter;

/**
 * 系统配置表相关的异常枚举
 *
 * @author fengshuonan
 * @date 2020/10/16 10:53
 */
@Getter
public enum ConfigExceptionEnum implements AbstractExceptionEnum {

    /**
     * 数据库操作未知异常
     */
    DAO_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "01", "配置表信息操作异常"),

    /**
     * 系统配置表不存在该配置
     * <p>
     * 使用时候，用StrUtil.format()将配置名称带上
     */
    CONFIG_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "02", "系统配置表不存在该配置，请检查该配置是否存在，配置名称：{}"),

    /**
     * 系统配置表获取值时，强转类型异常
     * <p>
     * 使用时候，用StrUtil.format()将配置名称带上
     */
    CONVERT_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "03", "获取系统配置值时，强转类型异常，配置名称：{}，配置值：{}，转化类型：{}"),

    /**
     * 获取不到application.yml中的数据库配置
     */
    APP_DB_CONFIG_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "04", "获取不到application.yml中的数据库配置，无法从数据库加载系统配置表"),

    /**
     * 初始化系统配置表失败，找不到com.mysql.cj.jdbc.Driver驱动类
     */
    CLASS_NOT_FOUND_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "06", "初始化系统配置表失败，找不到com.mysql.cj.jdbc.Driver驱动类"),

    /**
     * 初始化系统配置表失败，执行查询语句失败
     */
    CONFIG_SQL_EXE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "07", "初始化系统配置表失败，执行查询语句失败"),

    /**
     * 系统参数配置编码重复
     */
    CONFIG_CODE_REPEAT(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "08", "系统参数配置编码重复，请检查code参数"),

    /**
     * 删除失败，不能删除系统参数
     */
    CONFIG_SYS_CAN_NOT_DELETE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "09", "删除失败，不能删除系统参数"),

    /**
     * 配置容器是空，请先初始化配置容器
     */
    CONFIG_CONTAINER_IS_NULL(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "10", "配置容器为空，请先初始化配置容器，请调用ConfigContext.setConfigApi()初始化");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    ConfigExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
