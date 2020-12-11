package cn.stylefeng.roses.kernel.system.modular.organization.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.system.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.exception.enums.EmployeeExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.SysEmployeeApi;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.SysEmployee;
import cn.stylefeng.roses.kernel.system.modular.organization.entity.SysOrganization;
import cn.stylefeng.roses.kernel.system.modular.organization.mapper.SysEmployeeMapper;
import cn.stylefeng.roses.kernel.system.modular.organization.service.SysEmployeeService;
import cn.stylefeng.roses.kernel.system.modular.organization.service.SysOrganizationService;
import cn.stylefeng.roses.kernel.system.modular.organization.service.SysPositionService;
import cn.stylefeng.roses.kernel.system.pojo.organization.SysEmployeeRequest;
import cn.stylefeng.roses.kernel.system.pojo.organization.SysEmployeeResponse;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 企业员工表，用户-组织机构的关联 服务实现类
 *
 * @author fengshuonan
 * @date 2020/11/04 11:05
 */
@Service
public class SysEmployeeServiceImpl extends ServiceImpl<SysEmployeeMapper, SysEmployee> implements SysEmployeeService, SysEmployeeApi {

    @Resource
    private SysOrganizationService sysOrganizationService;

    @Resource
    private SysPositionService sysPositionService;

