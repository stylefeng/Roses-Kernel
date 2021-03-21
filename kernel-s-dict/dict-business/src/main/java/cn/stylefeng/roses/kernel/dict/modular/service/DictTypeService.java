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
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDictType;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictTypeRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 字典类型管理
 *
 * @author fengshuonan
 * @date 2020/10/29 18:54
 */
public interface DictTypeService extends IService<SysDictType> {

    /**
     * 添加字典类型
     *
     * @param dictTypeRequest 字典类型请求
     * @author fengshuonan
     * @date 2020/10/29 18:55
     */
    void add(DictTypeRequest dictTypeRequest);

    /**
     * 删除字典类型
     *
     * @param dictTypeRequest 字典类型请求
     * @author fengshuonan
     * @date 2020/10/29 18:55
     */
    void del(DictTypeRequest dictTypeRequest);

    /**
     * 修改字典类型
     *
     * @param dictTypeRequest 字典类型请求
     * @author fengshuonan
     * @date 2020/10/29 18:55
     */
    void edit(DictTypeRequest dictTypeRequest);

    /**
     * 修改字典状态
     *
     * @param dictTypeRequest 字典类型请求
     * @author fengshuonan
     * @date 2020/10/29 18:56
     */
    void editStatus(DictTypeRequest dictTypeRequest);

    /**
     * 查询-详情-按实体对象
     *
     * @param dictTypeRequest 参数对象
     * @author chenjinlong
     * @date 2021/1/26 12:52
     */
    SysDictType detail(DictTypeRequest dictTypeRequest);

    /**
     * 获取字典类型列表
     *
     * @param dictTypeRequest 字典类型请求
     * @return 字典类型列表
     * @author fengshuonan
     * @date 2020/10/29 18:55
     */
    List<SysDictType> findList(DictTypeRequest dictTypeRequest);

    /**
     * 获取字典类型列表（带分页）
     *
     * @param dictTypeRequest 字典类型请求
     * @return 字典类型列表
     * @author fengshuonan
     * @date 2020/10/29 18:55
     */
    PageResult<SysDictType> findPage(DictTypeRequest dictTypeRequest);

}
