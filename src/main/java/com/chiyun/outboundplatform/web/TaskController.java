package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiPageResult;
import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.CasebasemessageEntity;
import com.chiyun.outboundplatform.entity.TaskEntity;
import com.chiyun.outboundplatform.entity.UserEntity;
import com.chiyun.outboundplatform.repository.TaskRepository;
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
import java.util.Map;

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
    private TaskRepository taskRepository;


    @ApiOperation("区域管理员添加任务添加")
    @RequestMapping("/add")
    @ApiImplicitParam(name = "ajid",value = "案件id",dataType = "int", paramType = "query")
    public ApiResult<Object> add(TaskEntity entity, Integer ajid) {
        if (ajid == null) {
            return ApiResult.FAILURE("案件id不能为空");
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
            // 修改状态-指派
            entity.setRwzt(2);
            // 设置接单方式：2-指派
            entity.setJdfs(2);
        } else {
            // 设置任务状态：1-新建
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
        if (entity.getAjid() == null || entity.getRwfs() == null) {
            return ApiResult.FAILURE("案件id和任务方式不能为空");
        }
        // 判断时间
        Date now = new Date();
        if (now.after(entity.getRwjzsj())) {
            return ApiResult.FAILURE("任务截止时间不能早于当前时间");
        }
        // 如果执行人不为空，则修改任务状态
        if (entity.getRwzxr() != null) {
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
        try {
            taskRepository.deleteById(id);
        } catch (Exception e) {
            return ApiResult.FAILURE("删除任务失败");
        }
        return ApiResult.SUCCESS("删除任务成功");
    }

    @ApiOperation("通过任务id查询")
    @RequestMapping("/findById")
    public ApiResult<Object> findById(Integer id) {
        if (id == null) {
            return ApiResult.FAILURE("id不能为空");
        }
        TaskEntity entity = itaskService.findById(id);
        return ApiResult.SUCCESS(entity);
    }

    @ApiOperation("多条件查询:任务名称、任务截止时间、任务方式、任务状态、任务执行人、任务完成时间")
    @RequestMapping("/findAllByCondition")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rwmc", value = "任务名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginJzsj", value = "任务截止时间的开始时间", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "endJzsj", value = "任务截止时间的结束时间", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "rwfs", value = "任务方式id", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "rwzt", value = "任务状态id", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "rwzxr", value = "任务执行人id", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "beginWcsj", value = "任务完成时间的开始时间", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "endWcsj", value = "任务完成时间的开始时间", dataType = "Date", paramType = "query")
    })
    public ApiResult<Object> findAllByCondition(String rwmc, Date beginJzsj, Date endJzsj,
                                                Integer rwfs, Integer rwzt, Integer shzt, Integer rwzxr,
                                                Date beginWcsj, Date endWcsj, int page, int pagesize) {
        Pageable pageable = PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "id"));
        Page<TaskEntity> list = itaskService.findAllByCondition(rwmc, beginJzsj, endJzsj, rwfs,
                rwzt, shzt, rwzxr, beginWcsj, endWcsj, pageable);
        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
    }

    @ApiOperation("业务员登录查询本区域的任务:新建、指派")
    @RequestMapping("/findAllByYwyqy")
    @ApiImplicitParam(name = "ywyid", value = "业务员id", dataType = "Integer", paramType = "query")
    public ApiResult<Object> findAllByYwyidWjd(Integer ywyid, int page, int pagesize) {
        if (ywyid == null) {
            return ApiResult.FAILURE("业务员id不能为空");
        }
        UserEntity entity = userReposity.findById(ywyid);
        if (entity == null) {
            return ApiResult.FAILURE("该业务员不存在");
        }
        Pageable pageable = PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "id"));
        Page<TaskEntity> list = itaskService.findAllByYwyqy(Integer.parseInt(entity.getSzxzqdm()), pageable);
        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
    }

    @ApiOperation("业务员登录查询本区域已接的任务:接收等")
    @RequestMapping("/findAllByYwyidYjd")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ywyid", value = "业务员id", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "参数1-审核通过 1-审核未通过 3-已接单 4-已完成", dataType = "Integer", paramType = "query")
    })
    public ApiResult<Object> findAllByYwyidYjd(Integer ywyid, Integer flag, int page, int pagesize) {
        if (ywyid == null) {
            return ApiResult.FAILURE("业务员id不能为空");
        }
        Pageable pageable = PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "id"));
        Page<TaskEntity> list = itaskService.findAllByFlag(ywyid, flag, pageable);
        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
    }

    @ApiOperation("业务员接单")
    @RequestMapping("/order")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ywyid", value = "业务员id", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "rwid", value = "任务id", dataType = "Integer", paramType = "query")
    })
    public ApiResult<Object> order(Integer ywyid, Integer rwid) {
        if (ywyid == null || rwid == null) {
            return ApiResult.FAILURE("业务员id不能为空和任务id不能为空");
        }
        TaskEntity entity = itaskService.findById(rwid);
        if (entity == null) {
            return ApiResult.FAILURE("该任务不存在");
        }
        if (entity.getRwzt() != 1 && entity.getRwzt() != 2) {
            return ApiResult.FAILURE("该任务已接单");
        }
        // 接单方式：1-自己接单
        entity.setJdfs(1);
        // 任务状态：3-接单
        entity.setRwzt(3);
        entity.setRwzxr(ywyid);
        try {
            itaskService.save(entity);
        } catch (Exception e) {
            return ApiResult.FAILURE("接单失败");
        }
        return ApiResult.SUCCESS("接单成功");
    }

    @ApiOperation("业务员通过接单方式查询")
    @RequestMapping("/findAllByJdfs")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ywyid", value = "业务员id", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "jdfs", value = "接单方式 1-自己接单 2-管理员派发", dataType = "Integer", paramType = "query")
    })
    public ApiResult<Object> findAllByJdfs(Integer rwzxr, Integer jdfs, int page, int pagesize) {
        Pageable pageable = PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "id"));
        if (rwzxr == null) {
            return ApiResult.FAILURE("业务员id不能为空");
        }
        Page<TaskEntity> list = itaskService.findAllByJdfs(jdfs, rwzxr, pageable);
        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
    }

    @ApiOperation("统计业务员 已接收、已处理案件数、应得佣金及实际佣金")
    @RequestMapping("/countYwyRwxx")
    @ApiImplicitParam(name = "rwzxr", value = "业务员id", dataType = "Integer", paramType = "query")
    public ApiResult<Object> countYwyRwxx(Integer rwzxr) {
        if (rwzxr == null) {
            return ApiResult.FAILURE("业务员id不能为空");
        }
        Map<String, Object> map = itaskService.countYwyRwxx(rwzxr);
        return ApiResult.SUCCESS(map);
    }

    @ApiOperation("区域管理员审核并修改任务信息")
    @RequestMapping("/check")
    public ApiResult<Object> check(Integer id, Integer shzt, String shbz, Double sjyj) {
        if (id == null || shzt == null) {
            return ApiResult.FAILURE("id和审核状态不能为空");
        }
        TaskEntity entity = itaskService.findById(id);
        entity.setRwzt(5);
        entity.setShzt(shzt);
        entity.setShbz(shbz);
        entity.setSjyj(sjyj);
        try {
            itaskService.save(entity);
        } catch (Exception e) {
            return ApiResult.FAILURE("修改失败");
        }
        return ApiResult.SUCCESS("修改成功");
    }


}
