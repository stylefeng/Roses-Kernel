package cn.stylefeng.roses.kernel.jwt.api.pojo.payload;

import cn.hutool.core.util.IdUtil;
import lombok.Data;

import java.util.Map;

/**
 * jwt的载体，也就是jwt本身带的一些信息
 *
 * @author fengshuonan
 * @date 2020/10/21 11:37
 */
@Data
public class DefaultJwtPayload {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 唯一表示id, 用于缓存登录用户的唯一凭证
     */
    private String uuid;

    /**
     * 是否记住我
     */
    private Boolean rememberMe;

    /**
     * 其他载体信息
     */
    private Map<String, Object> others;

    public DefaultJwtPayload() {
    }

    public DefaultJwtPayload(Long userId, String account, boolean rememberMe) {
        this.userId = userId;
        this.account = account;
        this.uuid = IdUtil.fastUUID();
        this.rememberMe = rememberMe;
    }

}
