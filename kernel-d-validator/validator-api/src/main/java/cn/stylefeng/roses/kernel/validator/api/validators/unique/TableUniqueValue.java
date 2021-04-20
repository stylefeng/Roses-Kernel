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
package cn.stylefeng.roses.kernel.validator.api.validators.unique;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static cn.stylefeng.roses.kernel.validator.api.constants.ValidatorConstants.DEFAULT_LOGIC_DELETE_FIELD_NAME;
import static cn.stylefeng.roses.kernel.validator.api.constants.ValidatorConstants.DEFAULT_LOGIC_DELETE_FIELD_VALUE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证表的的某个字段值是否在是唯一值
 * <p>
 * 一般用来校验code字段，例如同一个表中，code字段不能重复
 *
 * @author fengshuonan
 * @date 2020/11/4 14:06
 */
@Documented
@Constraint(validatedBy = TableUniqueValueValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableUniqueValue {

    String message() default "库中存在重复编码，请更换该编码值";

    Class[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 表名称，例如 sys_user
     */
    String tableName();

    /**
     * 列名称，例如 user_code
     */
    String columnName();

    /**
     * 数据库主键id的字段名，例如 user_id
     */
    String idFieldName() default "id";

    /**
     * 是否开启逻辑删除校验，默认是关闭的
     * <p>
     * 关于为何开启逻辑删除校验：
     * <p>
     * 若项目中某个表包含控制逻辑删除的字段，我们在进行唯一值校验的时候要排除这种状态的记录，所以需要用到这个功能
     */
    boolean excludeLogicDeleteItems() default false;

    /**
     * 逻辑删除的字段名称
     */
    String logicDeleteFieldName() default DEFAULT_LOGIC_DELETE_FIELD_NAME;

    /**
     * 默认逻辑删除的值（Y是已删除）
     */
    String logicDeleteValue() default DEFAULT_LOGIC_DELETE_FIELD_VALUE;

    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        TableUniqueValue[] value();
    }
}
