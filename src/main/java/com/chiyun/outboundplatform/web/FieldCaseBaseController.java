package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.BasetypeEntity;
import com.chiyun.outboundplatform.entity.FieldcasebaseEntity;
import com.chiyun.outboundplatform.repository.BasetypeRepository;
import com.chiyun.outboundplatform.repository.FieldCaseBaseRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(description = "基础信息字段管理")
@RestController
@RequestMapping(value = "/fieldCaseBase", method = {RequestMethod.GET, RequestMethod.POST})
public class FieldCaseBaseController {

    @Resource
    private FieldCaseBaseRepository fieldCaseBaseRepository;

    @ApiOperation("通过类型查询所有")
    @RequestMapping("/findAllByJcxxlx")
    public ApiResult<Object> findAllByJcxxlx(@ApiParam(value = "基础信息类型 数字0-9") Integer jcxxlx) {
        if (jcxxlx == null) {
            return ApiResult.FAILURE("字段类型不能为空");
        }
        List<FieldcasebaseEntity> list = fieldCaseBaseRepository.findAllByJcxxlx(jcxxlx);
        return ApiResult.SUCCESS(list);
    }

    @ApiOperation("查询所有")
    @RequestMapping("/findAll")
    public ApiResult<Object> findAll() {
        List<FieldcasebaseEntity> list = fieldCaseBaseRepository.findAll();
        return ApiResult.SUCCESS(list);
    }
}
