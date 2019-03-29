package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.FeedbackEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Integer> {

    Page<FeedbackEntity> findAllByFkztOrderByFksjDesc(Integer fkzt, Pageable pageable);



}
