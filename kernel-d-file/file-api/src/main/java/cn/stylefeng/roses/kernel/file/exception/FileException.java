package cn.stylefeng.roses.kernel.file.exception;

import cn.stylefeng.roses.kernel.file.constants.FileConstants;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;

/**
 * 系统配置表的异常
 *
 * @author fengshuonan
 * @date 2020/10/15 15:59
 */
public class FileException extends ServiceException {

    public FileException(String errorCode, String userTip) {
        super(FileConstants.FILE_MODULE_NAME, errorCode, userTip);
    }

    public FileException(AbstractExceptionEnum exception) {
        super(FileConstants.FILE_MODULE_NAME, exception);
    }

    public FileException(AbstractExceptionEnum exception, String userTip) {
        super(FileConstants.FILE_MODULE_NAME, exception.getErrorCode(), userTip);
    }

}
