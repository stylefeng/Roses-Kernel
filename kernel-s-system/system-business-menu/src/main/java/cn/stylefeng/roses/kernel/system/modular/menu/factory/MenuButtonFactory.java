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
package cn.stylefeng.roses.kernel.system.modular.menu.factory;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.modular.menu.constants.MenuButtonConstant;
import cn.stylefeng.roses.kernel.system.modular.menu.entity.SysMenuButton;

import java.util.List;
import java.util.Locale;

/**
 * 组装菜单按钮
 *
 * @author chenjinlong
 * @date 2021/1/27 16:22
 */
public class MenuButtonFactory {

    /**
     * 生成系统默认菜单按钮
     *
     * @param menuId 菜单id
     * @return 系统默认菜单按钮集合
     * @author chenjinlong
     * @date 2021/1/27 15:36
     */
    public static List<SysMenuButton> createSystemDefaultButton(Long menuId, String menuName, String menuCode) {

        List<SysMenuButton> sysMenuButtonList = CollUtil.newArrayList();

        // 菜单编码加下划线
        if (StrUtil.isNotBlank(menuCode)) {
            menuCode = menuCode.toUpperCase(Locale.ROOT) + "_";
        }

        // 菜单名称添加下划线
        if (StrUtil.isNotBlank(menuName)) {
            menuName = menuName + "_";
        }

        // 新增按钮
        SysMenuButton addButton = new SysMenuButton();
        addButton.setMenuId(menuId);
        addButton.setButtonCode(menuCode + MenuButtonConstant.DEFAULT_ADD_BUTTON_CODE);
        addButton.setButtonName(menuName + MenuButtonConstant.DEFAULT_ADD_BUTTON_NAME);
        addButton.setDelFlag(YesOrNotEnum.N.getCode());
        sysMenuButtonList.add(addButton);

        // 删除按钮
        SysMenuButton delButton = new SysMenuButton();
        delButton.setMenuId(menuId);
        delButton.setButtonCode(menuCode + MenuButtonConstant.DEFAULT_DEL_BUTTON_CODE);
        delButton.setButtonName(menuName + MenuButtonConstant.DEFAULT_DEL_BUTTON_NAME);
        delButton.setDelFlag(YesOrNotEnum.N.getCode());
        sysMenuButtonList.add(delButton);

        // 修改按钮
        SysMenuButton updateButton = new SysMenuButton();
        updateButton.setMenuId(menuId);
        updateButton.setButtonCode(menuCode + MenuButtonConstant.DEFAULT_UPDATE_BUTTON_CODE);
        updateButton.setButtonName(menuName + MenuButtonConstant.DEFAULT_UPDATE_BUTTON_NAME);
        updateButton.setDelFlag(YesOrNotEnum.N.getCode());
        sysMenuButtonList.add(updateButton);

        // 查询按钮
        SysMenuButton searchButton = new SysMenuButton();
        searchButton.setMenuId(menuId);
        searchButton.setButtonCode(menuCode + MenuButtonConstant.DEFAULT_SEARCH_BUTTON_CODE);
        searchButton.setButtonName(menuName + MenuButtonConstant.DEFAULT_SEARCH_BUTTON_NAME);
        searchButton.setDelFlag(YesOrNotEnum.N.getCode());
        sysMenuButtonList.add(searchButton);

        return sysMenuButtonList;
    }

}
