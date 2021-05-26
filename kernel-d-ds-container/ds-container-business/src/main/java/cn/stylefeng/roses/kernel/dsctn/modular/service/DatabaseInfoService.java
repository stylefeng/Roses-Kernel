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
package cn.stylefeng.roses.kernel.dsctn.modular.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dsctn.api.DataSourceApi;
import cn.stylefeng.roses.kernel.dsctn.api.pojo.request.DatabaseInfoRequest;
import cn.stylefeng.roses.kernel.dsctn.modular.entity.DatabaseInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 数据库信息表 服务类
 *
 * @author fengshuonan
 * @date 2020/11/1 21:46
 */
public interface DatabaseInfoService extends IService<DatabaseInfo>, DataSourceApi {

    /**
     * 删除，删除会导致某些用该数据源的service操作失败
     *
     * @param databaseInfoRequest 删除参数
     * @author fengshuonan
     * @date 2020/11/1 21:47
     */
    void del(DatabaseInfoRequest databaseInfoRequest);

    /**
     * 编辑数据库信息
     *
     * @param databaseInfoRequest 编辑参数
     * @author fengshuonan
     * @date 2020/11/1 21:47
     */
    void edit(DatabaseInfoRequest databaseInfoRequest);

    /**
     * 查询数据库信息详情
     *
     * @param databaseInfoRequest 查询参数
     * @author fengshuonan
     * @date 2021/1/23 20:30
     */
    DatabaseInfo detail(DatabaseInfoRequest databaseInfoRequest);

    /**
     * 查询数据库信息
     *
     * @param databaseInfoRequest 查询参数
     * @return 查询分页结果
     * @author fengshuonan
     * @date 2020/11/1 21:47
     */
    PageResult<DatabaseInfo> findPage(DatabaseInfoRequest databaseInfoRequest);

    /**
     * 列表查询
     *
     * @param databaseInfoRequest 参数
     * @author liuhanqing
     * @date 2021/2/2 21:21
     */
    List<DatabaseInfo> findList(DatabaseInfoRequest databaseInfoRequest);

    /**
     * 校验数据库连接的正确性
     *
     * @param param 参数
     * @author fengshuonan
     * @date 2021/4/22 10:46
     */
    void validateConnection(DatabaseInfoRequest param);

}
