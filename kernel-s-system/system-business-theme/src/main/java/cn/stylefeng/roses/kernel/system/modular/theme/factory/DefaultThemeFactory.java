package cn.stylefeng.roses.kernel.system.modular.theme.factory;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.system.modular.theme.pojo.DefaultTheme;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 如果当前激活的系统主题没有，或者获取不到，则使用本类创建的默认系统主题
 *
 * @author fengshuonan
 * @date 2022/1/11 9:30
 */
@Data
public class DefaultThemeFactory {

    /**
     * 获取系统默认主题
     *
     * @author fengshuonan
     * @date 2022/1/11 9:31
     */
    public static DefaultTheme getSystemDefaultTheme() {
        DefaultTheme defaultTheme = new DefaultTheme();
        defaultTheme.setGunsMgrBeiUrl("https://beian.miit.gov.cn/");
        defaultTheme.setGunsMgrBeiNo("京ICP备001-1");
        defaultTheme.setGunsMgrFavicon("1479753047148322818");
        defaultTheme.setGunsMgrFooterText("stylefeng开源技术 javaguns.com");
        defaultTheme.setGunsMgrLogo("1479753047148322818");
        defaultTheme.setGunsMgrName("Guns Tech.");
        defaultTheme.setGunsMgrLoginBackgroundImg("1479751422149074948");
        return defaultTheme;
    }

    /**
     * 通过jsonObject解析默认主题数据
     *
     * @author fengshuonan
     * @date 2022/1/11 9:31
     */
    public static DefaultTheme parseDefaultTheme(JSONObject jsonObject) {

        // 初始化主题参数
        DefaultTheme defaultTheme = new DefaultTheme();

        // 存放没有被set值的key列表
        ArrayList<String> noneSetKeys = new ArrayList<>();

        // 遍历Key值，为对应的key属性赋值
        for (String jsonObjectKey : jsonObject.keySet()) {
            String propertyName = StrUtil.toCamelCase(jsonObjectKey);
            try {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, DefaultTheme.class);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(defaultTheme, jsonObject.getString(jsonObjectKey));
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                noneSetKeys.add(jsonObjectKey);
            }
        }

        // 遍历没有被set值的key，放到单独的map中
        HashMap<String, String> otherInfos = new HashMap<>();
        for (String noneSetKey : noneSetKeys) {
            String propertyName = StrUtil.toCamelCase(noneSetKey);
            String value = jsonObject.getString(noneSetKey);
            otherInfos.put(propertyName, value);
        }
        defaultTheme.setOtherConfigs(otherInfos);

        return defaultTheme;
    }

    public static void main(String[] args) throws Exception {
        DefaultTheme defaultTheme = new DefaultTheme();
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor("abc", DefaultTheme.class);
        Method writeMethod = propertyDescriptor.getWriteMethod();
        writeMethod.invoke(defaultTheme, "123123");
    }

}
