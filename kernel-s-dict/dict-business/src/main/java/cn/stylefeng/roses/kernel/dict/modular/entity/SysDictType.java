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
package cn.stylefeng.roses.kernel.dict.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 字典类型表，一个字典类型下有多个字典
 *
 * @author fengshuonan
 * @date 2020/10/30 10:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_dict_type")
public class SysDictType extends BaseEntity {

    /**
     * 字典类型id
     */
    @TableId(value = "dict_type_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("字典类型id")
    private Long dictTypeId;

    /**
     * 字典类型： 1-业务类型，2-系统类型，参考 DictTypeClassEnum
     */
    @TableField("dict_type_class")
    @ChineseDescription("字典类型")
    private Integer dictTypeClass;

    /**
     * 字典类型编码
     */
    @TableField("dict_type_code")
    @ChineseDescription("字典类型编码")
    private String dictTypeCode;

    /**
     * 字典类型业务编码
     */
    @TableField("dict_type_bus_code")
    @ChineseDescription("字典类型业务编码")
    private String dictTypeBusCode;

    /**
     * 字典类型名称
     */
    @TableField("dict_type_name")
    @ChineseDescription("字典类型名称")
    private String dictTypeName;

    /**
     * 字典类型名称拼音
     */
    @TableField("dict_type_name_pinyin")
    @ChineseDescription("字典类型名词拼音")
    private String dictTypeNamePinyin;

    /**
     * 字典类型描述
     */
    @TableField("dict_type_desc")
    @ChineseDescription("字典类型描述")
    private String dictTypeDesc;

    /**
     * 字典类型的状态：1-启用，2-禁用，参考 StatusEnum
     */
    @TableField("status_flag")
    @ChineseDescription("字典类型的状态：1-启用，2-禁用")
    private Integer statusFlag;

    /**
     * 删除标记 Y-已删除，N-未删除，参考 YesOrNotEnum
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @ChineseDescription("删除标记")
    private String delFlag;

    /**
     * 排序，带小数点
     */
    @TableField(value = "dict_type_sort")
    @ChineseDescription("排序")
    private BigDecimal dictTypeSort;

}
