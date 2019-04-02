package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.CasepeoplemessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by wazto on 2019/3/21.
 */
public interface CasepeoplemessageRepository extends JpaRepository<CasepeoplemessageEntity, Integer> {

    @Query(value = "update casepeoplemessage set show_state = 1 where case_id = ?1", nativeQuery = true)
    void setXszt(Integer ajid);
}
