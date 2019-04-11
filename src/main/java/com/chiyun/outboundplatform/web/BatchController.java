package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiPageResult;
import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.common.ControllerLog;
import com.chiyun.outboundplatform.common.MustLogin;
import com.chiyun.outboundplatform.entity.BatchEntity;
import com.chiyun.outboundplatform.entity.BatchrecordEntity;
import com.chiyun.outboundplatform.entity.FieldcasebaseEntity;
import com.chiyun.outboundplatform.repository.BatchRecordRepository;
import com.chiyun.outboundplatform.repository.BatchRepository;
import com.chiyun.outboundplatform.repository.FieldCaseBaseRepository;
import com.chiyun.outboundplatform.service.IbatchService;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.utils.ExcelImportUtils;
import com.chiyun.outboundplatform.utils.ExcelUtil;
import com.chiyun.outboundplatform.utils.StringUtil;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

@Api(description = "模板管理")
@RestController
@RequestMapping(value = "/batch", method = {RequestMethod.GET, RequestMethod.POST})
public class BatchController {

    @Resource
    private BatchRepository batchRepository;
    @Resource
    private FieldCaseBaseRepository fieldCaseBaseRepository;
    @Resource
    private IbatchService ibatchService;
    @Resource
    private BatchRecordRepository batchRecordRepository;

