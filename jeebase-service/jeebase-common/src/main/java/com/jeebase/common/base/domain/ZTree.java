package com.jeebase.common.base.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @ClassName: ZTree
 * @Description: zTree实体类
 * @author jeebase
 * @date 2016年1月8日 上午9:54:27
 */
public class ZTree {

    private String id;

    private String pId;

    private String name;

    private boolean open;

    private boolean isIsParent;

    private String extKey;

    private String extUrl;

    private String extType;

    private String extLevel;

    private String extIcon;

    private String description;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime modifyTime;

    /**
     * 操作人
     */
    private String operator;

    private List<ZTree> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isIsParent() {
        return isIsParent;
    }

    public void setIsParent(boolean isIsParent) {
        this.isIsParent = isIsParent;
    }

    public String getExtKey() {
        return extKey;
    }

    public void setExtKey(String extKey) {
        this.extKey = extKey;
    }

    public String getExtUrl() {
        return extUrl;
    }

    public void setExtUrl(String extUrl) {
        this.extUrl = extUrl;
    }

    public String getExtType() {
        return extType;
    }

    public void setExtType(String extType) {
        this.extType = extType;
    }

    public String getExtLevel() {
        return extLevel;
    }

    public void setExtLevel(String extLevel) {
        this.extLevel = extLevel;
    }

    public String getExtIcon() {
        return extIcon;
    }

    public void setExtIcon(String extIcon) {
        this.extIcon = extIcon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<ZTree> getChildren() {
        return children;
    }

    public void setChildren(List<ZTree> children) {
        this.children = children;
    }
}
