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
package cn.stylefeng.roses.kernel.config.modular.param;

import cn.stylefeng.roses.kernel.rule.annotation.ChineseDescription;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.validator.api.validators.flag.FlagValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统参数配置参数
 *
 * @author fengshuonan
 * @date 2020/4/14 10:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysConfigParam extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "configId不能为空", groups = {edit.class, delete.class, detail.class})
    @ChineseDescription("主键")
    private Long configId;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = {add.class, edit.class})
    @ChineseDescription("名称")
    private String configName;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空", groups = {add.class, edit.class})
    @ChineseDescription("编码")
    private String configCode;

    /**
     * 配置值
     */
    @NotBlank(message = "配置值不能为空", groups = {add.class, edit.class})
    @ChineseDescription("配置值")
    private String configValue;

    /**
     * 是否是系统参数：Y-是，N-否
     */
    @NotBlank(message = "是否是系统参数不能为空", groups = {add.class, edit.class})
    @FlagValue(message = "是否是系统参数格式错误，正确格式应该Y或者N", groups = {add.class, edit.class})
    @ChineseDescription("是否系统参数")
    private String sysFlag;

    /**
     * 备注
     */
    @ChineseDescription("备注")
    private String remark;

    /**
     * 状态：1-正常，2停用
     */
    @ChineseDescription("状态")
    private Integer statusFlag;

    /**
     * 常量所属分类的编码，来自于“常量的分类”字典
     */
    @NotBlank(message = "量所属分类的编码不能为空", groups = {add.class, edit.class})
    @ChineseDescription("常量所属分类的编码")
    private String groupCode;

}
