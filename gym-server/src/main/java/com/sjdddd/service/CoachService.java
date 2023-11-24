package com.sjdddd.service;

import com.sjdddd.dto.CoachAddDTO;
import com.sjdddd.dto.CoachEditDTO;
import com.sjdddd.entity.Coach;
import com.sjdddd.result.PageResult;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/23 22:23
 **/
public interface CoachService {

    PageResult list(Integer pageNum, Integer pageSize);

    Coach add(CoachAddDTO coachAddDTO);

    Coach edit(CoachEditDTO coachEditDTO);

    Coach delete(Long coachId);

    PageResult searchList(Integer pageNum, Integer pageSize, String coachRealName);
}
