package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.OutboundmessageEntity;
import com.chiyun.outboundplatform.entity.UsermessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserMessageRepository extends JpaRepository<UsermessageEntity, Integer> {

    @Query(value = "update usermessage set show_state = 2 where case_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void setXszt(Integer ajid);
}
