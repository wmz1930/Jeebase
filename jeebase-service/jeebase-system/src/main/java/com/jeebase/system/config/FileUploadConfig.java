package com.jeebase.system.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: FileUploadConfig
 * @Description: 文件上传配置
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午5:15:19
 */
@Configuration
public class FileUploadConfig {

    /**
     * 文件上传配置
     *
     * @return MultipartConfigElement
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大
        factory.setMaxFileSize("50Mb");
        // 设置总上传数据总大小
        factory.setMaxRequestSize("100MB");
        return factory.createMultipartConfig();
    }
}
