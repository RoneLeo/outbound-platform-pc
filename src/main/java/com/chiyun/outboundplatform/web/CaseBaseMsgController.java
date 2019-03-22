package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.CasebasemessageAllEntity;
import com.chiyun.outboundplatform.entity.CasebasemessageEntity;
import com.chiyun.outboundplatform.service.IcaseBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by wazto on 2019/3/22.
 */
@RestController
@RequestMapping(value = "/casebase", method = {RequestMethod.POST, RequestMethod.GET})
@Api(description = "案件基本信息")
public class CaseBaseMsgController {
    @Resource
    private IcaseBaseService icaseBaseService;

    @RequestMapping("/findAllInfoById")
    @ApiOperation("根据id获取案件关联信息")
    public ApiResult<Object> findAllInfoById(@RequestParam Integer id, HttpSession session) {
        CasebasemessageAllEntity entity = icaseBaseService.findAllInfoById(id);
        if (entity == null)
            return ApiResult.FAILURE("不存在的数据");
        return ApiResult.SUCCESS(entity);
    }

    @RequestMapping("/findInfoById")
    @ApiOperation("根据id获取案件基本信息")
    public ApiResult<Object> findInfoById(@RequestParam Integer id, HttpSession session) {
        CasebasemessageEntity entity = icaseBaseService.findInfoById(id);
        if (entity == null)
            return ApiResult.FAILURE("不存在的数据");
        return ApiResult.SUCCESS(entity);
    }

    @RequestMapping("/findAllByPage")
    @ApiOperation("分页获取案件信息")
    public ApiResult<Object> findAllByPage(@RequestParam int page, @RequestParam int pagesize, HttpSession session) {
        Pageable pageable = PageRequest.of(page, pagesize);
        Page<CasebasemessageEntity> list = icaseBaseService.findAllByPage(pageable);
        return ApiResult.SUCCESS(list);
    }

    @RequestMapping("/findAllByPchAndPage")
    @ApiOperation("根据批次号 分页获取案件信息")
    public ApiResult<Object> findAllByPchAndPage(@RequestParam String pch, @RequestParam int page, @RequestParam int pagesize, HttpSession session) {
        Pageable pageable = PageRequest.of(page, pagesize);
        Page<CasebasemessageEntity> list = icaseBaseService.findAllByPchAndPage(pch, pageable);
        return ApiResult.SUCCESS(list);
    }
}
