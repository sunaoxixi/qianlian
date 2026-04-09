package com.sunnao.qianlian.modules.file.mapper;

import com.mybatisflex.core.BaseMapper;
import com.sunnao.qianlian.modules.file.model.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<FileEntity> {
}
