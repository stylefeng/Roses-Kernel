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
package cn.stylefeng.roses.kernel.rule.exception.base;

import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static cn.stylefeng.roses.kernel.rule.constants.RuleConstants.RULE_MODULE_NAME;

/**
 * 所有业务异常的基类
 * <p>
 * 在抛出异常时候，务必带上AbstractExceptionEnum枚举
 * <p>
 * 业务异常分为三种
 * <p>
 * 第一种是用户端操作的异常，例如用户输入参数为空，用户输入账号密码不正确
 * <p>
 * 第二种是当前系统业务逻辑出错，例如系统执行出错，磁盘空间不足
 * <p>
 * 第三种是第三方系统调用出错，例如文件服务调用失败，RPC调用超时
 *
 * @author fengshuonan
 * @date 2020/10/15 9:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 返回给用户的提示信息
     */
    private String userTip;

    /**
     * 异常的模块名称
     */
    private String moduleName;

    /**
     * 异常的具体携带数据
     */
    private Object data;

    /**
     * 根据模块名，错误码，用户提示直接抛出异常
     */
    public ServiceException(String moduleName, String errorCode, String userTip) {
        super(userTip);
        this.errorCode = errorCode;
        this.moduleName = moduleName;
        this.userTip = userTip;
    }

    /**
     * 如果要直接抛出ServiceException，可以用这个构造函数
     */
    public ServiceException(String moduleName, AbstractExceptionEnum exception) {
        super(exception.getUserTip());
        this.moduleName = moduleName;
        this.errorCode = exception.getErrorCode();
        this.userTip = exception.getUserTip();
    }

    /**
     * 不建议直接抛出ServiceException，因为这样无法确认是哪个模块抛出的异常
     * <p>
     * 建议使用业务异常时，都抛出自己模块的异常类
     */
    public ServiceException(AbstractExceptionEnum exception) {
        super(exception.getUserTip());
        this.moduleName = RULE_MODULE_NAME;
        this.errorCode = exception.getErrorCode();
        this.userTip = exception.getUserTip();
    }

    /**
     * 携带数据的异常构造函数
     */
    public ServiceException(String moduleName, String errorCode, String userTip, Object data) {
        super(userTip);
        this.errorCode = errorCode;
        this.moduleName = moduleName;
        this.userTip = userTip;
        this.data = data;
    }

}
