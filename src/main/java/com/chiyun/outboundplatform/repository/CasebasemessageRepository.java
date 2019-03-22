package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.CasebasemessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wazto on 2019/3/21.
 */
public interface CasebasemessageRepository extends JpaRepository<CasebasemessageEntity, Integer> {

    Page<CasebasemessageEntity> findAllByPch(String pch, Pageable pageable);
}
