package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.entity.CasebasemessageAllEntity;
import com.chiyun.outboundplatform.entity.CasebasemessageEntity;
import com.chiyun.outboundplatform.repository.CasebasemessageAllRepository;
import com.chiyun.outboundplatform.repository.CasebasemessageRepository;
import com.chiyun.outboundplatform.service.IcaseBaseService;
import com.chiyun.outboundplatform.utils.DateUtils;
import com.chiyun.outboundplatform.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return casebasemessageRepository.findAll(pageable);
    }

    @Override
    public Page<CasebasemessageEntity> findAllByPchAndPage(String pch, Pageable pageable) {
        return casebasemessageRepository.findAllByPch(pch, pageable);
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
            begin = DateUtils.getDayTime(begin, end, 0);
            end = DateUtils.getDayTime(begin, end,1);
            return casebasemessageRepository.findAllByConditionAndDrsjBetween(pcid, ajmc, ajlx, ajzt, ajqy, begin, end, pageable);
        }
    }
}
