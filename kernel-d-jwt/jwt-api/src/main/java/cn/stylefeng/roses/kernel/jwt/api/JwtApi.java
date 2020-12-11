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
     *
     * @param token jwt的token
     * @return true-token正确，false-token错误或失效
     * @author fengshuonan
     * @date 2020/10/21 11:43
     */
    boolean validateToken(String token);

    /**
     * 校验jwt token是否正确，如果jwt异常，或者jwt过期，则直接抛出异常
     *
     * @param token jwt的token
     * @throws JwtException Jwt相关的业务异常
     * @author fengshuonan
     * @date 2020/10/21 11:43
     */
    void validateTokenWithException(String token) throws JwtException;

    /**
     * 获取 token 的失效时间
     *
     * @param token jwt token
     * @return true-token失效，false-token没失效
     * @author fengshuonan
     * @date 2020/10/21 11:56
     */
    boolean getTokenExpiredFlag(String token);

}
