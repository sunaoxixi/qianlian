package com.sunnao.qianlian.modules.file.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static cn.hutool.core.date.DatePattern.PURE_DATE_PATTERN;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileMapper, FileEntity> implements FileService {

    /**
     * 上传文件的前缀，是否包含日期（yyyyMMdd）
     * <p>
     * 目的：按照日期，进行分目录
     */
    static boolean PATH_PREFIX_DATE_ENABLE = true;
    /**
     * 上传文件的后缀，是否包含时间戳
     * <p>
     * 目的：保证文件的唯一性，避免覆盖
     * 定制：可按需调整成 UUID、或者其他方式
     */
    static boolean PATH_SUFFIX_TIMESTAMP_ENABLE = true;

    private final AliyunOssClient aliyunOssClient;
    private final FileConverter fileConverter;

    @Override
    public FileUploadResponse upload(MultipartFile file) {
        // 参数校验
        Assert.notNull(file, () -> new BusinessException(ResultCode.REQUEST_REQUIRED_PARAMETER_IS_EMPTY, "上传文件不能为空"));

        try {

            String name = file.getOriginalFilename();
            if (StrUtil.isBlankIfStr(name)) {
                // 处理name为空的情况
                name = DigestUtil.sha256Hex(file.getBytes());
            }
            if (StrUtil.isEmpty(FileUtil.extName(name))) {
                // 如果 name 没有后缀 type，则补充后缀
                String extension = FileUtil.extName(name);
                if (StrUtil.isNotEmpty(extension)) {
                    name = name + extension;
                }
            }
            // 生成文件path
            String path = generateUploadPath(name);
            // 上传文件到对象存储服务器
            String url = aliyunOssClient.putObject(path, file);
            // 保存上传记录到数据库
            FileEntity fileEntity = fileConverter.convertToEntity(file, name, path, url);
            save(fileEntity);
            log.info("[FileService] 文件上传成功, fileId: {}", fileEntity.getId());

            return fileConverter.convertToResponse(fileEntity);
        } catch (Exception e) {
            log.error("[FileService] 文件上传失败", e);
            throw new BusinessException(ResultCode.UPLOAD_FILE_EXCEPTION, "文件上传失败");
        }
    }

    @Override
    public boolean delete(Long fileId) {

        FileEntity fileEntity = getById(fileId);
        Assert.notNull(fileEntity, () -> new BusinessException(ResultCode.DELETE_FILE_EXCEPTION, "文件不存在"));
        aliyunOssClient.deleteObject(fileEntity.getPath());
        return removeById(fileId);
    }

    @Override
    public FileUploadResponse info(Long fileId) {
        FileEntity fileEntity = getById(fileId);
        Assert.notNull(fileEntity, () -> new BusinessException(ResultCode.USER_REQUEST_PARAMETER_ERROR, "文件不存在"));
        return fileConverter.convertToResponse(fileEntity);
    }

    /**
     * 生成文件上传路径
     *
     * @param name 文件名
     * @return 文件上传路径
     */
    private String generateUploadPath(String name) {
        // 生成前缀、后缀
        String prefix = null;
        if (PATH_PREFIX_DATE_ENABLE) {
            prefix = LocalDateTimeUtil.format(LocalDateTimeUtil.now(), PURE_DATE_PATTERN);
        }
        String suffix = null;
        if (PATH_SUFFIX_TIMESTAMP_ENABLE) {
            suffix = String.valueOf(System.currentTimeMillis());
        }

        // 先拼接 suffix 后缀
        if (StrUtil.isNotEmpty(suffix)) {
            String ext = FileUtil.extName(name);
            if (StrUtil.isNotEmpty(ext)) {
                name = FileUtil.mainName(name) + StrUtil.C_UNDERLINE + suffix + StrUtil.DOT + ext;
            } else {
                name = name + StrUtil.C_UNDERLINE + suffix;
            }
        }
        // 再拼接 prefix 前缀
        if (StrUtil.isNotEmpty(prefix)) {
            name = prefix + StrUtil.SLASH + name;
        }
        return name;
    }

}
