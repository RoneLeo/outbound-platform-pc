package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.LoanmessageEntity;
import com.chiyun.outboundplatform.entity.LoantypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LoanTypeRepository extends JpaRepository<LoantypeEntity, Integer> {

}
