package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiPageResult;
import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.service.impl.SatisticsServiceImpl;
import com.chiyun.outboundplatform.utils.DateUtils;
import com.chiyun.outboundplatform.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by wazto on 2019/4/8.
 */
@Api(description = "统计页面接口")
@RestController
@RequestMapping(value = "/statistics", method = {RequestMethod.POST, RequestMethod.GET})
public class StatisticsController {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    @Resource
    private SatisticsServiceImpl satisticsService;

    @ApiOperation("案件统计分析,月度报表")
    @RequestMapping("/case/analysis")
    public ApiResult caseAnalysis(@RequestParam(required = false) @ApiParam("查询月份：yyyy-MM") String cxyf) {
        Date month = null;
        if (StringUtil.isNotNull(cxyf)) {
            try {
                month = sdf.parse(cxyf);
            } catch (ParseException e) {
                e.printStackTrace();
                return ApiResult.FAILURE("月份格式错误");
            }
        }
        try {
            return ApiResult.SUCCESS(satisticsService.caseAnalysis(month));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.FAILURE("统计失败，请重试");
        }
    }

    @ApiOperation("案件统计分析,季度报表")
    @RequestMapping("/case/quarter")
    public ApiResult caseQuarter(@RequestParam(required = false) @ApiParam("查询季度：1,2,3,4,默认当前季度") Integer jd) {
        if (jd == null || jd == 0)
            jd = DateUtils.getquarter(new Date());
        try {
            return ApiResult.SUCCESS(satisticsService.casequarter(jd));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.FAILURE("统计失败，请重试");
        }
    }

    @ApiOperation("案件类型统计分析,月度")
    @RequestMapping("/case/typeAnalysis")
    public ApiResult castTypeAnalysis(@RequestParam(required = false) @ApiParam("查询月份：yyyy-MM") String cxyf) {
        Date month = null;
        if (StringUtil.isNotNull(cxyf)) {
            try {
                month = sdf.parse(cxyf);
            } catch (ParseException e) {
                e.printStackTrace();
                return ApiResult.FAILURE("月份格式错误");
            }
        }
        try {
            return ApiResult.SUCCESS(satisticsService.castTypeAnalysis(month));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.FAILURE("统计失败，请重试");
        }
    }

    @ApiOperation("用户案件完成情况统计分析,月度")
    @RequestMapping("/people/Analysis")
    public ApiResult peoplecount(@RequestParam @ApiParam("页码") int page,
                                 @RequestParam @ApiParam("分页大小") int pagesize) {
        Pageable pageable = PageRequest.of(page - 1, pagesize);
        try {
            Page<Map<String, Object>> list = satisticsService.peoplecount(pageable);
            return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.FAILURE("统计失败，请重试");
        }

    }
}
