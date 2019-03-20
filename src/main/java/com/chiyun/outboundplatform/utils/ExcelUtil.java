package com.chiyun.outboundplatform.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;
import java.util.Map;

public class ExcelUtil {

    /**
     * 导出空的Excel
     *
     * @param sheetName sheet名称
     * @param title     第二列标题
     * @param list      第一列标题信息：名称和个数
     * @param num       默认字段个数
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, List<Map<String, Object>> list, int num, HSSFWorkbook wb) {
        // 1、创建一个HSSFWorkbook，对应一个excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }
        // 2、在workbook添加sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        // 3、在sheet中添加表头第0行
        HSSFRow row = sheet.createRow(0);
        sheet.setDefaultColumnWidth(11);
        HSSFRow row1 = sheet.createRow(1);
        // 4、创建单元格
        HSSFCellStyle style = wb.createCellStyle();
        // 设置值表头，设置表头居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 5、声明列对象
        HSSFCell cell = null;
        // 创建第二行标题
        for (int i = 0; i < title.length; i++) {
            // 新建列
            cell = row1.createCell(i);
            // 设置列值
            cell.setCellValue(title[i]);
            // 设置样式居中
            cell.setCellStyle(style);
        }
        // 创建第一列
        int flag = 0;
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String mc = map.get("mc").toString(); //类型名称
            int sl = Integer.valueOf(map.get("sl").toString());//类型个数
            int lx = Integer.valueOf(map.get("lx").toString());//类型包含字段数
            for (int j = 0; j < sl; j++) {
                cell = row.createCell(flag);
                cell.setCellValue(mc);
                cell.setCellStyle(style);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, flag, flag + lx - 1));
                flag += lx;
            }
        }
        return wb;
    }


}