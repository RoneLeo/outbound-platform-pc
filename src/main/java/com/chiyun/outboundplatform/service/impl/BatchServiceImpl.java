package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.entity.*;
import com.chiyun.outboundplatform.repository.*;
import com.chiyun.outboundplatform.service.IbatchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wazto on 2019/3/20.
 */
@Service
public class BatchServiceImpl implements IbatchService {
    @Resource
    private CasebasemessageRepository casebasemessageRepository;
    @Resource
    private CardMessageRepository cardMessageRepository;
    @Resource
    private EmpMessageRepository empMessageRepository;
    @Resource
    private LoanMessageRepository loanMessageRepository;
    @Resource
    private UserMessageRepository userMessageRepository;
    @Resource
    private OutboundMessageRepository outboundMessageRepository;
    @Resource
    private CasepeoplemessageRepository casepeoplemessageRepository;
    @Resource
    private LinkmanMessageRepository linkmanMessageRepository;
    @Resource
    private OtherMessageRepository otherMessageRepository;
    @Resource
    private RemarkMsgRepository remarkMsgRepository;
    @Resource
    private BatchRecordRepository batchRecordRepository;

    @Override
    @Transactional
    public boolean saveall(CasebasemessageEntity baseentity,
                           List<CardmessageEntity> cardmessageEntityList,
                           List<EmpmessageEntity> empmessageEntityList,
                           List<LoanmessageEntity> loanmessageEntityList,
                           List<UsermessageEntity> usermessageEntityList,
                           List<OutboundmessageEntity> outboundmessageEntityList,
                           List<CasepeoplemessageEntity> casepeoplemessageEntityList,
                           List<LinkmanmessageEntity> linkmanmessageEntityList,
                           List<OthermessageEntity> othermessageEntityList,
                           List<RemarkmsgEntity> remarkmsgEntityList) {
        baseentity = casebasemessageRepository.save(baseentity);
        for (CardmessageEntity entity : cardmessageEntityList) {
            entity.setAjid(baseentity.getId());
        }
        cardMessageRepository.saveAll(cardmessageEntityList);
        for (EmpmessageEntity entity : empmessageEntityList) {
            entity.setAjid(baseentity.getId());
        }
        empMessageRepository.saveAll(empmessageEntityList);
        for (LoanmessageEntity entity : loanmessageEntityList) {
            entity.setAjid(baseentity.getId());
        }
        loanMessageRepository.saveAll(loanmessageEntityList);
        for (UsermessageEntity entity : usermessageEntityList) {
            entity.setAjid(baseentity.getId());
        }
        userMessageRepository.saveAll(usermessageEntityList);
        for (OutboundmessageEntity entity : outboundmessageEntityList) {
            entity.setAjid(baseentity.getId());
        }
        outboundMessageRepository.saveAll(outboundmessageEntityList);
        for (CasepeoplemessageEntity entity : casepeoplemessageEntityList) {
            entity.setAjid(baseentity.getId());
        }
        casepeoplemessageRepository.saveAll(casepeoplemessageEntityList);
        for (LinkmanmessageEntity entity : linkmanmessageEntityList) {
            entity.setAjid(baseentity.getId());
        }
        linkmanMessageRepository.saveAll(linkmanmessageEntityList);
        for (OthermessageEntity entity : othermessageEntityList) {
            entity.setAjid(baseentity.getId());
        }
        otherMessageRepository.saveAll(othermessageEntityList);
        for (RemarkmsgEntity entity : remarkmsgEntityList) {
            entity.setAjid(baseentity.getId());
        }
        remarkMsgRepository.saveAll(remarkmsgEntityList);
        return true;
    }

    @Override
    public List<Map<String, Object>> getBatchMap(String pcid) {
        return batchRecordRepository.getBatchMap(pcid);
    }
}
