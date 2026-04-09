package com.sunnao.qianlian.modules.file.service.impl;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sunnao.qianlian.common.exception.BusinessException;
import com.sunnao.qianlian.common.result.ResultCode;
import com.sunnao.qianlian.framework.oss.AliyunOssClient;
import com.sunnao.qianlian.modules.file.converter.FileConverter;
import com.sunnao.qianlian.modules.file.mapper.FileMapper;
import com.sunnao.qianlian.modules.file.model.entity.FileEntity;
import com.sunnao.qianlian.modules.file.model.response.FileUploadResponse;
import com.sunnao.qianlian.modules.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class FileServiceImpl extends ServiceImpl<FileMapper, FileEntity> implements FileService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private final AliyunOssClient aliyunOssClient;
    private final FileConverter fileConverter;

    @Override
    public FileUploadResponse upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ResultCode.REQUEST_REQUIRED_PARAMETER_IS_EMPTY, "上传文件不能为空");
        }

        String originalFilename = file.getOriginalFilename();
        String ext = StrUtil.isNotBlank(originalFilename) ? FileNameUtil.extName(originalFilename) : "";
        String key = buildObjectKey(ext);

        String fileUrl = aliyunOssClient.putObject(key, file);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(originalFilename);
        fileEntity.setFileKey(key);
        fileEntity.setFileUrl(fileUrl);
        fileEntity.setFileSize(file.getSize());
        fileEntity.setFileType(file.getContentType());

        save(fileEntity);

        return fileConverter.convertToResponse(fileEntity);
    }

    @Override
    public boolean delete(Long fileId) {
        FileEntity fileEntity = getById(fileId);
        Assert.notNull(fileEntity, () -> new BusinessException(ResultCode.DELETE_FILE_EXCEPTION, "文件不存在"));

        aliyunOssClient.deleteObject(fileEntity.getFileKey());

        return removeById(fileId);
    }

    @Override
    public FileUploadResponse info(Long fileId) {
        FileEntity fileEntity = getById(fileId);
        Assert.notNull(fileEntity, () -> new BusinessException(ResultCode.USER_REQUEST_PARAMETER_ERROR, "文件不存在"));
        return fileConverter.convertToResponse(fileEntity);
    }

    private String buildObjectKey(String ext) {
        String datePath = LocalDate.now().format(DATE_FORMATTER);
        String uuid = IdUtil.fastSimpleUUID();
        if (StrUtil.isNotBlank(ext)) {
            return datePath + "/" + uuid + "." + ext;
        }
        return datePath + "/" + uuid;
    }

}
