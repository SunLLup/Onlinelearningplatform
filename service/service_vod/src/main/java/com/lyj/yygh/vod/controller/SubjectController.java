package com.lyj.yygh.vod.controller;


import com.atguigu.ggkt.model.vod.Subject;
import com.lyj.result.Result;
import com.lyj.yygh.vod.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lyj
 * @since 2023-05-09
 */
@RestController
@RequestMapping("/admin/subject/vod")
@CrossOrigin
@Api(tags = "课程接口")
public class SubjectController {
    @Resource
    SubjectService subjectService;

    @GetMapping("subjectlist/{id}")
    @ApiOperation("获取课程列表")
    public Result subjectlist(@PathVariable long id){
        List<Subject> list = subjectService.getSubject(id);

        return  Result.ok(list);
    }

    @ApiOperation("课程导出接口")
    @GetMapping("exportData")
    public void exportdata(HttpServletResponse response){
        subjectService.exportData(response);
    }

    @ApiOperation("课程导入接口")
    @PostMapping("importData")
    public void importData(MultipartFile file){
        System.out.println("-----");
        subjectService.importData(file);
    }



}

