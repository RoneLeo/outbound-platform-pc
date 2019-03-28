package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiPageResult;
import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.CasebasemessageEntity;
import com.chiyun.outboundplatform.entity.TaskEntity;
import com.chiyun.outboundplatform.repository.TaskRepository;
import com.chiyun.outboundplatform.repository.TaskstateRepository;
import com.chiyun.outboundplatform.repository.UserReposity;
import com.chiyun.outboundplatform.service.IcaseBaseService;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.service.ItaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@Api(description = "任务管理")
@RequestMapping(value = "/task", method = {RequestMethod.POST, RequestMethod.GET})
@RestController
public class TaskController {

    @Resource
    private ItaskService itaskService;
    @Resource
    private IcaseBaseService icaseBaseService;
    @Resource
    private IdictionaryListService idictionaryListService;
    @Resource
    private UserReposity userReposity;
    @Resource
    private TaskstateRepository taskstateRepository;
    @Resource
    private TaskRepository taskRepository;


    @ApiOperation("添加")
    @RequestMapping("/add")
    @ApiImplicitParam(name = "ajid",value = "案件id",dataType = "Integer", paramType = "query")
    public ApiResult<Object> add(TaskEntity entity, Integer ajid) {
        if (ajid == null) {
            return ApiResult.FAILURE("案件id不能为空");
        }
        CasebasemessageEntity casebasemessageEntity = icaseBaseService.findInfoById(ajid);
        if (casebasemessageEntity == null) {
            return ApiResult.FAILURE("该案件数据不存在");
        }
        // 判断任务方式
        if (idictionaryListService.findById(entity.getRwfs()) == null) {
            return ApiResult.FAILURE("该任务方式不存在");
        }
        // 判断时间
        Date now = new Date();
        if (now.after(entity.getRwjzsj())) {
            return ApiResult.FAILURE("任务截止时间不能早于当前时间");
        }
        // 如果执行人不为空，则将任务状态改为指派
        if (entity.getRwzxr() != null) {
            if (userReposity.findById(entity.getRwzxr()) == null) {
                return ApiResult.FAILURE("该执行人不存在");
            }
            // 修改状态
            entity.setRwzt(2);
        } else {
            entity.setRwzt(1);
        }
        try {
            itaskService.save(entity);
        } catch (Exception e) {
            return ApiResult.FAILURE("添加任务失败");
        }
        return ApiResult.SUCCESS("添加成功");
    }

    @ApiOperation("修改")
    @RequestMapping("/update")
    public ApiResult<Object> update(TaskEntity entity) {
        if (taskstateRepository.findById(entity.getId()) == null) {
            return ApiResult.FAILURE("该数据不存在");
        }
        if (entity.getAjid() == null || entity.getRwfs() == null)
        if (entity.getAjid() == null) {
            return ApiResult.FAILURE("案件id不能为空");
        }
        if (icaseBaseService.findInfoById(entity.getAjid()) == null) {
            return ApiResult.FAILURE("该案件数据不存在");
        }
        // 判断任务方式
        if (idictionaryListService.findById(entity.getRwfs()) == null) {
            return ApiResult.FAILURE("该任务方式不存在");
        }
        // 判断时间
        Date now = new Date();
        if (now.after(entity.getRwjzsj())) {
            return ApiResult.FAILURE("任务截止时间不能早于当前时间");
        }
        // 如果执行人不为空，则修改任务状态
        if (entity.getRwzxr() != null) {
            if (userReposity.findById(entity.getRwzxr()) == null) {
                return ApiResult.FAILURE("该执行人不存在");
            }
            // 修改状态
            if (entity.getRwzt() == null) {
                entity.setRwzt(1);
            } else {
                entity.setRwzt(entity.getRwzt());
            }
        } else {
            entity.setRwzt(1);
        }
        try {
            itaskService.save(entity);
        } catch (Exception e) {
            return ApiResult.FAILURE("修改任务失败");
        }
        return ApiResult.SUCCESS("修改任务成功");
    }

    @ApiOperation("删除")
    @RequestMapping("/delete")
    public ApiResult<Object> delete(Integer id) {
        if (id == null) {
            return ApiResult.FAILURE("id不能为空");
        }
        if (itaskService.findById(id) == null) {
            return ApiResult.FAILURE("该数据不存在");
        }
        try {
            taskRepository.deleteById(id);
        } catch (Exception e) {
            return ApiResult.FAILURE("删除任务失败");
        }
        return ApiResult.SUCCESS("删除任务成功");
    }

//    @ApiOperation("修改任务状态")
//    @RequestMapping("/updateZt")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "dqZt", value = "当前状态", dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "sxZt", value = "要修改成的状态", dataType = "Integer", paramType = "query")
//    })
//    public ApiResult<Object> updateZt(Integer id, Integer dqZt, Integer sxZt) {
//        if (id == null) {
//            return ApiResult.FAILURE("id不能为空");
//        }
//        if (dqZt == null || sxZt == null) {
//            return ApiResult.FAILURE("参数不能为空");
//        }
//        if (taskstateRepository.findById(dqZt) == null || taskstateRepository.findById(sxZt) == null) {
//            return ApiResult.FAILURE("该状态不存在");
//        }
//        TaskEntity entity = itaskService.findById(id);
//        if (entity == null) {
//            return ApiResult.FAILURE("该条数据不存在");
//        }
//        switch (dqZt) {
//            case 1:
//                entity.setRwzt(sxZt);
//                break;
//            case 2:
//
//                break;
//            case 3:
//
//                break;
//            case 4:
//
//                break;
//        }
//        return ApiResult.SUCCESS();
//    }

    @ApiOperation("修改任务完成状态")
    @RequestMapping("/updateWczt")
    @ApiImplicitParam(name = "rwzxr", value = "任务执行人id", dataType = "Integer", paramType = "query")
    public ApiResult<Object> updateWczt(Integer id, Integer rwzxr) {
        if (id == null) {
            return ApiResult.FAILURE("id不能为空");
        }
        TaskEntity entity = itaskService.findById(id);
        if (entity == null) {
            return ApiResult.FAILURE("该任务不存在");
        }
        if (rwzxr != null) {
            if (userReposity.findById(id) == null) {
                return ApiResult.FAILURE("该任务执行人不存在");
            }
            entity.setRwzxr(rwzxr);
        }
        entity.setRwzt(4);
        entity.setRwwcsj(new Date());
        try {
            itaskService.save(entity);
        } catch (Exception e) {
            return ApiResult.FAILURE("修改失败");
        }
        return ApiResult.SUCCESS("修改成功");
    }

    @ApiOperation("多条件查询:任务名称、任务截止时间、任务方式、任务状态、任务执行人、任务完成时间")
    @RequestMapping("/findAllByCondition")
    public ApiResult<Object> findAllByCondition(String rwmc, Date beginJzsj, Date endJzsj,
                                                Integer rwfs, Integer rwzt, Integer rwzxr,
                                                Date beginWcsj, Date endWcsj, int page, int pagesize) {
        Pageable pageable = PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "id"));
        Page<TaskEntity> list = itaskService.findAllByCondition(rwmc, beginJzsj, endJzsj, rwfs,
                rwzt, rwzxr, beginWcsj, endWcsj, pageable);
        return ApiPageResult.SUCCESS(list, page, pagesize, list.getTotalElements(), list.getTotalPages());
    }

    @ApiOperation("查询所有任务状态")
    @RequestMapping("/findAllRwzt")
    public ApiResult<Object> findAllRwzt() {
        return ApiResult.SUCCESS(taskstateRepository.findAll());
    }
}
