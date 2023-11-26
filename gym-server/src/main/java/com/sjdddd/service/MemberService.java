package com.sjdddd.service;

import com.sjdddd.dto.MemberAddDTO;
import com.sjdddd.dto.MemberEditDTO;
import com.sjdddd.entity.MemberCard;
import com.sjdddd.result.PageResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/22 15:40
 **/
public interface MemberService {

    PageResult list(Integer pageNum, Integer pageSize);

    //List<MemberCard> list2();

    MemberCard add(MemberAddDTO memberAddDTO);

    MemberCard edit(MemberEditDTO memberEditDTO);

    void delete(Long memberCardId);

    PageResult searchList(Integer pageNum, Integer pageSize, String keyword);

    BigDecimal getMemberFee(Object userId);
}
