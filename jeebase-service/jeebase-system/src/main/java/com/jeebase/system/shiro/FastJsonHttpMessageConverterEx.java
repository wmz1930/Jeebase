package com.jeebase.system.shiro;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * @ClassName: FastJsonHttpMessageConverterEx
 * @Description: TODO
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午3:59:54
 */
public class FastJsonHttpMessageConverterEx extends FastJsonHttpMessageConverter {

    public FastJsonHttpMessageConverterEx() {
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return super.supports(clazz);
    }
}
