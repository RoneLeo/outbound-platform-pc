package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiPageResult;
import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.CasebasemessageAllEntity;
import com.chiyun.outboundplatform.entity.CasebasemessageEntity;
import com.chiyun.outboundplatform.entity.TaskEntity;
import com.chiyun.outboundplatform.entity.UserEntity;
import com.chiyun.outboundplatform.repository.BatchRepository;
import com.chiyun.outboundplatform.repository.CasebasemessageRepository;
import com.chiyun.outboundplatform.repository.TaskRepository;
import com.chiyun.outboundplatform.repository.UserReposity;
import com.chiyun.outboundplatform.service.IcaseBaseService;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.utils.StringUtil;
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
import java.util.List;
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
    private TaskRepository taskRepository;
    @Resource
    private UserReposity userReposity;

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

//    @ApiOperation("通过案件id查询任务")
//    @RequestMapping("/findAllTaskByAjid")
//    public ApiResult<Object> findAllByAjidOrderByRwcjsj(Integer ajid, int page, int pagesize) {
//        if (ajid == null) {
//            return ApiResult.FAILURE("案件id不能为空");
//        }
//        Pageable pageable = PageRequest.of(page - 1, pagesize);
//        Page<TaskEntity> list = taskRepository.findAllByAjidOrderByRwcjsj(ajid, pageable);
//        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
//    }

    @ApiOperation("通过案件id和任务状态查询任务")
    @RequestMapping("/findAllTaskByAjidAndRwzt")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ajid", value = "案件id", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "rwzt", value = "任务状态", dataType = "Integer", paramType = "query"),
    })
    public ApiResult<Object> findAllByAjidOrderByRwcjsj(Integer ajid, Integer rwzt, int page, int pagesize) {
        if (ajid == null) {
            return ApiResult.FAILURE("案件id不能为空");
        }
        Pageable pageable = PageRequest.of(page - 1, pagesize);
        Page<TaskEntity> list = null;
        if (rwzt == null) {
            list = taskRepository.findAllByAjidOrderByRwcjsj(ajid, pageable);
        } else {
            list = taskRepository.findAllByAjidAndRwztOrderByRwcjsj(ajid, rwzt, pageable);
        }
        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
    }

    @ApiOperation("修改")
    @RequestMapping("/update")
    public ApiResult<Object> update(CasebasemessageEntity entity) {
        if (entity.getPcid() == null || entity.getAjlx() == null || entity.getAjqy() == null || entity.getAjzt() == null) {
            return ApiResult.FAILURE("批次id、案件类型、案件区域、案件状态不能为空");
        }
        try {
            casebasemessageRepository.save(entity);
        } catch (Exception e) {
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
                                                Date begin, Date end, int page, int pagesize, HttpSession session) {
        // 判断权限，如果是区域管理员，则只能查询本区域
        Integer id = (Integer) session.getAttribute("id");
        UserEntity userEntity = userReposity.findById(id);
        Pageable pageable = PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "id"));
        Page<CasebasemessageEntity> list = null;
        if (StringUtil.isNull(pcid) && StringUtil.isNull(ajmc) && ajlx == null &&
                ajzt == null && ajqy == null && begin == null && end == null) {
            list = casebasemessageRepository.findAllByYhid(id, pageable);
        } else {
            if (userEntity.getJs() == 3) {
                // 区域管理员
                ajqy = Integer.parseInt(userEntity.getSzxzqdm());
            }
            list = icaseBaseService.findAllByCondition(pcid, ajmc, ajlx, ajzt, ajqy, begin, end, pageable);
        }
        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
    }



    @ApiOperation("登录用户查询本区域案件")
    @RequestMapping("/findAllByAjqy")
    public ApiResult<Object> findAllByAjqy(HttpSession session, int page, int pagesize) {
        Integer id = (Integer) session.getAttribute("id");
        if (id == null) {
            return ApiResult.FAILURE("未登录");
        }
        Pageable pageable = PageRequest.of(page - 1, pagesize);
        Page<CasebasemessageEntity> list = casebasemessageRepository.findAllByYhid(id, pageable);
        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
    }

    @ApiOperation("管理员确认案件完成状态")
    @RequestMapping("/check")
    public ApiResult<Object> check(Integer id) {
        if (id == null) {
            return ApiResult.FAILURE("id不能为空");
        }
        CasebasemessageEntity entity = casebasemessageRepository.findById(id).get();
        if (taskRepository.findAllByAjid(entity.getId()).size() < 0) {
            return ApiResult.FAILURE("该案件还未添加任务");
        }
        // 通过案件id查询任务是否都已完成
        List<Integer> list = taskRepository.findAllIdByAjidAndAjztIn(entity.getId());
        if (list.size() > 0) {
            return ApiResult.FAILURE("该案件还有未完成的任务");
        }
        // 都完成
        entity.setAjzt(3);
        try {
            casebasemessageRepository.save(entity);
        } catch (Exception e) {
            return ApiResult.FAILURE("确认案件完成失败");
        }
        return ApiResult.FAILURE("确认案件完成成功");
    }

}
