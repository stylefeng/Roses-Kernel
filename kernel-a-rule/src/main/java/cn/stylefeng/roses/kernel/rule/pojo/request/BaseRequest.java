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
package cn.stylefeng.roses.kernel.rule.pojo.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 请求基类，所有接口请求可继承此类
 *
 * @author fengshuonan
 * @date 2020/10/14 18:12
 */
@Data
public class BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 开始时间
     */
    private String searchBeginTime;

    /**
     * 结束时间
     */
    private String searchEndTime;

    /**
     * 分页：每页大小（默认20）
     */
    private Integer pageSize;

    /**
     * 分页：第几页（从1开始）
     */
    private Integer pageNo;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 正序或者倒序排列（asc 或 desc）
     */
    private String sortBy;

    /**
     * 其他参数（如有需要）
     */
    private Map<String, Object> otherParams;

    /**
     * 唯一请求号
     */
    private String requestNo;

    /**
     * 业务节点id
     */
    private String spanId;

    /**
     * 当前登录用户的token
     */
    private String token;

    /**
     * 参数校验分组：分页
     */
    public @interface page {
    }

    /**
     * 参数校验分组：查询所有
     */
    public @interface list {
    }

    /**
     * 参数校验分组：增加
     */
    public @interface add {
    }

    /**
     * 参数校验分组：编辑
     */
    public @interface edit {
    }

    /**
     * 参数校验分组：删除
     */
    public @interface delete {
    }

    /**
     * 参数校验分组：详情
     */
    public @interface detail {
    }

    /**
     * 参数校验分组：导出
     */
    public @interface export {
    }

    /**
     * 参数校验分组：修改状态
     */
    public @interface updateStatus {
    }

    /**
     * 参数校验分组：批量删除
     */
    public @interface batchDelete {
    }

}
