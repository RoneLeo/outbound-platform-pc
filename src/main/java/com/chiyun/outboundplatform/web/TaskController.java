package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiPageResult;
import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.CasebasemessageEntity;
import com.chiyun.outboundplatform.entity.CasepeoplemessageEntity;
import com.chiyun.outboundplatform.entity.TaskEntity;
import com.chiyun.outboundplatform.entity.UserEntity;
import com.chiyun.outboundplatform.repository.CasebasemessageRepository;
import com.chiyun.outboundplatform.repository.CasepeoplemessageRepository;
import com.chiyun.outboundplatform.repository.TaskRepository;
import com.chiyun.outboundplatform.repository.UserReposity;
import com.chiyun.outboundplatform.service.IcaseBaseService;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.service.ItaskService;
import com.chiyun.outboundplatform.utils.StringUtil;
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
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(description = "任务管理")
@RequestMapping(value = "/task", method = {RequestMethod.POST, RequestMethod.GET})
@RestController
public class TaskController {

    @Resource
    private ItaskService itaskService;
    @Resource
    private UserReposity userReposity;
    @Resource
    private TaskRepository taskRepository;
    @Resource
    private CasepeoplemessageRepository casepeoplemessageRepository;
    @Resource
    private CasebasemessageRepository casebasemessageRepository;


