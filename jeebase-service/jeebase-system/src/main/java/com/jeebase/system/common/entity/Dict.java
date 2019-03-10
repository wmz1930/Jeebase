package com.jeebase.system.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author jeebase
 * @since 2018-10-28
 */
@TableName("t_sys_dict")
@ApiModel(value="Dict对象", description="数据字典表")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "字典类型")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "字典名称")
    @TableField("dict_name")
    private String dictName;

    @ApiModelProperty(value = "字典值")
    @TableField("dict_code")
    private String dictCode;

    @ApiModelProperty(value = "排序")
    @TableField("dict_order")
    private Integer dictOrder;

    @ApiModelProperty(value = "1有效，0禁用")
    @TableField("dict_status")
    private String dictStatus;

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
    
    
    public String getDictName() {
        return dictName;
    }

    
    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    
    public String getDictCode() {
        return dictCode;
    }

    
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    
    public Integer getDictOrder() {
        return dictOrder;
    }

    
    public void setDictOrder(Integer dictOrder) {
        this.dictOrder = dictOrder;
    }

    
    public String getDictStatus() {
        return dictStatus;
    }

    
    public void setDictStatus(String dictStatus) {
        this.dictStatus = dictStatus;
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

    @Override
    public String toString() {
        return "Dict{" +
        "id=" + id +
        ", parentId=" + parentId +
        ", dictName=" + dictName +
        ", dictCode=" + dictCode +
        ", dictOrder=" + dictOrder +
        ", dictStatus=" + dictStatus +
        ", description=" + description +
        ", createTime=" + createTime +
        ", creator=" + creator +
        ", updateTime=" + updateTime +
        ", operator=" + operator +
        ", delFlag=" + delFlag +
        "}";
    }
}
