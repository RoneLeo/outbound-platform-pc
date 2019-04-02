package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.BasetypeEntity;
import com.chiyun.outboundplatform.entity.EmpmessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmpMessageRepository extends JpaRepository<EmpmessageEntity, Integer> {

    @Query(value = "update empmessage set show_state = 1 where case_id = ?1", nativeQuery = true)
    void setXszt(Integer ajid);
}
