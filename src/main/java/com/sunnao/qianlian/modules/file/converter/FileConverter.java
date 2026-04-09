package com.sunnao.qianlian.modules.file.converter;

import com.sunnao.qianlian.common.exception.BusinessException;
import com.sunnao.qianlian.common.result.ResultCode;
import com.sunnao.qianlian.modules.file.model.entity.FileEntity;
import com.sunnao.qianlian.modules.file.model.response.FileUploadResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FileConverter {

    FileUploadResponse convertToResponse(FileEntity fileEntity);

    default FileEntity convertToEntity(MultipartFile file, String name, String path, String url) {
        try {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setName(name);
            fileEntity.setPath(path);
            fileEntity.setUrl(url);
            fileEntity.setSize(file.getSize());
            fileEntity.setType(file.getContentType());
            return fileEntity;
        } catch (Exception e) {
            throw new BusinessException(ResultCode.UPLOAD_FILE_EXCEPTION);
        }
    }
}
