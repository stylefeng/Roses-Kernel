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
package cn.stylefeng.roses.kernel.config.modular.service;

import cn.stylefeng.roses.kernel.config.api.pojo.ConfigInitItem;
import cn.stylefeng.roses.kernel.config.api.pojo.ConfigInitRequest;
import cn.stylefeng.roses.kernel.config.modular.entity.SysConfig;
import cn.stylefeng.roses.kernel.config.modular.param.SysConfigParam;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统参数配置service接口
 *
 * @author fengshuonan
 * @date 2020/4/14 11:14
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 添加系统参数配置
     *
     * @param sysConfigParam 添加参数
     * @author fengshuonan
     * @date 2020/4/14 11:14
     */
    void add(SysConfigParam sysConfigParam);

    /**
     * 删除系统参数配置
     *
     * @param sysConfigParam 删除参数
     * @author fengshuonan
     * @date 2020/4/14 11:15
     */
    void del(SysConfigParam sysConfigParam);

    /**
     * 编辑系统参数配置
     *
     * @param sysConfigParam 编辑参数
     * @author fengshuonan
     * @date 2020/4/14 11:15
     */
    void edit(SysConfigParam sysConfigParam);

    /**
     * 查看系统参数配置
     *
     * @param sysConfigParam 查看参数
     * @return 系统参数配置
     * @author fengshuonan
     * @date 2020/4/14 11:15
     */
    SysConfig detail(SysConfigParam sysConfigParam);

    /**
     * 查询系统参数配置
     *
     * @param sysConfigParam 查询参数
     * @return 查询分页结果
     * @author fengshuonan
     * @date 2020/4/14 11:14
     */
    PageResult<SysConfig> findPage(SysConfigParam sysConfigParam);

    /**
     * 查询系统参数配置
     *
     * @param sysConfigParam 查询参数
     * @return 系统参数配置列表
     * @author fengshuonan
     * @date 2020/4/14 11:14
     */
    List<SysConfig> findList(SysConfigParam sysConfigParam);

    /**
     * 初始化配置参数
     *
     * @author fengshuonan
     * @date 2021/7/8 16:48
     */
    void initConfig(ConfigInitRequest configInitRequest);

    /**
     * 获取配置是否初始化的标志
     *
     * @return true-系统已经初始化，false-系统没有初始化
     * @author fengshuonan
     * @date 2021/7/8 17:20
     */
    Boolean getInitConfigFlag();

    /**
     * 获取初始化的配置列表
     *
     * @author fengshuonan
     * @date 2021/7/8 17:49
     */
    List<ConfigInitItem> getInitConfigs();

    /**
     * 获取后端部署的地址
     *
     * @author fengshuonan
     * @date 2022/3/3 14:23
     */
    String getServerDeployHost();
}
