package com.jeebase.system.security.dto;

import com.jeebase.system.security.entity.RoleResource;

import java.util.List;

/**
 * @author jeebase
 */
public class UpdateRoleResource {

    /**
     * 需要操作的角色id
     */
    private Integer roleId;

    /**
     * 添加的资源列表
     */
    private List<RoleResource> addResources;

    /**
     * 删除的资源列表
     */
    private List<RoleResource> delResources;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<RoleResource> getAddResources() {
        return addResources;
    }

    public void setAddResources(List<RoleResource> addResources) {
        this.addResources = addResources;
    }

    public List<RoleResource> getDelResources() {
        return delResources;
    }

    public void setDelResources(List<RoleResource> delResources) {
        this.delResources = delResources;
    }
}
