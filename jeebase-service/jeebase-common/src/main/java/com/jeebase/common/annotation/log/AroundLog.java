package com.jeebase.common.annotation.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: AroundLog
 * @Description:记录around日志
 * @author jeebase
 * @date 2016年4月27日 下午3:36:29
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface AroundLog {

    String name() default "";
}
