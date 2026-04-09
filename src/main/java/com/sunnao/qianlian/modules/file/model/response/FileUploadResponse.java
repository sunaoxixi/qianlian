package com.sunnao.qianlian.modules.file.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadResponse {

    /**
     * 文件ID
     */
    private Long id;

    /**
     * 原始文件名
     */
    private String name;

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
