//package com.chiyun.outboundplatform.web;
//
//import com.chiyun.outboundplatform.common.ApiResult;
//import com.chiyun.outboundplatform.entity.BasetypeEntity;
//import com.chiyun.outboundplatform.repository.BasetypeRepository;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//@Api(description = "基础信息类型表")
//@RestController
//@RequestMapping(value = "/baseType", method = {RequestMethod.GET, RequestMethod.POST})
//public class BaseTypeController {
//
//    @Resource
//    private BasetypeRepository basetypeRepository;
//
//
//    @ApiOperation("查询所有")
//    @RequestMapping("/findAll")
//    public ApiResult<Object> findAll() {
//        List<BasetypeEntity> list = basetypeRepository.findAll();
//        return ApiResult.SUCCESS(list);
//    }
//}
