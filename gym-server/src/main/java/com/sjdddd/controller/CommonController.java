package com.sjdddd.controller;

import com.sjdddd.annotation.OperationLog;
import com.sjdddd.constant.MessageConstant;
import com.sjdddd.result.Result;
import com.sjdddd.service.UserService;
import com.sjdddd.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/8/3 15:26
 **/

@RestController
@Slf4j
@CrossOrigin
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/upload/avatar")
    public Result<String> upload(MultipartFile file, @RequestParam("userId") Long userId) throws IOException {
        log.info("文件上传：{}", file.getOriginalFilename());

        try {
            String originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
            String filePath = aliOssUtil.upload(file.getBytes(), fileName);

            userService.updateAvatar(userId, filePath);

            return Result.success(filePath);
        } catch (IOException e) {
            log.info("文件上传失败：{}", e.getMessage());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
