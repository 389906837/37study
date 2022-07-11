package com.cloud.cang.rec.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 数据导出到Excel工具
 */
public class ExcelExportUtil {

	// 定制日期格式
	private static String DATE_FORMAT = "yyyy-m-d"; // "m/d/yy h:mm"
	// 定制浮点数格式
	private static String NUMBER_FORMAT = " #,##0.00 ";
	private String xlsFileName;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private HSSFRow row;
	
	
	HSSFCellStyle cellStyle=null;
	HSSFDataFormat format = null;

	/**
	 * 初始化Excel
	 * @param fileName 导出文件名
	 */
	public ExcelExportUtil(String fileName) {
		this.xlsFileName = fileName;
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet();
	}
	
	/**
	 * 初始化Excel
	 */
	public ExcelExportUtil() {
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet();
	}

	/**
	 * 导出Excel文件
	 */
	public void exportXLS() {
		try {
			FileOutputStream fOut = new FileOutputStream(xlsFileName);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	/**
	 * 下载导出文件
	 * @param response
	 * @param filename
	 * @throws IOException
	 */
	public void downloadExcel(HttpServletResponse response, String filename) {
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(filename, "UTF-8"));
			response.setContentType("application/msexcel;charset=UTF-8");
			response.addHeader("Pargam", "no-cache");  
			response.addHeader("Cache-Control", "no-cache");  
			workbook.write(out);
		} catch (Exception e) {
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					
				}

			}

		}

	}
	/**
	 * 下载导出文件
	 * @param response
	 * @param filename
	 * @throws IOException
	 */
	public void downloadExcel(HttpServletRequest request,HttpServletResponse response, String filename) {
	    OutputStream out = null;
	    try {
	        out = response.getOutputStream();
	        String agent = request.getHeader("USER-AGENT");
            if(agent != null && agent.indexOf("MSIE") == -1 && agent.indexOf("Trident") == -1) {// FF    
                String enableFileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(filename.getBytes("UTF-8")))) + "?=";  
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);  
            } else { // IE    
                String enableFileName = URLEncoder.encode(filename, "UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);  
            }
	        response.setContentType("application/msexcel;charset=UTF-8");
	        response.addHeader("Pargam", "no-cache");  
	        response.addHeader("Cache-Control", "no-cache");  
	        workbook.write(out);
	    } catch (Exception e) {
	    } finally {
	        if (out != null) {
	            try {
	                out.flush();
	                out.close();
	            } catch (IOException e) {
	                
	            }
	            
	        }
	        
	    }
	    
	}

	/**
	 * 增加一行
	 * @param index 行号
	 */
	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}

	/**
	 * 设置字符串单元格
	 * @param index 列号
	 * @param value 单元格填充值
	 */
	public void setCell(int index, String value) {
		HSSFCell cell = this.row.createCell((short) index);
		if(value == null){
			cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
		}else{
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new HSSFRichTextString(value));
		}
			
		
	}

	/**
	 * 设置日期类型单元格
	 * @param index 列号
	 * @param value 单元格填充值
	 */
	public void setCell(int index, Date value) {
		HSSFCell cell = this.row.createCell((short) index);
		if (value == null) {
			cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
		} else {
			cell.setCellValue(value);
			
			if(cellStyle==null || format==null){
				cellStyle = workbook.createCellStyle();
				format = workbook.createDataFormat();
			}
			
			cellStyle.setDataFormat(format.getFormat(DATE_FORMAT));
			cell.setCellStyle(cellStyle);
		}

	}

	/**
	 * 设置Int类型单元格
	 * @param index 列号
	 * @param value 单元格填充值
	 */
	public void setCell(int index, Integer value) {
		HSSFCell cell = this.row.createCell((short) index);
		if(value == null){
			cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
		}else{
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(value);
		}
		
	}

	/**
	 * 设置Double类型单元格
	 * @param index 列号
	 * @param value 单元格填充值
	 */
	public void setCell(int index, Double value) {
		HSSFCell cell = this.row.createCell((short) index);
		if(value == null){
			cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
		}else{
			if(cellStyle==null || format==null){
				cellStyle = workbook.createCellStyle();
				format = workbook.createDataFormat();
			}
			
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(value);
			cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
			cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
		}
	}
	
	/**
	 * 设置Long类型单元格
	 * @param index 列号
	 * @param value 单元格填充值
	 */
	public void setCell(int index, Long value) {
		HSSFCell cell = this.row.createCell((short) index);
		if(value == null){
			cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
		}else{
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new HSSFRichTextString(String.valueOf(value)));
		}
	}
	
	
	 public   static   void  main(String[] args)  {
	        System.out.println( " 开始导出Excel文件 " );
	        ExcelExportUtil e  =   new  ExcelExportUtil( "d:/test.xls" );
	        List<Student> students = new ArrayList<Student>();
	        Student student1 = new Student();
	        student1.setId((long)1234);
	        student1.setBirth(new Date());
	        student1.setCert(11.8);
	        student1.setSname("sunny1");
	        students.add(student1);  
	        Student student2 = new Student();
	        student2.setId((long)12345);
	        student2.setBirth(new Date());
	        student2.setCert(null);
	        student2.setSname("sunny2");
	        students.add(student2);  
	        
	        
	        e.createRow( 0 );
	        e.setCell( 0 ,  " 编号 " );
	        e.setCell( 1 ,  " 姓名 " );
	        e.setCell( 2 ,  " 日期 " );
	        e.setCell( 3 ,  " 分数 " );
	     for(int i = 0; i < students.size();i++){
	    	 	Student  s = students.get(i);
	    	 	e.createRow( i+1 );
		        e.setCell( 0 ,  s.getId() );
		        e.setCell( 1 ,  s.getSname() );
		        e.setCell( 2 ,  s.getBirth() );
		        e.setCell( 3 ,  s.getCert() );
	     }
	        
	        

	         try   {
	            e.exportXLS();
	            System.out.println( " 导出Excel文件[成功] " );
	        }   catch  (Exception e1)  {
	            System.out.println( " 导出Excel文件[失败] " );
	            e1.printStackTrace();
	        } 
	    } 

}

class Student {
   private Long id;
   private String sname;
   private Date birth;
   private Double cert;

   public Long getId() {
       return id;
   }

   public void setId(Long id) {
       this.id = id;
   }

   public String getSname() {
       return sname;
   }

   public void setSname(String sname) {
       this.sname = sname;
   }

   public Date getBirth() {
       return birth;
   }

   public void setBirth(Date birth) {
       this.birth = birth;
   }

   public Double getCert() {
       return cert;
   }

   public void setCert(Double cert) {
       this.cert = cert;
   }

}
