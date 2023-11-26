package com.sjdddd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjdddd.dto.CourseAddDTO;
import com.sjdddd.dto.CourseEditDTO;
import com.sjdddd.entity.Coach;
import com.sjdddd.entity.Course;
import com.sjdddd.mapper.BookingMapper;
import com.sjdddd.mapper.CoachMapper;
import com.sjdddd.mapper.CourseMapper;
import com.sjdddd.result.PageResult;
import com.sjdddd.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/24 11:07
 **/
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CoachMapper coachMapper;

    @Autowired
    private BookingMapper bookingMapper;

    @Override
    public PageResult list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Course> list = courseMapper.selectAll();

        PageInfo page = new PageInfo(list);

        long total = page.getTotal();

        //System.out.println(page);

        return new PageResult(total, list);
    }

    @Override
    public Course add(CourseAddDTO courseAddDTO) {
        Course course = new Course();

        List<Coach> coach = coachMapper.selectByCoachRealName(courseAddDTO.getCoachRealName());

        Long coachId = coach.get(0).getCoachId();

        courseAddDTO.setCoachId(coachId);

        BeanUtils.copyProperties(courseAddDTO, course);

        course.builder()
                .courseName(courseAddDTO.getCourseName())
                .coachId(courseAddDTO.getCoachId())
                .courseFee(courseAddDTO.getCourseFee())
                .scheduleStart(courseAddDTO.getScheduleStart())
                .scheduleEnd(courseAddDTO.getScheduleEnd())
                .build();

        courseMapper.insert(course);

        return course;
    }

    @Override
    public List<Coach> getCoachList() {

        List<Coach> coach = coachMapper.selectCoachRealName();

        return coach;
    }

    @Override
    public Course edit(CourseEditDTO courseEditDTO) {
        Course course = new Course();

        List<Coach> coach = coachMapper.selectByCoachRealName(courseEditDTO.getCoachRealName());

        Long coachId = coach.get(0).getCoachId();

        courseEditDTO.setCoachId(coachId);

        BeanUtils.copyProperties(courseEditDTO, course);

        course.builder()
                .courseName(courseEditDTO.getCourseName())
                .coachId(courseEditDTO.getCoachId())
                .courseFee(courseEditDTO.getCourseFee())
                .scheduleStart(courseEditDTO.getScheduleStart())
                .scheduleEnd(courseEditDTO.getScheduleEnd())
                .build();

        courseMapper.updateByPrimaryKeySelective(course);

        return course;
    }

    @Override
    public Course delete(Long courseId) {
        Course course = new Course();

        course.builder()
                .courseId(courseId)
                .build();

        courseMapper.deleteByPrimaryKey(courseId);

        return course;
    }

    @Override
    public PageResult searchList(Integer pageNum, Integer pageSize, String courseName) {
        PageHelper.startPage(pageNum, pageSize);

        List<Course> list = courseMapper.selectByCourseName(courseName);

        PageInfo page = new PageInfo(list);

        long total = page.getTotal();

        return new PageResult(total, list);
    }

    @Override
    public BigDecimal getCoursePrice(Long courseId) {
        return courseMapper.selectCoursePrice(courseId);
    }

    @Override
    public boolean unenrollCourse(Object userId, Long courseId) {
        return bookingMapper.deleteByCourseId(courseId);
    }
}
