package cn.stylefeng.roses.kernel.db.mp.dboperator;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.DbOperatorApi;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
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
        String sqlTemplate = "select {} from {} where {} like '%[{}]%'";
        String sql = StrUtil.format(sqlTemplate, keyFieldName, tableName, parentIdsFieldName, keyFieldValue.toString());

        // 查询所有子级的id集合，结果不包含被查询的keyFieldValue
        List<Object> subIds = SqlRunner.db().selectObjs(sql);

        // 转为Set<Long>
        return subIds.stream().map(i -> Long.valueOf(i.toString())).collect(Collectors.toSet());
    }

}
