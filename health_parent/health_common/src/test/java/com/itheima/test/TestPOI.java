package com.itheima.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @ClassName TestPOI
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/6 14:53
 * @Version V1.0
 */
public class TestPOI {

    /**
     * POI报表的学习路线
         1：工作簿：XSSFWorkBook
         2：工作表：XSSFSheet
         3：行：XSSFRow
         4：单元格：XSSFCell
     */
    // 读取D盘下的hello.xlsx
    @Test
    public void readExcel() throws Exception {
        // 创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(new File("D:/hello.xlsx"));
        // 通过工作表的位置读取对应工作表，0表示第1个工作表
        XSSFSheet sheet = workbook.getSheetAt(0);
        // 简化遍历的方式
        for (Row row : sheet) {
            for (Cell cell : row) {
                System.out.println(cell.getStringCellValue());
            }
        }
        // 关闭
        workbook.close();
    }

    // 读取D盘下的hello.xlsx
    /**
     * 还有一种方式就是获取工作表最后一个行号，从而根据行号获得行对象，通过行获取最后一个单元格索引，从而根据单元格索引获取每行的一个单元格对象
     */
    @Test
    public void readExcel_index() throws Exception {
        // 创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(new File("D:/hello.xlsx"));
        // 通过工作表的位置读取对应工作表，0表示第1个工作表
        XSSFSheet sheet = workbook.getSheetAt(0);
        // sheet获取最后1行的行号
        int lastRowNum = sheet.getLastRowNum();
        System.out.println("lastRowNum:"+lastRowNum);
        // 遍历行（行号从0开始）
        for(int i=0;i<lastRowNum;i++){
            // 获取当前行的对象
            XSSFRow row = sheet.getRow(i);
            // 获取最后的单元格
            short lastCellNum = row.getLastCellNum();
            // 遍历单元格（单元格从0开始）
            for(int j=0;j<lastCellNum;j++){
                XSSFCell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
            }
        }
        // 关闭
        workbook.close();
    }

    // 向excel文件中写入数据
    @Test
    public void writeExcel() throws Exception {
        // 创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表
        XSSFSheet sheet = workbook.createSheet("测试");
        // 创建行
        XSSFRow row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("编号");
        row1.createCell(1).setCellValue("姓名");
        row1.createCell(2).setCellValue("年龄");
        // 创建行
        XSSFRow row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("1");
        row2.createCell(1).setCellValue("张三");
        row2.createCell(2).setCellValue("22");
        // 创建行
        XSSFRow row3 = sheet.createRow(2);
        row3.createCell(0).setCellValue("2");
        row3.createCell(1).setCellValue("李四");
        row3.createCell(2).setCellValue("20");

        // 将excel写入到文件
        OutputStream os = new FileOutputStream("D:/test.xlsx");
        workbook.write(os);
        // 刷新
        os.flush();
        os.close();
        // 关闭
        workbook.close();
    }
}
