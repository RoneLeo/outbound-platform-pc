package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.DictionaryEntity;

import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.service.IdictionaryService;
import com.chiyun.outboundplatform.utils.StringUtil;
import io.swagger.annotations.*;


import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adminostrator on 2019-03-14.
 */
@Api(description = "字典信息管理")
@RestController
@RequestMapping(value = "/dict")
public class DictionaryController {

    @Resource
    private IdictionaryService idictionaryService;  //字典名

    @Resource
    private IdictionaryListService idictionaryListService; //字典项

    @ApiOperation("查询字典表信息：" +
            "说明：传入参数都不填写表示查询所有的数据！！")
    @RequestMapping(value = "/findDicts", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "zdmc", value = "字典名称,不填写 表示这个不作为查询条件", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "zddm", value = "字典代码，不填写 表示这个不作为查询条件", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "zxbz", value = "注销标志 [0 :未注销/1:注销],不填写表示这个不作为查询条件", required = false, dataType = "String")

    })
    public ApiResult<Object> findDicts(@RequestParam(required = false) String zxbz,
                                       @RequestParam(required = false) String zddm,
                                       @RequestParam(required = false) String zdmc) {
        List<DictionaryEntity> list;
        if (StringUtil.isNull(zddm) && StringUtil.isNull(zdmc)) {
            list = idictionaryService.findAll(zxbz);
        } else if (!StringUtil.isNull(zddm) && StringUtil.isNull(zdmc)) {
            list = idictionaryService.findDictByEng_NameAndZxbz(zddm, zxbz);
        } else if (StringUtil.isNull(zddm) && !StringUtil.isNull(zdmc)) {
            list = idictionaryService.findDictByNameAndZxbz(zdmc, zxbz);
        } else {
            list = idictionaryService. findByZdmcAndZddmAndZxbz(zdmc, zddm, zxbz);
        }

        return ApiResult.SUCCESS(list);

    }

    @ApiOperation("根据字典Id查询单条字典信息")
    @RequestMapping(value = "/findDictById", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParam(paramType = "query", name = "id", value = "ID", required = true, dataType = "Integer")
    public ApiResult<Object> findDictById(@RequestParam Integer id) {
        if (id == 0) {
            return ApiResult.FAILURE("id不能为空！！");
        }
        DictionaryEntity entity = idictionaryService.findById(id);
        if (entity == null) {
            return ApiResult.FAILURE("未查询到该字典！");
        }
        return ApiResult.SUCCESS(entity);
    }


    @ApiOperation("新增字典")
    @RequestMapping(value = "/addDict", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "zdmc", value = "字典名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "zddm", value = "字典代码", required = true, dataType = "String")
    })
    public ApiResult<Object> addDict(@RequestParam String zdmc,
                                     @RequestParam String zddm) {

        if (StringUtil.isNull(zdmc)) {
            return ApiResult.FAILURE("字典中文名不能为空！");
        } else if (StringUtil.isNull(zddm)) {
            return ApiResult.FAILURE("字典英文名不能为空）！");
        }
        DictionaryEntity entity = new DictionaryEntity();
        entity.setZddm(zddm);
        entity.setZdmc(zdmc);

        Map<String, Object> msg = idictionaryService.save(entity);
        String str = msg.keySet().toString().replace("[", " ").replace("]", " ").trim();
        if ("failuer".equals(str)) {
            return ApiResult.FAILURE(msg.get("failuer").toString());
        } else if ("success".equals(str)) {
            entity = (DictionaryEntity) msg.get("success");
            return ApiResult.SUCCESS(entity);
        } else {
            return ApiResult.FAILURE("新增失败");
        }

    }

    @ApiOperation("更新字典")
    @RequestMapping(value = "/updateDict", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "字典ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "zdmc", value = "字典名称", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "zddm", value = "字典代码", required = false, dataType = "String")
    })
    public ApiResult<Object> updateDict(@RequestParam Integer id,
                                        @RequestParam(required = false) String zdmc,
                                        @RequestParam(required = false) String zddm) {
        //判断对象是否存在  ，其次才去更新
        if (id == 0) {
            return ApiResult.FAILURE("主键不能为空！！");
        }

        DictionaryEntity entity1 = idictionaryService.findById(id);
        if (!(entity1 == null)) {
            DictionaryEntity entity = new DictionaryEntity();
            entity.setId(id);
            if (StringUtil.isNull(zdmc)) {
                entity.setZdmc(entity1.getZdmc());
            } else {
                entity.setZdmc(zdmc);
            }
            if (StringUtil.isNull(zddm)) {
                entity.setZddm(entity1.getZddm());
            } else {
                entity.setZddm(zddm);
            }
            entity.setZxbz(entity1.getZxbz());


            int cn = idictionaryService.update(entity);
            if (cn == 1) {

                return ApiResult.SUCCESS("更新成功");
            } else {
                return ApiResult.FAILURE("更新失败");
            }
        } else {

            return ApiResult.FAILURE("更新失败，没有找到要更新的记录");
        }

    }


    @ApiOperation("根据id 注销字典")
    @RequestMapping(value = "/cancellationDictById", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "query", name = "id", value = "ID", required = true, dataType = "Integer")
    public ApiResult<Object> cancellationDictById(@RequestParam Integer id) {
        if (id == 0) {
            return ApiResult.FAILURE("主键不能为空！！");
        }

        int cn = idictionaryService.cancellationById(id);

        if (cn == 1) {
            return ApiResult.SUCCESS("注销成功");
        } else {
            return ApiResult.FAILURE("注销失败，可能已经被注销了");
        }

    }

    @ApiOperation("根据id 激活字典")
    @RequestMapping(value = "/activationDictById", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "query", name = "id", value = "ID", required = true, dataType = "Integer")
    public ApiResult<Object> activationDictById(@RequestParam Integer id) {
        if (id == 0) {
            return ApiResult.FAILURE("主键不能为空！！");
        }

        int cn = idictionaryService.unCancellationById(id);

        if (cn == 1) {
            return ApiResult.SUCCESS("激活成功");
        } else {
            return ApiResult.FAILURE("激活失败，可能已经被激活了");
        }
    }


    //前期开发使用，上线时需要注销掉这个方法
    @ApiOperation("根据id删除字典包括对应的字典项信息，数据不保留")
    @RequestMapping(value = "/deleteDictById", method = RequestMethod.POST)
    public ApiResult<Object> deleteDictById(@RequestParam @ApiParam(required = true, value = "ID") Integer id) {

        if (id == 0) {
            return ApiResult.FAILURE("主键不能为空！！");
        }
        idictionaryListService.deleteByDid(id);//级联删除字典项的信息，方式由于外键关联原因，无法成功删除
        int con = idictionaryService.deleteById(id);
        if (con == 1) {
            return ApiResult.SUCCESS("删除字典成功");
        } else {
            return ApiResult.FAILURE("删除失败，可能对应的字典已经被删除了");
        }
    }


}
