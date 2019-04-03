package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.EmpmessageEntity;
import com.chiyun.outboundplatform.entity.FieldcasebaseEntity;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FieldCaseBaseRepository extends JpaRepository<FieldcasebaseEntity, Integer> {


    /**
     *  通过类型查询
     */
    List<FieldcasebaseEntity> findAllByJcxxlx(Integer jcxxlx);

    /**
     *  查询所有默认字段
     */
    @Query(value = "select fieldCname from fieldcasebase where basetype = 0", nativeQuery = true)
    List<String> findAllZdzwmc();

    /**
     *  统计同一类型的个数
     */
    int countFieldcasebaseEntitiesByJcxxlx(Integer jcxxlx);


    @Query(value = "select basetype from fieldcasebase where id = ?1", nativeQuery = true)
    int findJcxxlxById(Integer id);



}
