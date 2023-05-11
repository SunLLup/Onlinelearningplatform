package com.lyj.yygh.vod.listener;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.vo.vod.SubjectEeVo;
import com.lyj.yygh.vod.mapper.SubjectMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ReadExcel extends AnalysisEventListener<SubjectEeVo> {
    @Resource
    SubjectMapper subjectMapper;

    @Override
    public void invoke(SubjectEeVo subjectEeVo, AnalysisContext analysisContext) {

        Subject subject = new Subject();
        BeanUtil.copyProperties(subjectEeVo,subject);

        System.out.println("转换之后的："+subject);
        subjectMapper.insert(subject);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
