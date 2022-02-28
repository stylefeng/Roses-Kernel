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
package cn.stylefeng.roses.kernel.system.modular.app.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统应用表
 *
 * @author fengshuonan
 * @date 2020/11/24 21:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_app")
public class SysApp extends BaseEntity {

    /**
     * 主键id
     */
    @TableId("app_id")
    @ChineseDescription("主键id")
    private Long appId;

    /**
     * 应用名称
     */
    @TableField("app_name")
    @ChineseDescription("应用名称")
    private String appName;

    /**
     * 编码
     */
    @TableField("app_code")
    @ChineseDescription("编码")
    private String appCode;

    /**
     * 应用图标
     */
    @TableField("app_icon")
    @ChineseDescription("应用图标")
    private String appIcon;

    /**
     * 是否默认激活：Y-是，N-否，激活的应用下的菜单会在首页默认展开
     */
    @TableField("active_flag")
    @ChineseDescription("是否默认激活：Y-是，N-否，激活的应用下的菜单会在首页默认展开")
    private String activeFlag;

    /**
     * 状态：1-启用，2-禁用
     */
    @TableField("status_flag")
    @ChineseDescription("状态：1-启用，2-禁用")
    private Integer statusFlag;

    /**
     * 是否删除：Y-已删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @ChineseDescription("是否删除：Y-已删除，N-未删除")
    private String delFlag;

    /**
     * 排序-升序
     */
    @TableField("app_sort")
    @ChineseDescription("排序-升序")
    private Integer appSort;
}
