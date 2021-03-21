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
package cn.stylefeng.roses.kernel.jwt.api;

import cn.stylefeng.roses.kernel.jwt.api.exception.JwtException;
import cn.stylefeng.roses.kernel.jwt.api.pojo.payload.DefaultJwtPayload;

import java.util.Map;

/**
 * jwt相关的操作api
 *
 * @author fengshuonan
 * @date 2020/10/21 11:31
 */
public interface JwtApi {

    /**
     * 生成token
     *
     * @param payload jwt的载体信息
     * @return jwt token
     * @author fengshuonan
     * @date 2020/10/21 11:38
     */
    String generateToken(Map<String, Object> payload);

    /**
     * 生成token，用默认的payload格式
     *
     * @param defaultJwtPayload jwt的载体信息
     * @return jwt token
     * @author fengshuonan
     * @date 2020/10/21 11:38
     */
    String generateTokenDefaultPayload(DefaultJwtPayload defaultJwtPayload);

    /**
     * 获取jwt的payload（通用的）
     *
     * @param token jwt的token
     * @return jwt的payload
     * @author fengshuonan
     * @date 2020/10/21 11:52
     */
    Map<String, Object> getJwtPayloadClaims(String token);

    /**
     * 获取jwt的payload（限定默认格式）
     *
     * @param token jwt的token
     * @return 返回默认格式的payload
     * @author fengshuonan
     * @date 2020/10/21 11:51
     */
    DefaultJwtPayload getDefaultPayload(String token);

    /**
     * 校验jwt token是否正确
     * <p>
     * 不正确的token有两种：
     * <p>
     * 1. 第一种是jwt token过期了
     * 2. 第二种是jwt本身是错误的
     * <p>
     * 本方法只会响应正确还是错误
     *
     * @param token jwt的token
     * @return true-token正确，false-token错误或失效
     * @author fengshuonan
     * @date 2020/10/21 11:43
     */
    boolean validateToken(String token);

    /**
     * 校验jwt token是否正确，如果参数token是错误的会抛出对应异常
     * <p>
     * 不正确的token有两种：
     * <p>
     * 1. 第一种是jwt token过期了
     * 2. 第二种是jwt本身是错误的
     *
     * @param token jwt的token
     * @throws JwtException Jwt相关的业务异常
     * @author fengshuonan
     * @date 2020/10/21 11:43
     */
    void validateTokenWithException(String token) throws JwtException;

    /**
     * 校验jwt token是否失效了
     *
     * @param token jwt token
     * @return true-token失效，false-token没失效
     * @author fengshuonan
     * @date 2020/10/21 11:56
     */
    boolean validateTokenIsExpired(String token);

}
