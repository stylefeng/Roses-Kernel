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
package cn.stylefeng.roses.kernel.expand.modular.api;

import cn.stylefeng.roses.kernel.expand.modular.api.pojo.ExpandDataInfo;
import cn.stylefeng.roses.kernel.expand.modular.api.pojo.ExpandFieldInfo;

import java.util.List;
import java.util.Map;

/**
 * 拓展字段Api
 *
 * @author fengshuonan
 * @date 2022-03-29 23:14:31
 */
public interface ExpandApi {

    /**
     * 保存或更新动态数据
     *
     * @author fengshuonan
     * @date 2022/3/31 21:20
     */
    void saveOrUpdateExpandData(ExpandDataInfo expandDataInfo);

    /**
     * 获取列表需要的拓展字段信息
     *
     * @author fengshuonan
     * @date 2022/4/1 9:48
     */
    List<ExpandFieldInfo> getPageListExpandFieldList(String expandCode);

    /**
     * 获取某一条拓展数据
     *
     * @author fengshuonan
     * @date 2022/4/1 9:55
     */
    Map<String, Object> getExpandDataInfo(String expandCode, Long primaryFieldValue);

}
