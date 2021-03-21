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
package cn.stylefeng.roses.kernel.message.db.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.message.api.pojo.request.MessageRequest;
import cn.stylefeng.roses.kernel.message.db.entity.SysMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统消息 service接口
 *
 * @author liuhanqing
 * @date 2020/12/31 20:09
 */
public interface SysMessageService extends IService<SysMessage> {

    /**
     * 新增
     *
     * @param messageRequest 参数对象
     * @author liuhanqing
     * @date 2021/2/2 20:48
     */
    void add(MessageRequest messageRequest);

    /**
     * 删除
     *
     * @param messageRequest 参数对象
     * @author liuhanqing
     * @date 2021/1/26 12:52
     */
    void del(MessageRequest messageRequest);

    /**
     * 修改
     *
     * @param messageRequest 参数对象
     * @author liuhanqing
     * @date 2021/2/2 20:48
     */
    void edit(MessageRequest messageRequest);

    /**
     * 查询-详情-根据主键id
     *
     * @param messageRequest 参数对象
     * @author liuhanqing
     * @date 2021/2/2 20:48
     */
    SysMessage detail(MessageRequest messageRequest);

    /**
     * 分页查询
     *
     * @param messageRequest 参数
     * @author liuhanqing
     * @date 2021/2/2 20:48
     */
    PageResult<SysMessage> findPage(MessageRequest messageRequest);

    /**
     * 列表查询
     *
     * @param messageRequest 参数
     * @author liuhanqing
     * @date 2021/1/8 15:21
     */
    List<SysMessage> findList(MessageRequest messageRequest);

    /**
     * 数量查询
     *
     * @param messageRequest 参数
     * @author liuhanqing
     * @date 2021/1/11 19:21
     */
    Integer findCount(MessageRequest messageRequest);

}
