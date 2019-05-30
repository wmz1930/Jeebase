package com.jeebase.system.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author jeebase
 * @since 2018-10-24
 */
@TableName("t_sys_resource")
@ApiModel(value = "Resource对象", description = "权限表")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("resource_name")
    private String resourceName;

    @TableField("parent_id")
    private Integer parentId;

    @TableField("resource_key")
    private String resourceKey;

    @ApiModelProperty(value = "资源类型：1、模块 2、菜单 3、按钮 4、链接")
    @TableField("resource_type")
    private String resourceType;

    @TableField("resource_icon")
    private String resourceIcon;

    @TableField("resource_path")
    private String resourcePath;

    @TableField("resource_url")
    private String resourceUrl;

    @TableField("resource_level")
    private Integer resourceLevel;

    @ApiModelProperty(value = "是否显示")
    @TableField("resource_show")
    private Boolean resourceShow;

    @ApiModelProperty(value = "是否缓存")
    @TableField("resource_cache")
    private Boolean resourceCache;

    @ApiModelProperty(value = "页面name")
    @TableField("resource_page_name")
    private String resourcePageName;

    @TableField("description")
    private String description;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "creator", fill = FieldFill.INSERT)
    private Integer creator;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableField(value = "operator", fill = FieldFill.UPDATE)
    private Integer operator;

    @ApiModelProperty(value = "1:删除 0:不删除")
    @TableField("del_flag")
    @TableLogic
    private String delFlag;

    /**
     * 子菜单，必须初始化否则vue新增不展示树子菜单
     */
    @TableField(exist = false)
    private List<Resource> children = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceIcon() {
        return resourceIcon;
    }

    public void setResourceIcon(String resourceIcon) {
        this.resourceIcon = resourceIcon;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Integer getResourceLevel() {
        return resourceLevel;
    }

    public void setResourceLevel(Integer resourceLevel) {
        this.resourceLevel = resourceLevel;
    }

    public Boolean getResourceShow() {
        return resourceShow;
    }

    public void setResourceShow(Boolean resourceShow) {
        this.resourceShow = resourceShow;
    }

    public Boolean getResourceCache() {
        return resourceCache;
    }

    public void setResourceCache(Boolean resourceCache) {
        this.resourceCache = resourceCache;
    }


    public String getResourcePageName() {
        return resourcePageName;
    }

    public void setResourcePageName(String resourcePageName) {
        this.resourcePageName = resourcePageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public List<Resource> getChildren() {
        return children;
    }

    public void setChildren(List<Resource> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Resource{" + "id=" + id + ", resourceName=" + resourceName + ", parentId=" + parentId + ", resourceKey="
                + resourceKey + ", resourceType=" + resourceType + ", resourceIcon=" + resourceIcon + ", resourcePath="
                + resourcePath + ", resourceUrl=" + resourceUrl + ", resourceLevel=" + resourceLevel + ", resourceShow="
                + resourceShow + ", resourceCache=" + resourceCache + ", resourcePageName=" + resourcePageName + ", description=" + description + ", createTime="
                + createTime + ", creator=" + creator + ", updateTime=" + updateTime + ", operator=" + operator
                + ", delFlag=" + delFlag + "}";
    }
}
