package com.course.system.controller.admin;

import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.RoleService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/role")
public class RoleController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);
    public static final String BUSINESS_NAME="角色";


    @Resource
    private RoleService roleService;

    /**
     * 列表查询
     */
    @PostMapping(value = "/list",produces = {"application/json;charset=UTF-8"})
    public ResponseDto list(@RequestBody PageDto pageDto){
          ResponseDto responseDto = new ResponseDto();
          roleService.list(pageDto);
          responseDto.setContent(pageDto);
          return  responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping(value = "/save",produces = {"application/json;charset=UTF-8"})
    public ResponseDto save(@RequestBody RoleDto roleDto){
        // 保存校验
                    ValidatorUtil.require(roleDto.getName(), "角色");
                    ValidatorUtil.length(roleDto.getName(), "角色", 1, 50);
                    ValidatorUtil.require(roleDto.getDesc(), "描述");
                    ValidatorUtil.length(roleDto.getDesc(), "描述", 1, 100);

        ResponseDto responseDto = new ResponseDto();
        roleService.save(roleDto);
        responseDto.setContent(roleDto);
        return  responseDto;
    }

    /**
     *删除
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        ResponseDto responseDto = new ResponseDto();
        roleService.delete(id);
        return  responseDto;
    }


    /**
     * 保存资源
     * @param roleDto
     */
    @PostMapping("/save-resource")
    public ResponseDto saveResource(@RequestBody RoleDto roleDto) {
        LOG.info("保存角色资源关联开始");
        ResponseDto<RoleDto> responseDto = new ResponseDto<>();
        roleService.saveResource(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }
}
