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
package cn.stylefeng.roses.kernel.demo.interceptor;

import cn.stylefeng.roses.kernel.auth.api.expander.AuthConfigExpander;
import cn.stylefeng.roses.kernel.demo.exception.DemoException;
import cn.stylefeng.roses.kernel.demo.exception.enums.DemoExceptionEnum;
import cn.stylefeng.roses.kernel.demo.expander.DemoConfigExpander;
import cn.stylefeng.roses.kernel.demo.util.StartCalcUtil;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.AntPathMatcher;

import java.sql.Connection;
import java.util.Date;

/**
 * 演示环境的sql过滤器，只放开select语句，其他语句都不放过
 *
 * @author stylefeng
 * @date 2020/5/5 12:21
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DemoProfileSqlInterceptor implements Interceptor {

    /**
     * 系统启动预估时间
     */
    private final int projectStartInterval;

    public DemoProfileSqlInterceptor(int projectStartInterval) {
        this.projectStartInterval = projectStartInterval;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        // 演示环境没开，直接跳过此过滤器
        if (!DemoConfigExpander.getDemoEnvFlag()) {
            return invocation.proceed();
        }

        // 如果是演示环境，并且项目还没起来，则直接放过sql
        if (DemoConfigExpander.getDemoEnvFlag() && !StartCalcUtil.calcEnable(new Date(), projectStartInterval)) {
            return invocation.proceed();
        }

        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");

        if (SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        } else {

            //放开不进行安全过滤的接口
            for (String notAuthResource : AuthConfigExpander.getNoneSecurityConfig()) {
                AntPathMatcher antPathMatcher = new AntPathMatcher();
                if (antPathMatcher.match(notAuthResource, HttpServletUtil.getRequest().getRequestURI())) {
                    return invocation.proceed();
                }
            }

            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
    }

}
