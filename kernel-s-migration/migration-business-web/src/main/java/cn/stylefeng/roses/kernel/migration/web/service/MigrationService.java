package cn.stylefeng.roses.kernel.migration.web.service;

import cn.stylefeng.roses.kernel.migration.api.pojo.MigrationAggregationPOJO;
import cn.stylefeng.roses.kernel.migration.web.pojo.MigrationRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 迁移服务接口
 *
 * @author majianguo
 * @date 2021/7/7 9:34
 */
public interface MigrationService {

    /**
     * 获取所有可备份数据列表
     *
     * @return {@link List< MigrationRequest>}
     * @author majianguo
     * @date 2021/7/7 9:36
     **/
    List<MigrationRequest> getAllMigrationList();

    /**
     * 备份指定数据列表
     *
     * @author majianguo
     * @date 2021/7/7 9:37
     **/
    String migrationSelectData(MigrationAggregationPOJO migrationAggregationPOJO);

    /**
     * 恢复备份数据
     *
     * @author majianguo
     * @date 2021/7/7 11:14
     **/
    void restoreData(MultipartFile file,String type);
}
