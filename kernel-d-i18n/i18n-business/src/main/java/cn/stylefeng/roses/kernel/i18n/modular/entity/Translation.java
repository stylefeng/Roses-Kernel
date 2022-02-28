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
package cn.stylefeng.roses.kernel.i18n.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 多语言表
 *
 * @author fengshuonan
 * @date 2021/1/24 19:12
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sys_translation")
@Data
public class Translation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "tran_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("主键id")
    private Long tranId;

    /**
     * 编码
     */
    @TableField("tran_code")
    @ChineseDescription("编码")
    private String tranCode;

    /**
     * 多语言条例名称
     */
    @TableField("tran_name")
    @ChineseDescription("多语言条例名称")
    private String tranName;

    /**
     * 语种字典
     */
    @TableField("tran_language_code")
    @ChineseDescription("语种字典")
    private String tranLanguageCode;

    /**
     * 翻译的值
     */
    @TableField("tran_value")
    @ChineseDescription("翻译的值")
    private String tranValue;

}
