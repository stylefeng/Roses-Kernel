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
package cn.stylefeng.roses.kernel.rule.exception;

/**
 * 异常枚举格式规范，每个异常枚举都要实现这个接口
 * <p>
 * 为了在抛出ServiceException的时候规范抛出的内容
 * <p>
 * ServiceException抛出时必须为本接口的实现类
 *
 * @author fengshuonan
 * @date 2020/10/14 21:41
 */
public interface AbstractExceptionEnum {

    /**
     * 获取异常的状态码
     *
     * @return 状态码
     * @author fengshuonan
     * @date 2020/10/14 21:42
     */
    String getErrorCode();

    /**
     * 获取给用户提示信息
     *
     * @return 提示信息
     * @author fengshuonan
     * @date 2020/10/14 21:42
     */
    String getUserTip();

}
