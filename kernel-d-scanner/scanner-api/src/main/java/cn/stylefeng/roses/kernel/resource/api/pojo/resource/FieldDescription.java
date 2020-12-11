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
public class FieldDescription {

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
     * key = add, value = [NotBlank,TableUniqueValue]
     */
    private Map<String, Set<String>> groupAnnotations;

}
