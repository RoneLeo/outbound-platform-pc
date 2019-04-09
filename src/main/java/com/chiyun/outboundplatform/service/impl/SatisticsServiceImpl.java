package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.repository.CasebasemessageRepository;
import com.chiyun.outboundplatform.repository.DictionaryListRepository;
import com.chiyun.outboundplatform.repository.TaskRepository;
import com.chiyun.outboundplatform.utils.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created by wazto on 2019/4/8.
 */
@Service
public class SatisticsServiceImpl {

    @Resource
    private CasebasemessageRepository casebasemessageRepository;
    @Resource
    private TaskRepository taskRepository;
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
}
