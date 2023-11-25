package com.sjdddd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjdddd.entity.Course;
import com.sjdddd.entity.Log;
import com.sjdddd.mapper.LogMapper;
import com.sjdddd.result.PageResult;
import com.sjdddd.service.OperateLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/26 02:02
 **/

@Service
public class OperateLogsServiceImpl implements OperateLogsService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public void save(Log operlog) {
        logMapper.insertSelective(operlog);
    }

    @Override
    public PageResult list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<Log> list = logMapper.selectAll();

        PageInfo page = new PageInfo(list);

        long total = page.getTotal();

        //System.out.println(page);

        return new PageResult(total, list);
    }

    @Override
    public PageResult searchList(Integer pageNum, Integer pageSize, String query) {
        PageHelper.startPage(pageNum, pageSize);

        List<Log> list = logMapper.searchLogs(query);

        PageInfo page = new PageInfo(list);

        long total = page.getTotal();

        return new PageResult(total, list);
    }
}
