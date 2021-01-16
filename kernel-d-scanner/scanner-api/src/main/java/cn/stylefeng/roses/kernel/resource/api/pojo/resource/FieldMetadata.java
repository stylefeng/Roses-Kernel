package cn.stylefeng.roses.kernel.resource.api.pojo.resource;

import lombok.Data;

import java.util.Map;
import java.util.Set;

/**
 * 字典描述类
 *
 * @author fengshuonan
 * @date 2020/12/8 18:25
 */
@Data
public class FieldMetadata {

    /**
     * 生成给前端用
     * <p>
     * uuid，标识一个字段的唯一性
     */
    private String metadataId;

    /**
     * 字段中文名称，例如：创建用户
     */
    private String chineseName;

    /**
     * 字段类型，例如：String
     */
    private String fieldClassType;

    /**
     * 字段名称，例如：createUser
     */
    private String fieldName;

    /**
     * 字段的注解，例如：NotBlack，NotNull
     */
    private Set<String> annotations;

    /**
     * 按校验组分的注解集合
     * <p>
     * 例如：
     * key = add, value = [不能为空，最大多少位，邮箱类型]
     */
    private Map<String, Set<String>> groupValidationMessage;

    /**
     * 校验信息的提示信息
     */
    private String validationMessages;

    /**
     * 泛型或object类型的字段的描述
     */
    private Set<FieldMetadata> genericFieldMetadata;

}
