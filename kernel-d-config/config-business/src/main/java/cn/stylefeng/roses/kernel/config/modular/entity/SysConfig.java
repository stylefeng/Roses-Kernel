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
package cn.stylefeng.roses.kernel.config.modular.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 参数配置
 * </p>
 *
 * @author stylefeng
 * @date 2019/6/20 13:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_config")
public class SysConfig extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "config_id", type = IdType.ASSIGN_ID)
    @ChineseDescription("主键id")
    private Long configId;

    /**
     * 名称
     */
    @TableField("config_name")
    @ChineseDescription("名称")
    private String configName;

    /**
     * 编码
     */
    @TableField("config_code")
    @ChineseDescription("编码")
    private String configCode;

    /**
     * 属性值
     */
    @TableField("config_value")
    @ChineseDescription("属性值")
    private String configValue;

    /**
     * 是否是系统参数：Y-是，N-否
     */
    @TableField("sys_flag")
    @ChineseDescription("是否是系统参数：Y-是，N-否")
    private String sysFlag;

    /**
     * 备注
     */
    @TableField("remark")
    @ChineseDescription("备注")
    private String remark;

    /**
     * 状态：1-正常，2停用
     */
    @TableField("status_flag")
    @ChineseDescription("状态：1-正常，2停用")
    private Integer statusFlag;

    /**
     * 常量所属分类的编码，来自于“常量的分类”字典
     */
    @TableField("group_code")
    @ChineseDescription("常量所属分类的编码")
    private String groupCode;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @ChineseDescription("是否删除：Y-被删除，N-未删除")
    private String delFlag;

}
