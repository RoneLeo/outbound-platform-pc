package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiPageResult;
import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.common.SessionHelper;
import com.chiyun.outboundplatform.entity.LogEntity;
import com.chiyun.outboundplatform.entity.UserEntity;
import com.chiyun.outboundplatform.repository.LogRepository;
import com.chiyun.outboundplatform.utils.OtherUtils;
import com.chiyun.outboundplatform.utils.SessionUtil;
import com.chiyun.outboundplatform.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.awt.SunHints;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Api(description = "日志管理")
@RestController
@RequestMapping(value = "/log",method = {RequestMethod.POST, RequestMethod.GET})
public class LogController {

    @Resource
    private LogRepository logRepository;

    @Value("${enable}")
    boolean enable;

    public void addLog(LogEntity logEntity) throws Exception{
        if (enable==true){
        if (logEntity.getCjsj() == null)
            logEntity.setCjsj(new Date());
            logRepository.save(logEntity);
        }
    }

    @ApiOperation("删除日志")
    @RequestMapping("/delete")
    public ApiResult<Object> delete(@RequestParam(required = true) @ApiParam(value = "数据id") int id){
        //判断是否登录
        HttpSession session = SessionHelper.getSession();
        ApiResult<Object> isLogin = SessionUtil.isLogin(session);
        if (isLogin.getResCode() < 200) return isLogin;
        //删除操作
        LogEntity oldLogEntity = logRepository.findById(id);
        if(oldLogEntity==null){
            return ApiResult.FAILURE("没有该条数据");
        }
        int result = logRepository.deleteById(id);
        if (result==0){
            return ApiResult.FAILURE("删除失败");
        }
        return ApiResult.SUCCESS("删除成功");
    }

    @ApiOperation("查询日志")
    @RequestMapping("/findAll")
    public ApiResult<Object> findAll(@RequestParam(required = false) @ApiParam(value = "操作人") String czr,
                                     @RequestParam(required = false) @ApiParam(value = "操作事件") String czsj,
                                     @RequestParam(required = false) @ApiParam(value = "开始时间(只传开始时间则查开始时间之后的数据)格式（2019-04-03 17:03:35）") Date kssj,
                                     @RequestParam(required = false) @ApiParam(value = "结束时间(只传结束时间则查结束时间之前的数据)格式（2019-04-03 17:03:35）") Date jssj,
                                     @RequestParam int page, @RequestParam int pagesize){
        //判断是否登录
        HttpSession session = SessionHelper.getSession();
        ApiResult<Object> isLogin = SessionUtil.isLogin(session);
        if (isLogin.getResCode() < 200) return isLogin;
        //查询操作
        Pageable pageable = PageRequest.of(page - 1, pagesize, Sort.by(new Sort.Order(Sort.Direction.DESC, "create_time")));
        Page<LogEntity> result;
        String userName=OtherUtils.nullReplace(czr);
        String event=OtherUtils.nullReplace(czsj);
        if (kssj == null && jssj == null) {
            //时间为空
            result = logRepository.findByCzrAndCzsj(userName, event, pageable);
        } else if (kssj == null) {
            //开始时间为空
            result = logRepository.findByCzrAndCzsjAndJssj(userName, event, jssj, pageable);
        } else if (kssj == null) {
            //结束时间为空
            result = logRepository.findByCzrAndCzsjAndKssj(userName, event, kssj, pageable);
        } else {
            //时间不为空
            result = logRepository.findByCzrAndCzsjAndKssjAndJssj(userName, event, kssj, jssj, pageable);
        }
        return ApiPageResult.SUCCESS(result.getContent(), page, pagesize, result.getTotalElements(), result.getTotalPages());
    }

}
