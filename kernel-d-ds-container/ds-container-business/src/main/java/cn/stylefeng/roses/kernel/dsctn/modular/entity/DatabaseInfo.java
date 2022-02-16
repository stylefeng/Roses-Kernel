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
package cn.stylefeng.roses.kernel.dsctn.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据库信息表
 *
 * @author fengshuonan
 * @date 2020/11/1 0:15
 */
@TableName("sys_database_info")
@Data
@EqualsAndHashCode(callSuper = true)
public class DatabaseInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "db_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("主键id")
    private Long dbId;

    /**
     * 数据库名称（英文名称）
     */
    @TableField("db_name")
    @ChineseDescription("数据库名称（英文名称）")
    private String dbName;

    /**
     * jdbc的驱动类型
     */
    @TableField("jdbc_driver")
    @ChineseDescription("jdbc的驱动类型")
    private String jdbcDriver;

    /**
     * jdbc的url
     */
    @TableField("jdbc_url")
    @ChineseDescription("jdbc的url")
    private String jdbcUrl;

    /**
     * 数据库连接的账号
     */
    @TableField("username")
    @ChineseDescription("数据库连接的账号")
    private String username;

    /**
     * 数据库连接密码
     */
    @TableField("password")
    @ChineseDescription("数据库连接密码")
    private String password;

    /**
     * 数据库的schema名称，每种数据库的schema意义都不同
     */
    @TableField("schema_name")
    @ChineseDescription("数据库的schema名称")
    private String schemaName;

    /**
     * 状态标识：1-正常，2-无法连接
     */
    @TableField("status_flag")
    @ChineseDescription("状态标识：1-正常，2-无法连接")
    private Integer statusFlag;

    /**
     * 无法连接原因
     */
    @TableField("error_description")
    @ChineseDescription("无法连接原因")
    private String errorDescription;

    /**
     * 备注，摘要
     */
    @TableField("remarks")
    @ChineseDescription("备注")
    private String remarks;

    /**
     * 是否删除，Y-被删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private String delFlag;

}
