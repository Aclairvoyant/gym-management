package com.sjdddd.controller.admin;

import com.sjdddd.annotation.OperationLog;
import com.sjdddd.result.PageResult;
import com.sjdddd.result.Result;
import com.sjdddd.service.OperateLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/26 02:28
 **/

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/log")
public class LogController {

    @Autowired
    private OperateLogsService operatelogsService;

    @GetMapping("/list")
    @OperationLog(operDesc = "查询日志列表")
    public Result<PageResult> logList(
            Integer pageNum,
            Integer pageSize
    ) {

        PageResult pageResult = operatelogsService.list(pageNum, pageSize);

        return Result.success(pageResult);

    }

    @GetMapping("/search")
    @OperationLog(operDesc = "搜索日志列表")
    public Result<PageResult> searchLogList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String query
    ) {

        PageResult pageResult = operatelogsService.searchList(pageNum, pageSize, query);

        return Result.success(pageResult);

    }
}
