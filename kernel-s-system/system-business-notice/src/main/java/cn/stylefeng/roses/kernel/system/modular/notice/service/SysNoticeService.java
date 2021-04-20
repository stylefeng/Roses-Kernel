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
package cn.stylefeng.roses.kernel.system.modular.notice.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.system.api.NoticeServiceApi;
import cn.stylefeng.roses.kernel.system.modular.notice.entity.SysNotice;
import cn.stylefeng.roses.kernel.system.api.pojo.notice.SysNoticeRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 通知服务类
 *
 * @author liuhanqing
 * @date 2021/1/8 19:56
 */
public interface SysNoticeService extends IService<SysNotice>, NoticeServiceApi {

    /**
     * 添加系统应用
     *
     * @param sysNoticeRequest 添加参数
     * @author liuhanqing
     * @date 2021/1/9 14:57
     */
    void add(SysNoticeRequest sysNoticeRequest);

    /**
     * 删除系统应用
     *
     * @param sysNoticeRequest 删除参数
     * @author liuhanqing
     * @date 2021/1/9 14:57
     */
    void del(SysNoticeRequest sysNoticeRequest);

    /**
     * 编辑系统应用
     *
     * @param sysNoticeRequest 编辑参数
     * @author liuhanqing
     * @date 2021/1/9 14:58
     */
    void edit(SysNoticeRequest sysNoticeRequest);

    /**
     * 查看系统应用
     *
     * @param sysNoticeRequest 查看参数
     * @return 系统应用
     * @author liuhanqing
     * @date 2021/1/9 14:56
     */
    SysNotice detail(SysNoticeRequest sysNoticeRequest);

    /**
     * 查询系统应用
     *
     * @param sysNoticeRequest 查询参数
     * @return 查询分页结果
     * @author liuhanqing
     * @date 2021/1/9 14:56
     */
    PageResult<SysNotice> findPage(SysNoticeRequest sysNoticeRequest);

    /**
     * 系统应用列表
     *
     * @param sysNoticeRequest 查询参数
     * @return 系统应用列表
     * @author liuhanqing
     * @date 2021/1/9 14:56
     */
    List<SysNotice> findList(SysNoticeRequest sysNoticeRequest);

}
