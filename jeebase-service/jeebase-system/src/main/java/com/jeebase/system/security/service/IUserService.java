package com.jeebase.system.security.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.security.dto.CreateUser;
import com.jeebase.system.security.dto.QueryUser;
import com.jeebase.system.security.dto.UpdateUser;
import com.jeebase.system.security.dto.UserInfo;
import com.jeebase.system.security.entity.User;

import java.util.List;

/**
 * @ClassName: IUserService
 * @Description: 用户相关操接口
 * @author jeebase
 * @date 2018年5月18日 下午3:19:33
 */
public interface IUserService extends IService<User> {

    /**
     * 创建用户
     * 
     * @param user
     * @param
     * @return
     */
    boolean createUser(CreateUser user);

    /**
     * 更新用户
     * 
     * @param user
     * @param
     * @return
     */
    boolean updateUser(UpdateUser user);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    boolean deleteUser(Integer userId);

    /**
     * 批量删除用户
     * @param userIds
     * @return
     */
    boolean batchDeleteUser(List<Integer> userIds);

    /**
     * 根据用户名查询用户
     * 
     * @param userAccount 用户账号
     * @return 用户
     */
    User queryUserByAccount(String userAccount);

    /**
     * 分页查询用户
     * @param page
     * @param user
     * @return
     */
    Page<UserInfo> selectUserList(Page<UserInfo> page, QueryUser user);
}
