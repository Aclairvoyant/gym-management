package com.sjdddd.mapper;

import com.sjdddd.annotation.AutoFill;
import com.sjdddd.entity.Coach;
import com.sjdddd.entity.Course;
import com.sjdddd.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/20 16:32
 **/
@Mapper
public interface CourseMapper {
    int deleteByPrimaryKey(Long courseId);

    @AutoFill(value = OperationType.INSERT)
    int insert(Course record);

    @AutoFill(value = OperationType.INSERT)
    int insertSelective(Course record);

    Course selectByPrimaryKey(Long courseId);

    @AutoFill(value = OperationType.UPDATE)
    int updateByPrimaryKeySelective(Course record);

    @AutoFill(value = OperationType.UPDATE)
    int updateByPrimaryKey(Course record);

    List<Course> selectAll();

    List<Course> selectByCourseName(String courseName);

    BigDecimal selectCoursePrice(Long courseId);

    void updateCourseStatus(Long courseId);

    String selectCourseStatus(Long courseId);

    void updateCourseStatusNo(Long courseId);
}
