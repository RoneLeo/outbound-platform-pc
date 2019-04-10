package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.BatchEntity;
import com.chiyun.outboundplatform.entity.CardmessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CardMessageRepository extends JpaRepository<CardmessageEntity, Integer> {

    @Query(value = "update cardmessage set show_state = 2 where case_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void setXszt(Integer ajid);

}
