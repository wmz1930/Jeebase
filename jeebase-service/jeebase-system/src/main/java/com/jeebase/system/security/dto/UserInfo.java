
package com.jeebase.system.security.dto;

import com.jeebase.system.security.entity.Resource;

import java.util.Date;
import java.util.List;

/**
 * @author jeebase
 */
public class UserInfo
{
    private Integer id;
    private String token;
    private String userAccount;
    private String userNickName;
    private String userName;
    private String userEmail;
    private String userMobile;
    private String userSex;
    private String userStatus;
    private Long time;
    private Integer roleId;
    private Integer organizationId;
    private String organizationName;
    private String roleIds;
    private String roleKey;
    private String roleName;
    private String dataPermission;
    private List<String> roles;
    private List<Resource> resources;
    private List<String> stringResources;
    private String headImgUrl;
    private String province;
    private String provinceName;
    private String city;
    private String cityName;
    private String area;
    private String street;
    private String description;
    private Date createTime;
    
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken( String token )
    {
        this.token = token;
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

    public String getUserSex()
    {
        return userSex;
    }

    public void setUserSex( String userSex )
    {
        this.userSex = userSex;
    }

    public Long getTime()
    {
        return time;
    }

    public void setTime( Long time )
    {
        this.time = time;
    }

    public Integer getRoleId()
    {
        return roleId;
    }

    public void setRoleId( Integer roleId )
    {
        this.roleId = roleId;
    }


    public String getDataPermission() {
        return dataPermission;
    }

    public void setDataPermission(String dataPermission) {
        this.dataPermission = dataPermission;
    }


    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }


    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }



    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }


    public String getRoleKey()
    {
        return roleKey;
    }

    public void setRoleKey( String roleKey )
    {
        this.roleKey = roleKey;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName( String roleName )
    {
        this.roleName = roleName;
    }

    public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
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


    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getUserStatus()
    {
        return userStatus;
    }

    public void setUserStatus( String userStatus )
    {
        this.userStatus = userStatus;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime( Date createTime )
    {
        this.createTime = createTime;
    }

    public String getProvinceName()
    {
        return provinceName;
    }

    public void setProvinceName( String provinceName )
    {
        this.provinceName = provinceName;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName( String cityName )
    {
        this.cityName = cityName;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


    public List<String> getStringResources() {
        return stringResources;
    }

    public void setStringResources(List<String> stringResources) {
        this.stringResources = stringResources;
    }
}
