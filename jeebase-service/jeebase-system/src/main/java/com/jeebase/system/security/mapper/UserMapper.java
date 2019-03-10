package com.jeebase.system.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.system.security.dto.QueryUser;
import com.jeebase.system.security.dto.UserInfo;
import com.jeebase.system.security.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author jeebase
 * @since 2018-05-19
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户列表
     * @param page
     * @param user
     * @return
     */
    Page<UserInfo> selectUserList( Page<UserInfo> page, @Param("user") QueryUser user);
}
