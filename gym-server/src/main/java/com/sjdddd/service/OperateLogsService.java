package com.sjdddd.service;

import com.sjdddd.entity.Log;
import com.sjdddd.result.PageResult;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/26 01:48
 **/
public interface OperateLogsService {
    void save(Log operlog);

    PageResult list(Integer pageNum, Integer pageSize);

    PageResult searchList(Integer pageNum, Integer pageSize, String query);
}
