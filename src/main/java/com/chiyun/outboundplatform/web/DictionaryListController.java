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
@Api(description = "字典词条信息管理")
@RestController
@RequestMapping(value = "/dict")
public class DictionaryListController {

    @Resource
    private IdictionaryService idictionaryService;  //字典名

    @Resource
    private IdictionaryListService idictionaryListService; //词条

    @ApiOperation("根据【字典ID】和其它信息查询对应的词条信息")
    @RequestMapping(value = "/findDictList", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "zdid", value = "字典ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "ctdm", value = "词条代码,不填写 表示这个不作为查询条件", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "ctmc", value = "词条名称,不填写 表示这个不作为查询条件", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "zxbz", value = "注销标志 [0 :未注销/1:注销],不填写后台默认查全部", required = false, dataType = "String")

    })
    public ApiResult<Object> findDictList(@RequestParam Integer zdid,
                                          @RequestParam(required = false) Integer ctdm,
                                          @RequestParam(required = false) String ctmc,
                                          @RequestParam(required = false) String zxbz
    ) {
        if (zdid == 0) {
            return ApiResult.FAILURE("字典ID不能为空");
        }
        List<DictionarylistEntity> list = null;
        if (ctdm == null) {
            ctdm = 0;
        }
//        if ((ctdm == 0) && StringUtil.isNull(ctmc)) {
//            list = idictionaryListService.findByZdid(zdid, zxbz);
//        } else if (!(ctdm == 0) && StringUtil.isNull(ctmc)) {
//            list = idictionaryListService.findByCtdm(zdid, ctdm, zxbz);
//        } else if ((ctdm == 0) && !(StringUtil.isNull(ctmc))) {
//            list = idictionaryListService.findByCtz(zdid, ctmc, zxbz);
//        } else {
//            list = idictionaryListService.findByCtdmAndCtz(zdid, ctdm, ctmc, zxbz);
//        }
        DictionarylistEntity entity = new DictionarylistEntity();
        entity.setZdid(zdid);
        entity.setCtmc(ctmc);
        entity.setCtdm(ctdm);
        entity.setZxbz(zxbz);
        list = idictionaryListService.queryByEntity(entity, true);
        return ApiResult.SUCCESS(list);
    }

    @ApiOperation("根据【字典名称】查询对应的词条信息")
    @RequestMapping(value = "/findDictListByZdmc", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "zdmc", value = "字典名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "zxbz", value = "注销标志 [0 :未注销/1:注销],不填写后台默认查全部", required = false, dataType = "String")
    })
    public ApiResult<Object> findDictListByZdmc(@RequestParam String zdmc,
                                                @RequestParam(required = false) String zxbz) {
        if (StringUtil.isNull(zdmc)) {
            return ApiResult.FAILURE("字典名称不能为空！！");
        }
        DictionarylistEntity entity = new DictionarylistEntity();
        entity.setZdmc(zdmc);
        entity.setZxbz(zxbz);
        entity.setCtdm(0);
        entity.setZdid(0);
        List<DictionarylistEntity> list = idictionaryListService.queryByEntity(entity, false);
        return ApiResult.SUCCESS(list);
    }

    @ApiOperation("根据【字典代码】查询对应的词条信息")
    @RequestMapping(value = "/findDictListByZddm", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "zddm", value = "字典代码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "zxbz", value = "注销标志 [0 :未注销/1:注销],不填写后台默认查全部", required = false, dataType = "String")
    })
    public ApiResult<Object> findDictListByZddm(@RequestParam String zddm,
                                                @RequestParam(required = false) String zxbz) {
        if (StringUtil.isNull(zddm)) {
            return ApiResult.FAILURE("字典名称不能为空！！");
        }
        DictionarylistEntity entity = new DictionarylistEntity();
        entity.setZddm(zddm);
        entity.setZxbz(zxbz);
        entity.setCtdm(0);
        entity.setZdid(0);
        List<DictionarylistEntity> list = idictionaryListService.queryByEntity(entity, false);
        return ApiResult.SUCCESS(list);
    }

    @ApiOperation("新增词条")
    @RequestMapping(value = "/addDictList", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "zdid", value = "字典ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "ctmc", value = "项词条名称", required = true, dataType = "String")
    })
    public ApiResult<Object> addDictList(@RequestParam Integer zdid,
                                         @RequestParam String ctmc
    ) {

        //第一步获取字典表信息
        DictionaryEntity zdxx = idictionaryService.findById(zdid);

        if (zdxx == null) {
            return ApiResult.FAILURE("添加失败,未找到对应的字典");
        } else if (zdxx.getZxbz().equals('1')) {
            return ApiResult.FAILURE("添加失败,该字典已注销，请先去激活");
        }

        DictionarylistEntity entity = new DictionarylistEntity();
        entity.setZddm(zdxx.getZddm());
        entity.setZdmc(zdxx.getZdmc());
        entity.setZdid(zdid);
        entity.setCtmc(ctmc);
        entity.setZxbz("0");// 默认填写为0,未注销

        Map<String, Object> msg = idictionaryListService.save(entity);
        String str = msg.keySet().toString().replace("[", " ").replace("]", " ").trim();
        if (str.equals("success")) {
            return ApiResult.SUCCESS((DictionarylistEntity) msg.get("success"));
        } else if (str.equals("fail")) {
            return ApiResult.FAILURE(msg.get("fail").toString());
        } else {
            return ApiResult.FAILURE("添加失败");
        }

    }


    @ApiOperation("更新词条")
    @RequestMapping(value = "/updateDictList", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "ctmc", value = "词条名称", required = true, dataType = "String")
    })
    public ApiResult<Object> updateDictList(@RequestParam Integer id,
                                            @RequestParam String ctmc
    ) {
        if (id == 0) {
            return ApiResult.FAILURE("主键不能为空");
        }
        //查询词条，信息完善
        DictionarylistEntity entity = idictionaryListService.findById(id);
        if (entity == null) {
            return ApiResult.FAILURE("更新失败,未找到对应的词条");
        }

        entity.setCtmc(ctmc);
        int cn = idictionaryListService.updateOne(entity);
        if (cn == 1) {
            return ApiResult.SUCCESS("更新成功");
        } else {
            return ApiResult.FAILURE("更新失败");
        }


    }


    @ApiOperation("根据ID注销词条")
    @RequestMapping(value = "/cancellationDicListById", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "query", name = "id", value = "ID", required = true, dataType = "Integer")
    public ApiResult<Object> cancellationDicListById(Integer id) {
        if (id == 0) {
            return ApiResult.FAILURE("主键键不能为空");
        }
        int cn = idictionaryListService.cancellationDicListById(id);
        if (cn == 1) {
            return ApiResult.SUCCESS("注销成功");
        } else {
            return ApiResult.FAILURE("注销失败,可能已被注销了");
        }

    }

    @ApiOperation("根据ID激活已注销词条")
    @RequestMapping(value = "/activationDicListById", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "query", name = "id", value = "ID", required = true, dataType = "Integer")
    public ApiResult<Object> activationDicListById(Integer id) {
        if (id == 0) {
            return ApiResult.FAILURE("主键不能为空");
        }
        int cn = idictionaryListService.unCancellationDicListById(id);
        if (cn == 1) {
            return ApiResult.SUCCESS("激活成功");
        } else {
            return ApiResult.FAILURE("激活失败,可能已被激活");
        }

    }

    /**********************************************测试提供给其它模块使用的接口**********************************************************/

    @ApiOperation("给其他模块提供的接口测试- [根据ID获取词条信息]")
    @RequestMapping(value = "/findDictListById", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "Integer")
    public ApiResult<Object> findDictListById(Integer id) {
        if (id == 0) {
            return ApiResult.FAILURE("id不能为空");
        }

        DictionarylistEntity entity = idictionaryListService.findById(id);
        if (entity == null) {
            return ApiResult.FAILURE("未查询到该词条！");
        }
        return ApiResult.SUCCESS(entity);


    }


    @ApiOperation("给其他模块提供的接口测试- [根据字典名称和词条名称获取词条代码]")
    @RequestMapping(value = "/queryCtdmByZdmcAndCtmc", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "zdmc", value = "字典名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "ctmc", value = "词条名称", required = true, dataType = "String")
    })
    public ApiResult<Object> queryCtdmByZdmcAndCtmc(@RequestParam String zdmc,
                                                    @RequestParam String ctmc) {
        String msg = idictionaryListService.queryCtdmByZdmcAndCtmc(zdmc, ctmc);
        return ApiResult.SUCCESS(msg);
    }


    @ApiOperation("给其他模块提供的接口测试- [根据字典代码和词条名称获取词条代码]")
    @RequestMapping(value = "/queryCtdmByZddmAndCtmc", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "zddm", value = "字典代码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "ctmc", value = "词条名称", required = true, dataType = "String")
    })
    public ApiResult<Object> queryCtdmByZddmAndCtmc(@RequestParam String zddm,
                                                    @RequestParam String ctmc) {
        String msg = idictionaryListService.queryCtdmByZddmAndCtmc(zddm, ctmc);

        return ApiResult.SUCCESS(msg);
    }


    @ApiOperation("给其他模块提供的接口测试- [根据字典名称和词条代码获取词条名称]")
    @RequestMapping(value = "/queryCtmcByZdmcAndCtdm", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "zdmc", value = "字典名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "ctdm", value = "词条代码", required = true, dataType = "String")
    })
    public ApiResult<Object> queryCtmcByZdmcAndCtdm(@RequestParam String zdmc,
                                                    @RequestParam String ctdm) {
        String msg = idictionaryListService.queryCtmcByZdmcAndCtdm(zdmc, ctdm);
        return ApiResult.SUCCESS(msg);
    }


    @ApiOperation("给其他模块提供的接口测试- [根据字典代码和词条代码获取词条名称]")
    @RequestMapping(value = "/queryCtmcByZddmAndCtdm", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "zddm", value = "字典代码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "ctdm", value = "词条代码", required = true, dataType = "String")
    })
    public ApiResult<Object> queryCtmcByZddmAndCtdm(@RequestParam String zddm,
                                                    @RequestParam String ctdm) {
        String msg = idictionaryListService.queryCtmcByZddmAndCtdm(zddm, ctdm);
        return ApiResult.SUCCESS(msg);
    }

    /*********************便于开发调试提供的方法-在后期上线时需要删除掉****************************************************/

    //删除操作 在上线部署时需要去掉
    @ApiOperation("根据【ID】删除单个词条")
    @RequestMapping(value = "/deleteDictListById", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "Integer")
    public ApiResult<Object> deleteDictListById(Integer id) {
        if (id == 0) {
            return ApiResult.FAILURE("删除失败,主键不能为空！！");
        }

        int con = idictionaryListService.deleteById(id);
        if (con == 1) {
            return ApiResult.SUCCESS("删除成功");
        } else {
            return ApiResult.FAILURE("删除失败");
        }

    }

    //删除操作 在上线部署时需要去掉
    @ApiOperation("根据[字典ID]批量删除词条")
    @RequestMapping(value = "/deleteDictListByDid", method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "query", name = "zdid", value = "字典ID", required = true, dataType = "Integer")
    public ApiResult<Object> deleteDictListByDid(Integer zdid) {

        if (zdid == 0) {
            return ApiResult.FAILURE("删除失败,主键不能为空！！");
        }
        int con = idictionaryListService.deleteByDid(zdid);
        if (con >= 0) {
            return ApiResult.SUCCESS("删除成功,共删除了:" + con + "个词条");
        } else {
            return ApiResult.FAILURE("删除失败");
        }
    }


}
