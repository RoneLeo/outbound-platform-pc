package com.chiyun.outboundplatform.service;

import com.chiyun.outboundplatform.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Map;


public interface ItaskService {

    /**
     * 根据id查询
     */
    TaskEntity findById(Integer id);

    /**
     * 根据任务状态查询
     */
    Page<TaskEntity> findAllByRwzt(Integer rwzt, Pageable pageable);

    /**
     * 保存
     */
    TaskEntity save(TaskEntity entity);

    /**
     * 多条件查询:任务名称、任务截止时间、任务方式、任务状态、任务执行人、任务完成时间
     */
    Page<TaskEntity> findAllByCondition(String rwmc, Date beginJzsj, Date endJzsj,
                                        Integer rwfs, Integer rwzt, Integer shzt, Integer rwzxr,
                                        Date beginWcsj, Date endWcsj, Pageable pageable);

    /**
     *  查询业务员本区域的任务
     */
    Page<TaskEntity> findAllByYwyqy(Integer qy, Pageable pageable);

    /**
     * 根据接单方式查询
     */
    Page<TaskEntity> findAllByJdfs(Integer jdfs, Integer rwzxr, Pageable pageable);

    /**
     *  统计业务员 已接收、已处理案件数、应得佣金及实际佣金
     */
    Map<String, Object> countYwyRwxx(Integer rwzxr);

    /**
     *  通过任务执行人、任务状态、审核状态查询
     */
    Page<TaskEntity> findAllByFlag(Integer ywyid, Integer flag, Pageable pageable);

}
