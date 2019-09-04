package com.jeebase.system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jeebase
 */
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiNiuProperties {

    private String accessKey;

    private String secretKey;

    /**
     * 七牛机房 华东：zone0 华北：zone1 华南：zone2 北美：zoneNa0
     */
    private String zone;

    private String bucket;

    private String baseUrl;

    private String bucketPrivate;

    private String baseUrlPrivate;

    private String uploadDirPrefix;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBucketPrivate() {
        return bucketPrivate;
    }

    public void setBucketPrivate(String bucketPrivate) {
        this.bucketPrivate = bucketPrivate;
    }

    public String getBaseUrlPrivate() {
        return baseUrlPrivate;
    }

    public void setBaseUrlPrivate(String baseUrlPrivate) {
        this.baseUrlPrivate = baseUrlPrivate;
    }

    public String getUploadDirPrefix() {
        return uploadDirPrefix;
    }

    public void setUploadDirPrefix(String uploadDirPrefix) {
        this.uploadDirPrefix = uploadDirPrefix;
    }
}
