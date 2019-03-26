package com.chiyun.outboundplatform.service;

import com.chiyun.outboundplatform.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * 通过id删除
     */
    int deleteById(Integer id);
}
