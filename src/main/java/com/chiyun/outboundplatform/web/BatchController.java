package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.BatchEntity;
import com.chiyun.outboundplatform.entity.FieldcasebaseEntity;
import com.chiyun.outboundplatform.repository.BasetypeRepository;
import com.chiyun.outboundplatform.repository.BatchRepository;
import com.chiyun.outboundplatform.repository.FieldCaseBaseRepository;
import com.chiyun.outboundplatform.utils.ExcelUtil;
import com.chiyun.outboundplatform.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

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

    @ApiOperation("添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(String zdids) {
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

    @ApiOperation("查询所有")
    @RequestMapping("/findAll")
    public ApiResult<Object> findAll() {
        List<BatchEntity> list = batchRepository.findAll();
        return ApiResult.SUCCESS(list);
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
        String title[] = new String[list.size() + mrlist.size()];
        int j = 0;
//        for (int i = 0; i < mrlist.size(); i++) {
//            title[i] = mrlist.get(i);
//            j++;
//        }
        for (int i = 0; i < list.size(); i++) {
            title[i + j] = list.get(i).getZdzwmc();

        }
        // 查询所有类型名称、个数
        List<Map<String, Object>> firstList = batchRepository.findAllByPcid(pcid);
//        List<Integer> jczdids = batchRepository.findAllJczdidsByPcid(pcid);
//        for (int i = 0; i < jczdids.size(); i++) {
//            // 查询字段类型
//            Map<String, Object> map = new HashMap<>();
//            int jcxxlx = fieldCaseBaseRepository.findById(jczdids.get(i)).getJcxxlx();
//            map.put("name", basetypeRepository.findNameByType(jcxxlx));
//            map.put("num", fieldCaseBaseRepository.countFieldcasebaseEntitiesByJcxxlx(jcxxlx));
//            firstList.add(map);
//        }
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
    public ApiResult<Object> importExcel(String pcid, String filepath) {
        if (!filepath.endsWith(".xls") && !filepath.endsWith(".xlsx")) {
            return ApiResult.FAILURE("该文件不是excel类型");
        }
        FileInputStream fis = null;
        Workbook workbook = null;
        try {
            fis = new FileInputStream(filepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            // 2003版本
            if (filepath.endsWith(".xls")) {
                workbook = new HSSFWorkbook(fis);
            } else if (filepath.endsWith(".xlsx")){
                workbook = new XSSFWorkbook(fis);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取一张表
        Sheet sheet = workbook.getSheetAt(0);
        // 获得总行数
        int totalRowNum = sheet.getLastRowNum();
        // 获得所有数据
        for (int rownum = 1; rownum < totalRowNum; rownum++) {
            // 获得第i行
            Row row = sheet.getRow(rownum);
            // 获得所有列
            int firstCellNum = row.getFirstCellNum();
            int lastCellNum = row.getLastCellNum();
            List<String> rowlist = new ArrayList<>();
            // 遍历列
            for (int cellnum = firstCellNum; cellnum < lastCellNum; cellnum++) {
                Cell cell = row.getCell(cellnum);
                if (cell == null) {
                    continue;
                }
                String value = cell.getStringCellValue();
                System.out.println("第" + rownum + "行第" + cellnum + "列的值为：" + value);
            }
        }
        return ApiResult.SUCCESS();
    }


}
