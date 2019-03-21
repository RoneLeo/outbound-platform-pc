package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;

import com.chiyun.outboundplatform.entity.DictionaryEntity;
import com.chiyun.outboundplatform.entity.DictionarylistEntity;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.service.IdictionaryService;
import com.chiyun.outboundplatform.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("根据【字典ID】和其它信息查询对应的字典项信息")
    @RequestMapping(value = "/findDictList",method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "zdid", value = "字典ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "ctdm", value = "词条代码,不填写 表示这个不作为查询条件", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "ctmc", value = "词条名称,不填写 表示这个不作为查询条件", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "zxbz", value = "注销标志 [0 :未注销/1:注销],不填写后台默认查全部", required = false, dataType = "String")

    })
    public ApiResult<Object> findDictList(     @RequestParam Integer zdid,
                                               @RequestParam(required = false)  Integer ctdm,
                                               @RequestParam(required = false)  String ctmc,
                                               @RequestParam(required = false) String zxbz
                                          ){
            if(zdid==0){
                return ApiResult.FAILURE("字典ID不能为空");
            }
         List<DictionarylistEntity> list =null ;
            if( ctdm==null){
                ctdm=0;
            }
            if( (ctdm==0)&& StringUtil.isNull(ctmc)){
                 list = idictionaryListService.findBydid(zdid,zxbz);
            }else if (!(ctdm==0)&& StringUtil.isNull(ctmc)){
                list = idictionaryListService.findByCtdm(zdid,ctdm,zxbz);
            }
             else if((ctdm==0)&& !(StringUtil.isNull(ctmc))){
                list = idictionaryListService.findByCtz(zdid,ctmc,zxbz);
            } else {
                list = idictionaryListService.findByCtdmAndCtz(zdid,ctdm,ctmc,zxbz);
            }

            return ApiResult.SUCCESS(list);
    }


    @ApiOperation("新增字典项")
    @RequestMapping(value ="/addDictList" ,method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "zdid", value = "字典ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "ctmc", value = "项词条名称", required = true, dataType = "String")
    })
    public ApiResult<Object> addDictList(@RequestParam  Integer zdid,
                                        // @RequestParam  String  ctdm,
                                         @RequestParam  String  ctmc
                                         ){

         //第一步获取字典表信息
        DictionaryEntity zdxx =idictionaryService.findById(zdid);

             if(zdxx==null){
                 return  ApiResult.FAILURE("添加失败,未找到对应的字典");
             }else if(zdxx.getZxbz().equals('1')){
                 return  ApiResult.FAILURE("添加失败,该字典已注销，请先去激活");
             }

        DictionarylistEntity entity=new DictionarylistEntity ();
                entity.setZdywmc(zdxx.getZdywmc());
                entity.setZdzwmc(zdxx.getZdzwmc());
                entity.setZdid(zdid);
                entity.setCtmc(ctmc);
                entity.setZxbz("0");// 默认填写为0,未注销

        Map<String,Object> msg=idictionaryListService.save(entity);
        String str=msg.keySet().toString().replace("["," ").replace("]"," ").trim();
       if(str.equals("success")){
              return ApiResult.SUCCESS( (DictionarylistEntity)msg.get("success"));
          }
          else if(str.equals("fail")){
           return  ApiResult.FAILURE(msg.get("fail").toString());
       }
         else{
              return  ApiResult.FAILURE("添加失败");
          }

    }


   //删除操作 在上线部署时需要去掉
    @ApiOperation("根据【ID】删除单个字典项")
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

    //删除操作 在上线部署时需要去掉
    @ApiOperation("根据[字典ID]批量删除字典项")
    @RequestMapping(value = "/deleteDictListByDid",method = RequestMethod.POST)
    @ApiImplicitParam(paramType="query", name = "zdid", value = "字典ID", required = true, dataType = "Integer")
      public  ApiResult<Object>  deleteDictListByDid(Integer zdid){

        if(zdid==0){
            return ApiResult.FAILURE("删除失败,主键不能为空！！");
        }
        int con=idictionaryListService.deleteByDid(zdid);
        if(con>=0){
            return ApiResult.SUCCESS("删除成功,共删除了:"+con+"个字典项");
        }else{
            return ApiResult.FAILURE("删除失败");
        }
      }




    @ApiOperation("更新字典项")
    @RequestMapping(value = "/updateDictList",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "ctmc", value = "词条名称", required = true, dataType = "String")
    })
    public ApiResult<Object> updateDictList (  @RequestParam  Integer id,

                                               @RequestParam  String  ctmc

               ){
           if(id==0){
               return  ApiResult.FAILURE("主键不能为空");
           }
        //查询字典项，信息完善
        DictionarylistEntity  entity= idictionaryListService.findById(id);
        if(entity==null){
            return  ApiResult.FAILURE("更新失败,未找到对应的字典项");
        }

        entity.setCtmc(ctmc);
        int  cn=idictionaryListService.updateOne(entity);
        if(cn==1){
            return ApiResult.SUCCESS("更新成功");
        }
        else{
            return  ApiResult.FAILURE("更新失败");
        }


    }



    @ApiOperation("根据ID注销字典项")
    @RequestMapping(value = "/cancellationDicListById",method = RequestMethod.POST)
    @ApiImplicitParam(paramType="query", name = "id", value = "ID", required = true, dataType = "Integer")
    public  ApiResult<Object>   cancellationDicListById(Integer id){
        if(id==0){
            return   ApiResult.FAILURE("did 外键不能为空");
        }
        int cn= idictionaryListService.cancellationDicListById(id);
        if (cn==1){
            return  ApiResult.SUCCESS("注销成功");
        }
        else{
            return  ApiResult.FAILURE("注销失败,可能已被注销了");
        }

    }

    @ApiOperation("根据ID激活已注销字典项")
    @RequestMapping(value = "/activationDicListById",method = RequestMethod.POST)
    @ApiImplicitParam(paramType="query", name = "id", value = "ID", required = true, dataType = "Integer")
    public  ApiResult<Object>  activationDicListById(Integer id){
        if(id==0){
            return   ApiResult.FAILURE("did 外键不能为空");
        }
        int cn= idictionaryListService.unCancellationDicListById(id);
        if (cn==1){
            return  ApiResult.SUCCESS("激活成功");
        }
        else{
            return  ApiResult.FAILURE("激活失败,可能已被激活");
        }

    }


    //其它模块使用

    @ApiOperation("根据主ID查询字典项")
    @RequestMapping(value = "/findDictListById",method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParam(paramType="query", name = "id", value = "主键", required = true, dataType = "Integer")
     public ApiResult<Object>  findDictListById (Integer id){
        if(id==0){
            return   ApiResult.FAILURE("id不能为空");
        }

            DictionarylistEntity entity = idictionaryListService.findById(id);
        if(entity==null){
            return  ApiResult.FAILURE("未查询到该字典项！");
        }
            return  ApiResult.SUCCESS(entity);


    }










}
