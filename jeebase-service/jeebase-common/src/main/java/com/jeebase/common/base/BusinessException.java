package com.jeebase.common.base;

/**
 * @ClassName: BusinessException
 * @Description: TODO
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午11:49:12
 */
public class BusinessException extends RuntimeException {

    /**
     * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
     */
    private static final long serialVersionUID = 3455708526465670030L;

    private int code;

    private String msg;

    public BusinessException() {
        this.code = 500;
        this.msg = "未知错误";
    }

    public BusinessException(String message) {
        this.code = 500;
        this.msg = message;
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getExceptionCode() {
        return code;
    }

    public void setExceptionCode(int code) {
        this.code = code;
    }

    public String getExceptionMsg() {
        return msg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.msg = exceptionMsg;
    }
}