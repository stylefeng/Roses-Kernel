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
package cn.stylefeng.roses.kernel.log.api;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.log.api.pojo.manage.LogManagerRequest;
import cn.stylefeng.roses.kernel.log.api.pojo.record.LogRecordDTO;

import java.util.List;

/**
 * 日志管理相关的接口
 * <p>
 * 接口有多种实现，例如基于文件存储的日志，基于数据库存储的日志，基于es存储的日志
 *
 * @author fengshuonan
 * @date 2020/10/27 16:19
 */
public interface LogManagerApi {

    /**
     * 查询日志列表
     *
     * @param logManagerRequest 查询条件
     * @return 返回查询日志列表
     * @author fengshuonan
     * @date 2020/10/28 11:27
     */
    List<LogRecordDTO> findList(LogManagerRequest logManagerRequest);

    /**
     * 查询日志列表
     *
     * @param logManagerRequest 查询条件
     * @return 返回查询日志列表分页结果
     * @author luojie
     * @date 2020/11/3 10:40
     */
    PageResult<LogRecordDTO> findPage(LogManagerRequest logManagerRequest);

    /**
     * 批量删除日志
     * <p>
     * 删除日志条件必须传入开始时间、结束时间、服务名称三个参数
     *
     * @param logManagerRequest 参数的封装
     * @author fengshuonan
     * @date 2020/10/28 11:47
     */
    void del(LogManagerRequest logManagerRequest);

    /**
     * 查询日志详情
     *
     * @author chenjinlong
     * @date 2021/2/1 19:47
     */
    LogRecordDTO detail(LogManagerRequest logManagerRequest);

}
