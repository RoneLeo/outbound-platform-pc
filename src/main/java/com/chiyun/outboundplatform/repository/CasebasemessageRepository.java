package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.CasebasemessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by wazto on 2019/3/21.
 */
public interface CasebasemessageRepository extends JpaRepository<CasebasemessageEntity, Integer> {

    Page<CasebasemessageEntity> findAllByPcidAndXszt(String pcid, Integer xszt, Pageable pageable);

    /**
     *  查询所有
     */
    List<CasebasemessageEntity> findAll();

    /**
     *  保存
     */
    CasebasemessageEntity save(CasebasemessageEntity entity);

    Page<CasebasemessageEntity> findAllByXszt(Integer xszt, Pageable pageable);

    /**
     *  修改状态
     */
    @Query(value = "update casebasemessage set show_state = 1 where id = ?1", nativeQuery = true)
    void updateXszt(Integer id);

    /**
     *  通过区域查询案件id
     */
    @Query(value = "select distinct id from casebasemessage where area_id = ?1 and show_state = '1'", nativeQuery = true)
    List<Integer> findIdsByAjqy(Integer ajqy);

    /**
     *  获取最早和最晚的时间
     */
    @Query(value = "select import_time from casebasemessage order by import_time asc limit 1", nativeQuery = true)
    Date getEarliestTime();

    @Query(value = "select import_time from casebasemessage order by import_time desc limit 1", nativeQuery = true)
    Date getLatestTime();

    /**
     * 多条件查询：批次id、案件名称、案件类型、案件状态、案件区域、导入时间
     */
    @Query(value = "select * from casebasemessage where IF(?1 != '', batch_id = ?1, 1=1) and case_name like ?2 and if(?3 is not null, case_type = ?3, 1 = 1) and if(?4 is not null, case_state = ?4, 1 = 1) and if(?5 is not null, area_id = ?5, 1 = 1) and import_time between ?6 and ?7 and show_state = '1'", nativeQuery = true)
    Page<CasebasemessageEntity> findAllByConditionAndDrsjBetween(String pcid, String ajmc, Integer ajlx, Integer ajzt, Integer ajqy, Date begin, Date end, Pageable pageable);

    @Query(value = "select * from casebasemessage where IF(?1 != '', batch_id = ?1, 1=1) and case_name like ?2 and if(?3 is not null, case_type = ?3, 1 = 1) and if(?4 is not null, case_state = ?4, 1 = 1) and if(?5 is not null, area_id = ?5, 1 = 1) and show_state = '1'", nativeQuery = true)
    Page<CasebasemessageEntity> findAllByCondition(String pcid, String ajmc, Integer ajlx, Integer ajzt, Integer ajqy, Pageable pageable);

}


