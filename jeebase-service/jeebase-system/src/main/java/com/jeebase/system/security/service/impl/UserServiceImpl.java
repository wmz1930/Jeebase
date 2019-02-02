package com.jeebase.system.security.service.impl;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.common.base.Constant;
import com.jeebase.system.security.dto.CreateUser;
import com.jeebase.system.security.dto.QueryUser;
import com.jeebase.system.security.dto.UpdateUser;
import com.jeebase.system.security.dto.UserInfo;
import com.jeebase.system.security.entity.User;
import com.jeebase.system.security.entity.UserRole;
import com.jeebase.system.security.mapper.UserMapper;
import com.jeebase.system.security.service.IUserRoleService;
import com.jeebase.system.security.service.IUserService;

/**
 * @ClassName: UserServiceImpl
 * @Description: 用户相关操作接口实现类
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午3:20:30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private IUserRoleService userRoleService;

    @Value("${system.defaultPwd}")
    private String defaultPwd;

    @Value("${system.defaultRoleId}")
    private Integer defaultRoleId;

    @Override
    public Page<UserInfo> selectUserList(Page<UserInfo> page, QueryUser user) {
        Page<UserInfo> pageUserInfo = userMapper.selectUserList(page, user);
        return pageUserInfo;
    }

    @Override
    public boolean createUser(CreateUser user) {
        QueryWrapper<User> ew = new QueryWrapper<>();
        ew.and(e -> e.eq("user_account", user.getUserAccount()).or().eq("user_account", user.getUserNickName()).or()
                .eq("user_account", user.getUserEmail()).or().eq("user_account", user.getUserMobile()).or()
                .eq("user_nick_name", user.getUserAccount()).or().eq("user_nick_name", user.getUserNickName()).or()
                .eq("user_nick_name", user.getUserEmail()).or().eq("user_nick_name", user.getUserMobile()).or()
                .eq("user_email", user.getUserAccount()).or().eq("user_email", user.getUserNickName()).or()
                .eq("user_email", user.getUserEmail()).or().eq("user_email", user.getUserMobile()).or()
                .eq("user_email", user.getUserAccount()).or().eq("user_mobile", user.getUserNickName()).or()
                .eq("user_mobile", user.getUserEmail()).or().eq("user_mobile", user.getUserMobile()));
        List<User> userList = this.list(ew);
        if (!CollectionUtils.isEmpty(userList)) {
            throw new BusinessException("账号已经存在");
        }
        Integer roleId = user.getRoleId();
        if (null == roleId) {
            // 默认值，改成配置
            roleId = defaultRoleId;
        }
        List<String> areas = user.getAreas();
        if (!CollectionUtils.isEmpty(areas)) {
            user.setProvince(areas.get(Constant.Address.PROVINCE));
            user.setCity(areas.get(Constant.Address.CITY));
            user.setArea(areas.get(Constant.Address.AREA));
        }
        User userEntity = new User();
        BeanCopier.create(CreateUser.class, User.class, false).copy(user, userEntity, null);
        String pwd = userEntity.getUserPassword();
        if (StringUtils.isEmpty(pwd)) {
            // 默认密码，配置文件配置
            pwd = defaultPwd;
            // 初次登录需要修改密码
            // userEntity.setUserStatus( "2" );
        }
        String cryptPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
        userEntity.setUserPassword(cryptPwd);
        boolean result = this.save(userEntity);
        if (result) {
            user.setId(userEntity.getId());
            user.setUserPassword(cryptPwd);
            UserRole userRole = new UserRole();
            userRole.setUserId(userEntity.getId());
            userRole.setRoleId(roleId);
            result = userRoleService.save(userRole);
        }
        return result;
    }

    @Override
    @CacheEvict(value = "users", key = "'id_'.concat(#user.id)")
    public boolean updateUser(UpdateUser user) {
        QueryWrapper<User> ew = new QueryWrapper<>();
        ew.ne("id", user.getId())
                .and(e -> e.eq("user_account", user.getUserAccount()).or().eq("user_account", user.getUserNickName())
                        .or().eq("user_account", user.getUserEmail()).or().eq("user_account", user.getUserMobile()).or()
                        .eq("user_nick_name", user.getUserAccount()).or().eq("user_nick_name", user.getUserNickName())
                        .or().eq("user_nick_name", user.getUserEmail()).or().eq("user_nick_name", user.getUserMobile())
                        .or().eq("user_email", user.getUserAccount()).or().eq("user_email", user.getUserNickName()).or()
                        .eq("user_email", user.getUserEmail()).or().eq("user_email", user.getUserMobile()).or()
                        .eq("user_email", user.getUserAccount()).or().eq("user_mobile", user.getUserNickName()).or()
                        .eq("user_mobile", user.getUserEmail()).or().eq("user_mobile", user.getUserMobile()));
        List<User> userList = this.list(ew);
        if (!CollectionUtils.isEmpty(userList)) {
            throw new BusinessException("账号已经存在");
        }
        List<String> areas = user.getAreas();
        if (!CollectionUtils.isEmpty(areas)) {
            user.setProvince(areas.get(Constant.Address.PROVINCE));
            user.setCity(areas.get(Constant.Address.CITY));
            user.setArea(areas.get(Constant.Address.AREA));
        }
        User userEntity = new User();
        BeanCopier.create(UpdateUser.class, User.class, false).copy(user, userEntity, null);
        String pwd = userEntity.getUserPassword();
        if (!StringUtils.isEmpty(pwd)) {
            String cryptPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
            userEntity.setUserPassword(cryptPwd);
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", userEntity.getId());
        boolean result = this.update(userEntity, wrapper);
        if (result && null != user.getRoleId()) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userEntity.getId());
            userRole.setRoleId(user.getRoleId());
            QueryWrapper<UserRole> wp = new QueryWrapper<>();
            wp.eq("user_id", userEntity.getId()).eq("role_id", user.getRoleId());
            List<UserRole> urList = userRoleService.list(wp);
            if (CollectionUtils.isEmpty(urList)) {
                QueryWrapper<UserRole> wpd = new QueryWrapper<>();
                wpd.eq("user_id", userEntity.getId());
                userRoleService.remove(wpd);
                result = userRoleService.save(userRole);
            }
        }
        return result;
    }

    @Override
    @CacheEvict(value = "users", key = "'id_'.concat(#userId)")
    public boolean deleteUser(Integer userId) {
        boolean result = this.removeById(userId);
        if (result) {
            QueryWrapper<UserRole> wpd = new QueryWrapper<>();
            wpd.eq("user_id", userId);
            userRoleService.remove(wpd);
        }
        return result;
    }

    @Override
    @Cacheable(value = "users", key = "'account_'.concat(#userAccount)")
    public User queryUserByAccount(String userAccount) {
        QueryWrapper<User> ew = new QueryWrapper<>();
        ew.and(e -> e.eq("user_account", userAccount).or().eq("user_email", userAccount).or().eq("user_mobile",
                userAccount));
        return this.getOne(ew);
    }
}
