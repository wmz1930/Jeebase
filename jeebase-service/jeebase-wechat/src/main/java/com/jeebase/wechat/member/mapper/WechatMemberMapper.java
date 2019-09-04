package com.jeebase.wechat.member.mapper;

import com.jeebase.wechat.member.entity.WechatMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 微信注册会员表 Mapper 接口
 * </p>
 *
 * @author jeebase
 * @since 2019-03-08
 */
public interface WechatMemberMapper extends BaseMapper<WechatMember> {

    WechatMember getSoftDeleteWechatMember(@Param("requiring") WechatMember wechatMember);

    void recoverSoftDeleteWechatMember(@Param("requiring") WechatMember wechatMember);
}
