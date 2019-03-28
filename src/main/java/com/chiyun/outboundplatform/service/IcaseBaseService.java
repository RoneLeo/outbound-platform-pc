package com.chiyun.outboundplatform.service;

import com.chiyun.outboundplatform.entity.CasebasemessageAllEntity;
import com.chiyun.outboundplatform.entity.CasebasemessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

/**
 * Created by wazto on 2019/3/22.
 */
public interface IcaseBaseService {
    /**
     * @Description:根据id获取案件关联信息
     * @Param:[id]
     */
    CasebasemessageAllEntity findAllInfoById(Integer id);

    /**
     * @Desc: 根据id获取案件基本信息
     * @Param: [id]
     */
    CasebasemessageEntity findInfoById(Integer id);

    /**
     * @Desc: 分页获取案件信息
     * @Param: [pageable]
     */
    Page<CasebasemessageEntity> findAllByPage(Pageable pageable);

    /**
     * @Desc: 根据批次号 分页获取案件信息
     * @Param: [pch, pageable]
     */
    Page<CasebasemessageEntity> findAllByPchAndPage(String pch, Pageable pageable);

    /**
     * 多条件查询
     */
    Page<CasebasemessageEntity> findAllByCondition(String pcid, String ajmc, Integer ajlx, Integer ajzt, Integer ajqy, Date begin, Date end, Pageable pageable);
}
