package cn.stylefeng.roses.kernel.migration.aggregation.scheduling;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.migration.api.AccessMigrationApi;
import cn.stylefeng.roses.kernel.migration.api.constants.MigrationConstants;
import cn.stylefeng.roses.kernel.migration.api.pojo.MigrationAggregationPOJO;
import cn.stylefeng.roses.kernel.migration.api.pojo.MigrationInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 调度中心
 *
 * @author majianguo
 * @date 2021/7/6 16:01
 */
public class SchedulingCenter {

    /**
     * 迁移数据接入集合
     */
    private static final ConcurrentHashMap<String, AccessMigrationApi> migrationCollection = new ConcurrentHashMap<>();

    /**
     * 接入一个迁移类
     *
     * @param accessMigrationApi 迁移接口
     * @author majianguo
     * @date 2021/7/6 17:21
     **/
    public static void addMigration(AccessMigrationApi accessMigrationApi) {

        // 拼接key值
        String appAndModuleNameItem = accessMigrationApi.getAppName() + MigrationConstants.NAME_SEPARATOR + accessMigrationApi.getModuleName();

        // 存储迁移类
        migrationCollection.put(appAndModuleNameItem, accessMigrationApi);
    }

    /**
     * 导入数据
     *
     * @param migrationAggregationPOJO 导入的数据
     * @author majianguo
     * @date 2021/7/6 16:10
     **/
    public static void importData(MigrationAggregationPOJO migrationAggregationPOJO, String type) {

        // 所有导入数据集合
        Map<String, MigrationInfo> migrationInfoMap = migrationAggregationPOJO.getData();

        // 找到每个应用的处理程序，调用相关方法
        for (String appAndModuleName : migrationAggregationPOJO.getAppAndModuleNameList()) {
            AccessMigrationApi accessMigrationApi = migrationCollection.get(appAndModuleName);
            if (ObjectUtil.isNotEmpty(accessMigrationApi)) {

                // 找到数据
                MigrationInfo migrationInfo = migrationInfoMap.get(appAndModuleName);

                // 发送给实现类处理
                try {
                    accessMigrationApi.importData(type, migrationInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 导出数据
     *
     * @param migrationAggregationPOJO 导出要求
     * @return {@link MigrationAggregationPOJO}
     * @author majianguo
     * @date 2021/7/6 16:11
     **/
    public static MigrationAggregationPOJO exportData(MigrationAggregationPOJO migrationAggregationPOJO) {
        Map<String, MigrationInfo> data = migrationAggregationPOJO.getData();
        if (ObjectUtil.isEmpty(data)) {
            data = new HashMap<>();
            migrationAggregationPOJO.setData(data);
        }

        // 导出所有指定项
        for (String appAndModuleName : migrationAggregationPOJO.getAppAndModuleNameList()) {
            // 查找对应的实现类
            AccessMigrationApi accessMigrationApi = migrationCollection.get(appAndModuleName);
            if (ObjectUtil.isNotEmpty(accessMigrationApi)) {
                // 执行导出
                try {
                    // 获取数据
                    MigrationInfo migrationInfo = accessMigrationApi.exportData();

                    // 放入返回结果中
                    data.put(appAndModuleName, migrationInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                data.put(appAndModuleName, new MigrationInfo());
            }
        }

        return migrationAggregationPOJO;
    }

    /**
     * 获取所有迁移数据列表
     *
     * @return {@link java.util.List<MigrationInfo>}
     * @author majianguo
     * @date 2021/7/6 16:13
     **/
    public static List<String> getAllMigrationInfo() {
        return new ArrayList<>(migrationCollection.keySet());
    }
}
