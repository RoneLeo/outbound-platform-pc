package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.CasebasemessageEntity;
import com.chiyun.outboundplatform.entity.TaskEntity;
import com.chiyun.outboundplatform.repository.TaskstateRepository;
import com.chiyun.outboundplatform.repository.UserReposity;
import com.chiyun.outboundplatform.service.IcaseBaseService;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.service.ItaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
        TaskEntity entity1 = itaskService.save(entity);
        if (entity1 == null) {
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
        TaskEntity entity1 = itaskService.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("修改任务失败");
        }
        return ApiResult.SUCCESS("修改成功");
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
        int result = itaskService.deleteById(id);
        if (result < 1) {
            return ApiResult.FAILURE("删除失败");
        }
        return ApiResult.SUCCESS("删除成功");
    }




    @ApiOperation("查询所有任务状态")
    @RequestMapping("/findAllRwzt")
    public ApiResult<Object> findAllRwzt() {
        return ApiResult.SUCCESS(taskstateRepository.findAll());
    }
}
