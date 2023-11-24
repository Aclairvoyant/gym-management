package com.sjdddd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjdddd.dto.CoachAddDTO;
import com.sjdddd.dto.CoachEditDTO;
import com.sjdddd.entity.Coach;
import com.sjdddd.entity.MemberCard;
import com.sjdddd.mapper.CoachMapper;
import com.sjdddd.result.PageResult;
import com.sjdddd.service.CoachService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/23 22:23
 **/
@Service
public class CoachServiceImpl implements CoachService {

    @Autowired
    private CoachMapper coachMapper;


    @Override
    public PageResult list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Coach> list = coachMapper.selectAll();

        PageInfo page = new PageInfo(list);

        long total = page.getTotal();

        //System.out.println(page);

        return new PageResult(total, list);
    }

    @Override
    public Coach add(CoachAddDTO coachAddDTO) {
        Coach coach = new Coach();

        BeanUtils.copyProperties(coachAddDTO, coach);

       coach.builder()
               .coachRealName(coachAddDTO.getCoachRealName())
               .coachSex(coachAddDTO.getCoachSex())
               .coachPhone(coachAddDTO.getCoachPhone())
               .coachRemark(coachAddDTO.getCoachRemark())
               .build();

       coachMapper.insertSelective(coach);

        return coach;
    }

    @Override
    public Coach edit(CoachEditDTO coachEditDTO) {
        Coach coach = new Coach();

        BeanUtils.copyProperties(coachEditDTO, coach);

        coach.builder()
                .coachId(coachEditDTO.getCoachId())
                .coachRealName(coachEditDTO.getCoachRealName())
                .coachSex(coachEditDTO.getCoachSex())
                .coachPhone(coachEditDTO.getCoachPhone())
                .coachRemark(coachEditDTO.getCoachRemark())
                .build();

        coachMapper.updateByPrimaryKeySelective(coach);

        return coach;
    }

    @Override
    public Coach delete(Long coachId) {
        Coach coach = new Coach();

        coach.builder()
                .coachId(coachId)
                .build();

        coachMapper.deleteByPrimaryKey(coachId);

        return coach;
    }

    @Override
    public PageResult searchList(Integer pageNum, Integer pageSize, String coachRealName) {
        PageHelper.startPage(pageNum, pageSize);

        List<Coach> list = coachMapper.selectByCoachRealName(coachRealName);

        PageInfo page = new PageInfo(list);

        long total = page.getTotal();

        return new PageResult(total, list);
    }
}
