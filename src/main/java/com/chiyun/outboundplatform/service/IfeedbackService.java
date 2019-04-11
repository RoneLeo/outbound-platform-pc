package com.chiyun.outboundplatform.service;


import org.springframework.transaction.annotation.Transactional;

public interface IfeedbackService {

    /**
     *  删除
     */
    @Transactional
    void deleteById(Integer id);

}
