package com.jeebase.common.annotation.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: Pass
 * @Description: 在Controller方法上加入该注解不会验证身份
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午11:52:07
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoAuthentication {
}
