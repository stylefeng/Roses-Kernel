package cn.stylefeng.roses.kernel.db.mp.dboperator;

import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import cn.stylefeng.roses.kernel.db.api.DbOperatorApi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据库操作的实现
 *
 * @author fengshuonan
 * @date 2020/11/4 14:48
 */
@Service
public class DbOperatorImpl implements DbOperatorApi {

    @Override
    public int selectCount(String sql, Object... args) {
        return SqlRunner.db().selectCount(sql, args);
    }

    @Override
    public Set<Long> findSubListByParentId(String tableName, String parentIdsFieldName, String keyFieldName, Long keyFieldValue) {

        // 组装sql
        String sql = "select {0} from {1} where {2} like '%[{3}]%'";

        // 查询所有子级的id集合，结果不包含被查询的keyFieldValue
        List<Object> subIds = SqlRunner.db().selectObjs(sql, keyFieldName, tableName, parentIdsFieldName, keyFieldValue.toString());

        // 转为Set<Long>
        return subIds.stream().map(i -> Long.valueOf(i.toString())).collect(Collectors.toSet());
    }

}
