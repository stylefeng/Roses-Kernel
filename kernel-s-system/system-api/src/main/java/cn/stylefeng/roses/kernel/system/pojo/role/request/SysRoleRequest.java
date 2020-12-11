/*
Copyright [2020] [https://www.stylefeng.cn]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Guns源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns-separation
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns-separation
6.若您的项目无法满足以上几点，可申请商业授权，获取Guns商业授权许可，请在官网购买授权，地址为 https://www.stylefeng.cn
 */
package cn.stylefeng.roses.kernel.system.pojo.role.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.validator.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.List;

/**
 * 系统角色参数
 *
 * @author majianguo
 * @date 2020/11/5 下午4:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "id不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class, updateStatus.class, grantResource.class, grantData.class})
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空，请检查name参数", groups = {add.class, edit.class})
    private String name;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空，请检查code参数", groups = {add.class, edit.class})
    @TableUniqueValue(
            message = "编码存在重复，请检查code参数",
            groups = {add.class, edit.class},
            tableName = "sys_menu",
            columnName = "code")
    private String code;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空，请检查sort参数", groups = {add.class, edit.class})
    private BigDecimal sort;

    /**
     * 数据范围类型（字典 10全部数据 20本部门及以下数据 30本部门数据 40仅本人数据 50自定义数据）
     */
    @Null(message = "数据范围类型应该为空， 请移除dataScopeType参数", groups = {add.class, edit.class})
    @NotNull(message = "数据范围类型不能为空，请检查dataScopeType参数", groups = {grantData.class})
    private Integer dataScopeType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态（字典 1正常 2停用）
     */
    private Integer statusFlag;

    /**
     * 授权资源
     */
    @NotNull(message = "授权资源不能为空，请检查grantResourceList参数", groups = {grantResource.class})
    private List<String> grantResourceList;

    /**
     * 授权数据
     */
    @NotNull(message = "授权数据不能为空，请检查grantOrgIdList参数", groups = {grantData.class})
    private List<Long> grantOrgIdList;

    /**
     * 参数校验分组：授权资源
     */
    public @interface grantResource {
    }

    /**
     * 参数校验分组：授权数据
     */
    public @interface grantData {
    }

}
