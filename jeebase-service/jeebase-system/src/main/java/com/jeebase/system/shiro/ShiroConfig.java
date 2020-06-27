package com.jeebase.system.shiro;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeebase.common.base.Constant;
import com.jeebase.system.security.entity.Resource;
import com.jeebase.system.security.service.IResourceService;

/**
 * 
 * @ClassName: ShiroConfig
 * @Description:
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午4:01:20
 *
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private IResourceService resourceService;

    /**
     * 默认premission字符串
     */
    public static final String PERMISSION_STRING = "perms[\"{0}\"]";

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager getManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的realm
        manager.setRealm(userRealm());
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-
         * StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        return manager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new LinkedHashMap<>(Constant.Number.ONE);
        filterMap.put("jwt", jwtFilter());
        filterMap.put("perms", jwtFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setUnauthorizedUrl("/401");
        /*
         * 自定义url规则 http://shiro.apache.org/web.html#urls-
         */
        Map<String, String> filterRuleMap = new LinkedHashMap<>(Constant.Number.TWO);
        // 访问401和404页面不通过我们的Filter
        filterRuleMap.put("/401", "anon");
        filterRuleMap.put("/404", "anon");
        filterRuleMap.put("/500", "anon");
        filterRuleMap.put("/swagger-ui.html", "anon");
        filterRuleMap.put("/swagger-resources/configuration/ui", "anon");
        filterRuleMap.put("/swagger-resources", "anon");
        filterRuleMap.put("/swagger-resources/configuration/security", "anon");
        filterRuleMap.put("/v2/api-docs", "anon");
        filterRuleMap.put("/error", "anon");
        filterRuleMap.put("/webjars/springfox-swagger-ui/**", "anon");
        QueryWrapper<Resource> wrapper = new QueryWrapper<Resource>();
        // 获取所有Permission
        List<Resource> list = resourceService.selectResourceList(wrapper);
        // 循环Permission的url,逐个添加到filterChainDefinitionMap中。
        // 里面的键就是链接URL,值就是存在什么条件才能访问该链接
        for (Iterator<Resource> it = list.iterator(); it.hasNext();) {
            Resource resource = it.next();
            String pUrl = resource.getResourceUrl();
            String pKey = resource.getResourceKey();
            // 如果不为空值添加到section中
            if (!StringUtils.isEmpty(pUrl) && !StringUtils.isEmpty(pKey)) {
                filterRuleMap.put(pUrl, MessageFormat.format(PERMISSION_STRING, pKey));
            }
        }
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/**", "jwt");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
    
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
    
    @Bean
    public JWTFilter jwtFilter(){
        return new JWTFilter();
    }
}
