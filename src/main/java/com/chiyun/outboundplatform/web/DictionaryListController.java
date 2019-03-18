package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.DictionaryEntity;
import com.chiyun.outboundplatform.entity.DictionaryListEntity;
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
    public ApiResult<Object> findDictListAll(){

            List<DictionaryListEntity> list = idictionaryListService.findAll();
            return ApiResult.SUCCESS(list);

    }



    @ApiOperation("新增一个字典项")
    @RequestMapping(value ="/addDictList" ,method = RequestMethod.POST)
    @ApiImplicitParam( name = "entity", value = "字典项对象", required = true, dataType = "DictionaryListEntity")
    public ApiResult<Object> addDictList(@RequestBody DictionaryListEntity entity){

        String msg= pramesvalud(entity);
          if(msg.equals("ok")){
              entity= EntityisHebing(entity);
              entity.setZxbz("0"); //默认值为“0”，标志未注销
              DictionaryListEntity entity1=idictionaryListService.save(entity);
              if(entity1==null){
                  return  ApiResult.FAILURE("添加失败");
              }
              return ApiResult.SUCCESS("添加成功");
          }else{
              return  ApiResult.FAILURE(msg);
          }



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



    @ApiOperation("根据[字典英文名和字典项代号]查询字典项的值")
    @RequestMapping(value="/queryDictListByZdywmAndKey",method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "zdywm", value = "字典英文名", required = true, dataType = "String"),
          @ApiImplicitParam(paramType ="query",name = "key",value = "字典项代码",required = true,dataType = "String")
    })
    public  ApiResult<Object> queryDictListByZdywmAndKey(String zdywm, String key){
        if(StringUtil.isNull(zdywm)){
            return  ApiResult.FAILURE("(zdywm):字典英文名不能为空!!!");
        }
        if(StringUtil.isNull(key)){
            return  ApiResult.FAILURE("(key):字典项代码不能为空!!!");
        }
        String  value= idictionaryListService.querDictListByZdywmAndKey(zdywm,key);
        if(StringUtil.isNull(value)){
            return  ApiResult.FAILURE("查询失败，未查询到对应的值");
        }
        return  ApiResult.SUCCESS(value);
    }



    @ApiOperation("根据[字典中文名和字典项代号]查询字典项的值")
    @RequestMapping(value = "/queryDictListByZdzwmAndKey",method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "zdzwm", value = "字典中文名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType ="query",name = "key",value = "字典项代码",required = true,dataType = "String")
    })
    public  ApiResult<Object> queryDictListByZdzwmAndKey(String zdzwm, String key){
        if(StringUtil.isNull(zdzwm)){
            return  ApiResult.FAILURE("(zdywm):字典中文名不能为空!!!");
        }
        if(StringUtil.isNull(key)){
            return  ApiResult.FAILURE("(key):字典项代码不能为空!!!");
        }
        String  value= idictionaryListService.querDictListByZdzwmAndKey(zdzwm,key);
        if(StringUtil.isNull(value)){
            return  ApiResult.FAILURE("查询失败，未查询到对应的值");
        }
        return  ApiResult.SUCCESS(value);
    }



    @ApiOperation("更新一个字典项")
    @RequestMapping(value = "/updateDictList",method = RequestMethod.POST)
    @ApiImplicitParam( name = "entity", value = "字典项对象", required = true, dataType = "DictionaryListEntity")
    public ApiResult<Object> updateDictList (@RequestBody DictionaryListEntity entity){

           if(entity.getId()==0){
               return  ApiResult.FAILURE("主键不能为空");
           }
        entity= DicListEntuty(entity) ;  //信息补全
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

            DictionaryListEntity entity = idictionaryListService.findById(id);
        if(entity==null){
            return  ApiResult.FAILURE("未查询到该字典项！");
        }
            return  ApiResult.SUCCESS(entity);





    }


    @ApiOperation("根据[字典名表主键]查询关联的多个字典项")
    @RequestMapping(value = "/findDictListByDid",method = RequestMethod.POST)
    @ApiImplicitParam(paramType="query", name = "did", value = "外键", required = true, dataType = "Integer")
    public  ApiResult<Object>  findDictListByDid(Integer did){
        if(did==0){
            return   ApiResult.FAILURE("did 外键不能为空");
        }
        List<DictionaryListEntity> entitys = idictionaryListService.findBydid(did);
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
         List<DictionaryListEntity>  entitys=idictionaryListService.findByZdzwm(zdzwm);
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
        List<DictionaryListEntity>  entitys=idictionaryListService.findByZdywm(zdywm);
        if(entitys==null||entitys.size()==0){
            return  ApiResult.FAILURE("未查询到该字典项！");
        }
        return  ApiResult.SUCCESS(entitys);
    }



    //不能通过其他地方获取到的不为空的字段校验
    private  String   pramesvalud(DictionaryListEntity entity){
          String str=null;
          if( entity.getDid()==null||entity.getDid()==0){
              str=" 关联字典名表主键(did) 不能为空!!";
          }else if(StringUtil.isNull(entity.getC_id())){
              str="字典项词条代码（C_id）不能为空!!";
          }else if(StringUtil.isNull(entity.getC_name())){
              str="字典项词条值（C_name）不能为空！！";
          } else {
              str="ok";
          }
        return  str;

    }

    // 避免某些属性为空，导致sql报错   新增的时候用到
    private DictionaryListEntity EntityisHebing(DictionaryListEntity fromEn){

        if(!StringUtil.isNull(fromEn.getZdzwm())&&!StringUtil.isNull(fromEn.getZdywm())){
            return  fromEn;
        }else {
            DictionaryEntity queEn= idictionaryService.findById(fromEn.getDid());
            if (StringUtil.isNull(fromEn.getZdzwm())) {
                fromEn.setZdzwm(queEn.getName());
            } else if (StringUtil.isNull(fromEn.getZdywm())) {
                fromEn.setZdywm(queEn.getEng_name());
            }
            return  fromEn;
        }
    }
        //更新的时候用到 单个
        private    DictionaryListEntity DicListEntuty(DictionaryListEntity fromEn) {
            DictionaryListEntity  queEn=idictionaryListService.findById(fromEn.getId());
             if(StringUtil.isNull(fromEn.getZdzwm())){ fromEn.setZdzwm(queEn.getZdzwm()); }
             if(StringUtil.isNull(fromEn.getZdywm())){fromEn.setZdywm(queEn.getZdywm());}
             if(StringUtil.isNull(fromEn.getC_id())){fromEn.setC_id(queEn.getC_id());}
             if(StringUtil.isNull(fromEn.getC_name())){fromEn.setC_name(queEn.getC_name());}
             if(StringUtil.isNull(fromEn.getZxbz())){fromEn.setZxbz(queEn.getZxbz());}
             if(fromEn.getDid()==0){fromEn.setDid(queEn.getDid());}
            return  fromEn;
        }


}
