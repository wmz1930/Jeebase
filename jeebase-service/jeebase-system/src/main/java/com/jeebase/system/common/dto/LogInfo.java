package com.jeebase.system.common.dto;

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
@ApiModel(value = "Log对象", description = "")
public class LogInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private Long id;

    @ApiModelProperty(value = "接口名称")
    private String methodName;

    @ApiModelProperty(value = "日志类型")
    private String logType;

    @ApiModelProperty(value = "入参")
    private String inParams;

    @ApiModelProperty(value = "出参")
    private String outParams;

    @ApiModelProperty(value = "操作名称")
    private String operationName;

    @ApiModelProperty(value = "操作的IP")
    private String operationIp;

    @ApiModelProperty(value = "记录日期")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "操作人")
    private String creator;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogType() {
        return logType;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Log{" + ", methodName=" + methodName + ", logType=" + logType + ", operationName=" + operationName
                + ", operationIp=" + operationIp + ", createTime=" + createTime + ", creator=" + creator + "}";
    }
}
