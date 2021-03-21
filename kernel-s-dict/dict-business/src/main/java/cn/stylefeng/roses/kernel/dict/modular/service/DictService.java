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
package cn.stylefeng.roses.kernel.dict.modular.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.api.DictApi;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDict;
import cn.stylefeng.roses.kernel.dict.modular.pojo.TreeDictInfo;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 字典详情管理
 *
 * @author fengshuonan
 * @date 2020/10/29 17:43
 */
public interface DictService extends IService<SysDict>, DictApi {

    /**
     * 新增字典
     *
     * @param dictRequest 字典对象
     * @author fengshuonan
     * @date 2020/10/29 17:43
     */
    void add(DictRequest dictRequest);

    /**
     * 删除字典
     *
     * @param dictRequest 字典对象
     * @author fengshuonan
     * @date 2020/10/29 17:43
     */
    void del(DictRequest dictRequest);

    /**
     * 修改字典
     *
     * @param dictRequest 字典对象
     * @author fengshuonan
     * @date 2020/10/29 17:43
     */
    void edit(DictRequest dictRequest);

    /**
     * 查询字典详情
     *
     * @param dictRequest 字典id
     * @return 字典的详情
     * @author fengshuonan
     * @date 2020/10/30 16:15
     */
    SysDict detail(DictRequest dictRequest);

    /**
     * 获取字典列表
     *
     * @param dictRequest 字典对象
     * @return 字典列表
     * @author fengshuonan
     * @date 2020/10/29 18:48
     */
    List<SysDict> findList(DictRequest dictRequest);

    /**
     * 获取字典列表（带分页）
     *
     * @param dictRequest 查询条件
     * @return 带分页的列表
     * @author fengshuonan
     * @date 2020/10/29 18:48
     */
    PageResult<SysDict> findPage(DictRequest dictRequest);

    /**
     * 获取树形字典列表（antdv在用）
     *
     * @param dictRequest 查询条件
     * @return 字典信息列表
     * @author fengshuonan
     * @date 2020/10/29 18:50
     */
    List<TreeDictInfo> getTreeDictList(DictRequest dictRequest);


}
