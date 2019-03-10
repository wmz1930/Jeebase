package com.jeebase.system.config;

import com.jeebase.common.annotation.auth.NoAuthentication;
import com.jeebase.common.base.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: MyCommandLineRunner
 * @Description: 
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午5:18:42
 */
@Component
public class ScanNoAuthenticationMethod implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(ScanNoAuthenticationMethod.class);

    @Value("${controller.scanPackage}")
    private String scanPackage;

    @Override
    public void run(String... args) throws Exception {
        doScanner(scanPackage);
        Set<String> urlAndMethodSet = new HashSet<>();
        for (String aClassName : Constant.METHOD_URL_SET) {
            Class<?> clazz = Class.forName(aClassName);
            String baseUrl = "";
            String[] classUrl = {};
            if (!StringUtils.isEmpty(clazz.getAnnotation(RequestMapping.class))) {
                classUrl = clazz.getAnnotation(RequestMapping.class).value();
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(NoAuthentication.class)) {
                    String[] methodUrl = null;
                    StringBuilder sb = new StringBuilder();
                    if (!StringUtils.isEmpty(method.getAnnotation(PostMapping.class))) {
                        methodUrl = method.getAnnotation(PostMapping.class).value();
                        if (StringUtils.isEmpty(methodUrl)) {
                            methodUrl = method.getAnnotation(PostMapping.class).path();
                        }
                        baseUrl = getRequestUrl(classUrl, methodUrl, sb, "POST");
                    } else if (!StringUtils.isEmpty(method.getAnnotation(GetMapping.class))) {
                        methodUrl = method.getAnnotation(GetMapping.class).value();
                        if (StringUtils.isEmpty(methodUrl)) {
                            methodUrl = method.getAnnotation(GetMapping.class).path();
                        }
                        baseUrl = getRequestUrl(classUrl, methodUrl, sb, "GET");
                    } else if (!StringUtils.isEmpty(method.getAnnotation(DeleteMapping.class))) {
                        methodUrl = method.getAnnotation(DeleteMapping.class).value();
                        if (StringUtils.isEmpty(methodUrl)) {
                            methodUrl = method.getAnnotation(DeleteMapping.class).path();
                        }
                        baseUrl = getRequestUrl(classUrl, methodUrl, sb, "DELETE");
                    } else if (!StringUtils.isEmpty(method.getAnnotation(PutMapping.class))) {
                        methodUrl = method.getAnnotation(PutMapping.class).value();
                        if (StringUtils.isEmpty(methodUrl)) {
                            methodUrl = method.getAnnotation(PutMapping.class).path();
                        }
                        baseUrl = getRequestUrl(classUrl, methodUrl, sb, "PUT");
                    } else {
                        methodUrl = method.getAnnotation(RequestMapping.class).value();
                        baseUrl = getRequestUrl(classUrl, methodUrl, sb, RequestMapping.class.getSimpleName());
                    }
                    if (!StringUtils.isEmpty(baseUrl)) {
                        urlAndMethodSet.add(baseUrl);
                    }
                }
            }
        }
        Constant.METHOD_URL_SET = urlAndMethodSet;
        logger.info("@NoAuthentication:" + urlAndMethodSet);
    }

    private String getRequestUrl(String[] classUrl, String[] methodUrl, StringBuilder sb, String requestName) {
        if (!StringUtils.isEmpty(classUrl)) {
            for (String url : classUrl) {
                sb.append(url + "/");
            }
        }
        for (String url : methodUrl) {
            sb.append(url);
        }
        return sb.toString().replaceAll("/+", "/") + ":" + requestName;
    }

    private void doScanner(String packageName) throws IOException {
        ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
        Resource[] source = resourceLoader.getResources(packageName);
        for (int i = 0; i < source.length; i++) {
            Resource resource = source[i];
            String resourcePath = resource.getURI().toString();
            String soua = "";
            logger.info(resourcePath);
            if (resourcePath.startsWith("jar:")) {
                int firstIndex = resourcePath.indexOf("!");
                int secondIndex = resourcePath.indexOf("!", firstIndex + 1);
                String fpath = resourcePath.substring(0, secondIndex + 1);
                soua = resourcePath.replace(fpath, "");
            } else if (resourcePath.startsWith("file:")) {
                soua = resource.getURI().getPath().replaceFirst("/", "");
                String path = System.getProperty("java.class.path").replace("\\", "/");
                String[] paths = path.split(";");
                for (String p : paths) {
                    if (soua.startsWith(p)) {
                        soua = soua.replace(p, "");
                        break;
                    }
                }
            }
            String souc = soua.replace(".class", "").replaceFirst("/", "").replace("/", ".");
            Constant.METHOD_URL_SET.add(souc);
        }
    }
}
