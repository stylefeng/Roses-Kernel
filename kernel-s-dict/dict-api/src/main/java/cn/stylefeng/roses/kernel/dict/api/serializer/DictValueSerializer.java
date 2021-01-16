package cn.stylefeng.roses.kernel.dict.api.serializer;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.dict.api.context.DictContext;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * jackson 序列化获取字典名称
 * <p>
 * 使用@JsonSerialize(using=DictValueSerializer.class)
 *
 * @author liuhanqing
 * @date 2021/1/16 22:21
 */
@JacksonStdImpl
public final class DictValueSerializer
        extends StdScalarSerializer<Object> {
    private static final long serialVersionUID = 1L;
    /**
     * 字典类型编码和字典值的分隔符
     */
    private static final String SEPARATOR = "|";
    /**
     * 空值字符串
     */
    private static final String NULL_STR = "null";
    /**
     * 字典值之前分隔符
     */
    private static final String VALUE_SEPARATOR = ",";

    public DictValueSerializer() {
        super(String.class, false);
    }

    @Override
    public boolean isEmpty(SerializerProvider prov, Object value) {
        String str = (String) value;
        return str.length() == 0;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        if (value == null) {
            gen.writeNull();
            return;
        }

        String strVal = value.toString();
        // 判断需要翻译字典值是否为不空且含有字典标识
        if (StrUtil.isNotBlank(strVal) && strVal.indexOf(SEPARATOR) > 0) {
            // 分隔需要序列化的值
            String[] dictArr = strVal.split("\\|");
            if (dictArr.length == 2) {
                // 获取字典编码值
                String dictCode = dictArr[1];
                if (StrUtil.isBlank(dictCode) || NULL_STR.equals(dictArr[1])) {
                    strVal = StrUtil.EMPTY;
                } else {
                    // 获取字典名称逻辑，多个名称用逗号分隔
                    String[] codeArr = dictCode.split(VALUE_SEPARATOR);
                    if (codeArr.length > 0) {
                        if (codeArr.length == 1) {
                            strVal = DictContext.me().getDictName(dictArr[0], codeArr[0]);
                        } else {
                            StringBuffer sb = new StringBuffer();
                            for (String dic : codeArr) {
                                String dicVal = DictContext.me().getDictName(dictArr[0], dic);
                                if (StrUtil.isNotBlank(dicVal)) {
                                    sb.append(dicVal).append(",");
                                }
                            }
                            if (StrUtil.isNotEmpty(sb)) {
                                strVal = StrUtil.removeSuffix(sb.toString(), ",");
                            } else {
                                strVal = dictArr[1];
                            }
                        }
                    }
                }
            } else {
                strVal = StrUtil.EMPTY;
            }
        }
        gen.writeString(strVal);
    }

    @Override
    public final void serializeWithType(Object value, JsonGenerator gen, SerializerProvider provider,
                                        TypeSerializer typeSer) throws IOException {
        // no type info, just regular serialization
        gen.writeString((String) value);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        return createSchemaNode("string", true);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        visitStringFormat(visitor, typeHint);
    }
}
