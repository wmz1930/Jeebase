package com.jeebase.system.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author jeebase
 * @since 2018-10-24
 */
@TableName("t_sys_district")
@ApiModel(value = "District对象", description = "")
public class District implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("id")
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("parent_id")
    private Integer parentId;

    @TableField("initial")
    private String initial;

    @TableField("initials")
    private String initials;

    @TableField("pinyin")
    private String pinyin;

    @TableField("suffix")
    private String suffix;

    @TableField("code")
    private String code;

    @TableField("order")
    private Integer order;

    @TableField(exist = false)
    private List<District> children = new ArrayList<District>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<District> getChildren() {
        return children;
    }

    public void setChildren(List<District> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "District{" + "id=" + id + ", name=" + name + ", parentId=" + parentId + ", initial=" + initial
                + ", initials=" + initials + ", pinyin=" + pinyin + ", suffix=" + suffix + ", code=" + code + ", order="
                + order + "}";
    }
}
