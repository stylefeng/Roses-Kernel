package cn.stylefeng.roses.kernel.file.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.file.api.constants.FileConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * 系统配置表的异常
 *
 * @author fengshuonan
 * @date 2020/10/15 15:59
 */
public class FileException extends ServiceException {

    public FileException(AbstractExceptionEnum exception) {
        super(FileConstants.FILE_MODULE_NAME, exception);
    }

    public FileException(AbstractExceptionEnum exception, Object... params) {
        super(FileConstants.FILE_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
