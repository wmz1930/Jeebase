package com.jeebase.wechat.login.dto;


public class TokenUser {
    
    private boolean isReg;

    private boolean remember;
    
    private String openId;
    
    private String token;

    private Integer userType;

    
    public boolean isReg() {
        return isReg;
    }

    
    public void setReg(boolean isReg) {
        this.isReg = isReg;
    }



    public String getOpenId() {
        return openId;
    }

    
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    
    public String getToken() {
        return token;
    }

    
    public void setToken(String token) {
        this.token = token;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
