package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.entity.FeedbackEntity;
import com.chiyun.outboundplatform.repository.FeedbackRepository;
import com.chiyun.outboundplatform.repository.FileRepository;
import com.chiyun.outboundplatform.repository.TaskRepository;
import com.chiyun.outboundplatform.service.IfeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class FeedbackServiceImpl implements IfeedbackService {

    @Resource
    private FeedbackRepository feedbackRepository;
    @Resource
    private TaskRepository taskRepository;
    @Resource
    private FileRepository fileRepository;

    @Override
    @Transactional
    public void deleteById(Integer id) {
        //
        FeedbackEntity entity = feedbackRepository.findById(id).get();
        // 修改任务状态
        taskRepository.updateRwztById(3, entity.getRwid());
        // 删除文件
        fileRepository.deleteAllByRwid(entity.getRwid());
        // 删除反馈
        feedbackRepository.deleteById(id);
    }
}
