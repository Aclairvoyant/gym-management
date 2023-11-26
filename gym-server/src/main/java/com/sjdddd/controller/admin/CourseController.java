package com.sjdddd.controller.admin;

import com.sjdddd.annotation.OperationLog;
import com.sjdddd.dto.CoachAddDTO;
import com.sjdddd.dto.CoachEditDTO;
import com.sjdddd.dto.CourseAddDTO;
import com.sjdddd.dto.CourseEditDTO;
import com.sjdddd.entity.Coach;
import com.sjdddd.result.PageResult;
import com.sjdddd.result.Result;
import com.sjdddd.service.CourseService;
import com.sjdddd.vo.CoachAddVO;
import com.sjdddd.vo.CoachEditVO;
import com.sjdddd.vo.CourseAddVO;
import com.sjdddd.vo.CourseEditVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/24 11:06
 **/
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/list")
    @OperationLog(operDesc = "查询课程列表")
    public Result<PageResult> coachList(
            Integer pageNum,
            Integer pageSize
    ) {

        PageResult pageResult = courseService.list(pageNum, pageSize);

        return Result.success(pageResult);

    }

    @PostMapping("/add")
    @OperationLog(operDesc = "添加课程")
    public Result<CourseAddVO> add(@RequestBody CourseAddDTO courseAddDTO) {
        log.info("添加课程：{}", courseAddDTO);

        courseService.add(courseAddDTO);

        return Result.success();
    }

    @GetMapping("/getCoachList")
    @OperationLog(operDesc = "查询教练列表")
    public Result<List<Coach>> getCoachList() {
        List<Coach> coach = courseService.getCoachList();
        return Result.success(coach);
    }

    @PutMapping("/edit")
    @OperationLog(operDesc = "修改课程")
    public Result<CourseEditVO> edit(@RequestBody CourseEditDTO courseEditDTO) {
        log.info("修改课程：{}", courseEditDTO);

        courseService.edit(courseEditDTO);

        return Result.success();
    }

    @DeleteMapping("/delete")
    @OperationLog(operDesc = "删除课程")
    public Result<CourseEditVO> delete(@Param("courseId") Long courseId) {
        log.info("删除课程：{}", courseId);

        courseService.delete(courseId);

        return Result.success();
    }

    @GetMapping("/search")
    @OperationLog(operDesc = "搜索课程")
    public Result<PageResult> searchCourseList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String courseName
    ) {

        PageResult pageResult = courseService.searchList(pageNum, pageSize, courseName);

        return Result.success(pageResult);

    }
}
