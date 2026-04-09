package com.sunnao.qianlian.modules.file.service;

import com.mybatisflex.core.service.IService;
import com.sunnao.qianlian.modules.file.model.entity.FileEntity;
import com.sunnao.qianlian.modules.file.model.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService extends IService<FileEntity> {

    /**
     * 上传文件
     *
     * @param file 上传的文件
     * @return 文件上传响应
     */
    FileUploadResponse upload(MultipartFile file);

    /**
     * 删除文件
     *
     * @param fileId 文件ID
     * @return 是否删除成功
     */
    boolean delete(Long fileId);

    /**
     * 查询文件信息
     *
     * @param fileId 文件ID
     * @return 文件信息
     */
    FileUploadResponse info(Long fileId);

}
