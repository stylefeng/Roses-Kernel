package cn.stylefeng.roses.kernel.system.modular.statistic.pojo;

import lombok.Data;

import java.util.Set;

/**
 * 在线用户统计
 *
 * @author fengshuonan
 * @date 2022/2/11 10:54
 */
@Data
public class OnlineUserStat {

    /**
     * 总在线用户
     */
    private Integer totalNum;

    /**
     * 总的人员姓名汇总
     */
    private Set<String> totalUserNames;

}
