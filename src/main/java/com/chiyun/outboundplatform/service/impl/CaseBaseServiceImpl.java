package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.entity.CasebasemessageAllEntity;
import com.chiyun.outboundplatform.entity.CasebasemessageEntity;
import com.chiyun.outboundplatform.repository.*;
import com.chiyun.outboundplatform.service.IcaseBaseService;
import com.chiyun.outboundplatform.utils.DateUtils;
import com.chiyun.outboundplatform.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by wazto on 2019/3/22.
 */
@Service
public class CaseBaseServiceImpl implements IcaseBaseService {

    @Resource
    private CasebasemessageRepository casebasemessageRepository;
    @Resource
    private CasebasemessageAllRepository casebasemessageAllRepository;
    @Resource
    private CardMessageRepository cardMessageRepository;
    @Resource
    private CasepeoplemessageRepository casepeoplemessageRepository;
    @Resource
    private EmpMessageRepository empMessageRepository;
    @Resource
    private LinkmanMessageRepository linkmanMessageRepository;
    @Resource
    private LoanMessageRepository loanMessageRepository;
    @Resource
    private OtherMessageRepository otherMessageRepository;
    @Resource
    private OutboundMessageRepository outboundMessageRepository;
    @Resource
    private RemarkMsgRepository remarkMsgRepository;
    @Resource
    private UserMessageRepository userMessageRepository;
    @Resource
    private TaskRepository taskRepository;

    @Override
    public CasebasemessageAllEntity findAllInfoById(Integer id) {
        return casebasemessageAllRepository.findById(id).orElse(null);
    }

    @Override
    public CasebasemessageEntity findInfoById(Integer id) {
        return casebasemessageRepository.findById(id).orElse(null);
    }

    @Override
    public Page<CasebasemessageEntity> findAllByPage(Pageable pageable) {
        return casebasemessageRepository.findAllByXszt(1, pageable);
    }

    @Override
    public Page<CasebasemessageEntity> findAllByPcidAndPage(String pcid, Pageable pageable) {
        return casebasemessageRepository.findAllByPcidAndXszt(pcid, 1, pageable);
    }

    @Override
    public Page<CasebasemessageEntity> findAllByCondition(String pcid, String ajmc, Integer ajlx, Integer ajzt, Integer ajqy, Date begin, Date end, Pageable pageable) {
        if (StringUtil.isNull(ajmc)) {
            ajmc = "%%";
        } else {
            ajmc = "%" + ajmc + "%";
        }
        if (begin == null && end == null) {
            return casebasemessageRepository.findAllByCondition(pcid, ajmc, ajlx, ajzt, ajqy, pageable);
        } else {
            if (begin == null && end != null) {
                begin = casebasemessageRepository.getEarliestTime();
            } else if (begin != null && end == null) {
                end = casebasemessageRepository.getLatestTime();
            }
            return casebasemessageRepository.findAllByConditionAndDrsjBetween(pcid, ajmc, ajlx, ajzt, ajqy, begin, end, pageable);
        }
    }

    @Override
    @Transactional
    public boolean reset(Integer id) {
        // 先修改其他附表数据
        // 卡号信息
        cardMessageRepository.setXszt(id);
        // 案人信息
        casepeoplemessageRepository.setXszt(id);
        // 催收员信息
        empMessageRepository.setXszt(id);
        // 联系人信息
        linkmanMessageRepository.setXszt(id);
        // 贷款信息
        loanMessageRepository.setXszt(id);
        // 其他信息
        otherMessageRepository.setXszt(id);
        // 外访信息
        outboundMessageRepository.setXszt(id);
        // 备注信息
        remarkMsgRepository.setXszt(id);
        // 对象信息
        userMessageRepository.setXszt(id);
        // 注销任务
        taskRepository.resetByAjid(id);
        // 将案件显示状态修改：不显示
        casebasemessageRepository.updateXszt(id);
        return true;
    }
}
