package com.jeebase.system.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jeebase
 * @since 2018-10-24
 */
@TableName("t_sys_log")
@ApiModel(value = "Log对象", description = "")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "接口名称")
    @TableField("method_name")
    private String methodName;

    @ApiModelProperty(value = "入参")
    @TableField("in_params")
    private String inParams;

    @ApiModelProperty(value = "出参")
    @TableField("out_params")
    private String outParams;

    @ApiModelProperty(value = "日志类型")
    @TableField("log_type")
    private String logType;

    @ApiModelProperty(value = "操作名称")
    @TableField("operation_name")
    private String operationName;

    @ApiModelProperty(value = "操作的IP")
    @TableField("operation_ip")
    private String operationIp;

    @ApiModelProperty(value = "创建日期")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建者")
    @TableField(value = "creator", fill = FieldFill.INSERT)
    private Integer creator;

    @ApiModelProperty(value = "更新日期")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新者")
    @TableField(value = "operator", fill = FieldFill.UPDATE)
    private Integer operator;

    @ApiModelProperty(value = "1:删除 0:不删除")
    @TableField("del_flag")
    @TableLogic
    private String delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getInParams() {
        return inParams;
    }

    public void setInParams(String inParams) {
        this.inParams = inParams;
    }

    public String getOutParams() {
        return outParams;
    }

    public void setOutParams(String outParams) {
        this.outParams = outParams;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationIp() {
        return operationIp;
    }

    public void setOperationIp(String operationIp) {
        this.operationIp = operationIp;
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
        return "Log{" + "id=" + id + ", methodName=" + methodName + ", inParams=" + inParams + ", outParams="
                + outParams + ", logType=" + logType + ", operationName=" + operationName + ", operationIp="
                + operationIp + ", createTime=" + createTime + ", creator=" + creator + ", updateTime=" + updateTime
                + ", operator=" + operator + ", delFlag=" + delFlag + "}";
    }
}
