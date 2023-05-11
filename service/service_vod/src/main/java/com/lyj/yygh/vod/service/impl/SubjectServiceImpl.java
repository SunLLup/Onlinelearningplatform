package com.lyj.yygh.vod.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.vo.vod.SubjectEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.yygh.vod.listener.ReadExcel;
import com.lyj.yygh.vod.mapper.SubjectMapper;
import com.lyj.yygh.vod.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lyj
 * @since 2023-05-09
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    @Resource
    ReadExcel readExcel;


    @Override
    public List<Subject> getSubject(long id) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);

        List<Subject> subjects = baseMapper.selectList(wrapper);

        for (Subject subject : subjects) {
            if (isChild(subject.getId())){
                subject.setHasChildren(true);
            }
        }


        return subjects;
    }

    @Override
    public void exportData(HttpServletResponse response) {

        try {
            //设置下载信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");

            List<Subject> subjects = baseMapper.selectList(null);
            ArrayList<SubjectEeVo> subjectEeVos = new ArrayList<>();
            for (Subject subject : subjects) {
                SubjectEeVo subjectEeVo = new SubjectEeVo();
                BeanUtil.copyProperties(subject,subjectEeVo);
                subjectEeVos.add(subjectEeVo);
            }

            //数据写入
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class).sheet("课程分类").doWrite(subjectEeVos);



        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),SubjectEeVo.class, readExcel).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isChild(Long id) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer integer = baseMapper.selectCount(wrapper);


        return integer>0;
    }
}
