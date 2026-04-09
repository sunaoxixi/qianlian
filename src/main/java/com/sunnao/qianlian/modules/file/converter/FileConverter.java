package com.sunnao.qianlian.modules.file.converter;

import com.sunnao.qianlian.modules.file.model.entity.FileEntity;
import com.sunnao.qianlian.modules.file.model.response.FileUploadResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FileConverter {

    FileUploadResponse convertToResponse(FileEntity fileEntity);

}
