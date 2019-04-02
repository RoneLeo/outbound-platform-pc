package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.BatchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface BatchRepository extends JpaRepository<BatchEntity, Integer> {

    /**
     * 查询所有的类型
     */
    @Query(value = "SELECT mc, id ,sl,count(*) lx from (SELECT name mc, type id ,fieldcasebase_id ,count(*) sl FROM basetype,batch,fieldcasebase, batchrecord WHERE batch.batch_id = ?1 AND type = basetype AND fieldcasebase_id = fieldcasebase.id AND batch.batch_id = batchrecord.batch_id GROUP BY fieldcasebase_id ,type)ss GROUP BY mc, id,sl ORDER BY id ASC ", nativeQuery = true)
    List<Map<String, Object>> findAllByPcid(String pcid);

    /**
     *  通过模板id查询
     */
    BatchEntity findByPcid(String pcid);

    /**
     * 通过id删除
     */
    @Query(value = "delete from batch where  batch_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteByPcid(String pcid);

}
