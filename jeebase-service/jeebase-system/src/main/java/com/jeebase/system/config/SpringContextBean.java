package com.jeebase.system.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SpringContextBean
 * @Description: 获取spring上下文
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午5:19:02
 */
@Component
public class SpringContextBean implements ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        T bean = (T) context.getBean(name);
        return bean;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
