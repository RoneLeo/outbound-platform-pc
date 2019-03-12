package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.EmpmessageEntity;
import com.chiyun.outboundplatform.entity.FieldcasebaseEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FieldCaseBaseRepository extends CrudRepository<FieldcasebaseEntity, Long> {
    /**
     *  通过id查询
     */
    FieldcasebaseEntity findById(Integer id);

    /**
     *  通过类型查询
     */
    List<FieldcasebaseEntity> findAllByJcxxlx(Integer jcxxlx);

    /**
     *  查询所有
     */
    List<FieldcasebaseEntity> findAll();

    /**
     *  保存
     */
    FieldcasebaseEntity save(FieldcasebaseEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from fieldcasebase where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

}
