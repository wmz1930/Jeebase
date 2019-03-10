package com.jeebase.system.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.security.entity.Role;
import com.jeebase.system.security.entity.UserRole;

import java.util.List;

/**
 * @ClassName: IUserRoleService
 * @Description: 用户角色相关操接口
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午3:19:33
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 根据用户ID查询人员角色
     * 
     * @param userId
     *            用户ID
     * @return 结果
     */
    UserRole selectByUserId(Integer userId);

    /**
     * queryRolesByUserId
     * 
     * @Title: queryRolesByUserId
     * @Description: 查询用户的角色
     * @param userId
     * @return List<Role>
     */
    List<Role> queryRolesByUserId(Integer userId);
}
