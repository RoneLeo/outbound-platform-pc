package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.BasetypeEntity;
import com.chiyun.outboundplatform.entity.FieldcasebaseEntity;
import com.chiyun.outboundplatform.repository.BasetypeRepository;
import com.chiyun.outboundplatform.repository.FieldCaseBaseRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "基础信息字段管理")
@RestController
@RequestMapping(value = "/fieldCaseBase", method = {RequestMethod.GET, RequestMethod.POST})
public class FieldCaseBaseController {

    @Resource
    private FieldCaseBaseRepository fieldCaseBaseRepository;

    @ApiOperation("通过类型查询所有")
    @RequestMapping("/findAllByJcxxlx")
    @ApiImplicitParam(name = "jcxxlx",value = "基础信息类型 0-9",dataType = "Integer", paramType = "query")
    public ApiResult<Object> findAllByJcxxlx(Integer jcxxlx) {
        if (jcxxlx == null) {
            return ApiResult.FAILURE("字段类型不能为空");
        }
        List<FieldcasebaseEntity> list = fieldCaseBaseRepository.findAllByJcxxlx(jcxxlx);
        return ApiResult.SUCCESS(list);
    }

    @ApiOperation("查询所有")
    @RequestMapping("/findAll")
    public ApiResult<Object> findAll() {
        List<List<Map<String, Object>>> result = new ArrayList<>();
        for (int jcxxlx = 0; jcxxlx < 10; jcxxlx ++) {
            List<FieldcasebaseEntity> list = fieldCaseBaseRepository.findAllByJcxxlx(jcxxlx);
            List<Map<String, Object>> list1 = new ArrayList<>();
            for (FieldcasebaseEntity entity : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", entity.getId());
                map.put("name", entity.getZdzwmc());
                list1.add(map);
            }
            result.add(list1);
        }
        return ApiResult.SUCCESS(result);
    }
}
