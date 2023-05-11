package com.lyj.yygh.vod.controller;


import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.lyj.result.Result;
import com.lyj.yygh.vod.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lyj
 * @since 2023-05-09
 */
@RestController
@RequestMapping("/admin/chapter/vod")
@Api(tags = "课程章节表")
@CrossOrigin
public class ChapterController {
    @Resource
    ChapterService chapterService;

    @ApiOperation("查找章节列表")
    @GetMapping("findchapter/{courseid}")
    public Result findchapter(@PathVariable long courseid){

        List<ChapterVo> list = chapterService.getchapterlist(courseid);
        return  Result.ok(list);
    }

    //2 添加章节
    @PostMapping("save")
    public Result save(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return Result.ok(null);
    }

    //3 修改-根据id查询
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Chapter chapter = chapterService.getById(id);
        return Result.ok(chapter);
    }

    //4 修改-最终实现
    @PostMapping("update")
    public Result update(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return Result.ok(null);
    }

    //5 删除章节
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        chapterService.removeById(id);
        return Result.ok(null);
    }



}

