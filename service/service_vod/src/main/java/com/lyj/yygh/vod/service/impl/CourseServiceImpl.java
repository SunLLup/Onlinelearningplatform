package com.lyj.yygh.vod.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.model.vod.CourseDescription;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyj.yygh.vod.mapper.CourseDescriptionMapper;
import com.lyj.yygh.vod.mapper.CourseMapper;
import com.lyj.yygh.vod.service.CourseDescriptionService;
import com.lyj.yygh.vod.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyj.yygh.vod.service.SubjectService;
import com.lyj.yygh.vod.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lyj
 * @since 2023-05-09
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Resource
    TeacherService teacherService;

    @Resource
    SubjectService subjectService;

    @Resource
    CourseDescriptionMapper courseDescriptionMapper;

    @Override
    public Map<String, Object> getCourselist(long current, long limit, CourseQueryVo courseQueryVo) {
        Long subjectParentId = courseQueryVo.getSubjectParentId();
        Long subjectId = courseQueryVo.getSubjectId();
        String title = courseQueryVo.getTitle();
        Long teacherId = courseQueryVo.getTeacherId();
        System.out.println("哈喽："+subjectParentId+"--"+courseQueryVo);
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(subjectParentId)){
            courseQueryWrapper.eq("subject_parent_id",subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)){
            courseQueryWrapper.eq("subject_id",subjectId);
        }
        if (!StringUtils.isEmpty(title)){
            courseQueryWrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(teacherId)){
            courseQueryWrapper.eq("teacher_id",teacherId);
        }

        Page<Course> coursePage = new Page<>(current,limit);

        IPage<Course> coursePage1 = baseMapper.selectPage(coursePage, courseQueryWrapper);

        long total = coursePage1.getTotal();
        long pages = coursePage1.getPages();
        List<Course> records = coursePage1.getRecords();

        records.stream().forEach(item ->{
            getCourseName(item);
        });


        //封装返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("totalCount",total);
        map.put("totalPage",pages);
        map.put("records",records);


        return map;
    }

    //添加课程的基本信息
    @Override
    public long saveCourse(CourseFormVo courseFormVo) {
        Course course = new Course();

        //  匹配字段拷贝
        BeanUtil.copyProperties(courseFormVo,course);

        //添加课程
        baseMapper.insert(course);

        //添加课程简介
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescription.setCourseId(course.getId());

        System.out.println("要添加的详情数据为："+courseDescription);
        courseDescriptionMapper.insert(courseDescription);
        return course.getId();
    }
    //查找课程信息
    @Override
    public CourseFormVo findCourse(long id) {
        Course course = baseMapper.selectById(id);
        if (course==null){
            return null;
        }
        //todo 用于存放要查找的课程信息
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtil.copyProperties(course,courseFormVo);

        //todo 存放课程详情
        CourseDescription courseDescription = courseDescriptionMapper.selectById(id);
        courseFormVo.setDescription(courseDescription.getDescription());

        return courseFormVo;
    }

    //更新课程表数据
    @Override
    public void updataCourse(CourseFormVo courseFormVo) {
        Course course = new Course();
        BeanUtil.copyProperties(courseFormVo,course);

        baseMapper.updateById(course);

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseFormVo.getId());
        courseDescription.setCourseId(courseFormVo.getId());
        courseDescription.setDescription(courseFormVo.getDescription());

        courseDescriptionMapper.updateById(courseDescription);
    }

    private Course getCourseName(Course course) {

        Teacher teacher = teacherService.getById(course.getTeacherId());
        if (teacher != null){
            course.getParam().put("teacherName",teacher.getName());
        }

        //根据课程分类id获取课程分类名称
        Subject subjectOne = subjectService.getById(course.getSubjectParentId());
        if(subjectOne != null) {
            course.getParam().put("subjectParentTitle",subjectOne.getTitle());
        }
        Subject subjectTwo = subjectService.getById(course.getSubjectId());
        if(subjectTwo != null) {
            course.getParam().put("subjectTitle",subjectTwo.getTitle());
        }


        return course;
    }
}
