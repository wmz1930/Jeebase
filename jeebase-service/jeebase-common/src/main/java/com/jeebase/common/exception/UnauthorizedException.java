package com.jeebase.common.exception;

/**
 * @ClassName: UnauthorizedException
 * @Description: 身份认证异常
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午11:50:14
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = -2804627713269695216L;

    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}
