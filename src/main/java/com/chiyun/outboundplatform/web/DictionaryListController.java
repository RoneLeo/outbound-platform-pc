package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;

import com.chiyun.outboundplatform.entity.DictionarylistEntity;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.service.IdictionaryService;
import com.chiyun.outboundplatform.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Linqi on 2019-03-15.
 */
@Api(description = "字典项信息类型表")
@RestController
@RequestMapping(value = "/dict")
public class DictionaryListController {

    @Resource
    private IdictionaryService idictionaryService;  //字典名

    @Resource
    private IdictionaryListService idictionaryListService; //字典项

    @ApiOperation("查询所有字典项信息")
    @RequestMapping(value = "/findDictListAll",method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParam(paramType = "query", name = "state", value = "字典项状态 [0 :未注销/1:注销],不填写后台默认查全部", required = false, dataType = "String")
    public ApiResult<Object> findDictListAll(String state){

            List<DictionarylistEntity> list = idictionaryListService.findAll(state);
            return ApiResult.SUCCESS(list);

    }



    @ApiOperation("新增一个字典项")
    @RequestMapping(value ="/addDictList" ,method = RequestMethod.POST)
    @ApiImplicitParam( name = "entity", value = "字典项对象", required = true, dataType = "DictionarylistEntity")
    public ApiResult<Object> addDictList(@RequestBody DictionarylistEntity entity){

        Map<String,Object> msg=idictionaryListService.save(entity);
       //   if(msg.equals("ok")){


      //        if(==null){
                  return  ApiResult.FAILURE("添加失败");
     //         }
   //           return ApiResult.SUCCESS("添加成功");
    //      }else{
   //           return  ApiResult.FAILURE(msg);
   //       }



    }



    @ApiOperation("根据【主键】删除单个字典项")
    @RequestMapping(value = "/deleteDictListById",method = RequestMethod.POST)
    @ApiImplicitParam(paramType="query", name = "id", value = "主键", required = true, dataType = "Integer")
    public  ApiResult<Object>  deleteDictListById(Integer id){
        if(id==0){
            return ApiResult.FAILURE("删除失败,主键不能为空！！");
        }
        int con=idictionaryListService.deleteById(id);
        if(con==1){
            return ApiResult.SUCCESS("删除成功");
        }else{
            return ApiResult.FAILURE("删除失败");
        }

    }


    @ApiOperation("根据[字典名表主键]批量删除字典项")
    @RequestMapping(value = "/deleteDictListByDid",method = RequestMethod.POST)
    @ApiImplicitParam(paramType="query", name = "did", value = "外键", required = true, dataType = "Integer")
      public  ApiResult<Object>  deleteDictListByDid(Integer did){

        if(did==0){
            return ApiResult.FAILURE("删除失败,主键不能为空！！");
        }
        int con=idictionaryListService.deleteByDid(did);
        if(con>=0){
            return ApiResult.SUCCESS("删除成功,共删除了:"+con+"个字典项");
        }else{
            return ApiResult.FAILURE("删除失败");
        }
      }







    @ApiOperation("更新一个字典项")
    @RequestMapping(value = "/updateDictList",method = RequestMethod.POST)
    @ApiImplicitParam( name = "entity", value = "字典项对象", required = true, dataType = "DictionarylistEntity")
    public ApiResult<Object> updateDictList (@RequestBody DictionarylistEntity entity){

           if(entity.getId()==0){
               return  ApiResult.FAILURE("主键不能为空");
           }
       // entity= DicListEntuty(entity) ;  //信息补全
        int  cn=idictionaryListService.updateOne(entity);
        if(cn==1){
            return ApiResult.SUCCESS("更新成功");
        } else if (cn==0){
            return ApiResult.FAILURE("更新成功,但0条记录被更新了");
        }else if(cn>1){
            return ApiResult.FAILURE("更新失败,但"+cn+"条记录被更新");
        }
        else{
            return  ApiResult.FAILURE("更新失败");
        }


    }



    @ApiOperation("根据[字典名表主键]注销多个关联的字典项")
    @RequestMapping(value = "/zhuxiaoDicListByDid",method = RequestMethod.POST)
    @ApiImplicitParam(paramType="query", name = "did", value = "外键", required = true, dataType = "Integer")
    public  ApiResult<Object>  zhuxiaoDicListByDid(Integer did){
             if(did==0){
                 return   ApiResult.FAILURE("did 外键不能为空");
             }
            int cn= idictionaryListService.zhuXiaoByDid(did);
             if (cn>0){
                 return  ApiResult.SUCCESS("注销成功，共注销了："+cn+"个字典项");
             }
             else{
                 return  ApiResult.FAILURE("注销失败！！");
             }


    }


    @ApiOperation("根据[主键]注销单个字典项")
    @RequestMapping(value = "/zhuxiaoDicListById",method = RequestMethod.POST)
    @ApiImplicitParam(paramType="query", name = "id", value = "主键", required = true, dataType = "Integer")
    public  ApiResult<Object>   zhuxiaoDicListById(Integer id){
        if(id==0){
            return   ApiResult.FAILURE("did 外键不能为空");
        }
        int cn= idictionaryListService.zhuXiaoOne(id);
        if (cn==1){
            return  ApiResult.SUCCESS("注销成功");
        } else if(cn==0){
            return  ApiResult.FAILURE("注销失败,零条记录被注销");
        } else if(cn>1){
            return ApiResult.FAILURE("注销成功,但"+cn+"条记录被注销了");
        }
        else{
            return  ApiResult.FAILURE("注销失败！！");
        }

    }



    @ApiOperation("根据[主键]查询单个字典项")
    @RequestMapping(value = "/findDictListById",method = RequestMethod.POST)
    @ApiImplicitParam(paramType="query", name = "id", value = "主键", required = true, dataType = "Integer")
     public ApiResult<Object>  findDictListById (Integer id){
        if(id==0){
            return   ApiResult.FAILURE("did 外键不能为空");
        }

            DictionarylistEntity entity = idictionaryListService.findById(id);
        if(entity==null){
            return  ApiResult.FAILURE("未查询到该字典项！");
        }
            return  ApiResult.SUCCESS(entity);





    }


    @ApiOperation("根据[字典名表主键]查询关联的多个字典项")
    @RequestMapping(value = "/findDictListByDid",method = RequestMethod.POST)
    @ApiImplicitParam(paramType="query", name = "did", value = "外键", required = true, dataType = "Integer")
    public  ApiResult<Object>  findDictListByDid(Integer did,String state){
        if(did==0){
            return   ApiResult.FAILURE("did 外键不能为空");
        }
        List<DictionarylistEntity> entitys = idictionaryListService.findBydid(did);
        if(entitys==null||entitys.size()==0){
            return  ApiResult.FAILURE("未查询到该字典项！");
        }
        return  ApiResult.SUCCESS(entitys);

    }


    @ApiOperation("根据[字典中文名]查询关联的多个字典项")
    @RequestMapping(value = "/findDictListByZdzwm",method = RequestMethod.POST)
    @ApiImplicitParam(paramType="query", name = "zdzwm", value = "字典中文名", required = true, dataType = "String")
     public  ApiResult<Object>  findDictListByZdzwm(String  zdzwm){
        if(StringUtil.isNull(zdzwm)){
            return  ApiResult.FAILURE("字典中文名不能为空！！");
        }
         List<DictionarylistEntity>  entitys=idictionaryListService.findByZdzwm(zdzwm);
         if(entitys==null||entitys.size()==0){
             return  ApiResult.FAILURE("未查询到该字典项！");
         }
         return  ApiResult.SUCCESS(entitys);
     }



    @ApiOperation("根据[字典英文名]查询关联的多个字典项")
    @RequestMapping(value = "/findDictListByZdywm",method = RequestMethod.POST)
    @ApiImplicitParam(paramType="query", name = "zdywm", value = "字典英文名", required = true, dataType = "String")
    public  ApiResult<Object>  findDictListByZdywm(String  zdywm){
        if(StringUtil.isNull(zdywm)){
            return  ApiResult.FAILURE("字典中文名不能为空！！");
        }
        List<DictionarylistEntity>  entitys=idictionaryListService.findByZdywm(zdywm);
        if(entitys==null||entitys.size()==0){
            return  ApiResult.FAILURE("未查询到该字典项！");
        }
        return  ApiResult.SUCCESS(entitys);
    }








}
