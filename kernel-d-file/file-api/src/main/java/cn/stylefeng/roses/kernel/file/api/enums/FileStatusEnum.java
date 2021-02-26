package cn.stylefeng.roses.kernel.file.api.enums;

import lombok.Getter;

/**
 * 文件状态
 *
 * @author majianguo
 * @date 2020/12/16 12:00
 */
@Getter
public enum FileStatusEnum {

    /**
     * 新文件
     * <p>
     * 如果code相同，每次版本号替换都会把当前文件设置成最新文件
     */
    NEW("1"),

    /**
     * 旧文件
     */
    OLD("0");

    private final String code;

    FileStatusEnum(String code) {
        this.code = code;
    }

}
