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
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 字典请求参数封装
 *
 * @author fengshuonan
 * @date 2020/10/30 11:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictRequest extends BaseRequest {

    /**
     * 字典id
     */
    @NotNull(message = "id不能为空", groups = {edit.class, delete.class, detail.class, updateStatus.class})
    @ChineseDescription("字典id")
    private Long dictId;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空", groups = {add.class, edit.class, validateAvailable.class})
    @ChineseDescription("字典编码")
    private String dictCode;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("字典名称")
    private String dictName;

    /**
     * 字典名称拼音
     */
    @ChineseDescription("字典名称拼音")
    private String dictNamePinYin;

    /**
     * 字典编码
     */
    @ChineseDescription("字典编码")
    private String dictEncode;

    /**
     * 字典类型编码
     */
    @NotBlank(message = "字典类型编码不能为空", groups = {add.class, edit.class, treeList.class, dictZTree.class})
    @ChineseDescription("字典类型编码")
    private String dictTypeCode;

    /**
     * 字典简称
     */
    @ChineseDescription("字典简称")
    private String dictShortName;

    /**
     * 字典简称的编码
     */
    @ChineseDescription("字典简称的编码")
    private String dictShortCode;

    /**
     * 上级字典的id
     * <p>
     * 字典列表是可以有树形结构的，但是字典类型没有树形结构
     * <p>
     * 如果没有上级字典id，则为-1
     */
    @ChineseDescription("上级字典的id")
    private Long dictParentId;

    /**
     * 状态(1:启用,2:禁用)，参考 StatusEnum
     */
    @NotNull(message = "状态不能为空", groups = {updateStatus.class})
    @StatusValue(groups = updateStatus.class)
    @ChineseDescription("状态")
    private Integer statusFlag;

    /**
     * 排序，带小数点
     */
    @ChineseDescription("排序")
    @NotNull(message = "排序不能为空", groups = {add.class, edit.class})
    private BigDecimal dictSort;

    /**
     * 所有的父级id,逗号分隔
     */
    @ChineseDescription("所有的父级id")
    private String dictPids;

    /**
     * 字典类型id，用在作为查询条件
     */
    @ChineseDescription("字典类型id")
    private Long dictTypeId;

    /**
     * 获取树形列表
     */
    public @interface treeList {

    }

    /**
     * 校验编码是否重复
     */
    public @interface validateAvailable {

    }

    /**
     * 校验ztree必备参数
     */
    public @interface dictZTree {

    }

}
