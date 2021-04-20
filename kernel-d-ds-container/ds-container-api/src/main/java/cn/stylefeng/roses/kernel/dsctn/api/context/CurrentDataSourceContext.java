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
package cn.stylefeng.roses.kernel.dsctn.api.context;

/**
 * 利用ThreadLocal缓存当前请求的数据源
 *
 * @author fengshuonan
 * @date 2020/10/31 22:58
 */
public class CurrentDataSourceContext {

    private static final ThreadLocal<String> DATASOURCE_CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源类型
     *
     * @param dataSourceName 数据库类型
     * @date 2020/8/24
     */
    public static void setDataSourceName(String dataSourceName) {
        DATASOURCE_CONTEXT_HOLDER.set(dataSourceName);
    }

    /**
     * 获取数据源类型
     *
     * @author fengshuonan
     * @date 2020/8/24
     */
    public static String getDataSourceName() {
        return DATASOURCE_CONTEXT_HOLDER.get();
    }

    /**
     * 清除数据源类型
     *
     * @author fengshuonan
     * @date 2020/8/24
     */
    public static void clearDataSourceName() {
        DATASOURCE_CONTEXT_HOLDER.remove();
    }

}
