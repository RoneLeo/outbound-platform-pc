package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.DictionaryEntity;

import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.service.IdictionaryService;
import com.chiyun.outboundplatform.utils.StringUtil;
import io.swagger.annotations.*;


import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adminostrator on 2019-03-14.
 */
@Api(description = "字典信息类型表")
@RestController
@RequestMapping(value = "/dict")
public class DictionaryController {

      @Resource
      private IdictionaryService idictionaryService;  //字典名




     @ApiOperation("查询所有字典信息")
     @RequestMapping(value ="/findDictAll" ,method = {RequestMethod.GET, RequestMethod.POST})
     @ApiImplicitParam(paramType = "query", name = "state", value = "字典状态 [0 :未注销/1:注销],不填写后台默认查全部", required = false, dataType = "String")
     public ApiResult<Object> findDictAll(String state){
             List<DictionaryEntity> list= idictionaryService.findAll( state);
             return ApiResult.SUCCESS(list);

     }

    @ApiOperation("根据字典Id查询字典信息")
    @RequestMapping(value = "/findDictById",method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParam(paramType="query", name = "id", value = "ID", required = true, dataType = "Integer")
    public  ApiResult<Object>  findDictById(@RequestParam @ApiParam(required = true,value = "ID") Integer  id){
        if(id==0 ){
            return   ApiResult.FAILURE("id不能为空！！");
        }
        DictionaryEntity  entity= idictionaryService.findById(id);
        if(entity==null){
            return  ApiResult.FAILURE("未查询到该字典！");
        }
        return  ApiResult.SUCCESS(entity);
    }


    @ApiOperation("根据字典中文名和字典状态模糊查询字典信息")
    @RequestMapping(value = "/findDictByNameAndState",method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "字典中文名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "state", value = "字典状态 [0 :未注销/1:注销],不填写后台默认查全部满足条件的", required = false, dataType = "String")
    })
    public  ApiResult<Object>  findDictByNameAndState(@RequestParam  String  name ,String state){
        if(StringUtil.isNull(name)){
            return   ApiResult.FAILURE("字典中文名不能为空！！");
        }
          List<DictionaryEntity>  list= idictionaryService.findDictByNameAndState(name,state);
        if(list==null||list.size()==0){
            return  ApiResult.FAILURE("未查询到该字典！");
        }else if(list.size()>0){
            return  ApiResult.SUCCESS(list);
        }
        else{
            return   ApiResult.FAILURE("查询失败");
        }


    }

    @ApiOperation("根据字典英文名和字典状态模糊查询字典信息")
    @RequestMapping(value = "/findDictByEng_NameAndState",method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "eng_name", value = "字典英文名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "State", value = "字典状态 [0 :未注销/1:注销],不填写后台默认查全部满足条件的", required = false, dataType = "String")
    })
    public  ApiResult<Object>  findDictByEng_NameAndState(@RequestParam  String  eng_name ,String state) {
        if (StringUtil.isNull(eng_name)) {
            return ApiResult.FAILURE("字典英文名不能为空！！");
        }
        List<DictionaryEntity> list = idictionaryService.findDictByEng_NameAndState(eng_name,state);
        if (list == null || list.size() == 0) {
            return ApiResult.FAILURE("未查询到该字典！");
        } else if (list.size() > 0) {
            return ApiResult.SUCCESS(list);
        } else {
            return ApiResult.FAILURE("查询失败");
        }
    }


     @ApiOperation("新增字典")
     @RequestMapping(value = "/addDict",method = RequestMethod.POST)
     @ApiImplicitParam( name = "entity", value = "字典对象", required = true, dataType = "DictionaryEntity")
     public  ApiResult<Object> addDict( @RequestBody  DictionaryEntity entity){

         if(StringUtil.isNull(entity.getZdywmc()) ){
             return  ApiResult.FAILURE("字典英文名不能为空！");
         }else if(StringUtil.isNull(entity.getZdzwmc())){
             return  ApiResult.FAILURE("字典中文名不能为空）！");
         }

         Map<String,Object>   msg=idictionaryService.save(entity);
         if( msg.keySet().equals("failuer")){
             return  ApiResult.FAILURE(msg.get("failuer").toString());
         }
         else if(msg.keySet().equals("success")){
             entity=(DictionaryEntity)msg.get("success");
              return ApiResult.SUCCESS(entity);
          }
          else {
             return  ApiResult.FAILURE("新增失败");
         }

     }

    @ApiOperation("更新字典")
    @RequestMapping(value = "/updateDict",method = RequestMethod.POST)
    @ApiImplicitParam( name = "entity", value = "字典对象", required = true, dataType = "DictionaryEntity")
      public  ApiResult<Object>  updateDict(  @RequestBody DictionaryEntity entity){
         //判断对象是否存在  ，其次才去更新
      if(entity.getId()==0){
          return  ApiResult.FAILURE("主键不能为空！！");
      }
             boolean   flag= idictionaryService.existsById(entity.getId());
        if(flag=true){
            int cn=idictionaryService.update(entity);
            if(cn==1){

                return ApiResult.SUCCESS("更新成功");
            }else{
                return  ApiResult.FAILURE("更新失败");
            }
        }else{

           return  ApiResult.FAILURE("更新失败，没有找到要更新的记录");
       }

      }


    @ApiOperation("根据id 注销字典，数据还保留在数据库中")
    @RequestMapping(value = "/cancellationDictById",method = RequestMethod.POST)
    @ApiImplicitParam(paramType="query", name = "id", value = "ID", required = true, dataType = "Integer")
    public  ApiResult<Object>  cancellationDictById( @RequestParam  Integer id){
        if(id==0 ){
            return  ApiResult.FAILURE("主键不能为空！！");
        }

        int cn=idictionaryService.cancellationById(id);

        if(cn==1){
                return ApiResult.SUCCESS("注销成功");
        }else{
            return  ApiResult.FAILURE("注销失败，可能已经注销了");
        }

    }


      //前期开发使用，上线时需要注销掉这个方法
     @ApiOperation("根据id删除字典，数据不保留")
     @RequestMapping(value = "/deleteDictById" ,method = RequestMethod.POST)
    public  ApiResult<Object>  deleteDictById(@RequestParam @ApiParam(required = true,value = "ID") Integer id) {

        if (id == 0) {
            return ApiResult.FAILURE("主键不能为空！！");
        }
        int con = idictionaryService.deleteById(id);
        if (con == 1) {
                return ApiResult.SUCCESS("删除字典成功");
        }  else {
            return ApiResult.FAILURE("删除失败，可能对应的字典已经被删除了");
        }
    }







}
