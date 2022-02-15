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
package cn.stylefeng.roses.kernel.dict.modular.pojo.request;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.validator.api.validators.status.StatusValue;
import cn.stylefeng.roses.kernel.validator.api.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 字典类型，请求参数封装
 *
 * @author fengshuonan
 * @date 2020/10/30 11:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictTypeRequest extends BaseRequest {

    /**
     * 字典类型id
     */
    @NotNull(message = "id不能为空", groups = {edit.class, delete.class, detail.class, updateStatus.class})
    @ChineseDescription("字典类型id")
    private Long dictTypeId;

    /**
     * 字典类型： 1-业务类型，2-系统类型，参考 DictTypeClassEnum
     */
    @NotNull(message = "字典类型不能为空", groups = {add.class, edit.class})
    @ChineseDescription("字典类型")
    private Integer dictTypeClass;

    /**
     * 字典类型业务编码
     */
    @ChineseDescription("字典类型业务编码")
    private String dictTypeBusCode;

    /**
     * 字典类型编码
     */
    @NotBlank(message = "字典类型编码不能为空", groups = {add.class, edit.class, validateCode.class})
    @TableUniqueValue(
            message = "字典类型编码存在重复",
            groups = {add.class, edit.class},
            tableName = "sys_dict_type",
            columnName = "dict_type_code",
            idFieldName = "dict_type_id",
            excludeLogicDeleteItems = true)
    @ChineseDescription("字典类型编码")
    private String dictTypeCode;

    /**
     * 字典类型名称
     */
    @NotBlank(message = "字典类型名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("字典类型名称")
    private String dictTypeName;

    /**
     * 字典类型名词拼音
     */
    @ChineseDescription("字典类型名词拼音")
    private String dictTypeNamePinYin;

    /**
     * 字典类型描述
     */
    @ChineseDescription("字典类型描述")
    private String dictTypeDesc;

    /**
     * 字典类型的状态：1-启用，2-禁用，参考 StatusEnum
     */
    @NotNull(message = "状态不能为空", groups = {updateStatus.class})
    @StatusValue(groups = updateStatus.class)
    @ChineseDescription("字典类型的状态：1-启用，2-禁用")
    private Integer statusFlag;

    /**
     * 排序，带小数
     */
    @NotNull(message = "排序不能为空", groups = {add.class, edit.class})
    @ChineseDescription("排序")
    private BigDecimal dictTypeSort;

    /**
     * 参数校验分组：校验code是否可用
     */
    public @interface validateCode {

    }

}
