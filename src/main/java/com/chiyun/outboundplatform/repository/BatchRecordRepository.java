package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.BatchrecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface BatchRecordRepository extends JpaRepository<BatchrecordEntity, Integer> {

    @Query(value = "delete from batchrecord where  batch_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteAllByPcid(String pcid);

    /**
     * 通过批次id查询
     */
    List<BatchrecordEntity> findAllByPcidOrderBySort(String pcid);

    @Query(value = "select fieldcasebase_id from batchrecord where  batch_id = ?1", nativeQuery = true)
    List<Integer> findAllZdidsByPcid(String pcid);

    @Query(value = "select distinct fieldcasebase_id from batchrecord where  batch_id = ?1", nativeQuery = true)
    List<Integer> findZdidsByPcid(String pcid);

    @Query(value = "SELECT type ,name , sl, group_concat(fieldcasebase_id) bhzd from(SELECT type,fieldcasebase_id,name ,count(*) sl FROM batchrecord,basetype,fieldcasebase WHERE batch_id =?1 AND fieldcasebase_id = fieldcasebase.id AND basetype = basetype.id GROUP BY fieldcasebase_id)b GROUP BY name, type,sl ORDER BY type ASC\n", nativeQuery = true)
    List<Map<String, Object>> getBatchMap(String pcid);


}
