package com.sunnao.qianlian.modules.file.model.entity;

import com.mybatisflex.annotation.Table;
import com.sunnao.qianlian.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 文件表
 * 每次文件上传都会产生一条记录
 *
 * @author sunnao
 */
@Getter
@Setter
@Table("sys_file")
public class FileEntity extends BaseEntity {

    /**
     * 原始文件名
     */
    private String name;

    /**
     * 路径, 即文件名
     */
    private String path;

    /**
     * 文件访问URL
     */
    private String url;

    /**
     * 文件大小(字节)
     */
    private Long size;

    /**
     * MIME类型
     */
    private String type;

}
