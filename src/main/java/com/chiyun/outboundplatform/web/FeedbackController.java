package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiPageResult;
import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.FeedbackEntity;
import com.chiyun.outboundplatform.entity.TaskEntity;
import com.chiyun.outboundplatform.repository.FeedbackRepository;
import com.chiyun.outboundplatform.repository.UserReposity;
import com.chiyun.outboundplatform.service.ItaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Api(description = "反馈记录表")
@RequestMapping(value = "/feedback", method = {RequestMethod.POST, RequestMethod.GET})
@RestController
public class FeedbackController {

    @Resource
    private FeedbackRepository feedbackRepository;
    @Resource
    private ItaskService itaskService;

    @ApiOperation("添加")
    @RequestMapping("/add")
    @ApiImplicitParam(name = "fkfj", value = "反馈附件", dataType = "MultipartFile", paramType = "query")
    public ApiResult<Object> add(FeedbackEntity entity, MultipartFile fkfj) {
        if (entity.getRwid() == null) {
            return ApiResult.FAILURE("任务id不能为空");
        }
        TaskEntity taskEntity = itaskService.findById(entity.getRwid());
        if (entity.getFkr() == null) {
            return ApiResult.FAILURE("反馈人不能为空");
        }
        entity.setFksj(new Date());
        entity.setFkzt(1);
        File file = new File("C:\\upload" + System.currentTimeMillis() + fkfj.getOriginalFilename().substring(fkfj.getOriginalFilename().lastIndexOf(".")));
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            fkfj.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        entity.setFkfj(file.getPath());
        try {
            feedbackRepository.save(entity);
        } catch (Exception e) {
            return ApiResult.FAILURE("添加失败");
        }
        // 修改任务状态:已接单-待审核
        taskEntity.setRwzt(4);
        try {
            itaskService.save(taskEntity);
        } catch (Exception e) {
            return ApiResult.FAILURE("修改任务状态失败");
        }
        return ApiResult.SUCCESS(entity);
    }

    @ApiOperation("批量删除")
    @RequestMapping("/delete")
    public ApiResult<Object> delete(List<Integer> ids) {
        if (ids.size() < 1) {
            return ApiResult.FAILURE("未选择要删除的数据");
        }
        try {
            feedbackRepository.deleteAllByIdIn(ids);
        } catch (Exception e) {
            return ApiResult.FAILURE("删除失败");
        }
        return ApiResult.SUCCESS("删除成功");
    }


    @ApiOperation("修改反馈状态")
    @RequestMapping("/updateFkzt")
    @ApiImplicitParam(name = "ids", value = "反馈id组合，以英文','分隔", dataType = "List", paramType = "query")
    public ApiResult<Object> updateFkzt(List<Integer> ids) {
        if (ids.size() < 0) {
            return ApiResult.FAILURE("未选择反馈记录");
        }
        for (Integer id : ids) {
            FeedbackEntity entity = feedbackRepository.findById(id).get();
            if (entity == null) {
                return ApiResult.FAILURE("id为" + id + "的记录不存在");
            }
            entity.setFkzt(2);
            try {
                feedbackRepository.save(entity);
            } catch (Exception e) {
                return ApiResult.FAILURE("修改id为" + id + "的记录失败");
            }
        }
        return ApiResult.SUCCESS("修改成功");
    }

    @ApiOperation("根据反馈状态查询")
    @RequestMapping("/findAllByFkzt")
    @ApiImplicitParam(name = "fkzt", value = "反馈状态 1-未处理 2-已处理 不传查询未处理的", dataType = "Integer", paramType = "query")
    public ApiResult<Object> findAllByFkzt(Integer fkzt, int page, int pagesize) {
        Pageable pageable = PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "fksj"));
        Page<FeedbackEntity> list = null;
        if (fkzt == null) {
            list = feedbackRepository.findAllByFkztOrderByFksjDesc(1, pageable);
        } else {
            list = feedbackRepository.findAllByFkztOrderByFksjDesc(fkzt, pageable);
        }
        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
    }

    @ApiOperation("根据任务id查询")
    @RequestMapping("/findAllByRwid")
    public ApiResult<Object> findAllByRwid(Integer rwid, int page, int pagesize) {
        if (rwid == null) {
            return ApiResult.FAILURE("任务id不能为空");
        }
        Pageable pageable = PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "fksj"));
        Page<FeedbackEntity> list = feedbackRepository.findAllByRwid(rwid, pageable);
        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
    }
}
