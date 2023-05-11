package com.lyj.yygh.vod.controller;


import cn.hutool.core.util.StrUtil;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyj.exception.MyException;
import com.lyj.result.Result;
import com.lyj.yygh.vod.mapper.TeacherMapper;
import com.lyj.yygh.vod.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lyj
 * @since 2023-05-06
 */
@RestController
@RequestMapping("/admin/teacher/vod")
@Api(tags = "讲师列表")
@CrossOrigin
public class TeacherController {
    @Resource
    TeacherService teacherService;


    @ApiOperation("查询所有讲师信息")
    @GetMapping("findlist")
    public Result findlist(){
        List<Teacher> list = teacherService.list();
        return Result.ok(list);
    }

    @ApiOperation("根据id删除讲师")
    @DeleteMapping("remove/{id}")
    public Result delete(@PathVariable Long id){
        boolean isSucess = teacherService.removeById(id);
        if(isSucess){
            return Result.ok(null);
        }
        return Result.fail(null);
    }

    @ApiOperation("分页条件查询")
    @PostMapping("findQueryPage/{current}/{limit}")
    public Result findePage(@PathVariable long current,
                            @PathVariable long limit,
                            @RequestBody(required = false) TeacherQueryVo teacherQueryVo){

        Page<Teacher> page = new Page<>(current,limit);

        if (teacherQueryVo == null){
            IPage<Teacher> page1 = teacherService.page(page, null);
            return Result.ok(page1).code(20000);

        }else{
            String name = teacherQueryVo.getName();
            Integer level = teacherQueryVo.getLevel();
            String joinDateBegin = teacherQueryVo.getJoinDateBegin();
            String joinDateEnd = teacherQueryVo.getJoinDateEnd();

            QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
            System.out.println(!StrUtil.isEmpty(name));
            if (!StrUtil.isEmpty(name)){
                queryWrapper.like("name",name);
            }
            if (level!=null){
                queryWrapper.eq("level",level);
            }
            if (!StrUtil.isEmpty(joinDateBegin)){
                queryWrapper.ge("create_time",joinDateBegin);
            }
            if (!StrUtil.isEmpty(joinDateEnd)){
                queryWrapper.le("create_time",joinDateEnd);
            }

            IPage<Teacher> page1 = teacherService.page(page, queryWrapper);

            return Result.ok(page1).code(20000);

        }

    }

    @ApiOperation("讲师添加")
    @PostMapping("saveTeacher")
    public Result savateacher(@RequestBody Teacher teacher){
        System.out.println("添加的数据为："+teacher);

        boolean isSucess = teacherService.save(teacher);
        if (isSucess){
            return Result.ok(null);
        }

        return Result.fail(null);
    }

    @ApiOperation("单个讲师查询")
    @GetMapping("queryTeacher/{id}")
    public Result queryTeacher(@PathVariable long id){
        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher);
    }

    @ApiOperation("获取所有讲师")
    @GetMapping("list")
    public Result list(){
        List<Teacher> list = teacherService.list();
        return Result.ok(list);
    }


    @ApiOperation("讲师的修改")
    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher){
        System.out.println("更新的数据为："+teacher);
        boolean b = teacherService.updateById(teacher);
        if (b){
            return Result.ok(null);
        }

        return Result.fail(null);

    }

    @ApiOperation("讲师的批量删除")
    @DeleteMapping("removeBatch")
    public Result removeBatch(@RequestBody List<Long> list){

        System.out.println(list);
        boolean b = teacherService.removeByIds(list);
        if (b){
            return Result.ok(null);

        }
        return Result.fail(null);
    }



}


