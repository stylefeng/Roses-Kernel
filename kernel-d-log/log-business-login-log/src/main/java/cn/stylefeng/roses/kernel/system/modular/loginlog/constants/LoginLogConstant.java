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
package cn.stylefeng.roses.kernel.system.modular.loginlog.constants;

/**
 * 登录日志常量
 *
 * @author chenjinlong
 * @date 2021/2/24 14:07
 */
public interface LoginLogConstant {

    /**
     * 登录名称
     */
    String LOGIN_IN_LOGINNAME = "登录日志";

    /**
     * 推出名称
     */
    String LOGIN_OUT_LOGINNAME = "退出日志";

    /**
     * 操作成功
     */
    String OPERATION_SUCCESS = "成功";

    /**
     * 操作失败
     */
    String OPERATION_FAIL = "失败";

    /**
     * 登录成功提示
     */
    String LOGIN_IN_SUCCESS_MESSAGE = "系统登录成功！";

    /**
     * 登录失败提示
     */
    String LOGIN_IN_SUCCESS_FAIL = "系统登录失败！";

    /**
     * 退出成功提示
     */
    String LOGIN_OUT_SUCCESS_MESSAGE = "系统退出成功！";

    /**
     * 退出失败提示
     */
    String LOGIN_OUT_SUCCESS_FAIL = "系统退出失败！";
}
