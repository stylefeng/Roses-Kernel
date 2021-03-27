请求解密，响应加密模块

大致流程 生成随机key并使用AES加密 模式为CFB 填充为Pkcs7 加密请求的内容，并使用RSA公钥加密生成的key，用于后端进行解密，后端再根据这个key加密响应结果

前端实例代码

```javascript
    layui.use(['HttpEncryptionRequest'], function () {
        var result = new HttpEncryptionRequest(Feng.ctxPath + '/encode', function (res) {
            console.log(res)
        })
        result.set({name:'测试'});
        result.start();
    })
```

后端示例代码

在 PostResource 注解中 requiredEncryption 参数设置为true 开启参数解密，响应加密 接收参数实体加上 @RequestBody 注解

```java
/**
     * 示例加密方法
     * <p>
     * requiredEncryption = true
     * </p>
     *
     * @author luojie
     * @date 2021/3/27 22:31
     */
    @PostResource(name = "示例加密方法", path = "/encode", requiredPermission = false, requiredLogin = false, requiredEncryption = true)
    public ResponseData encode(@RequestBody Dict dict) {
        return new SuccessResponseData(dict);
    }
```