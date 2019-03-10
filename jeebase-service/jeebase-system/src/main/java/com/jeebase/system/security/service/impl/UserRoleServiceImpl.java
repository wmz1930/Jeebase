package com.jeebase.system.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.security.entity.Role;
import com.jeebase.system.security.entity.UserRole;
import com.jeebase.system.security.mapper.UserRoleMapper;
import com.jeebase.system.security.service.IRoleService;
import com.jeebase.system.security.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: RoleServiceImpl
 * @Description: 角色相关操作接口实现类
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午3:22:21
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Autowired
    private IRoleService roleService;

    @Override
    public UserRole selectByUserId(Integer userId) {
        QueryWrapper<UserRole> ew = new QueryWrapper<>();
        ew.eq("user_id", userId);
        return this.getOne(ew);
    }

    @Override
    @Cacheable(value = "roles", key = "'user_id_'.concat(#userId)")
    public List<Role> queryRolesByUserId(Integer userId) {
        QueryWrapper<UserRole> ew = new QueryWrapper<>();
        ew.eq("user_id", userId);
        List<UserRole> userRoleList = this.list(ew);
        if (!CollectionUtils.isEmpty(userRoleList)) {
            List<Integer> roleIds = new ArrayList<Integer>();
            for (UserRole userRole : userRoleList) {
                roleIds.add(userRole.getRoleId());
            }
            QueryWrapper<Role> ewRole = new QueryWrapper<>();
            ewRole.in("id", roleIds);
            List<Role> roleList = roleService.list(ewRole);
            return roleList;
        } else {
            return null;
        }
    }
}
