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
package cn.stylefeng.roses.kernel.dsctn.api.pojo.request;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.validator.api.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 数据库信息表
 *
 * @author fengshuonan
 * @date 2020/11/1 21:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DatabaseInfoRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @NotNull(message = "dbId不能为空", groups = {edit.class, delete.class, detail.class})
    @ChineseDescription("主键id")
    private Long dbId;

    /**
     * 数据库名称（英文名称）
     */
    @NotBlank(message = "数据库名称不能为空", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "数据库名称存在重复",
            groups = {add.class, edit.class},
            tableName = "sys_database_info",
            columnName = "db_name",
            idFieldName = "db_id",
            excludeLogicDeleteItems = true)
    @ChineseDescription("数据库名称（英文名称）")
    private String dbName;

    /**
     * jdbc的驱动类型
     */
    @NotBlank(message = "jdbc的驱动类型为空", groups = {add.class, edit.class})
    @ChineseDescription("jdbc的驱动类型")
    private String jdbcDriver;

    /**
     * jdbc的url
     */
    @NotBlank(message = "jdbc的url", groups = {add.class, edit.class})
    @ChineseDescription("jdbc的url")
    private String jdbcUrl;

    /**
     * 数据库连接的账号
     */
    @NotBlank(message = "数据库连接的账号", groups = {add.class, edit.class})
    @ChineseDescription("数据库连接的账号")
    private String username;

    /**
     * 数据库连接密码
     */
    @NotBlank(message = "数据库连接密码", groups = {add.class, edit.class})
    @ChineseDescription("数据库连接密码")
    private String password;

    /**
     * 数据库schemaName，注意，每种数据库的schema意义不同
     */
    @ChineseDescription("数据库schemaName")
    private String schemaName;

    /**
     * 状态标识：1-正常，2-无法连接
     */
    @ChineseDescription("状态标识：1-正常，2-无法连接")
    private Integer statusFlag;

    /**
     * 备注，摘要
     */
    @ChineseDescription("备注")
    private String remarks;

}
