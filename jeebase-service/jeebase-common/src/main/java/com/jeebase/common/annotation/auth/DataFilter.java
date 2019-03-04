package com.jeebase.common.annotation.auth;

import java.lang.annotation.*;

/**
 * 数据过滤
 *
 * @author WANGLEI
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFilter {

    /**
     * 数据权限中，需要过滤的表字段organizationId的别名
     */
    String orgIdAlias() default  "";

    /**
     * 数据权限中，需要过滤的表字段userId的别名
     */
    String userIdAlias() default  "";

    /**
     * 数据权限中，没有本部门数据权限时，是否可以查询本人数据
     */
    boolean ownQuery() default false;
}
