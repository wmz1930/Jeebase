package com.jeebase.system.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 组织表
 * </p>
 *
 * @author jeebase
 * @since 2018-10-24
 */
@TableName("t_sys_organization")
@ApiModel(value = "Organization对象", description = "组织表")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父组织id")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "组织类型：1总公司，2分公司，3事业部")
    @TableField("organization_type")
    private String organizationType;

    @ApiModelProperty(value = "组织名称")
    @TableField("organization_name")
    private String organizationName;

    @ApiModelProperty(value = "组织编码")
    @TableField("organization_key")
    private String organizationKey;

    @ApiModelProperty(value = "组织图标")
    @TableField("organization_icon")
    private String organizationIcon;

    @ApiModelProperty(value = "组织级别（排序）")
    @TableField("organization_level")
    private Integer organizationLevel;

    @ApiModelProperty(value = "省")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "区")
    @TableField("area")
    private String area;

    @ApiModelProperty(value = "街道详细地址")
    @TableField("street")
    private String street;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "创建日期")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "creator", fill = FieldFill.INSERT)
    private Integer creator;

    @ApiModelProperty(value = "修改日期")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "操作员")
    @TableField(value = "operator", fill = FieldFill.UPDATE)
    private Integer operator;

    @ApiModelProperty(value = "1:删除 0:不删除")
    @TableField("del_flag")
    @TableLogic
    private String delFlag;

    /**
     * 是否是叶子节点
     */
    @TableField(exist = false)
    private Integer isLeaf;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<Organization> children;

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

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public List<Organization> getChildren() {
        return children;
    }

    public void setChildren(List<Organization> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Organization{" + "id=" + id + ", parentId=" + parentId + ", organizationType=" + organizationType
                + ", organizationName=" + organizationName + ", organizationKey=" + organizationKey
                + ", organizationIcon=" + organizationIcon + ", organizationLevel=" + organizationLevel
                + ", description=" + description + ", createTime=" + createTime + ", creator=" + creator
                + ", updateTime=" + updateTime + ", operator=" + operator + ", delFlag=" + delFlag + "}";
    }
}