    @Override
    public PageResult<SysEmployee> page(SysEmployeeRequest sysEmployeeRequest) {

        // 构造条件
        LambdaQueryWrapper<SysEmployee> queryWrapper = createWrapper(sysEmployeeRequest);

        // 查询分页结果
        return PageResultFactory.createPageResult(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<SysEmployee> list(SysEmployeeRequest sysEmployeeRequest) {

        // 构造条件
        LambdaQueryWrapper<SysEmployee> queryWrapper = createWrapper(sysEmployeeRequest);

        return this.list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmployee(Long userId, List<SysEmployeeRequest> sysEmployeeRequest) {

        // 删除用户对应的所有员工关联信息
        SysEmployeeServiceImpl sysEmployeeService = (SysEmployeeServiceImpl) AopContext.currentProxy();
        sysEmployeeService.deleteEmployeeByUserId(userId);

        // 添加用户的组织架构关联
        ArrayList<SysEmployee> sysEmployees = new ArrayList<>();
        for (SysEmployeeRequest employeeRequest : sysEmployeeRequest) {
            SysEmployee sysEmployee = new SysEmployee();
            BeanUtil.copyProperties(employeeRequest, sysEmployee);
            sysEmployees.add(sysEmployee);
        }

        // 批量添加雇员信息
        sysEmployeeService.saveBatch(sysEmployees);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEmployeeByUserId(Long userId) {

        // 删除用户id对应的员工信息
        LambdaUpdateWrapper<SysEmployee> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysEmployee::getUserId, userId);

        this.remove(updateWrapper);
    }

    @Override
    public SysEmployeeResponse getUserMainEmployee(Long userId) {

        // 组装用户主部门员工信息查询条件
        SysEmployeeRequest sysEmployeeRequest = new SysEmployeeRequest();
        sysEmployeeRequest.setUserId(userId);
        sysEmployeeRequest.setMainDeptFlag(YesOrNotEnum.Y.getCode());

        LambdaQueryWrapper<SysEmployee> wrapper = createWrapper(sysEmployeeRequest);
        List<SysEmployee> sysEmployees = this.list(wrapper);

        // 查询不到，提示企业员工信息不存在
        if (sysEmployees == null || sysEmployees.isEmpty()) {
            String userTip = StrUtil.format(EmployeeExceptionEnum.EMPLOYEE_NOT_FOUND.getUserTip(), userId);
            throw new SystemModularException(EmployeeExceptionEnum.EMPLOYEE_NOT_FOUND, userTip);
        }

        // 查询出多个主部门，提示该用户异常
        if (sysEmployees.size() > 1) {
            String userTip = StrUtil.format(EmployeeExceptionEnum.EMPLOYEE_MANY_MAIN_NOT_FOUND.getUserTip(), userId);
            throw new SystemModularException(EmployeeExceptionEnum.EMPLOYEE_MANY_MAIN_NOT_FOUND, userTip);
        }

        return parseSysEmployee(sysEmployees.get(0));
    }

    @Override
    public List<SysEmployeeResponse> getUserAllEmployee(Long userId) {

        // 组装用户主部门员工信息查询条件
        SysEmployeeRequest sysEmployeeRequest = new SysEmployeeRequest();
        sysEmployeeRequest.setUserId(userId);

        LambdaQueryWrapper<SysEmployee> wrapper = createWrapper(sysEmployeeRequest);
        List<SysEmployee> sysEmployees = this.list(wrapper);

        // 查询不到员工信息报异常
        if (sysEmployees.isEmpty()) {
            String userTip = StrUtil.format(EmployeeExceptionEnum.EMPLOYEE_NOT_FOUND.getUserTip(), userId);
            throw new SystemModularException(EmployeeExceptionEnum.EMPLOYEE_NOT_FOUND, userTip);
        }

        // 转化为SysEmployeeResponse对象，拼接组织机构名称和职位名称
        ArrayList<SysEmployeeResponse> sysEmployeeResponses = new ArrayList<>();
        for (SysEmployee sysEmployee : sysEmployees) {
            sysEmployeeResponses.add(parseSysEmployee(sysEmployee));
        }

        return sysEmployeeResponses;
    }


    /**
     * 企业雇员的查询条件
     *
     * @author fengshuonan
     * @date 2020/11/6 16:37
     */
    private LambdaQueryWrapper<SysEmployee> createWrapper(SysEmployeeRequest sysEmployeeRequest) {
        LambdaQueryWrapper<SysEmployee> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysEmployeeRequest)) {

            // 拼接用户id
            if (ObjectUtil.isNotEmpty(sysEmployeeRequest.getUserId())) {
                queryWrapper.eq(SysEmployee::getUserId, sysEmployeeRequest.getUserId());
            }

            // 拼接组织机构id
            if (ObjectUtil.isNotEmpty(sysEmployeeRequest.getOrganizationId())) {
                queryWrapper.eq(SysEmployee::getOrganizationId, sysEmployeeRequest.getOrganizationId());
            }

            // 拼接职位id查询条件
            if (ObjectUtil.isNotEmpty(sysEmployeeRequest.getPositionId())) {
                queryWrapper.like(SysEmployee::getPositionIds, sysEmployeeRequest.getPositionId());
            }

            // 拼接主部门标识
            if (ObjectUtil.isNotEmpty(sysEmployeeRequest.getMainDeptFlag())) {
                queryWrapper.eq(SysEmployee::getMainDeptFlag, sysEmployeeRequest.getMainDeptFlag());
            }

        }
        return queryWrapper;
    }

    /**
     * 转化实体类为Response对象,填充机构名称和职位名称
     *
     * @author fengshuonan
     * @date 2020/11/20 22:44
     */
    private SysEmployeeResponse parseSysEmployee(SysEmployee sysEmployee) {

        SysEmployeeResponse sysEmployeeResponse = new SysEmployeeResponse();
        BeanUtil.copyProperties(sysEmployee, sysEmployeeResponse);

        // 组装组织机构名称
        SysOrganization sysOrganization = sysOrganizationService.getById(sysEmployee.getOrganizationId());
        if (sysOrganization != null) {
            sysEmployeeResponse.setOrganizationName(sysOrganization.getName());
        }

        // 组装职位名称,用逗号隔开多个
        String[] positionIdsString = StrUtil.split(sysEmployee.getPositionIds(), StrUtil.COMMA);
        List<Long> positionIds = Arrays.stream(positionIdsString).map(Long::valueOf).collect(Collectors.toList());
        List<String> positionNamesByPositionIds = sysPositionService.getPositionNamesByPositionIds(positionIds);
        String join = StrUtil.join(StrUtil.COMMA, positionNamesByPositionIds);
        sysEmployeeResponse.setPositionNames(join);

        return sysEmployeeResponse;
    }

}