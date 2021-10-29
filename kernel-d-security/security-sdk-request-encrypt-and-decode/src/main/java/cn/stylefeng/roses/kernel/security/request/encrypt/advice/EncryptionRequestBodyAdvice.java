package cn.stylefeng.roses.kernel.security.request.encrypt.advice;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SM4;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.security.request.encrypt.exception.EncryptionException;
import cn.stylefeng.roses.kernel.security.request.encrypt.exception.enums.EncryptionExceptionEnum;
import cn.stylefeng.roses.kernel.security.request.encrypt.holder.EncryptionHolder;
import cn.stylefeng.roses.kernel.security.request.encrypt.holder.EncryptionRsaHolder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.Security;
import java.util.Date;

/**
 * 请求参数解密
 *
 * @author luojie
 * @date 2021/3/23 12:53
 */
@Slf4j
@ControllerAdvice
@SuppressWarnings("all")
public class EncryptionRequestBodyAdvice implements RequestBodyAdvice {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            // 添加PKCS7Padding支持
            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * 设置条件,这个条件为true才会执行下面的beforeBodyRead方法
     *
     * @author luojie
     * @date 2021/10/29 9:32
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 判断当前请求的接口中是否有 PostResource 注解
        Annotation[] annotations = methodParameter.getAnnotatedElement().getAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            return PostResource.class.equals(annotationType);
        }
        return false;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        Method method = parameter.getMethod();
        if (method != null) {
            PostResource annotation = method.getAnnotation(PostResource.class);

            if (annotation != null) {
                // 是否需要加密
                if (annotation.requiredEncryption()) {
                    return new HttpInputMessage() {
                        @Override
                        public HttpHeaders getHeaders() {
                            return inputMessage.getHeaders();
                        }

                        @Override
                        public InputStream getBody() throws IOException {

                            // 获取请求的内容
                            InputStream body = inputMessage.getBody();
                            String bodyStr = IoUtil.readUtf8(body);

                            //JSON 解析请求中的内容
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = JSON.parseObject(bodyStr);
                            } catch (Exception e) {
                                e.printStackTrace();
                                log.error(e.getMessage());
                                log.error(StrUtil.format("请求的内容：{}", bodyStr));
                                // 请求json解析异常
                                throw new EncryptionException(EncryptionExceptionEnum.REQUEST_JSON_PARSE_ERROR);
                            }

                            // 使用私钥解密出返回加密数据的key和请求的内容
                            RSA rsa = EncryptionRsaHolder.STATIC_RSA;

                            // 先使用SM4解密出请求的json
                            String objectString = jsonObject.getString("data");
                            if (StrUtil.isBlank(objectString)) {
                                // 请求json解析异常
                                throw new EncryptionException(EncryptionExceptionEnum.REQUEST_JSON_PARSE_ERROR);
                            }

                            String sm4Key = SecureUtil.md5(DateUtil.format(new Date(), "yyyyMMdd"));
                            SM4 sm4 = new SM4(Mode.ECB, Padding.PKCS5Padding, HexUtil.decodeHex(sm4Key));
                            try {
                                String decryptStr = sm4.decryptStr(objectString);
                                jsonObject = JSON.parseObject(decryptStr);
                            } catch (Exception e) {
                                e.printStackTrace();
                                log.error(e.getMessage());
                                // 解密失败
                                throw new EncryptionException(EncryptionExceptionEnum.RSA_DECRYPT_ERROR);

                            }

                            // 请求中RSA公钥加密后的key  用于将返回的内容AES加密
                            String key = jsonObject.getString("key");

                            // 获取请求中 AES 加密后的数据
                            String data = jsonObject.getString("data");

                            if (StrUtil.isBlank(key) || StrUtil.isBlank(data)) {
                                // 请求的json格式错误，未包含加密的data字段数据以及加密的key字段
                                throw new EncryptionException(EncryptionExceptionEnum.REQUEST_JSON_ERROR);
                            }

                            String aesKey = null;
                            try {
                                // 使用 RSA 私钥解密请求中公钥加密后的key
                                aesKey = rsa.decryptStr(key, KeyType.PrivateKey, CharsetUtil.CHARSET_UTF_8);
                                log.info("本次请求数据AES加密的KEY为：" + aesKey);
                            } catch (Exception e) {
                                e.printStackTrace();
                                log.error(e.getMessage());
                                // 解密失败
                                throw new EncryptionException(EncryptionExceptionEnum.RSA_DECRYPT_ERROR);
                            }

                            byte[] iv = HexUtil.decodeHex(SecureUtil.md5(StrUtil.format("{}{}", aesKey, DateUtil.format(new Date(), "yyyyMMdd"))));
                            byte[] aesKeyByte = Base64.decode(aesKey);

                            AES aes = new AES("CFB", "PKCS7Padding", aesKeyByte, iv);
                            String reqData = null;
                            try {
                                // 使用aes解密请求的内容
                                reqData = aes.decryptStr(data);
                                log.info(StrUtil.format("本次请求的内容：{}", reqData));
                            } catch (Exception e) {
                                e.printStackTrace();
                                log.error(e.getMessage());
                                // 解密失败
                                throw new EncryptionException(EncryptionExceptionEnum.RSA_DECRYPT_ERROR);
                            }

                            log.info(StrUtil.format("返回数据加密的key：{}", aesKey));

                            // 将 AES KEY 放到 ThreadLocal 中
                            EncryptionHolder.setAesKey(aesKey);

                            return new ByteArrayInputStream(reqData.getBytes(CharsetUtil.CHARSET_UTF_8));
                        }
                    };
                }
            }
        }

        return inputMessage;

    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
