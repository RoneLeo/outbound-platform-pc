package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.OthermessageEntity;
import com.chiyun.outboundplatform.entity.OutboundmessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OutboundMessageRepository extends JpaRepository<OutboundmessageEntity, Integer> {

}
