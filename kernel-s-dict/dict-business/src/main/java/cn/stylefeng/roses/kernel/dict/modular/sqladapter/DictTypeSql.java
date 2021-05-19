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
package cn.stylefeng.roses.kernel.dict.modular.sqladapter;

import cn.stylefeng.roses.kernel.db.api.sqladapter.AbstractSql;
import lombok.Getter;

/**
 * 创建数据库的sql，可用在租户的创建
 *
 * @author fengshuonan
 * @date 2019-07-16-13:06
 */
@Getter
public class DictTypeSql extends AbstractSql {

    @Override
    protected String mysql() {
        return "CREATE TABLE `sys_dict_type` (\n" +
                "  `dict_type_id` bigint(20) NOT NULL COMMENT '字典类型id',\n" +
                "  `dict_type_class` int(11) DEFAULT NULL COMMENT '字典类型： 1-业务类型，2-系统类型，参考 DictTypeClassEnum',\n" +
                "  `dict_type_bus_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典类型业务编码',\n" +
                "  `dict_type_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典类型编码',\n" +
                "  `dict_type_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典类型名称',\n" +
                "  `dict_type_name_pinyin` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典类型名称首字母拼音',\n" +
                "  `dict_type_desc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典类型描述',\n" +
                "  `status_flag` tinyint(4) DEFAULT NULL COMMENT '字典类型的状态：1-启用，2-禁用，参考 StatusEnum',\n" +
                "  `dict_type_sort` decimal(10,2) DEFAULT NULL COMMENT '排序，带小数点',\n" +
                "  `del_flag` char(1) CHARACTER SET utf8 NOT NULL DEFAULT 'N' COMMENT '是否删除：Y-被删除，N-未删除',\n" +
                "  `create_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "  `create_user` bigint(20) DEFAULT NULL COMMENT '创建用户id',\n" +
                "  `update_time` datetime DEFAULT NULL COMMENT '修改时间',\n" +
                "  `update_user` bigint(20) DEFAULT NULL COMMENT '修改用户id',\n" +
                "  PRIMARY KEY (`dict_type_id`) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='字典类型';";
    }

    @Override
    protected String sqlServer() {
        return "";
    }

    @Override
    protected String pgSql() {
        return "";
    }

    @Override
    protected String oracle() {
        return "";
    }
}
