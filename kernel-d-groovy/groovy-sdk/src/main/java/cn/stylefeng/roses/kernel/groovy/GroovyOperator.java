/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.groovy;

import cn.stylefeng.roses.kernel.groovy.api.GroovyApi;
import cn.stylefeng.roses.kernel.groovy.api.exception.GroovyException;
import cn.stylefeng.roses.kernel.groovy.api.exception.enums.GroovyExceptionEnum;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * groovy动态脚本操作实现
 *
 * @author fengshuonan
 * @date 2021/1/22 16:28
 */
@Slf4j
public class GroovyOperator implements GroovyApi {

    @Override
    public Class<?> textToClass(String javaClassCode) {
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        return (Class<?>) groovyClassLoader.parseClass(javaClassCode);
    }

    @Override
    public Object executeShell(String javaCode) {
        GroovyShell shell = new GroovyShell();
        return shell.evaluate(javaCode);
    }

    @Override
    public Object executeClassMethod(String javaClassCode, String method, Class<?>[] parameterTypes, Object[] args) {
        try {
            Class<?> clazz = this.textToClass(javaClassCode);
            Method clazzMethod = clazz.getMethod(method, parameterTypes);
            Object obj = clazz.newInstance();
            return clazzMethod.invoke(obj, args);
        } catch (Exception e) {
            log.error("执行groovy类中方法出错！", e);
            throw new GroovyException(GroovyExceptionEnum.GROOVY_EXE_ERROR);
        }
    }

}
