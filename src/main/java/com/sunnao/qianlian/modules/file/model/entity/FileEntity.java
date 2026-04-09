package com.sunnao.qianlian.modules.file.model.entity;

import com.mybatisflex.annotation.Table;
import com.sunnao.qianlian.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("sys_file")
public class FileEntity extends BaseEntity {

    /**
     * 原始文件名
     */
    private String fileName;

    /**
     * OSS对象Key
     */
    private String fileKey;

    /**
     * 文件访问URL
     */
    private String fileUrl;

    /**
     * 文件大小(字节)
     */
    private Long fileSize;

    /**
     * MIME类型
     */
    private String fileType;

    /**
     * 创建人 ID
     */
    private Long createBy;

    /**
     * 更新人 ID
     */
    private Long updateBy;

}
