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
package cn.stylefeng.roses.kernel.dict.api;

import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;

import java.util.List;

/**
 * 字典模块对外提供的api，方便其他模块直接调用
 *
 * @author fengshuonan
 * @date 2020/10/29 14:45
 */
public interface DictApi {

    /**
     * 通过字典类型编码和字典编码获取名称
     *
     * @param dictTypeCode 字典类型编码
     * @param dictCode     字典编码
     * @return 字典名称
     * @author liuhanqing
     * @date 2021/1/16 23:18
     */
    String getDictName(String dictTypeCode, String dictCode);

    /**
     * 根据字典类型编码获取所有的字典
     *
     * @param dictTypeCode 字典类型编码
     * @return 字典的集合
     * @author fengshuonan
     * @date 2021/1/27 22:13
     */
    List<SimpleDict> getDictDetailsByDictTypeCode(String dictTypeCode);

    /**
     * 删除字典，通过dictId
     *
     * @param dictId 字典id
     * @author fengshuonan
     * @date 2021/1/30 10:03
     */
    void deleteByDictId(Long dictId);

}
