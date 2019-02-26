
package com.jeebase.system.security.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author jeebase
 * @since 2018-05-26
 */
public class QueryUser implements Serializable
{

    /**
      * @Fields serialVersionUID : 
      */
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键
     */
    private Integer id;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 昵称
     */
    private String userNickName;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 1 : 男，0 : 女
     */
    private String userSex;
    /**
     * 邮箱
     */
    private String userEmail;
    /**
     * 电话
     */
    private String userMobile;

    /**
     * '0'禁用，'1' 启用, '2' 密码过期或初次未修改
     */
    private String userStatus;

    /**
     * 
     */
    private String headImgUrl;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
    
    /**
     * vue级联选择
     */
    private List<String> areas;
    
    private Integer roleId;

    private String startDate;

    private String endDate;

    @ApiModelProperty(value = "组织机构id")
    private Integer organizationId;

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getUserAccount()
    {
        return userAccount;
    }

    public void setUserAccount( String userAccount )
    {
        this.userAccount = userAccount;
    }

    public String getUserNickName()
    {
        return userNickName;
    }

    public void setUserNickName( String userNickName )
    {
        this.userNickName = userNickName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public String getUserSex()
    {
        return userSex;
    }

    public void setUserSex( String userSex )
    {
        this.userSex = userSex;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail( String userEmail )
    {
        this.userEmail = userEmail;
    }

    public String getUserMobile()
    {
        return userMobile;
    }

    public void setUserMobile( String userMobile )
    {
        this.userMobile = userMobile;
    }

    public String getUserStatus()
    {
        return userStatus;
    }

    public void setUserStatus( String userStatus )
    {
        this.userStatus = userStatus;
    }

    public String getHeadImgUrl()
    {
        return headImgUrl;
    }

    public void setHeadImgUrl( String headImgUrl )
    {
        this.headImgUrl = headImgUrl;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince( String province )
    {
        this.province = province;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity( String city )
    {
        this.city = city;
    }

    public String getArea()
    {
        return area;
    }

    public void setArea( String area )
    {
        this.area = area;
    }

    public Integer getRoleId()
    {
        return roleId;
    }

    public void setRoleId( Integer roleId )
    {
        this.roleId = roleId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public List<String> getAreas() {
		return areas;
	}

	public void setAreas(List<String> areas) {
		this.areas = areas;
	}


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

	@Override
    public String toString()
    {
        return "User{" + "id=" + id + ", userAccount=" + userAccount + ", userNickName=" + userNickName + ", userName="
                + userName + ", userSex=" + userSex + ", userEmail=" + userEmail + ", userMobile=" + userMobile
                + ", userStatus=" + userStatus + ", headImgUrl=" + headImgUrl
                + ", province=" + province + ", city=" + city + ", area=" + area + "}";
    }
}
