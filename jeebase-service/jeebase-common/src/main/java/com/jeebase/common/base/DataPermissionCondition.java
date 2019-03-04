package com.jeebase.common.base;

import java.util.List;

/**
 * @author WANGLEI
 */
public class DataPermissionCondition {

    /**
     * 数据权限中，需要过滤的表字段organizationId的别名
     */
    private String orgIdAlias;

    /**
     * 数据权限中，拥有该权限organizationId的列表
     */
    private List<String> orgIdList;

    /**
     * 数据权限中，需要过滤的表字段userId的别名
     */
    private String userIdAlias;

    /**
     * 数据权限中，拥有该权限userId的值
     */
    private String userId;

    /**
     * 数据权限中，没有本部门数据权限时，是否可以查询本人数据
     */
    private boolean ownQuery;


    public String getOrgIdAlias() {
        return orgIdAlias;
    }

    public void setOrgIdAlias(String orgIdAlias) {
        this.orgIdAlias = orgIdAlias;
    }

    public List<String> getOrgIdList() {
        return orgIdList;
    }

    public void setOrgIdList(List<String> orgIdList) {
        this.orgIdList = orgIdList;
    }

    public String getUserIdAlias() {
        return userIdAlias;
    }

    public void setUserIdAlias(String userIdAlias) {
        this.userIdAlias = userIdAlias;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isOwnQuery() {
        return ownQuery;
    }

    public void setOwnQuery(boolean ownQuery) {
        this.ownQuery = ownQuery;
    }
}
