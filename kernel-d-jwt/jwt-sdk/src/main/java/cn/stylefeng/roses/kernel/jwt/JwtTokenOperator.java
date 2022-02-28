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
package cn.stylefeng.roses.kernel.jwt;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.roses.kernel.jwt.api.JwtApi;
import cn.stylefeng.roses.kernel.jwt.api.exception.JwtException;
import cn.stylefeng.roses.kernel.jwt.api.exception.enums.JwtExceptionEnum;
import cn.stylefeng.roses.kernel.jwt.api.pojo.config.JwtConfig;
import cn.stylefeng.roses.kernel.jwt.api.pojo.payload.DefaultJwtPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

import static cn.stylefeng.roses.kernel.jwt.api.exception.enums.JwtExceptionEnum.JWT_PARSE_ERROR;

/**
 * jwt token的操作对象
 *
 * @author fengshuonan
 * @date 2020/10/31 15:33
 */
public class JwtTokenOperator implements JwtApi {

    private final JwtConfig jwtConfig;

    public JwtTokenOperator(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public String generateToken(Map<String, Object> payload) {

        // 计算过期时间
        DateTime expirationDate = DateUtil.offsetSecond(new Date(), Convert.toInt(jwtConfig.getExpiredSeconds()));

        // 构造jwt token
        return Jwts.builder()
                .setClaims(payload)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getJwtSecret())
                .compact();
    }

    @Override
    public String generateTokenDefaultPayload(DefaultJwtPayload defaultJwtPayload) {

        // 计算过期时间
        DateTime expirationDate = DateUtil.offsetSecond(new Date(), Convert.toInt(jwtConfig.getExpiredSeconds()));

        // 设置过期时间
        defaultJwtPayload.setExpirationDate(expirationDate.getTime());

        // 构造jwt token
        return Jwts.builder()
                .setClaims(BeanUtil.beanToMap(defaultJwtPayload))
                .setSubject(defaultJwtPayload.getUserId().toString())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getJwtSecret())
                .compact();
    }

    @Override
    public Claims getJwtPayloadClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public DefaultJwtPayload getDefaultPayload(String token) {
        Map<String, Object> jwtPayload = getJwtPayloadClaims(token);
        return BeanUtil.toBeanIgnoreError(jwtPayload, DefaultJwtPayload.class);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            getJwtPayloadClaims(token);
            return true;
        } catch (io.jsonwebtoken.JwtException jwtException) {
            return false;
        }
    }

    @Override
    public void validateTokenWithException(String token) throws JwtException {

        // 1.先判断是否是token过期了
        boolean tokenIsExpired = this.validateTokenIsExpired(token);
        if (tokenIsExpired) {
            throw new JwtException(JwtExceptionEnum.JWT_EXPIRED_ERROR, token);
        }

        // 2.判断是否是jwt本身的错误
        try {
            getJwtPayloadClaims(token);
        } catch (io.jsonwebtoken.JwtException jwtException) {
            throw new JwtException(JWT_PARSE_ERROR, token);
        }
    }

    @Override
    public boolean validateTokenIsExpired(String token) {
        try {
            Claims claims = getJwtPayloadClaims(token);
            final Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }

}
