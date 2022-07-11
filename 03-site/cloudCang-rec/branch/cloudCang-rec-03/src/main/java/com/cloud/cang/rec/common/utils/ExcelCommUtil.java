/*
package com.cloud.cang.rec.common.utils;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * @version 1.0
 * @ClassName ExcelCommUtil
 * @Description 07-now版本，Excel工具类， 后缀名.xlsx
 * @Author zengzexiong
 * @Date 2018年8月15日10:08:40
 *//*

public class ExcelCommUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelCommUtil.class);

    private final static String XLS = "xls";
    private final static String XLSX = "xlsx";


    */
/**
     * 从上传文件中获取Excel表格
     *
     * @param file 上传文件对象
     * @return workbook文件
     * @throws IOException
     *//*

    public static Workbook readExcel(MultipartFile file) throws IOException {
        // 检车文件
        checkFile(file);
        // 获取workbook工作簿对象
        return getWorkbook(file);
    }

    */
/**
     * 读取Excel表格
     * @param sheetNum 第几个sheet
     * @param workbook 工作薄
     * @param jumpLineNum 跳过行数
     * @return
     *//*

    public static List<String[]> readExcel(int sheetNum, Workbook workbook, int jumpLineNum) {
        // 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<>();
        if (workbook != null) {
            // 获取当前sheet工作表
            Sheet sheet = workbook.getSheetAt(sheetNum);
            if (sheet == null) {
                return null;
            }
            int firstRowNum = sheet.getFirstRowNum(); // 获取当前sheet的开始行
            int lastRowNum = sheet.getLastRowNum(); // 获取当前sheet的结束行
            // 从beginLineNumh循环所有行
            for (int rowNum = firstRowNum + jumpLineNum; rowNum <= lastRowNum; rowNum++) {
                // 获取当前行
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                // 获取当前行的开始列
                int firstCellNum = row.getFirstCellNum();
                int lastCellNum = row.getLastCellNum();
                if (firstCellNum == -1 && lastCellNum == -1) {
                    return list;
                }
                int rowMaxImum = row.getPhysicalNumberOfCells();        // 每行有效单元格
                if (lastCellNum >= row.getPhysicalNumberOfCells()) {
                    rowMaxImum = lastCellNum;
                }
                String[] cells = new String[rowMaxImum];
                // 循环当前行
                for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                    Cell cell = row.getCell(cellNum);
                    cells[cellNum] = getCellValue(cell);
                }
                list.add(cells);
            }
        }
        try {
            workbook.close();
        } catch (IOException e) {
            logger.error("关闭工作薄出现异常");
        }
        return list;
    }


    */
/**
     * 获取03图片的字节数组
     *
     * @param sheetNum 当前sheet
     * @param workbook
     * @return 当前sheet的图片字节数组集合
     * @throws IOException
     *//*

    public static List<HSSFPictureData> getsheetPictures03(int sheetNum, HSSFWorkbook workbook) {
        List<HSSFPictureData> picDataList = new ArrayList<>();
        if (workbook != null) {
            // 获取当前sheet工作表
            HSSFSheet sheet = workbook.getSheetAt(sheetNum);
            List<HSSFPictureData> picXlsList = workbook.getAllPictures();
            for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) { // 遍历所有图形
                if (shape instanceof HSSFPicture) {
                    HSSFPicture picture = (HSSFPicture) shape; // 将图形转换成图片类型
                    int pictureIndex = picture.getPictureIndex() - 1;
                    HSSFPictureData pictureData = picXlsList.get(pictureIndex);
                    picDataList.add(pictureData);    //  添加图片临时保存地址
                }

            }

        }
        return picDataList;

    }

    */
/**
     * 获取07图片的字节数组集合
     *
     * @param sheetNum 当前sheet编号
     * @param workbook 当前工作薄
     * @return
     *//*

    public static List<XSSFPictureData> getsheetPictures07(int sheetNum, XSSFWorkbook workbook) {
        List<XSSFPictureData> pictureDataList = new ArrayList<>();
        if (workbook != null) {
            // 获取当前sheet工作表
            XSSFSheet sheet = workbook.getSheetAt(sheetNum);
            for (POIXMLDocumentPart dr : sheet.getRelations()) {
                if (dr instanceof XSSFDrawing) {
                    XSSFDrawing drawing = (XSSFDrawing) dr;
                    List<XSSFShape> shapes = drawing.getShapes();
                    for (XSSFShape shape : shapes) {
                        XSSFPicture picture = (XSSFPicture) shape;
                        pictureDataList.add(picture.getPictureData());
                    }
                }

            }
        }
        return pictureDataList;

    }

    */
/**
     * 检查文件是否为excel的xls或xlsx格式
     * @param file
     * @throws IOException
     *//*

    private static void checkFile(MultipartFile file) throws IOException{
        if (null == file) {
            logger.error("文件不存在！");
            throw new FileNotFoundException("文件不存在");
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 判断文件是否是Excel格式文件
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
            logger.error(fileName + "不是Excel文件");
            throw new IOException(fileName + "不是Excel文件");
        }

    }

    */
/**
     * 根据后缀名获取不同的Excel文件对象
     *
     * @param file 文件
     * @return
     *//*

    public static Workbook getWorkbook(MultipartFile file) {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 创建workbook 工作薄对象，表示整个Excel
        Workbook workbook = null;
        try {
            // 获取Excel 文件的io流
            InputStream is = file.getInputStream();
            // 根据文件后缀名不同（xls 和xlsx）获取不同的work book实现类对象
            if (fileName.endsWith(XLS)) {
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(XLSX)) {
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return workbook;
    }

    */
/**
     * 根据单元格类型返回对应字符串值
     *
     * @param cell 单元格对象
     * @return 单元格对应字符串值
     *//*

    private static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // 把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            cell.setCellType(CellType.STRING);
        }

        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK:
                cellValue = "";
                break;
            case ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    public static void main(String[] args) {
        try {
//            InputStream inp = new FileInputStream("E:\\test.xls");
//            Workbook workbook = WorkbookFactory.create(inp);
//            int allSheetNum = workbook.getNumberOfSheets();
//            if (allSheetNum > 0) {
//                for (int i = 0;i<allSheetNum;i++) {
//                    List<HSSFPictureData> pictureDataList = getsheetPictures03(i, (HSSFWorkbook) workbook);
//                    List<String[]> stringList = readExcel(i, workbook);
//                    System.out.println(stringList.toArray());
//
//                }
//
//            }

            InputStream inputstream = new FileInputStream("E:\\test.xlsx");
            Workbook workbook = WorkbookFactory.create(inputstream);
            int allSheetNum = workbook.getNumberOfSheets();
            if (allSheetNum > 0) {
                for (int i = 0; i < allSheetNum; i++) {
                    List<XSSFPictureData> pictureDataList = getsheetPictures07(i, (XSSFWorkbook) workbook);
                    List<String[]> stringList = readExcel(i, workbook, 1);
                    System.out.println("读取完成");
                    for (int j = 0; j < pictureDataList.size(); j++) {
                        String ext = pictureDataList.get(j).suggestFileExtension();
                        byte[] data = pictureDataList.get(j).getData();
                        if (ext.equals("jpeg")) {
                            try {
                                FileOutputStream out = new FileOutputStream("E:\\testPic\\pic" + j + ".jpg");
                                out.write(data);
                                out.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            System.out.println("后缀名不正确");
                        }
                    }

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/
