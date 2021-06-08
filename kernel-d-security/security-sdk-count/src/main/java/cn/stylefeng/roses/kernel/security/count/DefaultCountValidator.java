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
package cn.stylefeng.roses.kernel.security.count;

import cn.hutool.core.convert.Convert;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.cache.api.constants.CacheConstants;
import cn.stylefeng.roses.kernel.security.api.CountValidatorApi;
import cn.stylefeng.roses.kernel.security.api.constants.CounterConstants;
import cn.stylefeng.roses.kernel.security.api.exception.CountValidateException;
import cn.stylefeng.roses.kernel.security.api.exception.enums.CountValidateExceptionEnum;

/**
 * 默认的计数校验器
 *
 * @author fengshuonan
 * @date 2020/11/15 12:14
 */
public class DefaultCountValidator implements CountValidatorApi {

    private final CacheOperatorApi<Long> cacheOperatorApi;

    public DefaultCountValidator(CacheOperatorApi<Long> cacheOperatorApi) {
        this.cacheOperatorApi = cacheOperatorApi;
    }

    @Override
    public synchronized void countAndValidate(String key, Long timeWindowSeconds, Long timeWindowMaxCount) throws CountValidateException {

        // 获取当前时间的秒数
        long currentTimeSeconds = System.currentTimeMillis() / 1000;

        // 上一次操作时间秒数的缓存key COUNT_VALIDATE:key:RECORD_SECONDS
        String recordTimeSecondsKey = CounterConstants.COUNT_VALIDATE_CACHE_KEY_PREFIX + CacheConstants.CACHE_DELIMITER + key + CacheConstants.CACHE_DELIMITER + CounterConstants.RECORD_TIME_SECONDS;

        // 上一次执行次数的记录缓存key COUNT_VALIDATE:key:COUNT_NUMBER
        String countNumberKey = CounterConstants.COUNT_VALIDATE_CACHE_KEY_PREFIX + CacheConstants.CACHE_DELIMITER + key + CacheConstants.CACHE_DELIMITER + CounterConstants.COUNT_NUMBER;

        // 获取缓存中上一次操作时间秒数
        Object recordTimeSecondsObject = cacheOperatorApi.get(recordTimeSecondsKey);
        Long recordTimeSeconds = Convert.toLong(recordTimeSecondsObject);
        if (recordTimeSeconds == null) {
            recordTimeSeconds = currentTimeSeconds;
        }

        // 获取缓存中上一次执行次数的记录
        Object countNumberObject = cacheOperatorApi.get(countNumberKey);
        Long countNumber = Convert.toLong(countNumberObject);
        if (countNumber == null) {
            countNumber = 0L;
        }

        // 当前时间和记录时间的差 超过限制的时间段就归零计数，否则就直接加1
        if ((currentTimeSeconds - recordTimeSeconds) == timeWindowSeconds) {
            countNumber = 0L;
        } else if ((currentTimeSeconds - recordTimeSeconds) > timeWindowSeconds) {
            countNumber = 0L;
        } else if ((currentTimeSeconds - recordTimeSeconds) < timeWindowSeconds) {
            countNumber = countNumber + 1;
        } else if (recordTimeSeconds.equals(currentTimeSeconds)) {
            countNumber = countNumber + 1;
        }

        cacheOperatorApi.put(recordTimeSecondsKey, currentTimeSeconds, timeWindowSeconds);
        cacheOperatorApi.put(countNumberKey, countNumber, timeWindowSeconds);

        // 如果记录次数大于了时间窗内的最大容许值，则抛出异常
        if (countNumber > timeWindowMaxCount) {
            throw new CountValidateException(CountValidateExceptionEnum.INTERRUPT_EXECUTION);
        }

    }

}
