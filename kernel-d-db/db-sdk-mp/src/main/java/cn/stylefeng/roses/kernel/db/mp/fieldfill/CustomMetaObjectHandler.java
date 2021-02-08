package cn.stylefeng.roses.kernel.db.mp.fieldfill;

import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
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
            setFieldValByName(CREATE_USER, this.getUserUniqueId(), metaObject);

            // 设置createTime（BaseEntity)
            setFieldValByName(CREATE_TIME, new Date(), metaObject);

            // 设置删除标记，默认N，未删除
            setFieldValByName(DEL_FLAG, YesOrNotEnum.N.getCode(), metaObject);


        } catch (ReflectionException e) {
            log.warn("CustomMetaObjectHandler处理过程中无相关字段，不做处理");
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {

        try {

            // 设置updateUser（BaseEntity)
            setFieldValByName(UPDATE_USER, this.getUserUniqueId(), metaObject);

            // 设置updateTime（BaseEntity)
            setFieldValByName(UPDATE_TIME, new Date(), metaObject);

        } catch (ReflectionException e) {
            log.warn("CustomMetaObjectHandler处理过程中无相关字段，不做处理");
        }

    }

    /**
     * 获取用户唯一id
     */
    private Long getUserUniqueId() {

        try {
            return LoginContext.me().getLoginUser().getUserId();
        } catch (Exception e) {
            //如果获取不到就返回-1
            return -1L;
        }

    }

}
