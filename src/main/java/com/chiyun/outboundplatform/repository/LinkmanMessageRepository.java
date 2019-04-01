package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.FieldcasebaseEntity;
import com.chiyun.outboundplatform.entity.LinkmanmessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LinkmanMessageRepository extends JpaRepository<LinkmanmessageEntity, Integer> {

    @Query(value = "update linkmanmessage set show_state = 1 where case_id = ?1", nativeQuery = true)
    void setXszt(Integer ajid);

}
