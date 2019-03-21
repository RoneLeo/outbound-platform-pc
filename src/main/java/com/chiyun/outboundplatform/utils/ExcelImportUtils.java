package com.chiyun.outboundplatform.utils;

import com.alibaba.fastjson.JSON;
import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.*;
import com.chiyun.outboundplatform.service.IbatchService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelImportUtils {

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //@描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public static boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            return false;
        }
        return true;
    }

    /**
     * 上传excel文件到临时目录后并开始解析
     *
     * @param fileName
     * @param mfile
     * @return
     */
    public static ApiResult batchImport(String xmbh, String fileName, MultipartFile mfile, IbatchService ibatchService) {

        File uploadDir = new File("C:\\fileupload");
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!uploadDir.exists()) uploadDir.mkdirs();
        //新建一个文件
        File tempFile = new File("C:\\fileupload\\" + new Date().getTime() + ".xlsx");
        //初始化输入流
        InputStream is = null;
        try {
            //将上传的文件写入新建的文件中
            mfile.transferTo(tempFile);

            //根据新建的文件实例化输入流
            is = new FileInputStream(tempFile);

            //根据版本选择创建Workbook的方式
            Workbook wb = null;
            //根据文件名判断文件是2003版本还是2007版本
            if (ExcelImportUtils.isExcel2007(fileName)) {
                wb = new XSSFWorkbook(is);
            } else {
                wb = new HSSFWorkbook(is);
            }
            //根据excel里面的内容读取知识库信息
            return readExcelValue(xmbh, wb, tempFile, ibatchService);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return ApiResult.FAILURE("导入出错！请检查数据格式！");
    }

    //        第二行记录每个字段名称
    static List<String> listMap = new ArrayList<>();

    /**
     * 解析Excel里面的数据
     *
     * @param wb
     * @return
     */
    private static ApiResult readExcelValue(String xmbh, Workbook wb, File tempFile, IbatchService ibatchService) throws ParseException {
        //导入数据数量
        int sj = 0;
        //错误信息接收器
        String errorMsg = "";
        //得到第一个shell
        Sheet sheet1 = wb.getSheetAt(0);
        //得到Excel的行数
        int totalRows = sheet1.getPhysicalNumberOfRows();
        //总列数
        int totalCells = 0;
        //得到Excel的列数(前提是有行数)，从第1行算起
        if (totalRows >= 1 && sheet1.getRow(1) != null) {
            totalCells = sheet1.getRow(1).getPhysicalNumberOfCells();
        }
        String br = "<br/>";
//        从第一行开始。获取各表数量、字段数、各表初始列数
        List<HashMap<String, Object>> tablelist = new ArrayList<>();
        Row row = sheet1.getRow(0);
        for (int c = 0; c < totalCells; c++) {
            Cell cell = row.getCell(c);
            if (cell == null)
                continue;
            String str = cell.getStringCellValue();
            if (!StringUtil.isNull(str)) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", str);
                map.put("begin", c);
                if (tablelist.size() > 0) {
                    HashMap<String, Object> newone = tablelist.get(tablelist.size() - 1);
                    newone.put("end", c - 1);
                    tablelist.set(tablelist.size() - 1, newone);
                }
                tablelist.add(map);
            }
        }
        HashMap<String, Object> newone = tablelist.get(tablelist.size() - 1);
        newone.put("end", totalCells - 1);
        tablelist.set(tablelist.size() - 1, newone);
        //        第二行记录每个字段名称
        Row row2 = sheet1.getRow(1);
        for (int c = 0; c < totalCells; c++) {
            Cell cell = row2.getCell(c);
            String str;
            if (cell == null) {
                str = "";
            } else {
                str = cell.getStringCellValue();
            }
            listMap.add(str);
        }
        //循环Excel行数,从第三行开始记录数据
        for (int r = 2; r < totalRows; r++) {
            Row datarow = sheet1.getRow(r);
            if (datarow == null) {
                errorMsg += br + "第" + (r + 1) + "行数据为空！";
                continue;
            }
            CasebasemessageEntity baseentity = new CasebasemessageEntity();
            List<CardmessageEntity> cardmessageEntityList = new ArrayList<>();
            List<EmpmessageEntity> empmessageEntityList = new ArrayList<>();
            List<LoanmessageEntity> loanmessageEntityList = new ArrayList<>();
            List<UsermessageEntity> usermessageEntityList = new ArrayList<>();
            List<OutboundmessageEntity> outboundmessageEntityList = new ArrayList<>();
            List<CasepeoplemessageEntity> casepeoplemessageEntityList = new ArrayList<>();
            List<LinkmanmessageEntity> linkmanmessageEntityList = new ArrayList<>();
            List<OthermessageEntity> othermessageEntityList = new ArrayList<>();
            List<RemarkmsgEntity> remarkmsgEntityList = new ArrayList<>();
            for (HashMap<String, Object> map : tablelist) {
                getentitybytable(baseentity, map, datarow, cardmessageEntityList, empmessageEntityList, loanmessageEntityList, usermessageEntityList, outboundmessageEntityList
                        , casepeoplemessageEntityList, linkmanmessageEntityList, othermessageEntityList, remarkmsgEntityList);

            }
            if (ibatchService.saveall(baseentity, cardmessageEntityList, empmessageEntityList, loanmessageEntityList, usermessageEntityList, outboundmessageEntityList
                    , casepeoplemessageEntityList, linkmanmessageEntityList, othermessageEntityList, remarkmsgEntityList)) {
                sj++;
            } else {
                errorMsg += br + "第" + (r + 1) + "行数据有问题，请仔细检查！";
            }
        }
        //删除上传的临时文件
        if (tempFile.exists()) {
            tempFile.delete();
        }

        errorMsg = "导入成功，共" + sj + "条数据！" + errorMsg;
        return ApiResult.SUCCESS(errorMsg);
    }

    private static void getentitybytable(CasebasemessageEntity baseentity, HashMap<String, Object> map, Row datarow, List<CardmessageEntity> cardmessageEntityList
            , List<EmpmessageEntity> empmessageEntityList, List<LoanmessageEntity> loanmessageEntityList, List<UsermessageEntity> usermessageEntityList
            , List<OutboundmessageEntity> outboundmessageEntityList, List<CasepeoplemessageEntity> casepeoplemessageEntityList,
                                         List<LinkmanmessageEntity> linkmanmessageEntityList, List<OthermessageEntity> othermessageEntityList
            , List<RemarkmsgEntity> remarkmsgEntityList) {
        int begin = Integer.valueOf(map.get("begin").toString());
        int end = Integer.valueOf(map.get("end").toString());
        String table = map.get("name").toString();
        switch (table) {
            case "默认":
                for (int i = begin; i <= end; i++) {
                    Cell cell = datarow.getCell(i);
                    if (cell == null)
                        continue;
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String str = listMap.get(i);//目标字段名称
                    String datastr = cell.getStringCellValue();//数据内容
                    EntityDataSet.basedataset(datastr, str, baseentity);
                }
                break;
            case "卡号信息":
                CardmessageEntity card = new CardmessageEntity();
                for (int i = begin; i <= end; i++) {
                    Cell cell = datarow.getCell(i);
                    if (cell == null)
                        continue;
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String str = listMap.get(i);//目标字段名称
                    String datastr = cell.getStringCellValue();//数据内容
                    EntityDataSet.carddataset(datastr, str, card);
                }
                if (!StringUtil.checkObjAllFieldsIsNull(card))
                    cardmessageEntityList.add(card);
                break;
            case "催收员信息": {
                EmpmessageEntity entity = new EmpmessageEntity();
                for (int i = begin; i <= end; i++) {
                    Cell cell = datarow.getCell(i);
                    if (cell == null)
                        continue;
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String str = listMap.get(i);//目标字段名称
                    String datastr = cell.getStringCellValue();//数据内容
                    EntityDataSet.empdataset(datastr, str, entity);
                }
                if (!StringUtil.checkObjAllFieldsIsNull(entity))
                    empmessageEntityList.add(entity);
                break;
            }
            case "贷款信息": {
                LoanmessageEntity entity = new LoanmessageEntity();
                for (int i = begin; i <= end; i++) {
                    Cell cell = datarow.getCell(i);
                    if (cell == null)
                        continue;
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String str = listMap.get(i);//目标字段名称
                    String datastr = cell.getStringCellValue();//数据内容
                    EntityDataSet.loandataset(datastr, str, entity);
                }
                if (!StringUtil.checkObjAllFieldsIsNull(entity))
                    loanmessageEntityList.add(entity);
                break;
            }
            case "对象信息": {
                UsermessageEntity entity = new UsermessageEntity();
                for (int i = begin; i <= end; i++) {
                    Cell cell = datarow.getCell(i);
                    if (cell == null)
                        continue;
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String str = listMap.get(i);//目标字段名称
                    String datastr = cell.getStringCellValue();//数据内容
                    EntityDataSet.userdataset(datastr, str, entity);
                }
                if (!StringUtil.checkObjAllFieldsIsNull(entity))
                    usermessageEntityList.add(entity);
                break;
            }
            case "外访信息": {
                OutboundmessageEntity entity = new OutboundmessageEntity();
                for (int i = begin; i <= end; i++) {
                    Cell cell = datarow.getCell(i);
                    if (cell == null)
                        continue;
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String str = listMap.get(i);//目标字段名称
                    String datastr = cell.getStringCellValue();//数据内容
                    EntityDataSet.outbdataset(datastr, str, entity);
                }
                if (!StringUtil.checkObjAllFieldsIsNull(entity))
                    outboundmessageEntityList.add(entity);
                break;
            }
            case "案人信息": {
                CasepeoplemessageEntity entity = new CasepeoplemessageEntity();
                for (int i = begin; i <= end; i++) {
                    Cell cell = datarow.getCell(i);
                    if (cell == null)
                        continue;
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String str = listMap.get(i);//目标字段名称
                    String datastr = cell.getStringCellValue();//数据内容
                    EntityDataSet.casepdataset(datastr, str, entity);
                }
                if (!StringUtil.checkObjAllFieldsIsNull(entity))
                    casepeoplemessageEntityList.add(entity);
                break;
            }
            case "联系人信息": {
                LinkmanmessageEntity entity = new LinkmanmessageEntity();
                for (int i = begin; i <= end; i++) {
                    Cell cell = datarow.getCell(i);
                    if (cell == null)
                        continue;
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String str = listMap.get(i);//目标字段名称
                    String datastr = cell.getStringCellValue();//数据内容
                    EntityDataSet.linkdataset(datastr, str, entity);
                }
                if (!StringUtil.checkObjAllFieldsIsNull(entity))
                    linkmanmessageEntityList.add(entity);
                break;
            }
            case "其他信息": {
                OthermessageEntity entity = new OthermessageEntity();
                for (int i = begin; i <= end; i++) {
                    Cell cell = datarow.getCell(i);
                    if (cell == null)
                        continue;
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String str = listMap.get(i);//目标字段名称
                    String datastr = cell.getStringCellValue();//数据内容
                    EntityDataSet.otherdataset(datastr, str, entity);
                }
                if (!StringUtil.checkObjAllFieldsIsNull(entity))
                    othermessageEntityList.add(entity);
                break;
            }
            case "备注信息": {
                RemarkmsgEntity entity = new RemarkmsgEntity();
                for (int i = begin; i <= end; i++) {
                    Cell cell = datarow.getCell(i);
                    if (cell == null)
                        continue;
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String str = listMap.get(i);//目标字段名称
                    String datastr = cell.getStringCellValue();//数据内容
                    EntityDataSet.remarkdataset(datastr, str, entity);
                }
                if (!StringUtil.checkObjAllFieldsIsNull(entity))
                    remarkmsgEntityList.add(entity);
                break;
            }
        }
    }
}