package com.jeebase.system.security.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author jeebase
 * @since 2018-05-19
 */
public class CreateResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String resourceName;

    private Integer parentId;

    private String resourceKey;

    private String resourceType;

    private String resourceIcon;

    private String resourcePath;

    private Boolean resourceShow;

    private Boolean resourceCache;

    private String resourceUrl;

    private Integer resourceLevel;

    private String resourcePageName;

    private String description;

    private Date createTime;

    private Integer creator;

    private Date updateTime;

    private Integer operator;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
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

    @Override
    public String toString() {
        return "Resource{" + "id=" + id + ", resourceName=" + resourceName + ", parentId=" + parentId + ", resourceKey="
                + resourceKey + ", resourceType=" + resourceType + ", resourceIcon=" + resourceIcon + ", resourceUrl="
                + resourceUrl + ", resourceLevel=" + resourceLevel + ", description=" + description + ", createTime="
                + createTime + ", creator=" + creator + ", updateTime=" + updateTime + ", operator=" + operator + "}";
    }
}
