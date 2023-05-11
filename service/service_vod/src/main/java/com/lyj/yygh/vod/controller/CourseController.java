package com.lyj.yygh.vod.controller;


import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.lyj.result.Result;
import com.lyj.yygh.vod.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lyj
 * @since 2023-05-09
 */
@RestController
@RequestMapping("/admin/course/vod")
@CrossOrigin
@Api(tags = "课程列表")
public class CourseController {
    @Resource
    CourseService courseService;

    @PostMapping("courselist/{current}/{limit}")
    @ApiOperation("课程列表分页")
    public Result courselist(@PathVariable long current, @PathVariable long limit,
                             @RequestBody(required = false) CourseQueryVo courseQueryVo){
        System.out.println("hahaha");
        Map<String,Object> map = courseService.getCourselist(current,limit,courseQueryVo);
        return Result.ok(map);
    }

    @PostMapping("courseformadd")
    @ApiOperation("课程基本信息添加")
    @CrossOrigin
    public Result courseformadd(@RequestBody CourseFormVo courseFormVo){

        long l = courseService.saveCourse(courseFormVo);

        return Result.ok(l).message("课程基本信息添加成功");
    }

    @ApiOperation("更具id获取课程信息")
    @GetMapping("findcourse/{id}")
    @CrossOrigin
    public Result findcouse(@PathVariable long id){
        System.out.println("hahahaha");
       CourseFormVo courseFormVo =  courseService.findCourse(id);
        System.out.println(courseFormVo);
        return Result.ok(courseFormVo);

    }

    @PostMapping("courseformupdata")
    @ApiOperation("课程基本信息添加")
    @CrossOrigin
    public Result courseformupdata(@RequestBody CourseFormVo courseFormVo){

        courseService.updataCourse(courseFormVo);

        return Result.ok(null).message("课程基本信息修改成功");
    }








}

