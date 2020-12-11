package cn.stylefeng.roses.kernel.demo.exception;

import cn.stylefeng.roses.kernel.demo.constants.DemoConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * 演示环境操作异常
 *
 * @author fengshuonan
 * @date 2020/10/15 15:59
 */
public class DemoException extends ServiceException {

    public DemoException(AbstractExceptionEnum exception) {
        super(DemoConstants.DEMO_MODULE_NAME, exception);
    }

}
