package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.entity.TaskEntity;
import com.chiyun.outboundplatform.repository.CasebasemessageRepository;
import com.chiyun.outboundplatform.repository.TaskRepository;
import com.chiyun.outboundplatform.service.ItaskService;
import com.chiyun.outboundplatform.utils.DateUtils;
import com.chiyun.outboundplatform.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements ItaskService {

    @Resource
    private TaskRepository taskRepository;
    @Resource
    private CasebasemessageRepository casebasemessageRepository;

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
                                               Integer rwzt, Integer shzt, Integer rwzxr, Date beginWcsj, Date endWcsj, Pageable pageable) {
        if (StringUtil.isNull(rwmc)) {
            rwmc = "%%";
        } else {
            rwmc = "%" + rwmc + "%";
        }
        if (beginJzsj == null && endJzsj == null && beginWcsj == null && endWcsj == null) {
            return taskRepository.findAllByCondition(rwmc, rwfs, rwzt, shzt, rwzxr, pageable);
        } else {
            if ((beginJzsj != null || endJzsj != null) && (beginWcsj == null && endWcsj == null)) {
                beginJzsj = getTime(beginJzsj, endJzsj, 1, 1);
                endJzsj = getTime(beginJzsj, endJzsj, 1, 2);
                return taskRepository.findAllByConditionAndRwjzsjBetween(rwmc, beginJzsj, endJzsj,
                        rwfs, rwzt, shzt, rwzxr, pageable);
            } else if ((beginJzsj == null && endJzsj == null) && (beginWcsj != null || endWcsj != null)) {
                beginWcsj = getTime(beginWcsj, endWcsj, 2, 1);
                endWcsj = getTime(beginWcsj, endWcsj, 2,2);
                return taskRepository.findAllByConditionAndRwwcsjBetween(rwmc, rwfs, rwzt, shzt, rwzxr,
                        beginWcsj, endWcsj, pageable);
            } else {
                beginJzsj = getTime(beginJzsj, endJzsj, 1, 1);
                endJzsj = getTime(beginJzsj, endJzsj, 1, 2);
                beginWcsj = getTime(beginWcsj, endWcsj, 2, 1);
                endWcsj = getTime(beginWcsj, endWcsj, 2,2);
                return taskRepository.findAllByConditionAndRwjzsjBetweenAndRwwcsjBetween(rwmc, beginJzsj, endJzsj,
                        rwfs, rwzt, shzt, rwzxr, beginWcsj, endWcsj, pageable);
            }
        }

    }

    /**
     * @param begin
     * @param end
     * @param flag 1-任务截止时间 2-任务完成时间
     * @param flag2 1-返回begin 2-返回end
     * @return
     */
    public Date getTime(Date begin, Date end, int flag, int flag2) {
        if (flag == 1) {
            // 任务截止时间
            if (begin == null && end != null) {
                begin = taskRepository.getEarliestRwjzsj();
            } else if (begin != null && end == null) {
                end = taskRepository.getLatestRwjzsj();
            }
        } else {
            // 任务完成时间
            if (begin == null && end != null) {
                begin = taskRepository.getEarliestRwwcsj();
            } else if (begin != null && end == null) {
                end = taskRepository.getLatestRwwcsj();
            }
        }
        if (flag2 == 1) {
            // begin
            return begin;
        } else {
            return end;
        }
    }


    @Override
    public Page<TaskEntity> findAllByYwyqy(Integer qy, Pageable pageable) {
        // 获取该区域所有案件id
        List<Integer> ajids = casebasemessageRepository.findIdsByAjqy(qy);
        // 获取所有任务
        Page<TaskEntity> list = taskRepository.findAllByAjidIn(ajids, pageable);
        return list;
    }

    @Override
    public Page<TaskEntity> findAllByJdfs(Integer jdfs, Integer rwzxr, Pageable pageable) {
        if (jdfs == null) {
            return taskRepository.findAllByRwzxrOrderByIdDesc(rwzxr, pageable);
        }
        return taskRepository.findAllByJdfsAndRwzxrOrderByIdDesc(jdfs, rwzxr, pageable);
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

}
