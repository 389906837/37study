package com.cloud.cang.mgr.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * Excel导入工具
 * 
 */
public class ExcelUtil {
	/**
	 * 获得文件写入流
	 * @param filepath excel文件的路径
	 * @return
	 */
	public static POIFSFileSystem getPOIFS(String filepath) {
		POIFSFileSystem poiSFS = null;
		try {
			String xls = filepath.substring(filepath.lastIndexOf(".") + 1,
					filepath.length());
			if (xls == null || !xls.equalsIgnoreCase("xls")) {
				return null;
			}
			InputStream is = new FileInputStream(new File(filepath));
			if (is != null) {
				try {
					poiSFS = new POIFSFileSystem(is);
					is.close();
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return poiSFS;
	}

	/***
	 * 获取输入流
	 * @param inputstream
	 * @return
	 */
	public static POIFSFileSystem getPOIFSByInputStream(InputStream inputstream) {
		POIFSFileSystem poiSFS = null;
		try {

			if (inputstream != null) {
				try {
					poiSFS = new POIFSFileSystem(inputstream);
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return poiSFS;
	}

	/***
	 * 获取工作Workbook
	 * 
	 * @param poiSFS
	 * @return
	 */
	public static HSSFWorkbook getWorkbook(POIFSFileSystem poiSFS) {
		HSSFWorkbook workBook = null;
		if (poiSFS != null) {
			try {
				workBook = new HSSFWorkbook(poiSFS);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return workBook;
	}

	/**
	 * 根据excel表的索引获取工作表
	 * 
	 * @param workBook
	 *            excel文件
	 * @param sheelIndex
	 *            工作表的索引
	 * @return
	 */
	public static HSSFSheet getHSSFSheetByIndex(HSSFWorkbook workBook,
			int sheelIndex) {
		HSSFSheet sheet = null;
		if (workBook != null) {
			try {
				
				sheet = workBook.getSheetAt(sheelIndex);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sheet;
	}

	/**
	 * 根据excel表的名称获取工作表
	 * @param workBook
	 * @param sheelName
	 * @return
	 */
	public static HSSFSheet getHSSFSheetByName(HSSFWorkbook workBook,
			String sheelName) {
		HSSFSheet sheet = null;
		if (workBook != null) {
			try {
				sheet = workBook.getSheet(sheelName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sheet;
	}

	/***
	 * 获取excel所有工作表
	 * 
	 * @param workBook
	 * @return
	 */
	public static List<HSSFSheet> getAllHSSFSheet(HSSFWorkbook workBook) {
		List<HSSFSheet> list = null;
		if (workBook != null) {
			try {
				if (workBook != null) {
					if (workBook.getNumberOfSheets() > 0) {
						list = new ArrayList<HSSFSheet>();
						for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
							list.add(workBook.getSheetAt(i));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/***
	 * 获取某一行
	 * @param HSSFsheet
	 * @param rowIndex
	 * @return
	 */
	public static HSSFRow getRowByIndex(HSSFSheet HSSFsheet, int rowIndex) {
		HSSFRow row = null;
		try {
			if (HSSFsheet != null) {
				if (rowIndex > 0
						&& rowIndex <= (HSSFsheet.getLastRowNum() + 1)) {
					row = HSSFsheet.getRow(rowIndex - 1);
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 获取所有行
	 * @param HSSFSheet
	 * @return
	 */
	public static List<HSSFRow> getAllRow(HSSFSheet HSSFSheet) {
		List<HSSFRow> rows = null;
		try {
			if (HSSFSheet != null) {
				if (HSSFSheet.getLastRowNum() > 0) {
					rows = new ArrayList<HSSFRow>();
					for (int i = 0; i <= HSSFSheet.getLastRowNum(); i++) {
						if (HSSFSheet.getRow(i) != null) {
							rows.add(HSSFSheet.getRow(i));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}

	/**
	 * 获取工作表的哪几行
	 * @param HSSFSheet
	 * @param sstart 开始位置
	 * @param send 结束位置
	 * @return 集合
	 */
	public static List<HSSFRow> getRowByStartAndEnd(HSSFSheet HSSFSheet,
			String sstart, String send) {
		int start = Integer.parseInt(sstart == null ? "0" : sstart);
		int end = Integer.parseInt(send == null ? "0" : send);
		List<HSSFRow> rows = null;
		try {
			if (HSSFSheet != null && HSSFSheet.getLastRowNum() > 0) {
				rows = new ArrayList<HSSFRow>();
				if (start == 0 && end == 0) {
					for (int i = 0; i <= HSSFSheet.getLastRowNum(); i++) {
						if (HSSFSheet.getRow(i) != null) {
							rows.add(HSSFSheet.getRow(i));
						}
					}
				}

				else if (start <= HSSFSheet.getLastRowNum() + 1 && end == 0
						&& start > 0) {
					for (int i = start - 1; i <= HSSFSheet.getLastRowNum(); i++) {
						if (HSSFSheet.getRow(i) != null) {
							rows.add(HSSFSheet.getRow(i));
						}
					}
				}

				else if (start <= HSSFSheet.getLastRowNum() + 1 && end > 0
						&& end <= HSSFSheet.getLastRowNum() + 1 && end >= start
						&& start > 0) {
					for (int i = start - 1; i <= end - 1; i++) {
						if (HSSFSheet.getRow(i) != null) {
							rows.add(HSSFSheet.getRow(i));
						}
					}

				} else if (start > 0 && end <= HSSFSheet.getLastRowNum() + 1
						&& start <= HSSFSheet.getLastRowNum() + 1
						&& start == end) {
					for (int i = start - 1; i <= end - 1; i++) {
						if (HSSFSheet.getRow(i) != null) {
							rows.add(HSSFSheet.getRow(i));
						}
					}
				} else if (start > end) {
					for (int i = 0; i <= HSSFSheet.getLastRowNum(); i++) {
						if (HSSFSheet.getRow(i) != null) {
							rows.add(HSSFSheet.getRow(i));
						}
					}
				} else {
					for (int i = 0; i <= HSSFSheet.getLastRowNum(); i++) {
						if (HSSFSheet.getRow(i) != null) {
							rows.add(HSSFSheet.getRow(i));
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rows;
	}

	/***
	 * 获取某一行的所有单元格
	 * @param HSSFsheet
	 * @param rowIndex
	 * @return
	 */
	public static List<HSSFCell> getOneRowCells(HSSFSheet HSSFsheet,
			int rowIndex) {
		List<HSSFCell> cells = null;

		try {
			HSSFRow row = ExcelUtil.getRowByIndex(HSSFsheet, rowIndex);
			if (row != null) {
				if (row.getLastCellNum() > 0) {
					cells = new ArrayList<HSSFCell>();
					for (int i = 0; i < row.getLastCellNum(); i++) {
						cells.add(row.getCell((short) i));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cells;

	}

	/***
	 * 获取某一行的所有单元格的内容
	 * @param HSSFsheet
	 * @param rowIndex
	 * @return
	 */
	public static String[] getOneRowCellsContext(HSSFSheet HSSFsheet,
			int rowIndex) {
		String[] cells = null;

		try {
			HSSFRow row = ExcelUtil.getRowByIndex(HSSFsheet, rowIndex);
			if (row != null) {
				if (row.getLastCellNum() > 0) {
					cells = new String[row.getLastCellNum()];
					for (int i = 0; i < row.getLastCellNum(); i++) {
						cells[i] = ExcelUtil.getCellContent(row
								.getCell((short) i));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cells;

	}
	/***
	 * 获取某一行的所有单元格
	 * @param inputstream
	 * @param rowIndex
	 * @param sheelIndex
	 * @return
	 */
	public static String[] getOneRowCellsContextIs(InputStream inputstream,
			int rowIndex, int sheelIndex) {
		HSSFWorkbook workBook = ExcelUtil.getWorkbook(ExcelUtil
				.getPOIFSByInputStream(inputstream));
		return ExcelUtil.getOneRowCellsContext(
				ExcelUtil.getHSSFSheetByIndex(workBook, sheelIndex), rowIndex);
	}
	/****
	 * 获取某一行第几个单元格
	 * @param HSSFsheet
	 * @param rowIndex 行索引
	 * @param cellIndex 列索引
	 * @return单元格对象
	 */
	public static HSSFCell getOneCell(HSSFSheet HSSFsheet, int rowIndex,
			int cellIndex) {
		HSSFCell cell = null;
		try {
			HSSFRow row = ExcelUtil.getRowByIndex(HSSFsheet, rowIndex);
			if (row != null && cellIndex > 0
					&& cellIndex <= row.getLastCellNum()) {
				cell = row.getCell((short) (cellIndex - 1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cell;
	}

	/*****
	 * 获取哪几行的哪几个单元格
	 * 
	 * @param HSSFsheet
	 * @param scellstart
	 * @param scellend
	 * @param srowstart
	 * @param srowend
	 * @return
	 */
	public static List<HSSFCell> getCellsByStartAndEnd(HSSFSheet HSSFsheet,
			String scellstart, String scellend, String srowstart, String srowend) {
		int cellstart = Integer.parseInt(scellstart == null ? "0" : scellstart);
		int cellend = Integer.parseInt(scellend == null ? "0" : scellend);
		List<HSSFCell> cells = null;
		List<HSSFRow> rows = ExcelUtil.getRowByStartAndEnd(HSSFsheet,
				srowstart, srowend);
		try {
			if (rows != null && rows.size() > 0) {
				cells = new ArrayList<HSSFCell>();
				for (int i = 0; i < rows.size(); i++) {
					HSSFRow row = rows.get(i);
					if (cellstart == 0 && cellend == 0) {
						for (int j = 0; j < row.getLastCellNum(); j++) {
							cells.add(row.getCell((short) j));
						}
					} else if (cellstart > 0 && cellend <= row.getLastCellNum()
							&& cellend > 0 && cellstart <= cellend) {
						for (int j = cellstart - 1; j <= (cellend - 1); j++) {
							cells.add(row.getCell((short) j));
						}

					} else if (cellstart > 0
							&& cellstart <= row.getLastCellNum()
							&& cellend == 0) {
						for (int j = cellstart - 1; j < row.getLastCellNum(); j++) {
							cells.add(row.getCell((short) j));
						}
					} else {
						for (int j = 0; j < row.getLastCellNum(); j++) {
							cells.add(row.getCell((short) j));
						}
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cells;
	}

	/**
	 * 获取单元格内容
	 * @param cell
	 * @return
	 */
	public static String getCellContent(HSSFCell cell) {
		String str = "";
		
		int type = cell.getCellType();
		switch (type) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			DecimalFormat df = new DecimalFormat("0.00"); 
			str = killzero(df.format(cell.getNumericCellValue()));
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			str = String.valueOf(cell.getCellFormula());
			break;
		case HSSFCell.CELL_TYPE_STRING:
			str = String.valueOf(cell.getRichStringCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			str="";
		default:
			break;
		}
		return str;

	}

	/***
	 * 获取指定单元格内容
	 * @param HSSFsheet
	 * @param scellstart
	 * @param scellend
	 * @param srowstart
	 * @param srowend
	 * @return
	 */
	public static List<String[]> getCellsByStartAndEndContext(
			HSSFSheet HSSFsheet, String scellstart, String scellend,
			String srowstart, String srowend) {
		int cellstart = Integer.parseInt(scellstart == null ? "0" : scellstart);
		int cellend = Integer.parseInt(scellend == null ? "0" : scellend);
		List<String[]> cells = new ArrayList<String[]>();
		List<HSSFRow> rows = ExcelUtil.getRowByStartAndEnd(HSSFsheet,
				srowstart, srowend);
		try {
			if (rows != null && rows.size() > 0) {
				String[] str = null;
				for (int i = 0; i < rows.size(); i++) {

					HSSFRow row = rows.get(i);
					if (cellstart == 0 && cellend == 0) {
						int s = 0;
						str = new String[row.getLastCellNum()];
						for (int j = 0; j < row.getLastCellNum(); j++) {
							if(row.getCell(j)!=null){
								str[s] = ExcelUtil.getCellContent(row
										.getCell(j));
							}else{
								str[s]="";
							}
							
							s++;
						}
					} else if (cellstart > 0 && cellend <= row.getLastCellNum()
							&& cellend > 0 && cellstart <= cellend) {
						int s = 0;
						str = new String[((cellend - cellstart) < 0 ? 0
								: (cellend - cellstart)) + 1];
						for (int j = cellstart - 1; j <= (cellend - 1); j++) {
							if(row.getCell(j)!=null){
								str[s] = ExcelUtil.getCellContent(row
										.getCell(j));
							}else{
								str[s]="";
							}
							s++;
						}
					} else if (cellstart > 0
							&& cellstart <= row.getLastCellNum()
							&& cellend == 0) {
						int s = 0;
						str = new String[row.getLastCellNum() - cellstart + 1];
						for (int j = cellstart - 1; j < row.getLastCellNum(); j++) {
							if(row.getCell(j)!=null){
								str[s] = ExcelUtil.getCellContent(row
										.getCell(j));
							}else{
								str[s]="";
							}
							s++;
						}
					} else {
						int s = 0;
						str = new String[row.getLastCellNum()];
						for (int j = 0; j < row.getLastCellNum(); j++) {
							if(row.getCell(j)!=null){
								str[s] = ExcelUtil.getCellContent(row
										.getCell(j));
							}else{
								str[s]="";
							}
							s++;
						}
					}
					cells.add(str);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cells;
	}

	/***
	 * 解析excel
	 * 
	 * @param inputstream  excel数据流
	 * @param scellstart   开始单元格为NULL时默认从0格开始
	 * @param scellend     每一行的结束单元格为Null表示最后格
	 * @param srowstart    开始行
	 * @param srowend      结束行
	 * @param sheelIndex
	 * @return
	 */
	public static List<String[]> getAllExcelInfo(InputStream inputstream,
			String scellstart, String scellend, String srowstart,
			String srowend, int sheelIndex) {
		HSSFWorkbook workBook = ExcelUtil.getWorkbook(ExcelUtil
				.getPOIFSByInputStream(inputstream));
		return ExcelUtil.getCellsByStartAndEndContext(
				ExcelUtil.getHSSFSheetByIndex(workBook, sheelIndex),
				scellstart, scellend, srowstart, srowend);

	}
	
	
	/***
	 * 解析excel
	 * @param filepath
	 * @param scellstart
	 * @param scellend
	 * @param srowstart
	 * @param srowend
	 * @param sheelIndex
	 * @return
	 */
	public static List<String[]> getAllExcelInfo(String filepath,
			String scellstart, String scellend, String srowstart,
			String srowend, int sheelIndex) {
		HSSFWorkbook workBook = ExcelUtil.getWorkbook(ExcelUtil.getPOIFS(filepath));
		return ExcelUtil.getCellsByStartAndEndContext(
				ExcelUtil.getHSSFSheetByIndex(workBook, sheelIndex),
				scellstart, scellend, srowstart, srowend);

	}
	
	/**
	 * 去掉没有意义小数位0
	 * 
	 * @param str
	 * @return
	 */
	public static String killzero(final String str) {

		if (StringUtils.isBlank(str)) {
			return "";
		}

		String resultStr = str;

		while (resultStr.indexOf(".") != -1
				&& resultStr.charAt(resultStr.length() - 1) == '0') {
			resultStr = resultStr.substring(0, resultStr.length() - 1);
		}

		if (resultStr.charAt(resultStr.length() - 1) == '.') {
			resultStr = resultStr.substring(0, resultStr.length() - 1);
		}

		return resultStr;
	}

	public static void main(String[] args) throws Exception {
		InputStream fis = new FileInputStream(new File("E:\\AssetPackage.xls"));
		List<String[]> cells = ExcelUtil.getAllExcelInfo(fis, "1", null, "2", null, 0);
		for (int j = 0; j < cells.size(); j++) {
			for (int i = 0; i < cells.get(j).length; i++) {
				System.out.print(cells.get(j)[i]+"\t");
				
				
			}
			System.out.println();
		}
		
		
			
			

	}
}