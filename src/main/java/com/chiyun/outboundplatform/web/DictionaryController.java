package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.DictionaryEntity;

import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.service.IdictionaryService;
import com.chiyun.outboundplatform.utils.StringUtil;
import io.swagger.annotations.*;


import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by adminostrator on 2019-03-14.
 */
@Api(description = "字典信息类型表")
@RestController
@RequestMapping(value = "/dict")
public class DictionaryController {

      @Resource
      private IdictionaryService idictionaryService;  //字典名

      @Resource
      private IdictionaryListService idictionaryListService; //字典项


     @ApiOperation("查询所有字典信息")
     @RequestMapping(value ="/findDictAll" ,method = {RequestMethod.GET, RequestMethod.POST})
     public ApiResult<Object> findDictAll(){

             List<DictionaryEntity> list= idictionaryService.findAll();
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


    @ApiOperation("根据字典中文名模糊查询字典信息")
    @RequestMapping(value = "/findDictByName",method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParam(paramType="query", name = "name", value = "字典中文名", required = true, dataType = "String")
    public  ApiResult<Object>  findDictByName(@RequestParam  String  name){
        if(StringUtil.isNull(name)){
            return   ApiResult.FAILURE("字典中文名不能为空！！");
        }
          List<DictionaryEntity>  list= idictionaryService.findByName(name);
        if(list==null||list.size()==0){
            return  ApiResult.FAILURE("未查询到该字典！");
        }else if(list.size()>0){
            return  ApiResult.SUCCESS(list);
        }
        else{
            return   ApiResult.FAILURE("查询失败");
        }


    }

    @ApiOperation("根据字典英文文名模糊查询字典信息")
    @RequestMapping(value = "/findDictByEng_Name",method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParam(paramType="query", name = "eng_name", value = "字典英文名", required = true, dataType = "String")
    public  ApiResult<Object>  findDictByEng_Name(@RequestParam  String  eng_name) {
        if (StringUtil.isNull(eng_name)) {
            return ApiResult.FAILURE("字典中文名不能为空！！");
        }
        List<DictionaryEntity> list = idictionaryService.findByEng_Name(eng_name);
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
         if(StringUtil.isNull(entity.getEng_name()) ){
             return  ApiResult.FAILURE("添加失败,字典英文名不能为空（字典名代码）！");
         }else if(StringUtil.isNull(entity.getName())){
             return  ApiResult.FAILURE("添加失败,字典中文名不能为空（字典名称）！");
         }else  if(StringUtil.isNull(entity.getType())){
             return  ApiResult.FAILURE("添加失败,字典类型不能为空！！");
         }
         entity.setZxbz("0"); //默认值为“0”，标志未注销

          //开始添加
         DictionaryEntity entity1=idictionaryService.save(entity);
         if(entity1==null){
             return  ApiResult.FAILURE("添加失败");
         }
         //添加成功 返回该字典信息
         return ApiResult.SUCCESS(entity1);
     }

    @ApiOperation("更新字典")
    @RequestMapping(value = "/updateDict",method = RequestMethod.POST)
    @ApiImplicitParam( name = "entity", value = "字典对象", required = true, dataType = "DictionaryEntity")
      public  ApiResult<Object>  updateDict(  @RequestBody DictionaryEntity entity){
      if(entity.getId()==null||entity.getId()==0){
          return  ApiResult.FAILURE("主键不能为空！！");
      }
            entity= EntityisHebing(entity); //信息补全
            int cn=idictionaryService.updateOne(entity);
       if(cn==1){

           return ApiResult.SUCCESS("更新成功");
       }else{
           return  ApiResult.FAILURE("更新失败");
       }

      }


    @ApiOperation("根据id 注销字典，数据还保留在数据库中")
    @RequestMapping(value = "/zhuXiaoDict",method = RequestMethod.POST)

    public  ApiResult<Object>  zhuXiaoDict( @RequestParam @ApiParam(required=true,value = "ID") Integer id){
        if(id==0 ){
            return  ApiResult.FAILURE("主键不能为空！！");
        }

        int cn=idictionaryService.zhuXiaoOne(id);

        if(cn==1){
                return ApiResult.SUCCESS("注销成功");
        }else{
            return  ApiResult.FAILURE("注销失败，可能已经注销了");
        }

    }



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




        // 避免某些属性为空，导致sql报错
     private  DictionaryEntity  EntityisHebing(DictionaryEntity fromEn){
         DictionaryEntity queEn= idictionaryService.findById(fromEn.getId());
           if(StringUtil.isNull(fromEn.getName())){
               fromEn.setName(queEn.getName());
           }
         else if(StringUtil.isNull(fromEn.getEng_name())){
               fromEn.setEng_name(queEn.getEng_name());
          }
          else if(StringUtil.isNull(fromEn.getType())){
              fromEn.setType(queEn.getType());
          }


         return  fromEn;
     }



}
