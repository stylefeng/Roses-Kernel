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