    @ApiOperation("区域管理员添加任务添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(TaskEntity entity) {
        if (entity.getAjid() == null) {
            return ApiResult.FAILURE("案件id不能为空");
        }
        // 判断时间
        Date now = new Date();
        if (now.after(entity.getRwjzsj())) {
            return ApiResult.FAILURE("任务截止时间不能早于当前时间");
        }
        entity.setRwzt(1);
        entity.setRwcjsj(now);
        entity.setGxsj(now);
        // 判断任务佣金是否超过案件佣金
        double ajyj = casebasemessageRepository.findById(entity.getAjid()).get().getAjyj();
        if (taskRepository.findAllByAjid(entity.getAjid()).size() > 0) {
            // 任务总佣金
            double rwzyj = taskRepository.sumAllRwyjByAjid(entity.getAjid()) + entity.getRwyj();
            if (ajyj < rwzyj) {
                return ApiResult.FAILURE("任务总佣金已超过案件佣金，添加失败");
            }
            entity.setRwyj(StringUtil.getMoneyDouble(entity.getRwyj()));
        }
        //
        if (entity.getRwzxr() != null) {
            entity.setRwzxrmc(userReposity.findById(entity.getRwzxr()).getMz());
            entity.setRwzt(2);
        }
        // 将其他数据设置为null

        try {
            itaskService.save(entity);
        } catch (Exception e) {
            return ApiResult.FAILURE("添加任务失败");
        }
        CasebasemessageEntity entity1 = casebasemessageRepository.findById(entity.getAjid()).get();
        if (entity1.getAjzt() != 2) {
            entity1.setAjzt(2);
            try {
                casebasemessageRepository.save(entity1);
            } catch (Exception e) {
                return ApiResult.FAILURE("修改案件状态失败");
            }
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
        // 判断任务佣金是否超过案件佣金
        double ajyj = casebasemessageRepository.findById(entity.getAjid()).get().getAjyj();
        if (taskRepository.findAllByAjid(entity.getAjid()).size() > 0) {
            // 任务总佣金
            double rwzyj = taskRepository.sumAllRwyjByAjid(entity.getAjid()) + entity.getRwyj();
            if (ajyj < rwzyj) {
                return ApiResult.FAILURE("任务总佣金已超过案件佣金，修改失败");
            }
            entity.setRwyj(StringUtil.getMoneyDouble(entity.getRwyj()));
        }
        if (entity.getRwzxr() != null) {
            entity.setRwzxrmc(userReposity.findById(entity.getRwzxr()).getMz());
        }
        entity.setGxsj(now);
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
            @ApiImplicitParam(name = "rwzxrmc", value = "任务执行人名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginWcsj", value = "任务完成时间的开始时间", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "endWcsj", value = "任务完成时间的结束时间", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "beginCjsj", value = "任务创建的开始时间", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "endCjsj", value = "任务创建的结束时间", dataType = "Date", paramType = "query")
    })
    public ApiResult<Object> findAllByCondition(String rwmc, Date beginJzsj, Date endJzsj,
                                                Integer rwfs, Integer rwzt, String rwzxrmc,
                                                Date beginWcsj, Date endWcsj, Date beginCjsj, Date endCjsj, int page, int pagesize) {
        Pageable pageable = PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "id"));
        Page<TaskEntity> list = itaskService.findAllByCondition(rwmc, beginJzsj, endJzsj, rwfs,
                rwzt, rwzxrmc, beginWcsj, endWcsj, beginCjsj, endCjsj, pageable);
        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
    }

    @ApiOperation("业务员登录查询本区域的任务:新建、指派")
    @RequestMapping("/findAllByYwyqy")
    @ApiImplicitParam(name = "ywyid", value = "业务员id", dataType = "Integer", paramType = "query")
    public ApiResult<Object> findAllByYwyidWjd(Integer ywyid, int page, int pagesize) {
        if (ywyid == null) {
            return ApiResult.FAILURE("业务员id不能为空");
        }
        Pageable pageable = PageRequest.of(page - 1, pagesize);
        Page<TaskEntity> list = taskRepository.findAllByRwzxrOrderByRwztDesc(ywyid, pageable);
        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
    }

    @ApiOperation("业务员登录查询本区域已接的任务:接收等")
    @RequestMapping("/findAllByYwyidYjd")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ywyid", value = "业务员id", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "rwzt", value = "任务状态 3-已接单 4-已完成，待审核 5-审核未通过 6-审核通过 7-佣金发放", dataType = "Integer", paramType = "query")
    })
    public ApiResult<Object> findAllByYwyidYjd(Integer ywyid, Integer rwzt, int page, int pagesize) {
        if (ywyid == null) {
            return ApiResult.FAILURE("业务员id不能为空");
        }
        Pageable pageable = PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "gxsj"));
        Page<TaskEntity> list = taskRepository.findAllByRwzxrAndRwztOrderByGxsjDesc(ywyid, rwzt, pageable);
        // 查询案件的案人信息
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (TaskEntity entity : list) {
            CasepeoplemessageEntity entity1 = casepeoplemessageRepository.findByAjid(entity.getAjid());
            Map<String, Object> map = new HashMap<>();
            map.put("rwxx", entity);
            map.put("arxx", entity1);
            mapList.add(map);
        }
        return ApiPageResult.SUCCESS(mapList, page, pagesize, list.getTotalElements(), list.getTotalPages());
    }

    @ApiOperation("业务员单框查询")
    @RequestMapping("/findAllByYwyqyAndCondition")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ywyid", value = "业务员id", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "param", value = "参数", dataType = "Integer", paramType = "query")
    })
    public ApiResult<Object> findAllByYwyqyAndCondition(Integer ywyid, String param, int page, int pagesize) {
        if (ywyid == null) {
            return ApiResult.FAILURE("业务员id不能为空");
        }
        if (StringUtil.isNull(param)) {
            param = "%%";
        } else {
            param = "%" + param + "%";
        }
        Pageable pageable = PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "update_time"));
        Page<TaskEntity> list = taskRepository.findAllByCondition(param, ywyid, pageable);
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
        // 任务状态：3-接单
        entity.setRwzt(3);
        entity.setRwzxr(ywyid);
        //
        entity.setRwzxrmc(userReposity.findById(entity.getRwzxr()).getMz());
        //
        entity.setGxsj(new Date());
        try {
            itaskService.save(entity);
        } catch (Exception e) {
            return ApiResult.FAILURE("接单失败");
        }
        return ApiResult.SUCCESS("接单成功");
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

    @ApiOperation("区域管理员指派或改派任务")
    @RequestMapping("/appoint")
    @ApiImplicitParam(name = "ywyid", value = "业务员id", dataType = "Integer", paramType = "query")
    public ApiResult<Object> appoint(Integer ywyid, Integer id) {
        if (id == null || ywyid == null) {
            return ApiResult.FAILURE("id和业务员id不能为空");
        }
        TaskEntity entity = taskRepository.findById(id).get();
        if (entity.getRwzt() != 1 && entity.getRwzt() != 2) {
            return ApiResult.FAILURE("该任务不能指派或改派");
        }
        entity.setRwzxr(ywyid);
        entity.setRwzt(2);
        entity.setRwzxrmc(userReposity.findById(entity.getRwzxr()).getMz());
        entity.setGxsj(new Date());
        try {
            taskRepository.save(entity);
        } catch (Exception e) {
            return ApiResult.FAILURE("操作失败");
        }
        return ApiResult.SUCCESS("操作成功");
    }


    @ApiOperation("区域管理员审核并修改任务信息和反馈状态")
    @RequestMapping("/check")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "rwzt", value = "任务状态  6-审核通过", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "shbz", value = "审核备注", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sjyj", value = "实际佣金", dataType = "Double", paramType = "query")
    })
    public ApiResult<Object> check(Integer id, String shbz, Double sjyj, HttpSession session) {
        Integer userid = (Integer) session.getAttribute("id");
        if (id == null) {
            return ApiResult.FAILURE("id不能为空");
        }
        TaskEntity entity = itaskService.findById(id);
        if (entity.getRwzt() != 4) {
            return ApiResult.FAILURE("该任务不是待审核任务，不能审核");
        }
        entity.setRwzt(6);
        entity.setShbz(shbz);
        // 判断实际佣金
        if (sjyj > entity.getRwyj()) {
            return ApiResult.FAILURE("任务实际所得佣金不应大于任务佣金");
        }
        entity.setSjyj(StringUtil.getMoneyDouble(sjyj));
        //
        entity.setShrid(userid);
        entity.setShrxm(userReposity.findById(userid).getMz());
        //
        entity.setGxsj(new Date());
        try {
            itaskService.save(entity);
        } catch (Exception e) {
            return ApiResult.FAILURE("修改失败");
        }
        return ApiResult.SUCCESS("修改成功");
    }


    @ApiOperation("财务人员统计业务员实际总佣金")
    @RequestMapping("/countSjyj")
    public ApiResult<Object> countSjyj(Integer ywyid) {
        List<Map<String, Double>> map = new ArrayList<>();
        if (ywyid == null) {
            map = taskRepository.sumAllSjyjByAjid();
        } else {
            map = taskRepository.sumSjyjByAjid(ywyid);
        }
        return ApiResult.SUCCESS(map);
    }

    @ApiOperation("财务人员确认已发放佣金")
    @RequestMapping("/checkFfyj")
    public ApiResult<Object> checkFfyj(List<Integer> ids) {
        if (ids.size() < 1) {
            return ApiResult.FAILURE("未选择确认的任务");
        }
        try {
            taskRepository.updateRwztByIdIn(ids);
        } catch (Exception e) {
            return ApiResult.FAILURE("确认失败");
        }
        return ApiResult.SUCCESS("确认成功");
    }

}
