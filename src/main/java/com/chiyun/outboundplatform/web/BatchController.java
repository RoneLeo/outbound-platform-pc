package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiPageResult;
import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.BatchEntity;
import com.chiyun.outboundplatform.entity.FieldcasebaseEntity;
import com.chiyun.outboundplatform.repository.BasetypeRepository;
import com.chiyun.outboundplatform.repository.BatchRepository;
import com.chiyun.outboundplatform.repository.FieldCaseBaseRepository;
import com.chiyun.outboundplatform.service.IbatchService;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.utils.ExcelImportUtils;
import com.chiyun.outboundplatform.utils.ExcelUtil;
import com.chiyun.outboundplatform.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Api(description = "批次管理")
@RestController
@RequestMapping(value = "/batch", method = {RequestMethod.GET, RequestMethod.POST})
public class BatchController {

    @Resource
    private BatchRepository batchRepository;
    @Resource
    private FieldCaseBaseRepository fieldCaseBaseRepository;
    @Resource
    private BasetypeRepository basetypeRepository;
    @Resource
    private IbatchService ibatchService;
    @Resource
    private IdictionaryListService idictionaryListService;

    @ApiOperation("添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(@RequestParam(required = false) @ApiParam("批次名称")String pcmc,@RequestParam(required = false) @ApiParam(value = "所选字段id组合，英文','分隔") String zdids) {
        long now = System.currentTimeMillis();
        // 循环字段ids，查询选取保存
        String[] str = zdids.split(",");
        List<String> zdidslist = Arrays.asList(str);
        if (zdidslist.size() < 1) {
            return ApiResult.FAILURE("所选为空，添加失败");
        }
        for (int i = 0; i < zdidslist.size(); i++) {
            //查询字段
            FieldcasebaseEntity fieldcasebaseEntity = fieldCaseBaseRepository.findById(Integer.valueOf(zdidslist.get(i)));
            if (fieldcasebaseEntity == null) {
                return ApiResult.FAILURE("该字段不存在");
            }
            // 保存字段
            BatchEntity entity = new BatchEntity();
            entity.setZdzwmc(fieldcasebaseEntity.getZdzwmc());
            entity.setZdywmc(fieldcasebaseEntity.getZdywmc());
            entity.setJczdid(String.valueOf(fieldcasebaseEntity.getId()));
            entity.setSort(i + 1);
            entity.setPcid(String.valueOf(now));
            BatchEntity entity1 = batchRepository.save(entity);
            if (entity1 == null) {
                return ApiResult.FAILURE("添加失败");
            }
        }
        return ApiResult.SUCCESS("添加成功");
    }

    @ApiOperation("查询所有模版")
    @RequestMapping("/findAllPcid")
    public ApiResult<Object> findAll(int page, int pagesize, HttpSession session) {
        Pageable pageable = PageRequest.of(page - 1, pagesize);
        Page<Map<String, Object>> list = batchRepository.findAllPcid(pageable);
        return ApiPageResult.SUCCESS(list.getContent(), page, pagesize, list.getTotalElements(), list.getTotalPages());
    }

    @ApiOperation("导出模板")
    @RequestMapping("/exportExcel")
    public ApiResult<Object> exportExcel(String pcid, HttpServletResponse response) throws IOException {
        if (StringUtil.isNull(pcid)) {
            return ApiResult.FAILURE("批次id不能为空");
        }
        // 获取默认模板的名称
        List<String> mrlist = fieldCaseBaseRepository.findAllZdzwmc();
        List<BatchEntity> list = batchRepository.findAllByPcidOrderBySort(pcid);
        // 新建数组,保存第二列所有字段名称
        String title[] = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            title[i] = list.get(i).getZdzwmc();
        }
        // 查询所有类型名称、个数
        List<Map<String, Object>> firstList = batchRepository.findAllByPcid(pcid);
        // 获取默认字段个数
        int num = fieldCaseBaseRepository.countFieldcasebaseEntitiesByJcxxlx(0);
        String sheetname = "sheet1";
        HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook(sheetname, title, firstList, num, null);
        String fileName = sheetname + ".xls";
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        response.setContentType("application/vnd.ms-excel");
        OutputStream toClient = response.getOutputStream();
        workbook.write(toClient);
        toClient.flush();
        toClient.close();
        return ApiResult.SUCCESS();
    }

    @ApiOperation("导入模板")
    @RequestMapping("/importExcel")
    public ApiResult<Object> importExcel(String pcid, MultipartFile file, Integer ajqy, Integer ajlx) {
        //判断文件是否为空
        if (file == null) {
            return ApiResult.FAILURE("文件不能为空");
        }
        // 判断案件区域
//        if (ajqy == null || ajlx == null) {
//            return ApiResult.FAILURE("案件区域和案件类型不能为空");
//        }
//        if (idictionaryListService.findById(ajqy) == null) {
//            return ApiResult.FAILURE("该区域不存在");
//        }
//        // 判断案件类型
//        if (idictionaryListService.findById(ajlx) == null) {
//            return ApiResult.FAILURE("该案件类型不存在");
//        }
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
        ApiResult message = ExcelImportUtils.batchImport(pcid, fileName, file, ibatchService, ajqy, ajlx);
        return message;
    }


}
