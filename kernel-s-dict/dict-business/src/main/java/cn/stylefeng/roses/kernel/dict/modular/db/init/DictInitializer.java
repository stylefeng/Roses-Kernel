package cn.stylefeng.roses.kernel.dict.modular.db.init;

import cn.stylefeng.roses.kernel.db.init.actuator.DbInitializer;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDict;
import org.springframework.stereotype.Component;

/**
 * 字典数据库初始化程序
 *
 * @author majianguo
 * @date 2020/12/9 上午11:02
 */
@Component
public class DictInitializer extends DbInitializer {

    @Override
    protected String getTableInitSql() {
        return "CREATE TABLE `sys_dict`  (\n" +
                "  `dict_id` bigint(20) NOT NULL COMMENT '字典id',\n" +
                "  `dict_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典编码',\n" +
                "  `dict_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典名称',\n" +
                "  `dict_name_pinyin` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典名称首字母',\n" +
                "  `dict_encode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典编码',\n" +
                "  `dict_type_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典类型的编码',\n" +
                "  `dict_short_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典简称',\n" +
                "  `dict_short_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典简称的编码',\n" +
                "  `dict_parent_id` bigint(20) NOT NULL COMMENT '上级字典的id(如果没有上级字典id，则为-1)',\n" +
                "  `status_flag` tinyint(4) NOT NULL COMMENT '状态：(1-启用,2-禁用),参考 StatusEnum',\n" +
                "  `dict_sort` decimal(10, 2) NULL DEFAULT NULL COMMENT '排序，带小数点',\n" +
                "  `dict_pids` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '父id集合',\n" +
                "  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'N' COMMENT '是否删除，Y-被删除，N-未删除',\n" +
                "  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',\n" +
                "  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建用户id',\n" +
                "  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',\n" +
                "  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改用户id',\n" +
                "  PRIMARY KEY (`dict_id`) USING BTREE\n" +
                ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;";
    }

    @Override
    protected String getTableName() {
        return "sys_dict";
    }

    @Override
    protected Class<?> getEntityClass() {
        return SysDict.class;
    }
}
