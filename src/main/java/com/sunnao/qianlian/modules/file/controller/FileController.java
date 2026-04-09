package com.sunnao.qianlian.modules.file.controller;

import com.sunnao.qianlian.common.result.Result;
import com.sunnao.qianlian.modules.file.model.response.FileUploadResponse;
import com.sunnao.qianlian.modules.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public Result<FileUploadResponse> upload(@RequestParam("file") MultipartFile file) {
        FileUploadResponse response = fileService.upload(file);
        return Result.success(response);
    }

    @DeleteMapping("/delete/{fileId}")
    public Result<Void> delete(@PathVariable Long fileId) {
        boolean deleteResult = fileService.delete(fileId);
        return Result.judge(deleteResult);
    }

    @GetMapping("/info/{fileId}")
    public Result<FileUploadResponse> info(@PathVariable Long fileId) {
        FileUploadResponse response = fileService.info(fileId);
        return Result.success(response);
    }

}
