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
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat formatternew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
    @RequestMapping("/user/Analysis")
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

    @ApiOperation("统计选定时间内案件的变化情况,按日统计")
    @RequestMapping("/case/count")
    public ApiResult casecount(@RequestParam(required = false) @ApiParam("开始日期") Date begin,
                               @RequestParam(required = false) @ApiParam("结束日期") Date end) {
        String bdate = formatter.format(begin).concat(" 00:00:00");
        String edate = formatter.format(end).concat(" 23:59:59");
        try {
            begin = formatternew.parse(bdate);
            end = formatternew.parse(edate);
        } catch (ParseException e) {
            e.printStackTrace();
            return ApiResult.FAILURE("时间转换失败，请重试");
        }
        return ApiResult.SUCCESS(satisticsService.casecount(begin, end));
    }

    @ApiOperation("根据用户id以及时间段统计任务个状态数量")
    @RequestMapping("/task/uidcount")
    public ApiResult taskCountByUidAndDate(@RequestParam @ApiParam(value = "用户id", required = true) int uid,
                                           @RequestParam(required = false) @ApiParam("查询日期，为空则查全部,格式：yyyy-MM-dd") Date date,
                                           @RequestParam @ApiParam(value = "类型：1选定年,其他-选定月份,默认月份", required = true) Integer lx) {
        if (uid == 0)
            return ApiResult.FAILURE("错误的用户");
        if (lx == null)
            lx = 0;
        Date begin = null, end = null;
        if (date != null) {
            if (lx != 1) {
                begin = DateUtils.getMonthBegin(date);
                end = DateUtils.getMonthEnd(date);
            } else {
                begin = DateUtils.getYearBegin(date);
                end = DateUtils.getYearEnd(date);
            }
        }
        return ApiResult.SUCCESS(satisticsService.taskUidCount(uid, begin, end));
    }

    @ApiOperation("用户活跃度统计排名")
    @RequestMapping("/user/active")
    public ApiResult userActive(@RequestParam(required = false) @ApiParam("查询日期，为空则查当前时间,格式：yyyy-MM-dd") Date date,
                                @RequestParam(required = false) @ApiParam(value = "类型：1选定年,其他-选定月份,默认月份", required = false) Integer lx) {
        if (lx == null)
            lx = 0;
        Date begin, end;
        if (date == null)
            date = new Date();
        if (lx != 1) {
            begin = DateUtils.getMonthBegin(date);
            end = DateUtils.getMonthEnd(date);
        } else {
            begin = DateUtils.getYearBegin(date);
            end = DateUtils.getYearEnd(date);
        }
        return ApiResult.SUCCESS(satisticsService.userActive(begin, end));
    }

    @ApiOperation("用户业绩统计排名")
    @RequestMapping("/user/achieve")
    public ApiResult userAchieve(@RequestParam(required = false) @ApiParam("查询日期，为空则查当前时间,格式：yyyy-MM-dd") Date date,
                                 @RequestParam(required = false) @ApiParam(value = "类型：1选定年,其他-选定月份,默认月份", required = false) Integer lx) {
        if (lx == null)
            lx = 0;
        Date begin, end;
        if (date == null)
            date = new Date();
        if (lx != 1) {
            begin = DateUtils.getMonthBegin(date);
            end = DateUtils.getMonthEnd(date);
        } else {
            begin = DateUtils.getYearBegin(date);
            end = DateUtils.getYearEnd(date);
        }
        return ApiResult.SUCCESS(satisticsService.userAchieve(begin, end));
    }

    @ApiOperation("指定用户业绩统计排名")
    @RequestMapping("/user/achieveOne")
    public ApiResult userAchieveOne(@RequestParam @ApiParam(value = "用户id", required = true) int uid,
                                    @RequestParam(required = false) @ApiParam("查询日期，为空则查当前时间,格式：yyyy-MM-dd") Date date,
                                    @RequestParam(required = false) @ApiParam(value = "类型：1选定年,其他-选定月份,默认月份", required = false) Integer lx) {
        if (lx == null)
            lx = 0;
        Date begin, end;
        if (date == null)
            date = new Date();
        if (lx != 1) {
            begin = DateUtils.getMonthBegin(date);
            end = DateUtils.getMonthEnd(date);
        } else {
            begin = DateUtils.getYearBegin(date);
            end = DateUtils.getYearEnd(date);
        }
        return ApiResult.SUCCESS(satisticsService.userAchieve(uid, begin, end));
    }
}
