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
