package com.jeebase.wechat.member.service;

import com.jeebase.wechat.member.dto.CreateWechatMember;
import com.jeebase.wechat.member.entity.WechatMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.wechat.register.dto.CreateNormalUser;

/**
 * <p>
 * 微信注册会员表 服务类
 * </p>
 *
 * @author jeebase
 * @since 2019-03-08
 */
public interface IWechatMemberService extends IService<WechatMember> {

    boolean saveNormalUser(CreateWechatMember createWechatMember);


    WechatMember getSoftDeleteWechatMember(WechatMember wechatMember);

    void recoverSoftDeleteWechatMember(WechatMember wechatMember);
}
