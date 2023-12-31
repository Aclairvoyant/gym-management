package com.sjdddd.controller.admin;

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
            // 获取文件名
            String originalFilename = file.getOriginalFilename();
            // 判断文件类型
            if (!originalFilename.endsWith(".jpg") && !originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpeg")) {
                return Result.error(MessageConstant.UPLOAD_FAILED_WITH_WRONG_FILE_TYPE);
            }
            // 判断文件大小
            if (file.getSize() > 1024 * 1024 * 5) {
                return Result.error(MessageConstant.UPLOAD_FAILED_WITH_EXCEEDED_FILE_SIZE);
            }
            // UUID生成随机文件名
            String fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
            // 上传文件
            String filePath = aliOssUtil.upload(file.getBytes(), fileName);

            // 更新用户头像
            userService.updateAvatar(userId, filePath);

            return Result.success(filePath);
        } catch (IOException e) {
            log.info("文件上传失败：{}", e.getMessage());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
