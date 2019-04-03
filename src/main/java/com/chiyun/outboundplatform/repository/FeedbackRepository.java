package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.FeedbackEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Integer> {

    Page<FeedbackEntity> findAllByFkztOrderByFksjDesc(Integer fkzt, Pageable pageable);

    Page<FeedbackEntity> findAllByRwid(Integer rwid, Pageable pageable);

    @Query(value = "delete from feedback where id in ?1", nativeQuery = true)
    void deleteAllByIdIn(List<Integer> ids);

}
