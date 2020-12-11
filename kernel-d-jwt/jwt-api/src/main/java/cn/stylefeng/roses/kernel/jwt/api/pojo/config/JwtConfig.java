package cn.stylefeng.roses.kernel.jwt.api.pojo.config;

import lombok.Data;

/**
 * jwt相关的配置封装
 *
 * @author fengshuonan
 * @date 2020/10/21 11:37
 */
@Data
public class JwtConfig {

    /**
     * jwt秘钥
     */
    private String jwtSecret;

    /**
     * 过期时间（秒）
     */
    private Long expiredSeconds;

}
