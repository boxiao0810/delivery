package com.sky.controller.admin;


import com.sky.result.Result;
import com.sky.utils.LocalStorageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "common interface")
@Slf4j
public class CommonController {

    @Autowired
    private LocalStorageUtil localStorageUtil;

    @PostMapping("/upload")
    @ApiOperation("file upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        log.info("uploading file"+ file);
        try {
            String originalFileName = file.getOriginalFilename();

            //get the suffix of the original file name
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + extension;
            String fullPath = localStorageUtil.upload(file.getBytes(),fileName);
            return Result.success(fullPath);
        } catch (IOException e){
           log.error("the file upload failed: {}", e);
        }

        return Result.error("File upload failed");
    }
}
