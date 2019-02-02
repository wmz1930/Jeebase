package com.jeebase.common.base;

import java.util.List;

/**
 * @ClassName: PageResult
 * @Description: TODO
 * @author jeebase
 * @date 2018年5月25日 下午9:24:50
 * @param <T>
 */
public class PageResult<T> {

    /**
     * 状态码, 200表示成功
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 总数量
     */
    private long count;

    /**
     * 当前数据
     */
    private List<T> data;

    /**
     * 默认构造方法
     */
    public PageResult() {
    }

    public PageResult(long total, List<T> rows) {
        this.count = total;
        this.data = rows;
        this.code = 200;
        this.msg = "";
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
