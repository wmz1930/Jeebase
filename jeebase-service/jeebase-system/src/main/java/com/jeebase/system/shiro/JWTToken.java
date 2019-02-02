package com.jeebase.system.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName: JWTToken
 * @Description:
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午4:00:43
 */
public class JWTToken implements AuthenticationToken {

    private static final long serialVersionUID = -2819620437297382200L;

    /**
     * 密钥
     */
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
