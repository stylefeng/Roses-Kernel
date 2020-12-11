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
     * 获取某个表，某条数据的所有子列表 TODO 测试
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
