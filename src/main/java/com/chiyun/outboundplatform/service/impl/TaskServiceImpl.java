package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.entity.TaskEntity;
import com.chiyun.outboundplatform.repository.CasebasemessageRepository;
import com.chiyun.outboundplatform.repository.FeedbackRepository;
import com.chiyun.outboundplatform.repository.TaskRepository;
import com.chiyun.outboundplatform.service.ItaskService;
import com.chiyun.outboundplatform.utils.DateUtils;
import com.chiyun.outboundplatform.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class TaskServiceImpl implements ItaskService {

    @Resource
    private TaskRepository taskRepository;
    @Resource
    private FeedbackRepository feedbackRepository;

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
                                               Integer rwzt, String rwzxrmc, Date beginWcsj, Date endWcsj,
                                               Date beginCjsj, Date endCjsj, Pageable pageable) {
        if (StringUtil.isNull(rwmc)) {
            rwmc = "%%";
        } else {
            rwmc = "%" + rwmc + "%";
        }
        if (StringUtil.isNull(rwzxrmc)) {
            rwzxrmc = "%%";
        } else {
            rwzxrmc = "%" + rwzxrmc + "%";
        }
        if (beginJzsj == null) {
            beginJzsj = taskRepository.getEarliestRwjzsj();
        }
        if (endJzsj == null) {
            endJzsj = taskRepository.getLatestRwjzsj();
        }
        if (beginCjsj == null) {
            beginCjsj = taskRepository.getEarliestRwcjsj();
        }
        if (endCjsj == null) {
            endCjsj = taskRepository.getLatestRwcjsj();
        }

        if (beginWcsj == null && endWcsj == null) {
            //
            return taskRepository.findAllByConditionAndRwjzsjBetweenAndRwcjsjBetween(rwmc, beginJzsj, endJzsj,
                    rwfs, rwzt, rwzxrmc, beginCjsj, endCjsj, pageable);
        } else {
            if (beginWcsj == null) {
                beginWcsj = taskRepository.getEarliestRwwcsj();
            }
            if (endWcsj == null) {
                endWcsj = taskRepository.getLatestRwwcsj();
            }
            return taskRepository.findAllByConditionAndRwjzsjBetweenAAndRwcjsjBetweenAndRwcjsjBetween(
                    rwmc, beginJzsj, endJzsj, rwfs, rwzxrmc, beginWcsj, endWcsj, beginCjsj, endCjsj, pageable);
        }

    }


    @Override
    public Map<String, Object> countYwyRwxx(Integer rwzxr) {
        Map<String, Object> map = new HashMap<>();
        // 已接收数量
        int yjsNum = taskRepository.countAllByRwzxrAndRwzt(rwzxr, 3);
        // 已处理数量
        int yclNum = taskRepository.countAllByRwzxrAndRwzt(rwzxr, 4);
        // 应得佣金
        double ydMoney = taskRepository.sumAllRwyjByRwzxr(rwzxr);
        // 实际佣金
        double sjMoney = taskRepository.sumAllSjyjByRwzxr(rwzxr);
        map.put("yjsNum", yjsNum);
        map.put("yclNum", yclNum);
        map.put("ydMoney", ydMoney);
        map.put("sjMoney", sjMoney);
        return map;
    }

    @Override
    @Transactional
    public void check(TaskEntity entity) {
        // 修改反馈状态
        feedbackRepository.updateFkztByRwid(entity.getId());
        //
        taskRepository.save(entity);
    }


}
