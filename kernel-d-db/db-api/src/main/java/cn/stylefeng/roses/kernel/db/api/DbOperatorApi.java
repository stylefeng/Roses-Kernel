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
package cn.stylefeng.roses.kernel.db.api;

import java.util.Set;

/**
 * 数据库操作的api，用于快速进行sql操作并获取结果
 *
 * @author fengshuonan
 * @date 2020/11/4 14:43
 */
public interface DbOperatorApi {

    /**
     * 返回SelectCount SQL执行的结果
     *
     * @param sql  带有select count()相关语句的sql
     * @param args sql中的参数
     * @return sql执行的结果，取第一行第一个数字
     * @author fengshuonan
     * @date 2020/11/4 14:43
     */
    int selectCount(String sql, Object... args);

    /**
     * 获取某个表，某条数据的所有子列表
     * <p>
     * 本方法用在带有层级关系的表，并且有 "pids" 类似的字段
     * <p>
     * 通过 like 操作可以查询到该条数据的所有子数据
     * <p>
     * pids的组成规范必须是[0],[xxx],[xxx]
     *
     * @param tableName          表名称，例如sys_user
     * @param parentIdsFieldName 父级ids的字段名
     * @param keyFieldName       主键id的字段名
     * @param keyFieldValue      主键id的值
     * @return keyFieldValue的值
     * @author fengshuonan
     * @date 2020/11/5 17:32
     */
    Set<Long> findSubListByParentId(String tableName, String parentIdsFieldName, String keyFieldName, Long keyFieldValue);

}
