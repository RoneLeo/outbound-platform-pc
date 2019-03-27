package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.entity.TaskEntity;
import com.chiyun.outboundplatform.repository.TaskRepository;
import com.chiyun.outboundplatform.service.ItaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TaskServiceImpl implements ItaskService {

    @Resource
    private TaskRepository taskRepository;

    @Override
    public TaskEntity findById(Integer id) {
        return taskRepository.findById(id);
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
    public int deleteById(Integer id) {
        return taskRepository.deleteById(id);
    }
}
