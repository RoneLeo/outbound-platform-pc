package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.repository.CasebasemessageRepository;
import com.chiyun.outboundplatform.repository.DictionaryListRepository;
import com.chiyun.outboundplatform.repository.LogRepository;
import com.chiyun.outboundplatform.repository.TaskRepository;
import com.chiyun.outboundplatform.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wazto on 2019/4/8.
 */
@Service
public class SatisticsServiceImpl {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Resource
    private CasebasemessageRepository casebasemessageRepository;
    @Resource
    private TaskRepository taskRepository;
    @Resource
    private LogRepository logRepository;
    @Resource
    private DictionaryListRepository dictionaryListRepository;

    /**
     * 案件月度报表查询
     *
     * @param cxyf
     * @return
     */
    public Object caseAnalysis(Date cxyf) {
        Date begin = DateUtils.getMonthBegin(cxyf);
        Date end = DateUtils.getMonthEnd(cxyf);
        return casebasemessageRepository.getCaseCount(begin, end);
    }

    /**
     * 案件类型月度报表查询
     *
     * @param cxyf
     * @return
     */
    public Object castTypeAnalysis(Date cxyf) {
        Date begin = DateUtils.getMonthBegin(cxyf);
        Date end = DateUtils.getMonthEnd(cxyf);
        return casebasemessageRepository.castTypeAnalysis(begin, end);
    }

    /**
     * 案件季度报表查询
     *
     * @param jd
     * @return
     */
    public Object casequarter(int jd) {
        return casebasemessageRepository.casequarter(jd);
    }

    /**
     * 业务员任务统计
     *
     * @return
     */
    public Page<Map<String, Object>> peoplecount(Pageable pageable) {
        return taskRepository.peoplecount(pageable);
    }

    /**
     * 统计选定时间内案件的变化情况,按日统计
     *
     * @return
     */
    public Map<String, Object> casecount(Date begin, Date end) {
        Map map = new HashMap();
        map.put("ajsj", casebasemessageRepository.casecount(begin, end));
        map.put("wwcsl", casebasemessageRepository.countAllByDrsjAndaAndAjzt(begin));
        return map;
    }

    public Object taskUidCount(int uid, Date begin, Date end) {
        return taskRepository.taskCountByUidAndDate(uid, begin, end);
    }

    /**
     * @param begin
     * @param end
     * @Desc: 查询时间段内用户活跃度排名
     */
    public Object userActive(Date begin, Date end) {
        return logRepository.userActive(begin, end);
    }

    /**
     * @param begin
     * @param end
     * @Desc: 查询时间段内佣金获取排名
     */
    public Object userAchieve(Date begin, Date end) {
        String sql = "SELECT sjyj,rwl,uid,@rownum:=@rownum+1 px FROM (SELECT sum(actual_money) sjyj,count(*) rwl,task_peopleId uid ,@rownum:=0  FROM task WHERE if(? IS NULL ,1=1,update_time >=?)AND if(? IS NULL ,1=1,update_time <=?) AND exists(SELECT 1 FROM casebasemessage WHERE show_state =1 AND case_id = casebasemessage.id) AND exists(SELECT 1 FROM user WHERE task_peopleId = user.id AND type = 1 AND role_id >=3 AND role_id<=4) GROUP BY task_peopleId ORDER BY sjyj DESC)se";
//        jdbcTemplate.query(sql,);
        return taskRepository.taskCountAchieveByDate(begin, end);
    }

    /**
     * @param uid
     * @param begin
     * @param end
     * @Desc: 查询时间段内某个业务员佣金获取排名
     */
    public Object userAchieve(int uid, Date begin, Date end) {
        return taskRepository.taskCountAchieveByUidAndDate(uid, begin, end);
    }
}
