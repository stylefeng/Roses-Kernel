package cn.stylefeng.roses.kernel.system.modular.title.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.file.api.pojo.request.SysFileInfoRequest;
import cn.stylefeng.roses.kernel.file.api.pojo.response.SysFileInfoResponse;
import cn.stylefeng.roses.kernel.file.modular.service.SysFileInfoService;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.system.api.exception.SystemModularException;
import cn.stylefeng.roses.kernel.system.api.exception.enums.title.SysTitleExceptionEnum;
import cn.stylefeng.roses.kernel.system.api.pojo.title.SysTitleRequest;
import cn.stylefeng.roses.kernel.system.modular.title.entity.SysTitle;
import cn.stylefeng.roses.kernel.system.modular.title.mapper.SysTitleMapper;
import cn.stylefeng.roses.kernel.system.modular.title.service.SysTitleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 标题图片配置服务实现类
 *
 * @author xixiaowei
 * @date 2021/12/13 17:28
 */
@Service
public class SysTitleServiceImpl extends ServiceImpl<SysTitleMapper, SysTitle> implements SysTitleService {

    private static final Long DEFAULT_FILE_UPLOAD = null;

    @Resource
    private SysFileInfoService sysFileInfoService;

    @Override
    public void add(SysTitleRequest sysTitleRequest) {
        SysTitle sysTitle = new SysTitle();

        // 拷贝属性
        BeanUtil.copyProperties(sysTitleRequest, sysTitle);

        // 启用状态默认为未启用  N
        sysTitle.setStatus(YesOrNotEnum.N.getCode().charAt(0));

        // 保存图片信息
        SysFileInfoResponse backgroundImageResponse = this.uploadFile(sysTitleRequest.getBackgroundImage(), DEFAULT_FILE_UPLOAD);
        sysTitle.setBackgroundImage(backgroundImageResponse.getFileId().toString());
        SysFileInfoResponse browserIconResponse = this.uploadFile(sysTitleRequest.getBrowserIcon(), DEFAULT_FILE_UPLOAD);
        sysTitle.setBrowserIcon(browserIconResponse.getFileId().toString());
        SysFileInfoResponse companyLogoResponse = this.uploadFile(sysTitleRequest.getCompanyLogo(), DEFAULT_FILE_UPLOAD);
        sysTitle.setCompanyLogo(companyLogoResponse.getFileId().toString());
        SysFileInfoResponse pageImageResponse = this.uploadFile(sysTitleRequest.getPageImage(), DEFAULT_FILE_UPLOAD);
        sysTitle.setPageImage(pageImageResponse.getFileId().toString());

        // 保存配置信息
        this.save(sysTitle);
    }

    /**
     * 上传图片
     *
     * @param multipartFile 上传的图片
     * @author xixiaowei
     * @date 2021/12/14 14:40
     */
    private SysFileInfoResponse uploadFile(MultipartFile multipartFile, Long fileCode) {
        SysFileInfoRequest sysFileInfoRequest = new SysFileInfoRequest();
        sysFileInfoRequest.setFileId(IdWorker.getId());
        if (fileCode != null) {
            sysFileInfoRequest.setFileCode(fileCode);
        }
        sysFileInfoRequest.setSecretFlag(YesOrNotEnum.N.getCode());
        sysFileInfoRequest.setFileLocation(4);
        sysFileInfoRequest.setFilePath("D:\\test");
        sysFileInfoRequest.setFileOriginName(multipartFile.getOriginalFilename());
        return sysFileInfoService.uploadFile(multipartFile, sysFileInfoRequest);
    }

    @Override
    public void del(SysTitleRequest sysTitleRequest) {
        SysTitle sysTitle = this.querySysTitleById(sysTitleRequest);

        // 启用状态不允许删除
        if (Character.valueOf(YesOrNotEnum.Y.getCode().charAt(0)).equals(sysTitle.getStatus())) {
            throw new SystemModularException(SysTitleExceptionEnum.ENABLE_NOT_ALLOW_DELETE, sysTitleRequest.getTitleId());
        }

        // 删除配置
        this.removeById(sysTitle);
    }

    /**
     * 获取标题图片配置
     *
     * @author xixiaowei
     * @date 2021/12/14 9:18
     */
    private SysTitle querySysTitleById(SysTitleRequest sysTitleRequest) {
        SysTitle sysTitle = this.getById(sysTitleRequest.getTitleId());
        if (ObjectUtil.isEmpty(sysTitle)) {
            throw new SystemModularException(SysTitleExceptionEnum.TITLE_NOT_EXIST, sysTitleRequest.getTitleId());
        }
        return sysTitle;
    }

