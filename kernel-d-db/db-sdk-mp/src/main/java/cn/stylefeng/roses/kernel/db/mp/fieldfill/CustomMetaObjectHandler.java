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
package cn.stylefeng.roses.kernel.db.mp.fieldfill;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;

import java.util.Date;

import static cn.stylefeng.roses.kernel.db.api.constants.DbFieldConstants.*;

/**
 * 字段自动填充工具，在mybatis-plus执行更新和新增操作时候，会对指定字段进行自动填充，例如 create_time 等字段
 *
 * @author fengshuonan
 * @date 2020/10/16 17:14
 */
@Slf4j
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        try {
            // 设置createUser（BaseEntity)
            setValue(metaObject, CREATE_USER, this.getUserUniqueId());

            // 设置createTime（BaseEntity)
            setValue(metaObject, CREATE_TIME, new Date());

            // 设置删除标记 默认N-删除
            setValue(metaObject, DEL_FLAG, YesOrNotEnum.N.getCode());

            // 设置状态字段 默认1-启用
            setValue(metaObject, STATUS_FLAG, StatusEnum.ENABLE.getCode());
        } catch (ReflectionException e) {
            log.warn("CustomMetaObjectHandler处理过程中无相关字段，不做处理");
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {

        try {
            // 设置updateUser（BaseEntity)
            setValue(metaObject, UPDATE_USER, this.getUserUniqueId());

            // 设置updateTime（BaseEntity)
            setValue(metaObject, UPDATE_TIME, new Date());
        } catch (ReflectionException e) {
            log.warn("CustomMetaObjectHandler处理过程中无相关字段，不做处理");
        }

    }

    /**
     * 获取用户唯一id
     *
     * @author fengshuonan
     * @date 2021/10/29 10:01
     */
    private Long getUserUniqueId() {

        try {
            return LoginContext.me().getLoginUser().getUserId();
        } catch (Exception e) {
            //如果获取不到就返回-1
            return -1L;
        }

    }

    /**
     * 设置属性
     *
     * @author fengshuonan
     * @date 2021/10/29 10:01
     */
    private void setValue(MetaObject metaObject, String fieldName, Object value) {
        Object originalAttr = getFieldValByName(fieldName, metaObject);
        if (ObjectUtil.isEmpty(originalAttr)) {
            setFieldValByName(fieldName, value, metaObject);
        }
    }

}
