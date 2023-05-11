package com.lyj.yygh.vod.service;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lyj
 * @since 2023-05-09
 */
public interface CourseService extends IService<Course> {

    Map<String, Object> getCourselist(long current, long limit, CourseQueryVo courseQueryVo);

    long saveCourse(CourseFormVo courseFormVo);

    CourseFormVo findCourse(long id);

    void updataCourse(CourseFormVo courseFormVo);
}
