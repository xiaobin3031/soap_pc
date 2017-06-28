package com.xiaobin.util;

import com.jfinal.plugin.activerecord.Record;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 操作excel
 * Created by XWB on 2017-06-17.
 */
public class ExcelUtil {

    private List<Record> dataList;
    private Record record;
    //目前定死格式
    private int dataListIndexBegin = 4;
    private int dataListIndexEnd = 10;

    public void setRecord(Record record) {
        this.record = record;
    }

    public void setDataList(List<Record> dataList) {
        this.dataList = dataList;
    }

    public ExcelUtil(List<Record> dataList,Record record){
        this.dataList = dataList;
        this.record = record;
    }
    public void copyFromExcel(File srcFile,File orgFile){
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new BufferedInputStream(new FileInputStream(orgFile));
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(srcFile));
            XSSFWorkbook workbook2 = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Calendar c = Calendar.getInstance();
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                c.add(Calendar.WEEK_OF_YEAR,-1);
            }
            c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
            XSSFSheet sheet2 = workbook2.createSheet(new SimpleDateFormat("yyyyMMdd").format(c.getTime()));
            workbook2.setSheetOrder(sheet2.getSheetName(),0);
            XSSFRow row,row2;
            XSSFCell cell,cell2;
            Object value;
            List<CellRangeAddress> cellRangeAddressList = sheet.getMergedRegions();
            //设置合并单元格
            cellRangeAddressList.forEach(sheet2::addMergedRegion);
            int lastCellNum = 0;
            XSSFFont font = null;
            List<String> cellMaxValue = new ArrayList<>();
            //偏移量，防止不是从0行开始
            int offsetRowIndex = sheet.getFirstRowNum();
            for(int i=sheet.getFirstRowNum();i<=sheet.getLastRowNum();i++){
                row = sheet.getRow(i);
                if(row == null){
                    continue;
                }
                row2 = sheet2.createRow(i);
                row2.setRowStyle(workbook2.createCellStyle());
                copyRowStyle(row,row2);
                copyStyle(row.getRowStyle(),row2.getRowStyle());
                if(lastCellNum < row.getLastCellNum()){
                    lastCellNum = row.getLastCellNum();
                }
                String maxCellValue = "";
                for(int j=row.getFirstCellNum();j<=row.getLastCellNum();j++){
                    cell = row.getCell(j);
                    if(cell == null){
                        continue;
                    }
                    CellType cellType = cell.getCellTypeEnum();
                    cell2 = row2.createCell(j);
                    if(cellType.compareTo(CellType.BOOLEAN) == 0){
                        cell2.setCellValue(cell.getBooleanCellValue());
                    }else if(cellType.compareTo(CellType.STRING) == 0){
                        setStringData(i-offsetRowIndex,cell,cell2);
                        value = cell2.getStringCellValue();
                        if(font == null){
                            font = cell2.getCellStyle().getFont();
                        }

                        if(maxCellValue.length() < value.toString().length()){
                            maxCellValue = value.toString();
                        }
                    }else if(cellType.compareTo(CellType.NUMERIC) == 0){
                        cell2.setCellValue(cell.getNumericCellValue());
                    }
                    cell2.setCellStyle(workbook2.createCellStyle());
                    copyStyle(cell.getCellStyle(),cell2.getCellStyle());
                }
                cellMaxValue.add(maxCellValue);
            }
            //设置列宽
            int maxCellWidth = 0;
            for(int j=0;j<=lastCellNum;j++){
                if(maxCellWidth < sheet.getColumnWidth(j)){
                    maxCellWidth = sheet.getColumnWidth(j);
                }
                sheet2.setColumnWidth(j,sheet.getColumnWidth(j));
            }
            short fontHeight = 11;
            if(font != null){
                fontHeight = font.getFontHeight();
            }
            //设置高度自适应
            for(int i=dataListIndexBegin;i<=sheet2.getLastRowNum() && i<= dataListIndexEnd;i++){
                row2 = sheet2.getRow(i);
                value = cellMaxValue.get(i - offsetRowIndex);
                //cell内容总宽度
                double cellContent = value.toString().getBytes().length * 2 * 256;
                double stringNeedsRows = cellContent / maxCellWidth;
                stringNeedsRows = Math.ceil(stringNeedsRows);
                double stringNeedsHeight = fontHeight * stringNeedsRows;
                stringNeedsHeight = Math.ceil(stringNeedsHeight);
                row2.setHeight((short) stringNeedsHeight);
            }
            is.close();
            is = null;
            os = new BufferedOutputStream(new FileOutputStream(orgFile));
            workbook2.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void copyRowStyle(XSSFRow srcRow, XSSFRow orgRow){
        orgRow.setHeight(srcRow.getHeight());
        orgRow.setHeightInPoints(srcRow.getHeightInPoints());
        orgRow.setZeroHeight(orgRow.getZeroHeight());
    }

    private void copyStyle(XSSFCellStyle srcStyle, XSSFCellStyle orgStyle){
        if(srcStyle == null){
            return;
        }
        orgStyle.setAlignment(srcStyle.getAlignmentEnum());
        orgStyle.setVerticalAlignment(srcStyle.getVerticalAlignmentEnum());
        orgStyle.setBorderBottom(srcStyle.getBorderBottomEnum());
        orgStyle.setBorderLeft(srcStyle.getBorderLeftEnum());
        orgStyle.setBorderRight(srcStyle.getBorderRightEnum());

        orgStyle.setBorderTop(srcStyle.getBorderTopEnum());
        orgStyle.setBottomBorderColor(srcStyle.getBottomBorderColor());
        orgStyle.setBottomBorderColor(srcStyle.getBottomBorderXSSFColor());
        orgStyle.setDataFormat(srcStyle.getDataFormat());
        orgStyle.setFillBackgroundColor(srcStyle.getFillBackgroundColor());


        orgStyle.setWrapText(srcStyle.getWrapText());
        orgStyle.setFillForegroundColor(srcStyle.getFillForegroundColor());
        orgStyle.setHidden(srcStyle.getHidden());
        orgStyle.setIndention(srcStyle.getIndention());
        orgStyle.setLeftBorderColor(srcStyle.getLeftBorderColor());
        orgStyle.setLocked(srcStyle.getLocked());
        orgStyle.setFillPattern(srcStyle.getFillPatternEnum());
        orgStyle.setQuotePrefixed(srcStyle.getQuotePrefixed());
        orgStyle.setRightBorderColor(srcStyle.getRightBorderColor());
        orgStyle.setRotation(srcStyle.getRotation());
        orgStyle.setShrinkToFit(srcStyle.getShrinkToFit());
        orgStyle.setTopBorderColor(srcStyle.getTopBorderColor());

        XSSFColor color = srcStyle.getFillBackgroundColorColor();
        if(color != null){
            orgStyle.setFillBackgroundColor(color);
        }
        color = srcStyle.getFillBackgroundXSSFColor();
        if(color != null){
            orgStyle.setFillBackgroundColor(color);
        }
        color = srcStyle.getFillForegroundColorColor();
        if(color != null){
            orgStyle.setFillForegroundColor(color);
        }
        color = srcStyle.getFillForegroundXSSFColor();
        if(color != null){
            orgStyle.setFillForegroundColor(color);
        }
        color = srcStyle.getLeftBorderXSSFColor();
        if(color != null){
            orgStyle.setLeftBorderColor(color);
        }
        color = srcStyle.getRightBorderXSSFColor();
        if(color != null){
            orgStyle.setRightBorderColor(color);
        }
        color = srcStyle.getTopBorderXSSFColor();
        if(color != null){
            orgStyle.setTopBorderColor(color);
        }
    }

    private void setStringData(int rowIndex,XSSFCell srcCell,XSSFCell orgCell){
        String value = srcCell.getStringCellValue();
        orgCell.setCellValue(value);
        if(value == null || !value.startsWith("$")){
            return;
        }
        if(value.startsWith("$$")){
            if(value.startsWith("$$date")){
                Calendar c = Calendar.getInstance();
                if(value.length() > "$$date".length()){
                    String date = value.substring(6,7);
                    if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                        c.add(Calendar.WEEK_OF_YEAR,-1);
                    }
                    int intDate = Integer.parseInt(date) % 7 + 1;
                    if(intDate == Calendar.SUNDAY){
                        c.add(Calendar.WEEK_OF_YEAR,1);
                    }
                    c.set(Calendar.DAY_OF_WEEK,intDate);
                }
                value = new SimpleDateFormat("yyyy/MM/dd").format(c.getTime());
            }
        }else if(value.startsWith("$")){
            int index = rowIndex - dataListIndexBegin;
            Record record = null;
            if(index < dataList.size() && index >= 0){
                record = dataList.get(index);
            }
            if(record == null){
                record = this.record;
            }
            if(record == null){
                value = "";
            }else{
                value = record.get(value.substring(1));
            }
        }
        if(Util.isEmpty(value)){
            value = "";
        }
        orgCell.setCellValue(value);
    }
}
