package com.jeebase.system.common.domain;

import java.util.List;

/**
 * @author jeebase
 */
public class UserListInfoRequest {

    private List<UserInfoRequest> user_list;

    public List<UserInfoRequest> getUser_list() {
        return user_list;
    }

    public void setUser_list(List<UserInfoRequest> user_list) {
        this.user_list = user_list;
    }
}
