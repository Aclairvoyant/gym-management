package com.sjdddd.controller;

import com.sjdddd.annotation.OperationLog;
import com.sjdddd.dto.MemberAddDTO;
import com.sjdddd.dto.MemberEditDTO;
import com.sjdddd.entity.MemberCard;
import com.sjdddd.result.PageResult;
import com.sjdddd.result.Result;
import com.sjdddd.service.MemberService;
import com.sjdddd.vo.MemberAddVO;
import com.sjdddd.vo.MemberEditVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/22 15:26
 **/
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/list")
    @OperationLog(operDesc = "查询会员列表")
    public Result<PageResult> memberList(
            Integer pageNum,
            Integer pageSize
    ) {

        PageResult pageResult = memberService.list(pageNum, pageSize);

        return Result.success(pageResult);

    }

//    @GetMapping("/list")
//    public Result memberList() {
//
//        List<MemberCard> memberCard = memberService.list2();
//
//        return Result.success(memberCard);
//    }

    @PostMapping("/add")
    @OperationLog(operDesc = "添加会员")
    public Result<MemberAddVO> add(@RequestBody MemberAddDTO memberAddDTO) {
        log.info("添加会员：{}", memberAddDTO);

        memberService.add(memberAddDTO);

        return Result.success();
    }

    @PutMapping("/edit")
    @OperationLog(operDesc = "修改会员")
    public Result<MemberEditVO> edit(@RequestBody MemberEditDTO memberEditDTO) {
        log.info("修改会员：{}", memberEditDTO);

        memberService.edit(memberEditDTO);

        return Result.success();
    }

    @DeleteMapping("/delete")
    @OperationLog(operDesc = "删除会员")
    public Result<MemberEditVO> delete(@Param("memberCardID") Long memberCardId) {
        log.info("删除会员：{}", memberCardId);

        memberService.delete(memberCardId);

        return Result.success();
    }

    @GetMapping("/search")
    @OperationLog(operDesc = "搜索会员")
    public Result<PageResult> searchMemberList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String userRealName
    ) {

        PageResult pageResult = memberService.searchList(pageNum, pageSize, userRealName);

        return Result.success(pageResult);

    }
}
