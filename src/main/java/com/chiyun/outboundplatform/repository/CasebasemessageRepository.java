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
import java.util.Map;

/**
 * Created by wazto on 2019/3/21.
 */
public interface CasebasemessageRepository extends JpaRepository<CasebasemessageEntity, Integer> {

    Page<CasebasemessageEntity> findAllByPcidAndXszt(String pcid, Integer xszt, Pageable pageable);

    /**
     * 查询所有
     */
    List<CasebasemessageEntity> findAll();

    /**
     * 保存
     */
    CasebasemessageEntity save(CasebasemessageEntity entity);

    Page<CasebasemessageEntity> findAllByXszt(Integer xszt, Pageable pageable);

    /**
     * 修改状态
     */
    @Query(value = "update casebasemessage set show_state = 1 where id = ?1", nativeQuery = true)
    void updateXszt(Integer id);

    /**
     * 通过区域查询案件id
     */
    @Query(value = "select * from casebasemessage where area_id in (select area_code from user where id = ?1) order by import_time", nativeQuery = true)
    Page<CasebasemessageEntity> findAllByYhid(Integer yhid, Pageable pageable);

    /**
     * 获取最早和最晚的时间
     */
    @Query(value = "select import_time from casebasemessage order by import_time asc limit 1", nativeQuery = true)
    Date getEarliestTime();

    @Query(value = "select import_time from casebasemessage order by import_time desc limit 1", nativeQuery = true)
    Date getLatestTime();

    /**
     * 多条件查询：批次id、案件名称、案件类型、案件状态、案件区域、导入时间
     */
    @Query(value = "select * from casebasemessage where IF(?1 != '', batch_id = ?1, 1=1) and (?2 != '', case_name like ?2, case_name like '%%' or case_name is null) and if(?3 is not null, case_type = ?3, 1 = 1) and if(?4 is not null, case_state = ?4, 1 = 1) and if(?5 is not null, area_id = ?5, 1 = 1) and import_time between ?6 and ?7 and show_state = '1'", nativeQuery = true)
    Page<CasebasemessageEntity> findAllByConditionAndDrsjBetween(String pcid, String ajmc, Integer ajlx, Integer ajzt, Integer ajqy, Date begin, Date end, Pageable pageable);

    @Query(value = "select * from casebasemessage where IF(?1 != '', batch_id = ?1, 1=1) and if(?2 != '', case_name like ?2, case_name like '%%' or case_name is null)  and if(?3 is not null, case_type = ?3, 1 = 1) and if(?4 is not null, case_state = ?4, 1 = 1) and if(?5 is not null, area_id = ?5, 1 = 1) and show_state = '1'", nativeQuery = true)
    Page<CasebasemessageEntity> findAllByCondition(String pcid, String ajmc, Integer ajlx, Integer ajzt, Integer ajqy, Pageable pageable);

    /**
     * @param begin
     * @param end
     * @Desc: 统计选择时间内案件状态数量
     */
    @Query(value = "SELECT entrycode lx ,CASE WHEN sl IS NULL THEN 0 ELSE sl END sl FROM (SELECT entrycode FROM dictionarylist WHERE dictid = 1 )dic LEFT JOIN (SELECT case_state,count(*) sl FROM casebasemessage WHERE if(?1 is null,update_time =update_time,update_time >=?1)  AND if(?2 is null,update_time =update_time,update_time <=?2) AND show_state =1 GROUP BY case_state )mes ON case_state = entrycode ORDER BY entrycode ASC", nativeQuery = true)
    List<Map<String, Object>> getCaseCount(Date begin, Date end);

    /**
     * @param begin
     * @param end
     * @Desc: 统计选择时间内案件类型数量
     */
    @Query(value = "SELECT entrycode lx ,CASE WHEN sl IS NULL THEN 0 ELSE sl END sl FROM (SELECT entrycode FROM dictionarylist WHERE dictid = 2 )dic LEFT JOIN (SELECT case_state,count(*) sl FROM casebasemessage WHERE if(?1 is null,update_time =update_time,update_time >=?1)  AND if(?2 is null,update_time =update_time,update_time <=?2) AND show_state =1 GROUP BY case_state )mes ON case_state = entrycode ORDER BY entrycode ASC", nativeQuery = true)
    List<Map<String, Object>> castTypeAnalysis(Date begin, Date end);

    /**
     * @param jd
     * @Desc: 统计选择季度内案件状态数量
     */
    @Query(value = "SELECT entrycode lx ,CASE WHEN sl IS NULL THEN 0 ELSE sl END sl FROM (SELECT entrycode FROM dictionarylist WHERE dictid = 1 )dic LEFT JOIN (SELECT case_state,count(*) sl FROM casebasemessage WHERE quarter(update_time) = ?1 AND show_state =1  GROUP BY case_state )mes ON case_state = entrycode ORDER BY entrycode ASC", nativeQuery = true)
    List<Map<String, Object>> casequarter(int jd);
}


