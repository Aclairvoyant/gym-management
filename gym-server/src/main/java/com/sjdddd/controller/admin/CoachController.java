package com.sjdddd.controller.admin;

import com.sjdddd.annotation.OperationLog;
import com.sjdddd.dto.CoachAddDTO;
import com.sjdddd.dto.CoachEditDTO;
import com.sjdddd.dto.MemberAddDTO;
import com.sjdddd.dto.MemberEditDTO;
import com.sjdddd.entity.Coach;
import com.sjdddd.result.PageResult;
import com.sjdddd.result.Result;
import com.sjdddd.service.CoachService;
import com.sjdddd.vo.CoachAddVO;
import com.sjdddd.vo.CoachEditVO;
import com.sjdddd.vo.MemberAddVO;
import com.sjdddd.vo.MemberEditVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/23 22:23
 **/

@RestController
@Slf4j
@RequestMapping("/coach")
@CrossOrigin
public class CoachController {

    @Autowired
    private CoachService coachService;

    @GetMapping("/list")
    @OperationLog(operDesc = "查询教练列表")
    public Result<PageResult> coachList(
            Integer pageNum,
            Integer pageSize
    ) {

        PageResult pageResult = coachService.list(pageNum, pageSize);

        return Result.success(pageResult);

    }

    @PostMapping("/add")
    @OperationLog(operDesc = "添加教练")
    public Result<CoachAddVO> add(@RequestBody CoachAddDTO coachAddDTO) {
        log.info("添加教练：{}", coachAddDTO);

        coachService.add(coachAddDTO);

        return Result.success();
    }

    @PutMapping("/edit")
    @OperationLog(operDesc = "修改教练")
    public Result<CoachEditVO> edit(@RequestBody CoachEditDTO coachEditDTO) {
        log.info("修改教练：{}", coachEditDTO);

        coachService.edit(coachEditDTO);

        return Result.success();
    }

    @DeleteMapping("/delete")
    @OperationLog(operDesc = "删除教练")
    public Result<CoachEditVO> delete(@Param("coachId") Long coachId) {
        log.info("删除教练：{}", coachId);

        coachService.delete(coachId);

        return Result.success();
    }

    @GetMapping("/search")
    @OperationLog(operDesc = "搜索教练")
    public Result<PageResult> searchMemberList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String coachRealName
    ) {

        PageResult pageResult = coachService.searchList(pageNum, pageSize, coachRealName);

        return Result.success(pageResult);

    }
}
