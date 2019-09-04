package com.jeebase.wechat.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.security.dto.CreateUser;
import com.jeebase.system.security.service.IUserService;
import com.jeebase.wechat.member.dto.CreateWechatMember;
import com.jeebase.wechat.member.entity.WechatMember;
import com.jeebase.wechat.member.mapper.WechatMemberMapper;
import com.jeebase.wechat.member.service.IWechatMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.wechat.register.dto.CreateNormalUser;
import com.jeebase.wechat.wechat.entity.Wechat;
import com.jeebase.wechat.wechat.service.IWechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 微信注册会员表 服务实现类
 * </p>
 *
 * @author jeebase
 * @since 2019-03-08
 */
@Service
public class WechatMemberServiceImpl extends ServiceImpl<WechatMemberMapper, WechatMember> implements IWechatMemberService {

    @Autowired
    WechatMemberMapper wechatMemberMapper;

    @Autowired
    IWechatService wechatService;

    @Autowired
    IUserService userService;

    @Override
    public boolean saveNormalUser(CreateWechatMember createWechatMember){
        CreateUser createUser = new CreateUser();
        WechatMember wechatMember = new WechatMember();
        Integer userId = null;
        boolean exist = false;
        BeanCopier.create(CreateUser.class, WechatMember.class, false).copy(createUser,
                wechatMember, null);

        String openId = createWechatMember.getWechatPlatformOpenId();
        //如果微信openid不为空
        if (!StringUtils.isEmpty(openId))
        {
            QueryWrapper<WechatMember> wrapper = new QueryWrapper<>();
            wrapper.eq("wechat_platform_open_id",openId);
            WechatMember user = wechatMemberMapper.selectOne(wrapper);
            if (null != user)
            {
                exist = true;
                createUser.setUserNickName(user.getNickname());
                createUser.setHeadImgUrl(user.getAvatarUrl());
                userId = user.getId();
                if (!StringUtils.isEmpty(user.getUserId()))
                {
                    throw new BusinessException("该微信已绑定用户，请先在会员中心退出");
                }
            }
            else
            {
                QueryWrapper<Wechat> wechatWrapper = new QueryWrapper<>();
                wechatWrapper.eq("openid",openId);
                Wechat wechat = wechatService.getOne(wechatWrapper);
                if (null != wechat)
                {
                    exist = false;
                    createUser.setUserNickName(wechat.getNickname());
                    createUser.setHeadImgUrl(wechat.getHeadimgurl());
                    wechatMember.setWechatPlatformOpenId(openId);
                    wechatMember.setNickname(wechat.getNickname());
                    wechatMember.setAvatarUrl(wechat.getHeadimgurl());
                }
            }

        }

        createUser.setUserAccount(createWechatMember.getMobile());
        createUser.setUserMobile(createWechatMember.getMobile());
        createUser.setUserPassword(createWechatMember.getUserPassword());
        createUser.setOrganizationId(createWechatMember.getOrganizationId());
        createUser.setRoleId(createWechatMember.getRoleId());
        boolean result = userService.createUser(createUser);

        //如果是微信注册的，则有openId，直接更新，如果是后台创建的，那么没有openId，则新建用户
        if (StringUtils.isEmpty(openId) || !exist)
        {
            wechatMember.setUserId(createUser.getId());
            result = this.save(wechatMember);
        }
        else
        {
            wechatMember.setId(userId);
            wechatMember.setUserId(createUser.getId());
            result = this.updateById(wechatMember);
        }
        return result;
    }

    @Override
    public WechatMember getSoftDeleteWechatMember(WechatMember wechatMember) {
        return wechatMemberMapper.getSoftDeleteWechatMember(wechatMember);
    }

    @Override
    public void recoverSoftDeleteWechatMember(WechatMember wechatMember) {
        wechatMemberMapper.recoverSoftDeleteWechatMember(wechatMember);
    }
}
