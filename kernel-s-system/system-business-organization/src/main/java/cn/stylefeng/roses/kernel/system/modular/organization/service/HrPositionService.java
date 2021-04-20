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
package cn.stylefeng.roses.kernel.system.modular.organization.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.PositionServiceApi;
import cn.stylefeng.roses.kernel.system.api.pojo.organization.HrPositionRequest;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.HrPosition;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 职位信息服务
 *
 * @author chenjinlong
 * @date 2020/11/04 11:07
 */
public interface HrPositionService extends IService<HrPosition>, PositionServiceApi {

    /**
     * 添加职位
     *
     * @param hrPositionRequest 请求参数
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    void add(HrPositionRequest hrPositionRequest);

    /**
     * 删除职位
     *
     * @param hrPositionRequest 请求参数
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    void del(HrPositionRequest hrPositionRequest);

    /**
     * 编辑职位
     *
     * @param hrPositionRequest 请求参数
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    void edit(HrPositionRequest hrPositionRequest);

    /**
     * 更新状态
     *
     * @param hrPositionRequest 请求参数
     * @author chenjinlong
     * @date 2020/11/18 23:00
     */
    void changeStatus(HrPositionRequest hrPositionRequest);

    /**
     * 查看详情
     *
     * @param hrPositionRequest 请求参数
     * @return 职位详情
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    HrPosition detail(HrPositionRequest hrPositionRequest);

    /**
     * 查询职位详情列表
     *
     * @param hrPositionRequest 请求参数
     * @return 职位详情列表
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    List<HrPosition> findList(HrPositionRequest hrPositionRequest);

    /**
     * 分页查询职位详情列表
     *
     * @param hrPositionRequest 请求参数
     * @return 职位详情分页列表
     * @author chenjinlong
     * @date 2020/11/04 11:07
     */
    PageResult<HrPosition> findPage(HrPositionRequest hrPositionRequest);

    /**
     * 批量删除系统职位
     *
     * @param hrPositionRequest 请求参数
     * @author fengshuonan
     * @date 2021/4/8 13:51
     */
    void batchDel(HrPositionRequest hrPositionRequest);

}
