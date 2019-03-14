package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.DictionaryEntity;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.service.IdictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by adminostrator on 2019-03-14.
 */
@Api(description = "字典信息类型表")
@RestController
@RequestMapping(value = "/dict", method = {RequestMethod.GET, RequestMethod.POST})
public class DictionaryController {

      @Resource
      private IdictionaryService idictionaryService;

      @Resource
      private IdictionaryListService idictionaryListService;


    /* public  String findDictBydId( Integer id){


         return null;
     } */


     /*public  String findDictListByid(Integer id){

         return null;
     }*/
     @ApiOperation("查询所有字典名")
     @RequestMapping("/findDictAll")
     public ApiResult<Object> findDictAll(){
         List<DictionaryEntity> list= idictionaryService.findAll();
         return ApiResult.SUCCESS(list);
     }

     @ApiOperation("添加一个字典")
     @RequestMapping("/addDict")
     public  ApiResult<Object> addDict(DictionaryEntity entity){
         DictionaryEntity entity1=idictionaryService.save(entity);
         if(entity1==null){
             return  ApiResult.FAILURE("添加失败");
         }
         return ApiResult.SUCCESS("添加成功");
     }

     @ApiOperation("根据主键删除字典")
     @RequestMapping("/deleteDictById")
     public  ApiResult<Object>  deleteDictById(Integer id){
          int con=idictionaryService.deleteById(id);
          if(con==1){
              return ApiResult.SUCCESS("删除成功");
          }else{
              return ApiResult.FAILURE("删除失败");
          }

     }
}
