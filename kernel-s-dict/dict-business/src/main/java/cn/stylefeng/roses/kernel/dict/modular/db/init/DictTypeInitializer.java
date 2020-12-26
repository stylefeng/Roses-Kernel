package cn.stylefeng.roses.kernel.dict.modular.db.init;

import cn.stylefeng.roses.kernel.db.init.actuator.DbInitializer;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDictType;
import org.springframework.stereotype.Component;

/**
 * 字典数据库初始化程序
 *
 * @author majianguo
 * @date 2020/12/9 上午11:02
 */
@Component
public class DictTypeInitializer extends DbInitializer {

    @Override
    protected String getTableInitSql() {
        return "CREATE TABLE `sys_dict_type`  (\n" +
                "  `dict_type_id` bigint(20) NOT NULL COMMENT '字典类型id',\n" +
                "  `dict_type_class` int(2) NULL DEFAULT NULL COMMENT '字典类型： 1-业务类型，2-系统类型，参考 DictTypeClassEnum',\n" +
                "  `dict_type_bus_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典类型业务编码',\n" +
                "  `dict_type_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典类型编码',\n" +
                "  `dict_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典类型名称',\n" +
                "  `dict_type_name_pinyin` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典类型名称首字母拼音',\n" +
                "  `dict_type_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典类型描述',\n" +
                "  `status_flag` tinyint(4) NULL DEFAULT NULL COMMENT '字典类型的状态：1-启用，2-禁用，参考 StatusEnum',\n" +
                "  `dict_type_sort` decimal(10, 2) NULL DEFAULT NULL COMMENT '排序，带小数点',\n" +
                "  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'N' COMMENT '是否删除：Y-被删除，N-未删除',\n" +
                "  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',\n" +
                "  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建用户id',\n" +
                "  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',\n" +
                "  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改用户id',\n" +
                "  PRIMARY KEY (`dict_type_id`) USING BTREE\n" +
                ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典类型表，一个字典类型下有多个字典' ROW_FORMAT = Dynamic;";
    }

    @Override
    protected String getTableName() {
        return "sys_dict_type";
    }

    @Override
    protected Class<?> getEntityClass() {
        return SysDictType.class;
    }
}
