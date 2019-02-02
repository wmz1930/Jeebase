package com.jeebase.common.base;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: Constant
 * @Description: 常量类
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午11:49:21
 */
public class Constant {

    /**
     * 忽略鉴权的方法列表
     */
    public static Set<String> METHOD_URL_SET = new HashSet<>();

    /**
     * 根节点id
     */
    public static final String PARENT_ID = "0";

    /**
     * 启用
     */
    public static final int ENABLE = 1;

    /**
     * 禁用
     */
    public static final int DISABLE = 0;

    /**
     * 未知
     */
    public static final String UNKNOWN = "unknown";

    /**
     * 本机IP
     */
    public static final String LOCALHOST_IP = "127.0.0.1";

    /**
     * 逗号分隔符
     */
    public static final String DELIMITER_COMMA = ",";

    /**
     * 微信openid
     */
    public static final String WEI_XIN_OPENID = "openid";

    /**
     * 数组取值
     */
    public class Number {

        public static final int ZERO = 0;

        public static final int ONE = 1;

        public static final int TWO = 2;

        public static final int THREE = 3;

        public static final int FOUR = 4;

        public static final int FIVE = 5;

        public static final int FIFTEEN = 15;
    }

    /**
     * 地址
     */
    public class Address {

        public static final int PROVINCE = 0;

        public static final int CITY = 1;

        public static final int AREA = 2;

    }

    /**
     * 超时时间类型
     */
    public class ExpTimeType {

        public static final String WEB = "web";

        public static final String APP = "app";

    }
}
