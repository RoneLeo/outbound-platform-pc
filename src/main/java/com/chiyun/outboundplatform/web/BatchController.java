package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.BatchEntity;
import com.chiyun.outboundplatform.entity.CasemanagementEntity;
import com.chiyun.outboundplatform.repository.BatchRepository;
import com.chiyun.outboundplatform.repository.CaseManagementRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(description = "批次管理")
@RestController
@RequestMapping(value = "/batch", method = {RequestMethod.GET, RequestMethod.POST})
public class BatchController {

    @Resource
    private BatchRepository batchRepository;

    @ApiOperation("添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(BatchEntity entity) {
        BatchEntity entity1 = batchRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("添加失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("查询所有")
    @RequestMapping("/findAll")
    public ApiResult<Object> findAll() {
        List<BatchEntity> list = batchRepository.findAll();
        return ApiResult.SUCCESS(list);
    }
}
