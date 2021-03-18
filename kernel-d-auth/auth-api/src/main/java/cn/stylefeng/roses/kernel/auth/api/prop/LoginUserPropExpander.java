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
package cn.stylefeng.roses.kernel.auth.api.prop;

import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;

/**
 * 当前登录用户属性的组装和填充
 * <p>
 * LoginUser对象的属性比较多，为了方便后期拓展，统一用此接口为新字段set值
 * <p>
 * 使用方法：
 * <p>
 * 第一步，为LoginUser加新的字段
 * <p>
 * 第二步，新写一个类实现本接口，在expandAction方法为新属性赋值即可
 * <p>
 * 注意，这个方法里边的实现在编写时，不能使用 LoginContext 获取当前用户
 *
 * @author fengshuonan
 * @date 2020/12/22 14:18
 */
public interface LoginUserPropExpander {

    /**
     * 为loginUser赋值
     *
     * @param loginUser 当前登录用户
     * @author fengshuonan
     * @date 2020/12/22 14:26
     */
    void expandAction(LoginUser loginUser);

}
