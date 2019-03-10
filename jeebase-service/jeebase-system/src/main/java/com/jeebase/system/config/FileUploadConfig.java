package com.jeebase.system.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

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
        factory.setMaxFileSize("102400KB");
        // 设置总上传数据总大小
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }
}
