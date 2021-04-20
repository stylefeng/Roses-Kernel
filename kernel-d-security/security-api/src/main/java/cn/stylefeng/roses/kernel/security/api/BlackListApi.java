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
package cn.stylefeng.roses.kernel.security.api;


import java.util.Collection;

/**
 * 黑名单Api
 * <p>
 * 在黑名单的用户会被禁止访问程序
 *
 * @author fengshuonan
 * @date 2020/11/15 16:31
 */
public interface BlackListApi {

    /**
     * 添加名单条目
     *
     * @param content 黑名单的一条内容，可以是ip，用户id之类的
     * @author fengshuonan
     * @date 2020/11/15 16:32
     */
    void addBlackItem(String content);

    /**
     * 删除名单条目
     *
     * @param content 黑名单的一条内容，可以是ip，用户id之类的
     * @author fengshuonan
     * @date 2020/11/15 16:32
     */
    void removeBlackItem(String content);

    /**
     * 获取整个黑名单
     *
     * @return 黑名单内容的列表
     * @author fengshuonan
     * @date 2020/11/15 16:33
     */
    Collection<String> getBlackList();

    /**
     * 是否包含某个值
     *
     * @param content 黑名单的一条内容，可以是ip，用户id之类的
     * @return true-包含值，false-不包含值
     * @author fengshuonan
     * @date 2020/11/20 16:55
     */
    boolean contains(String content);

}
