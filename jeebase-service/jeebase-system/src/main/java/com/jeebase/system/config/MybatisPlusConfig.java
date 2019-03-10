package com.jeebase.system.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.jeebase.common.base.component.DataPermissionInterceptor;
import com.jeebase.common.base.component.DataPermissionPaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName: MybatisPlusConfig
 * @Description: mybatis-plus配置
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午5:16:34
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.jeebase.*.*.mapper.*")
public class MybatisPlusConfig {

    /**
     * 数据权限插件
     */
    @Bean
    public DataPermissionInterceptor dataPermissionInterceptor() {
        DataPermissionInterceptor dataPermission = new DataPermissionInterceptor();
        dataPermission.setDialectType("mysql");
        return dataPermission;
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public DataPermissionPaginationInterceptor paginationInterceptor() {
        DataPermissionPaginationInterceptor page = new DataPermissionPaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }
    
    /**
     * 逻辑删除插件
     * @return
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }
}
