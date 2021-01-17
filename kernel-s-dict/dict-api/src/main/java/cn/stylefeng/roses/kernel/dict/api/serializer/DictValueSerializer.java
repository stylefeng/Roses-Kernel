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
 * 使用注意事项：
 * <p>
 * 1.在pojo的字段上加 @JsonSerialize(using=DictValueSerializer.class)
 * 2.pojo字段的返回值为："字典类型编码|字典的编码"
 *
 * @author liuhanqing
 * @date 2021/1/16 22:21
 */
@JacksonStdImpl
public final class DictValueSerializer extends StdScalarSerializer<Object> {

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

        // 被序列化字段的值
        String fieldValue = value.toString();

        // 如果为空或者没有分隔符返回空串
        if (StrUtil.isBlank(fieldValue) || !fieldValue.contains(SEPARATOR)) {
            fieldValue = StrUtil.EMPTY;
            gen.writeString(fieldValue);
            return;
        }

        // 分隔需要序列化的值
        String[] dictTypeCodeAndDictCode = fieldValue.split("\\|");

        // 如果分割出来不是2，则格式不正确
        if (dictTypeCodeAndDictCode.length != 2) {
            fieldValue = StrUtil.EMPTY;
            gen.writeString(fieldValue);
            return;
        }

        // 获取字典类型编码和字典编码
        String dictTypeCode = dictTypeCodeAndDictCode[0];
        String dictCode = dictTypeCodeAndDictCode[1];

        // 字典code为空，返回空串
        if (StrUtil.isBlank(dictCode) || NULL_STR.equals(dictCode)) {
            fieldValue = StrUtil.EMPTY;
            gen.writeString(fieldValue);
            return;
        }

        // 字典编码是多个，存在逗号分隔符
        if (dictCode.contains(VALUE_SEPARATOR)) {
            String[] dictCodeArray = dictCode.split(VALUE_SEPARATOR);
            StringBuilder dictNames = new StringBuilder();
            for (String singleDictCode : dictCodeArray) {
                String dictName = DictContext.me().getDictName(dictTypeCode, singleDictCode);
                if (StrUtil.isNotBlank(dictName)) {
                    dictNames.append(dictName).append(VALUE_SEPARATOR);
                }
            }
            fieldValue = StrUtil.removeSuffix(dictNames.toString(), VALUE_SEPARATOR);
            gen.writeString(fieldValue);
            return;
        }

        // 字典编码是一个
        fieldValue = DictContext.me().getDictName(dictTypeCode, dictCode);
        gen.writeString(fieldValue);
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
