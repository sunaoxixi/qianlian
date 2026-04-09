package com.sunnao.qianlian.framework.oss;

import cn.hutool.core.util.StrUtil;
import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.credentials.Credentials;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProviderSupplier;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.sunnao.qianlian.common.exception.BusinessException;
import com.sunnao.qianlian.common.result.ResultCode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(AliyunOssProperties.class)
public class AliyunOssClient {

    private final AliyunOssProperties aliyunOssProperties;

    private OSSClient client;

    @PostConstruct
    public void init() {
        validateConfiguration();
        CredentialsProvider credentialsProvider = new CredentialsProviderSupplier(
                () -> new Credentials(aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret()));
        this.client = OSSClient.newBuilder()
                .credentialsProvider(credentialsProvider)
                .region(aliyunOssProperties.getRegion())
                .build();
    }

    public String putObject(String key, byte[] content) {
        if (content == null) {
            throw new BusinessException(ResultCode.REQUEST_REQUIRED_PARAMETER_IS_EMPTY, "上传文件内容不能为空");
        }
        doPutObject(key, BinaryData.fromBytes(content));
        return buildFileUrl(key);
    }

    public String putObject(String key, MultipartFile file) {
        if (file == null) {
            throw new BusinessException(ResultCode.REQUEST_REQUIRED_PARAMETER_IS_EMPTY, "上传文件不能为空");
        }
        try (InputStream inputStream = file.getInputStream()) {
            doPutObject(key, BinaryData.fromStream(inputStream, file.getSize()));
            return buildFileUrl(key);
        } catch (IOException e) {
            throw new BusinessException(ResultCode.UPLOAD_FILE_EXCEPTION, buildErrorMessage("读取上传文件流失败", key, e));
        }
    }

    public String putObject(String key, InputStream inputStream, Long contentLength) {
        if (inputStream == null) {
            throw new BusinessException(ResultCode.REQUEST_REQUIRED_PARAMETER_IS_EMPTY, "上传文件流不能为空");
        }
        doPutObject(key, BinaryData.fromStream(inputStream, contentLength));
        return buildFileUrl(key);
    }

    public String buildFileUrl(String key) {
        return "https://" + aliyunOssProperties.getBucketName()
                + ".oss-" + aliyunOssProperties.getRegion()
                + ".aliyuncs.com/" + key;
    }

    public DeleteObjectResult deleteObject(String key) {
        validateObjectKey(key);
        try {
            return client.deleteObject(DeleteObjectRequest.newBuilder()
                    .bucket(aliyunOssProperties.getBucketName())
                    .key(key)
                    .build());
        } catch (Exception e) {
            throw new BusinessException(ResultCode.DELETE_FILE_EXCEPTION, buildErrorMessage("删除OSS文件失败", key, e));
        }
    }

    public GetObjectMetaResult getObjectMeta(String key) {
        validateObjectKey(key);
        try {
            return client.getObjectMeta(GetObjectMetaRequest.newBuilder()
                    .bucket(aliyunOssProperties.getBucketName())
                    .key(key)
                    .build());
        } catch (Exception e) {
            throw new BusinessException(ResultCode.THIRD_PARTY_SERVICE_ERROR, buildErrorMessage("获取OSS文件元数据失败", key, e));
        }
    }

    private PutObjectResult doPutObject(String key, BinaryData body) {
        validateObjectKey(key);
        if (body == null) {
            throw new BusinessException(ResultCode.REQUEST_REQUIRED_PARAMETER_IS_EMPTY, "上传文件内容不能为空");
        }
        try {
            return client.putObject(PutObjectRequest.newBuilder()
                    .bucket(aliyunOssProperties.getBucketName())
                    .key(key)
                    .body(body)
                    .build());
        } catch (Exception e) {
            throw new BusinessException(ResultCode.UPLOAD_FILE_EXCEPTION, buildErrorMessage("上传OSS文件失败", key, e));
        }
    }

    private void validateConfiguration() {
        if (StrUtil.isBlank(aliyunOssProperties.getAccessKeyId())) {
            throw new IllegalStateException("oss.aliyun.access-key-id 未配置");
        }
        if (StrUtil.isBlank(aliyunOssProperties.getAccessKeySecret())) {
            throw new IllegalStateException("oss.aliyun.access-key-secret 未配置");
        }
        if (StrUtil.isBlank(aliyunOssProperties.getBucketName())) {
            throw new IllegalStateException("oss.aliyun.bucket-name 未配置");
        }
        if (StrUtil.isBlank(aliyunOssProperties.getRegion())) {
            throw new IllegalStateException("oss.aliyun.region 未配置");
        }
    }

    private void validateObjectKey(String key) {
        if (StrUtil.isBlank(key)) {
            throw new BusinessException(ResultCode.REQUEST_REQUIRED_PARAMETER_IS_EMPTY, "OSS对象Key不能为空");
        }
    }

    private String buildErrorMessage(String action, String key, Exception e) {
        if (StrUtil.isBlank(e.getMessage())) {
            return action + ", 对象Key: " + key;
        }
        return action + ", 对象Key: " + key + ", 原因: " + e.getMessage();
    }

}
