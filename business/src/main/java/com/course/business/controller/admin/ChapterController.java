package com.course.business.controller.admin;

import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ChapterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);

    @Resource
    private ChapterService chapterService;

    @PostMapping(value = "/list",produces = {"application/json;charset=UTF-8"})
    public ResponseDto list(@RequestBody PageDto pageDto){
          LOG.info("pageDto:{}",pageDto);
          ResponseDto responseDto = new ResponseDto();
          chapterService.list(pageDto);
          responseDto.setContent(pageDto);
          return  responseDto;
    }

    @PostMapping(value = "/save",produces = {"application/json;charset=UTF-8"})
    public ResponseDto save(@RequestBody ChapterDto chapterDto){
        LOG.info("chapterDto:{}",chapterDto);
        ResponseDto responseDto = new ResponseDto();
        chapterService.save(chapterDto);
        responseDto.setContent(chapterDto);
        return  responseDto;
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        LOG.info("id:{}",id);
        ResponseDto responseDto = new ResponseDto();
        chapterService.delete(id);
        return  responseDto;
    }
}
