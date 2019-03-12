package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.LinkmanmessageEntity;
import com.chiyun.outboundplatform.entity.LoanmessageEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LoanMessageRepository extends CrudRepository<LoanmessageEntity, Long> {
    /**
     *  通过id查询
     */
    LoanmessageEntity findById(Integer id);

    /**
     *  查询所以
     */
    List<LoanmessageEntity> findAll();

    /**
     *  保存
     */
    LoanmessageEntity save(LoanmessageEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from loanmessage where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

}
