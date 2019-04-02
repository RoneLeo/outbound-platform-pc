package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiPageResult;
import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.CasebasemessageAllEntity;
import com.chiyun.outboundplatform.entity.CasebasemessageEntity;
import com.chiyun.outboundplatform.repository.BatchRepository;
import com.chiyun.outboundplatform.repository.CasebasemessageRepository;
import com.chiyun.outboundplatform.service.IcaseBaseService;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Optional;

/**
 * Created by wazto on 2019/3/22.
 */
@RestController
@RequestMapping(value = "/casebase", method = {RequestMethod.POST, RequestMethod.GET})
@Api(description = "案件基本信息")
public class CaseBaseMsgController {
    @Resource
    private IcaseBaseService icaseBaseService;
    @Resource
    private CasebasemessageRepository casebasemessageRepository;
    @Resource
    private BatchRepository batchRepository;
    @Resource
    private IdictionaryListService idictionaryListService;

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
    public ApiResult<Object> findAllByPage(@RequestParam @ApiParam("页数,需大于1") int page, @RequestParam int pagesize, HttpSession session) {
        Pageable pageable = PageRequest.of(page - 1, pagesize);
        Page<CasebasemessageEntity> list = icaseBaseService.findAllByPage(pageable);
        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
    }

//    @RequestMapping("/findAllByPchAndPage")
//    @ApiOperation("根据批次id 分页获取案件信息")
//    @ApiImplicitParam(name = "pcid",value = "批次id",dataType = "String", paramType = "query")
//    public ApiResult<Object> findAllByPchAndPage(String pcid, int page, int pagesize, HttpSession session) {
//        Pageable pageable = PageRequest.of(page - 1, pagesize);
//        Page<CasebasemessageEntity> list = icaseBaseService.findAllByPcidAndPage(pcid, pageable);
//        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
//    }

    @ApiOperation("修改")
    @RequestMapping("/update")
    public ApiResult<Object> update(CasebasemessageEntity entity) {
        if (casebasemessageRepository.findById(entity.getId()) == null) {
            return ApiResult.FAILURE("该案件不存在");
        }
        if (entity.getPcid() == null || entity.getAjlx() == null || entity.getAjqy() == null || entity.getAjzt() == null) {
            return ApiResult.FAILURE("批次id、案件类型、案件区域、案件状态不能为空");
        }
        // 批次id
        if (batchRepository.findAllByPcid(entity.getPcid()).size() < 1) {
            return ApiResult.FAILURE("该批次id不存在");
        }
        // 案件类型
        if (idictionaryListService.findById(entity.getAjlx()) == null) {
            return ApiResult.FAILURE("该案件类型不存在");
        }
        // 案件区域
        if (idictionaryListService.findById(entity.getAjqy()) == null) {
            return ApiResult.FAILURE("该区域不存在");
        }
        // 案件状态
        if (idictionaryListService.findById(entity.getAjzt()) == null) {
            return ApiResult.FAILURE("该状态不存在");
        }

        CasebasemessageEntity entity1 = casebasemessageRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("修改失败");
        }
        return ApiResult.SUCCESS("修改成功");
    }

    @ApiOperation("删除")
    @RequestMapping("/delete")
    public ApiResult<Object> delete(Integer id) {
        if (id == null) {
            return ApiResult.FAILURE("id不能为空");
        }
        if (!casebasemessageRepository.existsById(id)) {
            return ApiResult.FAILURE("该数据不存在");
        }
        try {
            icaseBaseService.reset(id);
        } catch (Exception e) {
            return ApiResult.FAILURE("删除失败");
        }
        return ApiResult.SUCCESS("删除成功");
    }

//    @ApiOperation("根据区域查询")
//    @RequestMapping("/findAllByAjqy")
//    @ApiImplicitParam(name = "ajqy",value = "案件区域id",dataType = "Integer", paramType = "query")
//    public ApiResult<Object> findAllByAjqy(Integer ajqy, int page, int pagesize) {
//        if (ajqy == null) {
//            return ApiResult.FAILURE("未选择案件区域");
//        }
//        if (idictionaryListService.findById(ajqy) == null) {
//            return ApiResult.FAILURE("该案件区域不存在");
//        }
//        Pageable pageable = PageRequest.of(page - 1, pagesize, Sort.by(new Sort.Order(Sort.Direction.DESC, "id")));
//        Page<CasebasemessageEntity> list = casebasemessageRepository.findAllByAjqy(ajqy, pageable);
//        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
//    }

    @ApiOperation("修改案件状态")
    @RequestMapping("/updateAjzt")
    @ApiImplicitParam(name = "ajzt",value = "案件状态id",dataType = "Integer", paramType = "query")
    public ApiResult<Object> updateAjzt(Integer id, Integer ajzt) {
        if (id == null || ajzt == null) {
            return ApiResult.FAILURE("id和案件状态不能为空");
        }
        Optional<CasebasemessageEntity> optional = casebasemessageRepository.findById(id);
        if (!optional.isPresent()) {
            return ApiResult.FAILURE("该数据不存在");
        }
        if (idictionaryListService.findById(ajzt) == null) {
            return ApiResult.FAILURE("该案件状态不存在");
        }
        optional.get().setAjzt(ajzt);
        casebasemessageRepository.save(optional.get());
        return ApiResult.SUCCESS("修改成功");
    }

    @ApiOperation("多条件查询：批次id、案件名称、案件类型、案件状态、案件区域、导入时间")
    @RequestMapping("/findAllByCondition")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pcid", value = "批次id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ajmc", value = "案件名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ajlx", value = "案件类型", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "ajzt", value = "案件状态", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "ajqy", value = "案件区域", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "begin", value = "导入开始时间", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "end", value = "导入截止时间", dataType = "Date", paramType = "query")
    })
    public ApiResult<Object> findAllByCondition(String pcid, String ajmc,
                                                Integer ajlx, Integer ajzt, Integer ajqy,
                                                Date begin, Date end, int page, int pagesize) {
        Pageable pageable = PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "id"));
        Page<CasebasemessageEntity> list = icaseBaseService.findAllByCondition(pcid, ajmc, ajlx, ajzt, ajqy, begin, end, pageable);
        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
    }


}
