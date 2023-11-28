package com.sjdddd.service;

import com.sjdddd.dto.CourseAddDTO;
import com.sjdddd.dto.CourseEditDTO;
import com.sjdddd.entity.Coach;
import com.sjdddd.entity.Course;
import com.sjdddd.result.PageResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/24 11:07
 **/
public interface CourseService {

    PageResult list(Integer pageNum, Integer pageSize);

    Course add(CourseAddDTO courseAddDTO);

    List<Coach> getCoachList();

    Course edit(CourseEditDTO courseEditDTO);

    Course delete(Long courseId);

    PageResult searchList(Integer pageNum, Integer pageSize, String courseName);

    BigDecimal getCoursePrice(Long courseId);

    boolean unenrollCourse(Object userId, Long courseId);

    PageResult memberList(Integer pageNum, Integer pageSize, Object userId);
}
