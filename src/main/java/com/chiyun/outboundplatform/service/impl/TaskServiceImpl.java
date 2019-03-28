package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.entity.TaskEntity;
import com.chiyun.outboundplatform.repository.TaskRepository;
import com.chiyun.outboundplatform.service.ItaskService;
import com.chiyun.outboundplatform.utils.DateUtils;
import com.chiyun.outboundplatform.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TaskServiceImpl implements ItaskService {

    @Resource
    private TaskRepository taskRepository;

    @Override
    public TaskEntity findById(Integer id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Page<TaskEntity> findAllByRwzt(Integer rwzt, Pageable pageable) {
        return taskRepository.findAllByRwzt(rwzt, pageable);
    }

    @Override
    public TaskEntity save(TaskEntity entity) {
        return taskRepository.save(entity);
    }

    @Override
    public Page<TaskEntity> findAllByCondition(String rwmc, Date beginJzsj, Date endJzsj, Integer rwfs,
                                               Integer rwzt, Integer rwzxr, Date beginWcsj, Date endWcsj, Pageable pageable) {
        if (StringUtil.isNull(rwmc)) {
            rwmc = "%%";
        } else {
            rwmc = "%" + rwmc + "%";
        }
        if (beginJzsj == null && endJzsj == null && beginWcsj == null && endWcsj == null) {
            return taskRepository.findAllByCondition(rwmc, rwfs, rwzt, rwzxr, pageable);
        } else {
            if ((beginJzsj != null || endJzsj != null) && (beginWcsj == null && endWcsj == null)) {
                beginJzsj = DateUtils.getDayTime(beginJzsj, endJzsj,0);
                endJzsj = DateUtils.getDayTime(beginJzsj, endJzsj,1);
                return taskRepository.findAllByConditionAndRwjzsjBetween(rwmc, beginJzsj, endJzsj,
                        rwfs, rwzt, rwzxr, pageable);
            } else if ((beginJzsj == null && endJzsj == null) && (beginWcsj != null || endWcsj != null)) {
                beginWcsj = DateUtils.getDayTime(beginWcsj, endWcsj, 0);
                endWcsj = DateUtils.getDayTime(beginWcsj, endWcsj, 1);
                return taskRepository.findAllByConditionAndRwwcsjBetween(rwmc, rwfs, rwzt, rwzxr,
                        beginWcsj, endWcsj, pageable);
            } else {
                beginJzsj = DateUtils.getDayTime(beginJzsj, endJzsj,0);
                endJzsj = DateUtils.getDayTime(beginJzsj, endJzsj,1);
                beginWcsj = DateUtils.getDayTime(beginWcsj, endWcsj, 0);
                endWcsj = DateUtils.getDayTime(beginWcsj, endWcsj, 1);
                return taskRepository.findAllByConditionAndRwjzsjBetweenAndRwwcsjBetween(rwmc, beginJzsj, endJzsj,
                        rwfs, rwzt, rwzxr, beginWcsj, endWcsj, pageable);
            }
        }

    }

}
