package com.jeebase.system.shiro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeebase.common.base.component.JwtComponent;
import com.jeebase.common.exception.UnauthorizedException;
import com.jeebase.system.config.SpringContextBean;
import com.jeebase.system.security.entity.Resource;
import com.jeebase.system.security.entity.Role;
import com.jeebase.system.security.entity.User;
import com.jeebase.system.security.service.IRoleResourceService;
import com.jeebase.system.security.service.IUserRoleService;
import com.jeebase.system.security.service.IUserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: MyRealm
 * @Description: TODO
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午4:00:59
 */
public class UserRealm extends AuthorizingRealm {
    
    @Autowired
    private JwtComponent jwtComponent;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleResourceService roleResourceService;

    @Override
    public boolean supports(AuthenticationToken token) {
        /**
         * 表示此Realm只支持JWTToken类型
         */
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        JSONObject userObj = JSON.parseObject(principals.toString());
        Integer userId = userObj.getInteger("id");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 查询角色
        ArrayList<String> roles = new ArrayList<>();
        // 查询权限
        ArrayList<String> permissions = new ArrayList<>();
        // 查询用户权限
        List<Role> roleList = userRoleService.queryRolesByUserId(userId);
        // 查询用户所在组织权限(暂不启用)
        // List<Role> orgRoleList =
        // organizationRoleService.queryRolesByOrganizationId( );
        for (Role role : roleList) {
            if (!StringUtils.isEmpty(role.getRoleKey())) {
                if (!StringUtils.isEmpty(role.getRoleKey().replace(" ", ""))) {
                    roles.add(role.getRoleKey());
                }
            }
            List<Resource> resourceList = roleResourceService.queryResourceByRoleId(role.getId());
            if(!CollectionUtils.isEmpty(resourceList))
            {
                for (Resource res : resourceList) {
                    if (!StringUtils.isEmpty(res.getResourceKey())) {
                        if (!StringUtils.isEmpty(res.getResourceKey().replace(" ", ""))) {
                            permissions.add(res.getResourceKey());
                        }
                    }
                }
            }
        }
        Set<String> rolesSet = new HashSet<>(roles);
        simpleAuthorizationInfo.addRoles(rolesSet);
        Set<String> permission = new HashSet<>(permissions);
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws UnauthorizedException {
        if (null == userService) {
            this.userService = SpringContextBean.getBean(IUserService.class);
        }
        String token = (String) auth.getCredentials();
        // 解密token获得username，用于和数据库进行对比
        String userAccount = jwtComponent.getUserAccount(token);
        if (userAccount == null) {
            throw new UnauthorizedException("token invalid");
        }
        User userBean = userService.queryUserByAccount(userAccount);
        if (userBean == null) {
            throw new UnauthorizedException("User didn't existed!");
        }
        if (!jwtComponent.verify(token, userAccount, userBean.getUserPassword())) {
            throw new UnauthorizedException("Username or password error");
        }
        String userString = JSONObject.toJSONString(userBean);
        return new SimpleAuthenticationInfo(userString, token, this.getName());
    }
}
