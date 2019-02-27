package com.jeebase.system.security.dto;

import java.util.List;

/**
 * @author WANGLEI
 */
public class UpdateDataPermission {

    private Integer userId;

    private List<Integer> addDataPermissions;

    private List<Integer> removeDataPermissions;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getAddDataPermissions() {
        return addDataPermissions;
    }

    public void setAddDataPermissions(List<Integer> addDataPermissions) {
        this.addDataPermissions = addDataPermissions;
    }

    public List<Integer> getRemoveDataPermissions() {
        return removeDataPermissions;
    }

    public void setRemoveDataPermissions(List<Integer> removeDataPermissions) {
        this.removeDataPermissions = removeDataPermissions;
    }
}
