package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.RemarkmsgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by wazto on 2019/3/21.
 */
public interface RemarkMsgRepository extends JpaRepository<RemarkmsgEntity, Integer> {

    @Query(value = "update remarkmsg set show_state = 1 where case_id = ?1", nativeQuery = true)
    void setXszt(Integer ajid);
}
