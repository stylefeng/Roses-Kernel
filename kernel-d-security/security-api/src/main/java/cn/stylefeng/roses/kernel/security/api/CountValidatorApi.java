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
package cn.stylefeng.roses.kernel.security.api;

import cn.stylefeng.roses.kernel.security.api.exception.CountValidateException;

/**
 * 计数and校验API
 * <p>
 * 检验一个key（用户或某个IP等）在一个时间段内的操作次数是否超标
 *
 * @author fengshuonan
 * @date 2020/11/14 17:18
 */
public interface CountValidatorApi {

    /**
     * 记录key的操作次数加1，并校验次数是否在当前时间窗内超标
     *
     * @param key                一个操作的唯一标识，例如API_COUNT_192.168.1.15
     * @param timeWindowSeconds  一个时间窗的范围，时间窗单位是秒
     * @param timeWindowMaxCount 一个时间窗内操作的次数限制
     * @throws CountValidateException 如果校验失败，会抛出本异常
     * @author fengshuonan
     * @date 2020/11/14 17:27
     */
    void countAndValidate(String key, Long timeWindowSeconds, Long timeWindowMaxCount) throws CountValidateException;

}
