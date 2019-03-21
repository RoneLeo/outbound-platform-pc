package com.chiyun.outboundplatform.service;

import com.chiyun.outboundplatform.entity.*;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wazto on 2019/3/20.
 */
public interface IbatchService {
    @Transactional
    boolean saveall(CasebasemessageEntity baseentity, List<CardmessageEntity> cardmessageEntityList
            , List<EmpmessageEntity> empmessageEntityList, List<LoanmessageEntity> loanmessageEntityList, List<UsermessageEntity> usermessageEntityList
            , List<OutboundmessageEntity> outboundmessageEntityList, List<CasepeoplemessageEntity> casepeoplemessageEntityList,
                    List<LinkmanmessageEntity> linkmanmessageEntityList, List<OthermessageEntity> othermessageEntityList
            , List<RemarkmsgEntity> remarkmsgEntityList);
}
