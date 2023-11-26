package com.sjdddd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjdddd.dto.MemberAddDTO;
import com.sjdddd.dto.MemberEditDTO;
import com.sjdddd.entity.MemberCard;
import com.sjdddd.entity.User;
import com.sjdddd.mapper.MemberCardMapper;
import com.sjdddd.mapper.UserMapper;
import com.sjdddd.result.PageResult;
import com.sjdddd.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/22 15:41
 **/
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberCardMapper memberCardMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageResult list(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<MemberCard> list = memberCardMapper.selectAll();

        PageInfo page = new PageInfo(list);

        long total = page.getTotal();

        //System.out.println(page);

        return new PageResult(total, list);
    }

//    @Override
//    public List<MemberCard> list2() {
//        List<MemberCard> memberCard = memberCardMapper.selectAll2();
//
//        return memberCard;
//    }

    @Override
    public MemberCard add(MemberAddDTO memberAddDTO) {
        MemberCard memberCard = new MemberCard();
        User user = new User();

        BeanUtils.copyProperties(memberAddDTO, memberCard);
        BeanUtils.copyProperties(memberAddDTO, user);

        user.setUserRealName(memberAddDTO.getUserRealName());
        user.setUserPhone(memberAddDTO.getUserPhone());
        user.setSex(memberAddDTO.getSex());
        user.setUserName(memberAddDTO.getUserRealName());
        user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        user.setUserType("2");

        userMapper.insert(user);

        Long userId = userMapper.selectByUserName(memberAddDTO.getUserRealName()).getUserId();

        memberCard.setMemberFee(memberAddDTO.getMemberFee());
        memberCard.setUserId(userId);
        memberCard.setMemberCardStatus(memberAddDTO.getMemberCardStatus());
        memberCard.setActivateTime(LocalDateTime.now());


        memberCardMapper.insert(memberCard);
        //userMapper.insertSelective()

        return memberCard;
    }

    @Override
    public MemberCard edit(MemberEditDTO memberEditDTO) {
        MemberCard memberCard = new MemberCard();

        BeanUtils.copyProperties(memberEditDTO, memberCard);
        //Long memberCardId = memberCard.getMemberCardId();

        memberCardMapper.updateByPrimaryKeySelective(memberCard);

        User user = new User();
        user.setUserId(memberCard.getUserId());
        user.setUserRealName(memberEditDTO.getUserRealName());
        user.setUserPhone(memberEditDTO.getUserPhone());
        user.setSex(memberEditDTO.getSex());
        user.setUserName(memberEditDTO.getUserRealName());

        userMapper.updateByPrimaryKeySelective(user);

        return memberCard;
    }

    @Override
    public void delete(Long memberCardId) {
        memberCardMapper.deleteByPrimaryKey(memberCardId);

    }

    @Override
    public PageResult searchList(Integer pageNum, Integer pageSize, String userRealName) {
        PageHelper.startPage(pageNum, pageSize);

        List<MemberCard> list = memberCardMapper.selectByUserRealName(userRealName);

        PageInfo page = new PageInfo(list);

        long total = page.getTotal();

        return new PageResult(total, list);
    }

    @Override
    public BigDecimal getMemberFee(Object userId) {
        return memberCardMapper.selectByUserId(userId);
    }
}
