package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.CasepeoplemessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * Created by wazto on 2019/3/21.
 */
public interface CasepeoplemessageRepository extends JpaRepository<CasepeoplemessageEntity, Integer> {

    @Query(value = "update casepeoplemessage set show_state = 1 where case_id = ?1", nativeQuery = true)
    void setXszt(Integer ajid);

    /**
     *  查询业务员接单之后的根据任务状态查询的所有任务的任务id
     */
    @Query(value = "select * from casepeoplemessage where case_id = ?1", nativeQuery = true)
    CasepeoplemessageEntity findByAjid(Integer ajid);
}
