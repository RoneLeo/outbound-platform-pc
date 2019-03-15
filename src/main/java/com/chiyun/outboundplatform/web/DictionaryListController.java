package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.DictionaryEntity;
import com.chiyun.outboundplatform.entity.DictionaryListEntity;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.service.IdictionaryService;
import com.chiyun.outboundplatform.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "/dict", method = {RequestMethod.GET, RequestMethod.POST})
public class DictionaryListController {

    @Resource
    private IdictionaryService idictionaryService;  //字典名

    @Resource
    private IdictionaryListService idictionaryListService; //字典项

    @ApiOperation("查询所有字典项信息")
    @RequestMapping("/findDictListAll")
    public ApiResult<Object> findDictListAll(){
        List<DictionaryListEntity> list= idictionaryListService.findAll();
        return ApiResult.SUCCESS(list);
    }

    @ApiOperation("添加一个字典项")
    @RequestMapping(value ="/addDictList" ,method = RequestMethod.POST)
    public ApiResult<Object> addDictList(DictionaryListEntity entity){

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

    @ApiOperation("根据主键删除单个字典项")
    @RequestMapping(value = "/deleteDictListById",method = RequestMethod.POST)
    public  ApiResult<Object>  deleteDictListById(Integer id){
        int con=idictionaryListService.deleteById(id);
        if(con==1){
            return ApiResult.SUCCESS("删除成功");
        }else{
            return ApiResult.FAILURE("删除失败");
        }

    }

    @ApiOperation("根据字典英文名和字典项代号查询字典项的值")
    @RequestMapping("/queryByZdywmAndKey")
    public  ApiResult<Object> queryByZdywmAndKey(String zdywm, String key){

        String  value= idictionaryListService.querDictListByZdywmAndKey(zdywm,key);
        if(StringUtil.isNull(value)){
            return  ApiResult.FAILURE("查询失败，未查询到对应的值");
        }
        return  ApiResult.SUCCESS(value);
    }

    @ApiOperation("根据字典中文名和字典项代号查询字典项的值")
    @RequestMapping("/queryByZdzwmAndKey")
    public  ApiResult<Object> queryByZdzwmAndKey(String zdzwm, String key){
        String  value= idictionaryListService.querDictListByZdzwmAndKey(zdzwm,key);
        if(StringUtil.isNull(value)){
            return  ApiResult.FAILURE("查询失败，未查询到对应的值");
        }
        return  ApiResult.SUCCESS(value);
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

    // 避免某些属性为空，导致sql报错
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

}