    @Override
    public void edit(SysTitleRequest sysTitleRequest) {
        SysTitle sysTitle = this.querySysTitleById(sysTitleRequest);

        // 未启用状态不允许编辑
        if (Character.valueOf(YesOrNotEnum.N.getCode().charAt(0)).equals(sysTitle.getStatus())) {
            throw new SystemModularException(SysTitleExceptionEnum.DISABLE_NOT_ALLOW_EDIT, sysTitleRequest.getTitleId());
        }

        // 图片修改
        sysTitle.setBackgroundImage(this.updateFile(sysTitleRequest.getBackgroundImage(), sysTitle.getBackgroundImage()));
        sysTitle.setBrowserIcon(this.updateFile(sysTitleRequest.getBrowserIcon(), sysTitle.getBrowserIcon()));
        sysTitle.setCompanyLogo(this.updateFile(sysTitleRequest.getCompanyLogo(), sysTitle.getCompanyLogo()));
        sysTitle.setPageImage(this.updateFile(sysTitleRequest.getPageImage(), sysTitle.getPageImage()));

        // 更新属性
        BeanUtil.copyProperties(sysTitleRequest, sysTitle);

        // 修改配置
        this.updateById(sysTitle);
    }

    /**
     * 更新图片
     *
     * @param multipartFile 新图片
     * @param fileId        旧图片Id
     * @return 新图片Id
     * @author xixiaowei
     * @date 2021/12/14 15:21
     */
    private String updateFile(MultipartFile multipartFile, String fileId) {
        SysFileInfoResponse fileInfoResult = sysFileInfoService.getFileInfoResult(Long.parseLong(fileId));

        // 上传图片
        SysFileInfoResponse sysFileInfoResponse = this.uploadFile(multipartFile, fileInfoResult.getFileCode());

        return sysFileInfoResponse.getFileId().toString();
    }

    @Override
    public SysTitle detail(SysTitleRequest sysTitleRequest) {
        return this.querySysTitleById(sysTitleRequest);
    }

    @Override
    public PageResult<SysTitle> findPage(SysTitleRequest sysTitleRequest) {
        LambdaQueryWrapper<SysTitle> wrapper = this.createWrapper(sysTitleRequest);
        Page<SysTitle> page = this.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    /**
     * 创建Wrapper
     *
     * @author xixiaowei
     * @date 2021/12/14 10:04
     */
    private LambdaQueryWrapper<SysTitle> createWrapper(SysTitleRequest sysTitleRequest) {
        LambdaQueryWrapper<SysTitle> wrapper = new LambdaQueryWrapper<>();

        // 按照创建时间倒序
        wrapper.orderByDesc(BaseEntity::getCreateTime);

        // 如果标题不为空，根据标题名称模糊查询；如果标题为空，查询全部
        wrapper.like(StrUtil.isNotBlank(sysTitleRequest.getTitleName()), SysTitle::getTitleName, sysTitleRequest.getTitleName());
        return wrapper;
    }

    @Override
    public void copyTitle(SysTitleRequest sysTitleRequest) {
        SysTitle sysTitle = this.querySysTitleById(sysTitleRequest);

        SysTitle title = new SysTitle();
        // 拷贝属性
        BeanUtil.copyProperties(sysTitle, title);
        // 修改标题名称
        title.setTitleName(sysTitle.getTitleName() + "复制");
        // 标题状态设为默认状态 未启用 N
        title.setStatus(YesOrNotEnum.N.getCode().charAt(0));

        // 保存配置信息
        this.save(title);
    }

    @Override
    public void updateStatus(SysTitleRequest sysTitleRequest) {
        SysTitle sysTitle = this.querySysTitleById(sysTitleRequest);

        // 如果状态为唯一启用状态不允许被禁用， 如果为未启用状态则可以启用该配置同时禁用已启用的标题配置
        if (Character.valueOf(YesOrNotEnum.Y.getCode().charAt(0)).equals(sysTitle.getStatus())) {
            throw new SystemModularException(SysTitleExceptionEnum.UNIQUE_ENABLE, sysTitleRequest.getTitleId());
        } else {
            // 设置为启用状态
            sysTitle.setStatus(YesOrNotEnum.Y.getCode().charAt(0));
            // 修改已启用的标题配置
            if (this.updateEnableToDisableStatus()) {
                updateById(sysTitle);
            }
        }
    }

    /**
     * 修改已启用的标题配置为禁用状态
     *
     * @author xixiaowei
     * @date 2021/12/14 14:00
     */
    private Boolean updateEnableToDisableStatus() {
        // 查询已启用的标题配置
        LambdaQueryWrapper<SysTitle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysTitle::getStatus, YesOrNotEnum.Y.getCode().charAt(0));
        SysTitle sysTitle = getOne(wrapper);

        // 修改状态
        sysTitle.setStatus(YesOrNotEnum.N.getCode().charAt(0));
        return updateById(sysTitle);
    }
}
