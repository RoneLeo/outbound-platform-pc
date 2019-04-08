package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.FeedbackEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Integer> {

    Page<FeedbackEntity> findAllByFkztOrderByFksjDesc(Integer fkzt, Pageable pageable);


    @Query(value = "select id, task_id rwid, feedback_people fzr, content fknr, attachment fkfj, feedback_state fkzt, feedback_time fksj from feedback where task_id = ?1", nativeQuery = true)
    Map<String, Object> findAllByRwid(Integer rwid);

    Page<FeedbackEntity> findAllByRwid(Integer rwid, Pageable pageable);

    @Query(value = "delete from feedback where id in ?1", nativeQuery = true)
    void deleteAllByIdIn(List<Integer> ids);

}
