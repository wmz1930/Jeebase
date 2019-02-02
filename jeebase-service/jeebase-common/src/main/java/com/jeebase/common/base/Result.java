package com.jeebase.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author jeebase
 */
@ApiModel("响应类")
public class Result<T> {

    @ApiModelProperty(value ="响应代码 200:success;<br>500:系统错误;<br>90000000:操作失败;<br>90000002:未登录/token过期;<br>90000003:参数错误;<br>10000011:验证码错误;<br>10000007:当前用户无该接口权限")
    private  int code;

    @ApiModelProperty(value ="提示信息")
    private  String msg;

    @ApiModelProperty(value ="数据")
    private T data;


    /**
     * 返回成功
     */
    public Result<T> success() {
        return success("操作成功！");
    }

    /**
     * 返回成功
     */
    public Result<T> success(String message) {
        return success(200, message);
    }

    /**
     * 返回成功
     */
    public Result<T> success(ResponseConstant constant) {
        return success(constant.getResult(), constant.getMsg());
    }

    /**
     * 返回成功
     */
    public Result<T> success(int code, String message) {
        this.setCode(code);
        this.setMsg(message);
        return this;
    }

    /**
     * 返回失败
     */
    public Result<T> error() {
        return error("操作失败！");
    }

    /**
     * 返回失败
     */
    public Result<T> error(String messag) {
        return error(500, messag);
    }

    /**
     * 返回失败
     */
    public  Result<T> error(int code, String message) {
        return success(code, message);
    }

    /**
     * 返回信息
     */
    public  Result<T> error(ResponseConstant constant) {
        return success(constant.getResult(), constant.getMsg());
    }
    /**
     * 放入object
     */
    public Result<T> put(T object) {
        this.setData(object);
        return this;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
