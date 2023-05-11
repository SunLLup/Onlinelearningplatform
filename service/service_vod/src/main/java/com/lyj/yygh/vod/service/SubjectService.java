package com.lyj.yygh.vod.service;

import com.atguigu.ggkt.model.vod.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lyj
 * @since 2023-05-09
 */
public interface SubjectService extends IService<Subject> {

    List<Subject> getSubject(long id);

    void exportData(HttpServletResponse response);

    void importData(MultipartFile file);
}
