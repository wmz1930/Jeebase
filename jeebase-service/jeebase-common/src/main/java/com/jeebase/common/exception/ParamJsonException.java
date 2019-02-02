package com.jeebase.common.exception;

/**
 * @ClassName: ParamJsonException
 * @Description: 参数异常
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午11:49:58
 */
public class ParamJsonException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public ParamJsonException() {
    }

    public ParamJsonException(String message) {
        super(message);
        this.message = message;
    }
}
