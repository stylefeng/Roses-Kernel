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
package cn.stylefeng.roses.kernel.dict.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dict.api.enums.DictTypeClassEnum;
import cn.stylefeng.roses.kernel.dict.api.exception.DictException;
import cn.stylefeng.roses.kernel.dict.api.exception.enums.DictExceptionEnum;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDict;
import cn.stylefeng.roses.kernel.dict.modular.entity.SysDictType;
import cn.stylefeng.roses.kernel.dict.modular.mapper.DictTypeMapper;
import cn.stylefeng.roses.kernel.dict.modular.pojo.request.DictTypeRequest;
import cn.stylefeng.roses.kernel.dict.modular.service.DictService;
import cn.stylefeng.roses.kernel.dict.modular.service.DictTypeService;
import cn.stylefeng.roses.kernel.pinyin.api.PinYinApi;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典类型表 服务实现类
 *
 * @author fengshuonan
 * @date 2020/12/26 22:36
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, SysDictType> implements DictTypeService {

    @Resource
    private PinYinApi pinYinApi;

    @Override
    public void add(DictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        SysDictType sysDictType = new SysDictType();
        BeanUtil.copyProperties(dictTypeRequest, sysDictType);
        sysDictType.setStatusFlag(StatusEnum.ENABLE.getCode());
        sysDictType.setDictTypeNamePinyin(pinYinApi.parseEveryPinyinFirstLetter(sysDictType.getDictTypeName()));
        this.save(sysDictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(DictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        SysDictType sysDictType = this.querySysDictType(dictTypeRequest);
        sysDictType.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(sysDictType);

    }

    @Override
    public void edit(DictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        // 更新数据
        SysDictType sysDictType = this.querySysDictType(dictTypeRequest);
        BeanUtil.copyProperties(dictTypeRequest, sysDictType);
        sysDictType.setDictTypeCode(null);
        // 设置首字母拼音
        sysDictType.setDictTypeNamePinyin(pinYinApi.parseEveryPinyinFirstLetter(sysDictType.getDictTypeName()));
        this.updateById(sysDictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editStatus(DictTypeRequest dictTypeRequest) {

        // 如果是系统级字典，只允许管理员操作
        validateSystemTypeClassOperate(dictTypeRequest);

        // 更新数据
        SysDictType oldSysDictType = this.querySysDictType(dictTypeRequest);
        oldSysDictType.setStatusFlag(dictTypeRequest.getStatusFlag());
        this.updateById(oldSysDictType);
    }

    @Override
    public SysDictType detail(DictTypeRequest dictTypeRequest) {
        return this.getOne(this.createWrapper(dictTypeRequest), false);
    }

    @Override
    public List<SysDictType> findList(DictTypeRequest dictTypeRequest) {
        return this.list(this.createWrapper(dictTypeRequest));
    }

    @Override
    public PageResult<SysDictType> findPage(DictTypeRequest dictTypeRequest) {
        Page<SysDictType> page = this.page(PageFactory.defaultPage(), this.createWrapper(dictTypeRequest));
        return PageResultFactory.createPageResult(page);
    }


    /**
     * 校验dictTypeClass是否是系统字典，如果是系统字典只能超级管理员操作
     *
     * @author fengshuonan
     * @date 2020/12/25 15:57
     */
    private void validateSystemTypeClassOperate(DictTypeRequest dictTypeRequest) {
        if (DictTypeClassEnum.SYSTEM_TYPE.getCode().equals(dictTypeRequest.getDictTypeClass())) {
            if (!LoginContext.me().getSuperAdminFlag()) {
                throw new DictException(DictExceptionEnum.SYSTEM_DICT_NOT_ALLOW_OPERATION);
            }
        }
    }

    /**
     * 根据主键id获取对象
     *
     * @author chenjinlong
     * @date 2021/1/26 13:28
     */
    private SysDictType querySysDictType(DictTypeRequest dictTypeRequest) {
        SysDictType sysDictType = this.getById(dictTypeRequest.getDictTypeId());
        if (ObjectUtil.isEmpty(sysDictType)) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_NOT_EXISTED, dictTypeRequest.getDictTypeId());
        }
        return sysDictType;
    }

    /**
     * 实体构建queryWrapper
     *
     * @author fengshuonan
     * @date 2021/1/24 22:03
     */
    private LambdaQueryWrapper<SysDictType> createWrapper(DictTypeRequest dictTypeRequest) {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();

        Long dictTypeId = dictTypeRequest.getDictTypeId();
        String dictTypeCode = dictTypeRequest.getDictTypeCode();
        String dictTypeName = dictTypeRequest.getDictTypeName();

        // SQL拼接
        queryWrapper.eq(ObjectUtil.isNotNull(dictTypeId), SysDictType::getDictTypeId, dictTypeId);
        queryWrapper.eq(ObjectUtil.isNotNull(dictTypeCode), SysDictType::getDictTypeCode, dictTypeCode);
        queryWrapper.like(ObjectUtil.isNotNull(dictTypeName), SysDictType::getDictTypeName, dictTypeName);

        // 查询未删除的
        queryWrapper.eq(SysDictType::getDelFlag, YesOrNotEnum.N.getCode());

        return queryWrapper;
    }

}
