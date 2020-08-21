package com.course.business.controller.admin;

import com.course.server.dto.SmsDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.SmsService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/sms")
public class SmsController {

    private static final Logger LOG = LoggerFactory.getLogger(SmsController.class);
    public static final String BUSINESS_NAME="短信验证";


    @Resource
    private SmsService smsService;

    /**
     * 列表查询
     */
    @PostMapping(value = "/list",produces = {"application/json;charset=UTF-8"})
    public ResponseDto list(@RequestBody PageDto pageDto){
          ResponseDto responseDto = new ResponseDto();
          smsService.list(pageDto);
          responseDto.setContent(pageDto);
          return  responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping(value = "/save",produces = {"application/json;charset=UTF-8"})
    public ResponseDto save(@RequestBody SmsDto smsDto){
        // 保存校验
                    ValidatorUtil.require(smsDto.getMobile(), "手机号");
                    ValidatorUtil.length(smsDto.getMobile(), "手机号", 1, 50);
                    ValidatorUtil.require(smsDto.getCode(), "验证码");
                    ValidatorUtil.require(smsDto.getUse(), "用途");
                    ValidatorUtil.require(smsDto.getAt(), "生成时间");
                    ValidatorUtil.require(smsDto.getStatus(), "状态");

        ResponseDto responseDto = new ResponseDto();
        smsService.save(smsDto);
        responseDto.setContent(smsDto);
        return  responseDto;
    }

    /**
     *删除
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        ResponseDto responseDto = new ResponseDto();
        smsService.delete(id);
        return  responseDto;
    }
}
