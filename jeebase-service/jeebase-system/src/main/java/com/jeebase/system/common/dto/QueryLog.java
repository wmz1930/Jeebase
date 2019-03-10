package com.jeebase.system.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

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
public class QueryLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "接口名称")
    private String methodName;

    @ApiModelProperty(value = "日志类型")
    private String logType;

    @ApiModelProperty(value = "操作名称")
    private String operationName;

    @ApiModelProperty(value = "操作的IP")
    private String operationIp;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始日期")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束日期")
    private LocalDateTime endTime;

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

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Log{" + ", methodName=" + methodName + ", logType=" + logType + ", operationName=" + operationName
                + ", operationIp=" + operationIp + ", startTime=" + startTime + ", endTime=" + endTime + ", creator="
                + creator + "}";
    }
}
