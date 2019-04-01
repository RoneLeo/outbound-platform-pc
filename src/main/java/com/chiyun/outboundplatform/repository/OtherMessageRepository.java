package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.OthermessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OtherMessageRepository extends JpaRepository<OthermessageEntity, Integer> {

    @Query(value = "update othermessage set show_state = 1 where case_id = ?1", nativeQuery = true)
    void setXszt(Integer ajid);
}
