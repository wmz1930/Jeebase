package com.jeebase.system.security.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 组织表
 * </p>
 *
 * @author jeebase
 * @since 2018-05-26
 */
public class UpdateOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;
    /**
     * 父组织id
     */
    private Integer parentId;
    /**
     * 组织类型：1总公司，2分公司，3事业部
     */
    private String organizationType;
    /**
     * 组织名称
     */
    private String organizationName;
    /**
     * 组织编码
     */
    private String organizationKey;
    /**
     * 组织图标
     */
    private String organizationIcon;
    /**
     * 组织级别（排序）
     */
    private Integer organizationLevel;

    /**
     * 省
     */
    @ApiModelProperty(value = "省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty(value = "市")
    private String city;

    /**
     * 区
     */
    @ApiModelProperty(value = "区")
    private String area;

    /**
     * 地区数组
     */
    private List<String> areas;

    /**
     * 街道详细地址
     */
    @ApiModelProperty(value = "街道详细地址")
    private String street;

    /**
     * 描述
     */
    private String description;
    /**
     * 创建日期
     */
    private Date createTime;

    private Integer creator;
    /**
     * 修改日期
     */
    private Date updateTime;
    /**
     * 操作员
     */
    private Integer operator;
    /**
     * 1:删除 0:不删除
     */
    private String delFlag;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationKey() {
        return organizationKey;
    }

    public void setOrganizationKey(String organizationKey) {
        this.organizationKey = organizationKey;
    }

    public String getOrganizationIcon() {
        return organizationIcon;
    }

    public void setOrganizationIcon(String organizationIcon) {
        this.organizationIcon = organizationIcon;
    }

    public Integer getOrganizationLevel() {
        return organizationLevel;
    }

    public void setOrganizationLevel(Integer organizationLevel) {
        this.organizationLevel = organizationLevel;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<String> getAreas() {
        return areas;
    }

    public void setAreas(List<String> areas) {
        this.areas = areas;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Organization{" +
        "id=" + id +
        ", parentId=" + parentId +
        ", organizationType=" + organizationType +
        ", organizationName=" + organizationName +
        ", organizationKey=" + organizationKey +
        ", organizationIcon=" + organizationIcon +
        ", organizationLevel=" + organizationLevel +
        ", description=" + description +
        ", createTime=" + createTime +
        ", creator=" + creator +
        ", updateTime=" + updateTime +
        ", operator=" + operator +
        ", delFlag=" + delFlag +
        "}";
    }
}
