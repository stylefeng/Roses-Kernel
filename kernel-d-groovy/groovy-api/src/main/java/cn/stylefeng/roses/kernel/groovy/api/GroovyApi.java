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
package cn.stylefeng.roses.kernel.groovy.api;

/**
 * groovy动态脚本操作api
 *
 * @author fengshuonan
 * @date 2021/1/22 16:29
 */
public interface GroovyApi {

    /**
     * 将文本形式的java类转换成class对象
     *
     * @param javaClassCode java类的文本信息
     * @author fengshuonan
     * @date 2021/1/22 16:30
     */
    Class<?> textToClass(String javaClassCode);

    /**
     * 执行java语句
     *
     * @param javaCode java语句的文本信息
     * @author fengshuonan
     * @date 2021/1/22 16:31
     */
    Object executeShell(String javaCode);

    /**
     * 将java类代码转换成class，并执行某个方法
     *
     * @param javaClassCode  java类的代码
     * @param method         java类中某个方法名
     * @param parameterTypes 参数类型集合
     * @param args           参数的具体对象
     * @author fengshuonan
     * @date 2021/1/22 16:31
     */
    Object executeClassMethod(String javaClassCode, String method, Class<?>[] parameterTypes, Object[] args);

}
