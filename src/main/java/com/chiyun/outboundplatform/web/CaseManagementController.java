package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.BasetypeEntity;
import com.chiyun.outboundplatform.entity.CasemanagementEntity;
import com.chiyun.outboundplatform.repository.BasetypeRepository;
import com.chiyun.outboundplatform.repository.CaseManagementRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(description = "案件管理")
@RestController
@RequestMapping(value = "/caseManagement", method = {RequestMethod.GET, RequestMethod.POST})
public class CaseManagementController {

    @Resource
    private CaseManagementRepository caseManagementRepository;

    @ApiOperation("添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(CasemanagementEntity entity) {
        CasemanagementEntity entity1 = caseManagementRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("添加失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("查询所有")
    @RequestMapping("/findAll")
    public ApiResult<Object> findAll() {
        List<CasemanagementEntity> list = caseManagementRepository.findAll();
        return ApiResult.SUCCESS(list);
    }
}
