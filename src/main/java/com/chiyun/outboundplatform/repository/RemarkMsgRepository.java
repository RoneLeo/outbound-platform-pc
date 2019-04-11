package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.RemarkmsgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wazto on 2019/3/21.
 */
public interface RemarkMsgRepository extends JpaRepository<RemarkmsgEntity, Integer> {

    @Query(value = "update remarkmsg set show_state = 2 where case_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void setXszt(Integer ajid);
}