    @ApiOperation("添加")
//    @MustLogin(rolerequired = {1, 2})
    @RequestMapping("/add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pcmc", value = "模板名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "zdids", value = "所选字段id组合，英文','分隔", dataType = "String", paramType = "query")
    })
    public ApiResult<Object> add(String pcmc, String zdids) {
        long now = System.currentTimeMillis();
        // 循环字段ids，查询选取保存
        String[] str = zdids.split(",");
        List<String> zdidslist = Arrays.asList(str);
        if (zdidslist.size() < 1) {
            return ApiResult.FAILURE("所选为空，添加失败");
        }
        for (int i = 0; i < zdidslist.size(); i++) {
            //查询字段
            FieldcasebaseEntity fieldcasebaseEntity = fieldCaseBaseRepository.findById(Integer.valueOf(zdidslist.get(i))).get();
            if (fieldcasebaseEntity == null) {
                return ApiResult.FAILURE("该字段不存在");
            }
            // 保存字段
            BatchrecordEntity entity = new BatchrecordEntity();
            entity.setZdzwmc(fieldcasebaseEntity.getZdzwmc());
            entity.setZdywmc(fieldcasebaseEntity.getZdywmc());
            entity.setJczdid(fieldcasebaseEntity.getId());
            entity.setSort(i + 1);
            entity.setPcid(String.valueOf(now));
            System.out.println("i:" + zdidslist.get(i));
            try {
                batchRecordRepository.save(entity);
            } catch (Exception e) {
                return ApiResult.FAILURE("添加失败");
            }
        }
        BatchEntity batchEntity = new BatchEntity();
        batchEntity.setPcid(String.valueOf(now));
        batchEntity.setCjsj(new Date());
        batchEntity.setPcmc(pcmc);
        try {
            batchRepository.save(batchEntity);
        } catch (Exception e) {
            return ApiResult.FAILURE("添加失败");
        }
        return ApiResult.SUCCESS("添加成功");
    }

    @ApiOperation("修改")
    @MustLogin(rolerequired = {1, 2})
    @RequestMapping("/update")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pcmc", value = "模板名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "zdids", value = "所选字段id组合，英文','分隔", dataType = "String", paramType = "query")
    })
    public ApiResult<Object> update(String pcid, String pcmc, String zdids) {
        BatchEntity batchEntity = batchRepository.findByPcid(pcid);
        if (batchEntity == null) {
            return ApiResult.FAILURE("该模板不存在");
        }
        // 循环字段ids，查询选取保存
        String[] str = zdids.split(",");
        List<String> zdidslist = Arrays.asList(str);
        if (zdidslist.size() < 1) {
            return ApiResult.FAILURE("所选为空，添加失败");
        }
        // 删除所有数据
        try {
            batchRecordRepository.deleteAllByPcid(pcid);
        } catch (Exception e) {
            return ApiResult.FAILURE("删除旧失败");
        }
        for (int i = 0; i < zdidslist.size(); i++) {
            //查询字段
            FieldcasebaseEntity fieldcasebaseEntity = fieldCaseBaseRepository.findById(Integer.valueOf(zdidslist.get(i))).get();
            if (fieldcasebaseEntity == null) {
                return ApiResult.FAILURE("该字段不存在");
            }
            // 保存字段
            BatchrecordEntity entity = new BatchrecordEntity();
            entity.setZdzwmc(fieldcasebaseEntity.getZdzwmc());
            entity.setZdywmc(fieldcasebaseEntity.getZdywmc());
            entity.setJczdid(fieldcasebaseEntity.getId());
            entity.setSort(i + 1);
            entity.setPcid(pcid);
            try {
                batchRecordRepository.save(entity);
            } catch (Exception e) {
                return ApiResult.FAILURE("添加失败");
            }
        }

        batchEntity.setPcmc(pcmc);
        try {
            batchRepository.save(batchEntity);
        } catch (Exception e) {
            return ApiResult.FAILURE("修改失败");
        }
        return ApiResult.SUCCESS("修改成功");
    }

    @ApiOperation("删除")
    @MustLogin(rolerequired = {1, 2})
    @RequestMapping("/delete")
    @ApiImplicitParam(name = "pcid", value = "模板id", dataType = "String", paramType = "query")
    public ApiResult<Object> delete(String pcid) {
        if (StringUtil.isNull(pcid)) {
            return ApiResult.FAILURE("批次id不能为空");
        }
        try {
            batchRecordRepository.deleteAllByPcid(pcid);
            batchRepository.deleteByPcid(pcid);
        } catch (Exception e) {
            return ApiResult.FAILURE("删除失败");
        }
        return ApiResult.SUCCESS("删除成功");
    }

    //    @MustLogin(rolerequired = {1, 2})
    @ApiOperation("通过模板id查询所有字段组合")
    @RequestMapping("/findAllZdzh")
    public ApiResult<Object> findAllZdzh(String pcid) {
        if (StringUtil.isNull(pcid)) {
            return ApiResult.FAILURE("模板id不能为空");
        }
        return ApiResult.SUCCESS(ibatchService.getBatchMap(pcid));
    }

    //    @MustLogin(rolerequired = {1, 2})
    @ApiOperation("查询所有模板")
    @RequestMapping("/findAll")
    public ApiResult<Object> findAll(int page, int pagesize) {
        Pageable pageable = PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "cjsj"));
        Page<BatchEntity> list = batchRepository.findAll(pageable);
        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
    }

    @ApiOperation("导出模板")
    @MustLogin(rolerequired = {1, 2})
    @RequestMapping("/exportExcel")
    @ApiImplicitParam(name = "pcid", value = "模板id", dataType = "String", paramType = "query")
    public ApiResult<Object> exportExcel(String pcid, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if (StringUtil.isNull(pcid)) {
            return ApiResult.FAILURE("批次id不能为空");
        }
        // 获取默认模板的名称
        List<String> mrlist = fieldCaseBaseRepository.findAllZdzwmc();
        List<BatchrecordEntity> list = batchRecordRepository.findAllByPcidOrderBySort(pcid);
        // 新建数组,保存第二列所有字段名称
        String title[] = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            title[i] = list.get(i).getZdzwmc();
        }
        // 查询所有类型名称、个数
        List<Map<String, Object>> firstList = batchRepository.findAllByPcid(pcid);
        // 获取默认字段个数
        int num = fieldCaseBaseRepository.countFieldcasebaseEntitiesByJcxxlx(0);
        String sheetname = batchRepository.findByPcid(pcid).getPcmc();
        HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook(sheetname, title, firstList, num, null);
        String fileName = sheetname + ".xls";
        String userAgent = request.getHeader("USER-AGENT");
        String newFilename = URLEncoder.encode(fileName, "UTF-8").replace("+", " ");
        if (userAgent != null) {
            if (userAgent.contains("edge") || userAgent.contains("Edge") || userAgent.contains("Trident") || userAgent.contains("trident")) {
                newFilename = URLEncoder.encode(fileName, "UTF-8").replace("+", " ");
            } else {
                newFilename = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + newFilename);
        response.setContentType("application/vnd.ms-excel");
        OutputStream toClient = response.getOutputStream();
        workbook.write(toClient);
        toClient.flush();
        toClient.close();
        return ApiResult.SUCCESS();
    }

    @ApiOperation("导入模板")
    @MustLogin(rolerequired = {1, 2})
    @RequestMapping("/importExcel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pcid", value = "批次id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ajqy", value = "案件区域id", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "ajlx", value = "案件类型id", dataType = "Integer", paramType = "query")
    })
    public ApiResult importExcel(String pcid, MultipartFile file, Integer ajqy, Integer ajlx) {
        //判断文件是否为空
        if (file == null) {
            return ApiResult.FAILURE("文件不能为空");
        }
        // 判断案件区域
        if (ajqy == null || ajlx == null) {
            return ApiResult.FAILURE("案件区域和案件类型不能为空");
        }
        //获取文件名
        String fileName = file.getOriginalFilename();

        //验证文件名是否合格
        if (!ExcelImportUtils.validateExcel(fileName)) {
            return ApiResult.FAILURE("文件必须是excel格式");
        }

        //进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
        long size = file.getSize();
        if (StringUtil.isNull(fileName) || size == 0) {
            return ApiResult.FAILURE("文件不能为空");
        }
        //批量导入
        return ExcelImportUtils.batchImport(pcid, fileName, file, ibatchService, ajqy, ajlx);
    }


}
