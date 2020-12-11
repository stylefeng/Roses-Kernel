package cn.stylefeng.roses.kernel.menu.modular.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.roses.kernel.rule.abstracts.AbstractTreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 系统菜单
 *
 * @author stylefeng
 * @date 2020/11/22 21:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class SysMenu extends BaseEntity implements AbstractTreeNode {

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 父id，顶级节点的父id是0
     */
    @TableField("pid")
    private Long pid;

    /**
     * 父id集合，中括号包住，逗号分隔
     */
    @TableField("pids")
    private String pids;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 菜单的编码
     */
    @TableField("code")
    private String code;

    /**
     * 应用分类（应用编码）
     */
    @TableField("app_code")
    private String appCode;

    /**
     * 是否可见（Y-是，N-否）
     */
    @TableField("visible")
    private String visible;

    /**
     * 排序
     */
    @TableField("sort")
    private BigDecimal sort;

    /**
     * 状态（1-启用，2-禁用）
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 关联的资源的编码
     */
    @TableField("resource_code")
    private String resourceCode;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 路由地址，浏览器显示的URL，例如/menu
     */
    @TableField("router")
    private String router;

    /**
     * 前端组件名
     */
    @TableField("component")
    private String component;

    /**
     * 外部链接打开方式（1内置外链 2新页面外链）
     */
    @TableField("link_open_type")
    private Integer linkOpenType;

    /**
     * 外部链接地址
     */
    @TableField("link_url")
    private String linkUrl;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 是否删除，Y-被删除，N-未删除
     */
    @TableField("del_flag")
    private String delFlag;

    /**
     * 子节点（表中不存在，用于构造树）
     */
    @TableField(exist = false)
    private List children;

    @Override
    public String getNodeId() {
        return id.toString();
    }

    @Override
    public String getNodeParentId() {
        return pid.toString();
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.children = childrenNodes;
    }
}
