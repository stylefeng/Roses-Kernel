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
package cn.stylefeng.roses.kernel.db.init.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sql语句工具类
 *
 * @author fengshuonan
 * @date 2016年12月6日 下午1:01:54
 */
@Slf4j
public class SqlUtil {

    /**
     * 根据集合的大小，输出相应个数"?"
     *
     * @author fengshuonan
     */
    public static String parse(List<?> list) {
        String str = "";
        if (list != null && list.size() > 0) {
            str = str + "?";
            for (int i = 1; i < list.size(); i++) {
                str = str + ",?";
            }
        }
        return str;
    }

    /**
     * 结果集转化为map
     *
     * @author fengshuonan
     * @date 2020/1/29 6:12 下午
     */
    public static Map<String, Object> resultSet2Map(ResultSet resultSet) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                Object columnValue = resultSet.getObject(i);
                result.put(columnName, columnValue);
            }
            return result;
        } catch (SQLException e) {

            log.error("转化结果集错误！", e);

            //返回空map
            return new HashMap<>();
        }
    }

    /**
     * 结果集转化为map
     *
     * @author fengshuonan
     * @date 2020/1/29 6:12 下午
     */
    public static List<Map<String, Object>> resultSet2ListMap(ResultSet resultSet) {

        ArrayList<Map<String, Object>> result = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Map<String, Object> map = resultSet2Map(resultSet);
                result.add(map);
            }
            return result;
        } catch (SQLException e) {

            log.error("转化结果集错误！", e);

            //返回空map
            return result;
        }
    }

}
