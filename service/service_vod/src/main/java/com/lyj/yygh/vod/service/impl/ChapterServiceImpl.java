package com.lyj.yygh.vod.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.atguigu.ggkt.vo.vod.VideoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.yygh.vod.mapper.ChapterMapper;
import com.lyj.yygh.vod.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyj.yygh.vod.service.VideoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lyj
 * @since 2023-05-09
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Resource
    VideoService videoService;

    @Override
    public List<ChapterVo> getchapterlist(long courseid) {
        //根据课程id查找所有的篇章

        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseid);

        List<Chapter> chapters = baseMapper.selectList(chapterQueryWrapper);
        System.out.println("获取到的数据："+chapters+"id："+courseid);
        //todo 用于存放返回真是数据
        ArrayList<ChapterVo> chapterVos = new ArrayList<>();

        for (Chapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtil.copyProperties(chapter,chapterVo);
            //todo 查找所有的小节
            QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",courseid);
            List<Video> list = videoService.list(queryWrapper);
            //todo 用于存放大标题小节
            ArrayList<VideoVo> videos = new ArrayList<>();

            //todo 查找大文章标题的小节
            for (Video video : list) {
                if ((chapter.getId()).equals(video.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtil.copyProperties(video,videoVo);
                    videos.add(videoVo);
                }
            }

            chapterVo.setChildren(videos);
            chapterVos.add(chapterVo);

        }




        return chapterVos;
    }
}
