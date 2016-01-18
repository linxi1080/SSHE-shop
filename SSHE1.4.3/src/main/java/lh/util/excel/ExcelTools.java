package lh.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelTools {
  
	
	/**
	 *************写入Excel 2003版本*******
	 *@param head 代表列头
	 *@param filename 代表sheet名称
	 *@param list  代表内容
	 */
	public static void writeExcel(HttpServletResponse response,String [] head,String sheetName,List<Object[]> list){
		   if(head==null || head.equals("")){   
			   return;
		   }
		   if(sheetName==null || sheetName.equals("")){
			   sheetName="First";
		   }
		   if(list==null){
			   return;
		   }
		//创建一个excel 2003 版本文件
		HSSFWorkbook workbook=new HSSFWorkbook();
		  //生成样式
		//Map<String, CellStyle> styles = setStyle2003(workbook);
		//创建sheet	
     	HSSFSheet sheet=workbook.createSheet(getExcelFileName(sheetName));
     	//创建一行
        Row headerRow=sheet.createRow(0);
        //设置列头行高
        headerRow.setHeightInPoints(12.75f);
        //给列头赋值
        for(int i =0;i<head.length;i++){
        	//创建一个单元格
        	Cell cell = headerRow.createCell(i);
        	//赋值
            cell.setCellValue(head[i]);
        }
    	//固定列头
     	sheet.createFreezePane(0, 1);
      /******** 设置内容 begin*********/
        Row row;
        Cell cell;
        int rownum = 1;
       for(int j=0; j<list.size();j++,rownum++){
    	   //创建一行
    	   row=sheet.createRow(rownum);
    	   //获取对象
    	   Object[] dataObj = list.get(j);
    	   //判断这一行数据是否为空
    	   if(dataObj == null){
               continue;
           }
    	   //注意：给单元格赋值的时候应与列头字段对应
           for (int z = 0; z < dataObj.length; z++) {
               cell = row.createCell(z);
    	      //判断数据类型   
               if((dataObj[z]!="" || dataObj[z]!=null )&& dataObj[z] instanceof java.lang.String ){
                   cell.setCellValue(dataObj[z].toString());
                  }
               else if((dataObj[z]!="" || dataObj[z]!=null) && dataObj[z] instanceof java.lang.Double){
        	      cell.setCellValue(Double.parseDouble(dataObj[z].toString()));
                }
               //设置单元格宽度
             sheet.setColumnWidth(z,5000);
           }
       } 
       /******** 设置内容 end*********/
       /****** 输出 begin ******/ 
       try {
    	    ServletOutputStream sos = response.getOutputStream();
		    workbook.write(sos);
		    sos.close();
	   } catch (Exception e) {
		  e.printStackTrace();
	  }
	}       
	/**
	 * EXCEL 2003  样式设置
	 */
  public static  Map<String , CellStyle> setStyle2003(HSSFWorkbook workbook){
	  Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
	  CellStyle style;
      Font headerFont = workbook.createFont();
      headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
      style = createBorderedStyle(workbook);
      style.setAlignment(CellStyle.ALIGN_CENTER);
      style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE
              .getIndex());
      style.setFillPattern(CellStyle.SOLID_FOREGROUND);
      style.setFont(headerFont);
      styles.put("header", style);
	  return styles;
  }
  /**
	 * EXCEL 2007 样式设置
	 */
   public Map<String , CellStyle> setStyle2007(XSSFWorkbook xssfWorkbook){
	  Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
	  return styles;
   }
  /**
   *  防止中文乱码
   * @param str
   * @return String
   */
  public static  String getExcelFileName(String str){
  try {
	     str=new String(str.getBytes("utf-8"),"utf-8");
	   } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	   }
	    return str;
  }
  /**
   * 生成一个单元格样式
   * @param wb
   * @return CellStyle
   */
   private static CellStyle createBorderedStyle(Workbook wb) {
       CellStyle style = wb.createCellStyle();
       style.setBorderRight(CellStyle.BORDER_THIN);
       style.setRightBorderColor(IndexedColors.BLACK.getIndex());
       style.setBorderBottom(CellStyle.BORDER_THIN);
       style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
       style.setBorderLeft(CellStyle.BORDER_THIN);
       style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
       style.setBorderTop(CellStyle.BORDER_THIN);
       style.setTopBorderColor(IndexedColors.BLACK.getIndex());
       return style;
   }
   
   //******************************Excel 读取*****************************
	public static List<List<Object>> readExcel(File file,Object sheetName,int cellNum,int startRow) throws IOException{
		   String fileName = file.getName();
		   String extension = fileName.lastIndexOf(".")==-1?"":fileName.substring(fileName.lastIndexOf(".")+1);
		   if("xls".equals(extension)){
		    return read2003Excel(file,sheetName,cellNum,startRow);
		   }else if("xlsx".equals(extension)){
		    return read2007Excel(file,sheetName,cellNum,startRow);
		   }else{
		    throw new IOException("不支持的文件类型");
		   }
		}
   
   /**
	 * 读取03版本的Excel
	 * @param file 读取的文件
	 * @param sheetName //sheet 名
	 * @param cellNum 需读取列数
	 * @param startRow 开始读取的行数 从0开始
	 * @return
	 * @throws IOException
	 */
	private static List<List<Object>> read2003Excel(File file,Object sheetName,int cellNum,int startRow) throws IOException{
	   List<List<Object>> list = new LinkedList<List<Object>>();
	   HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
	   HSSFSheet sheet = null;
	   
	   if(sheetName instanceof String){
		  sheet= hwb.getSheet(sheetName.toString());
		}else if(sheetName instanceof Integer){
		  sheet= hwb.getSheetAt((Integer)sheetName);
		}else {
			return null;
		}
	   Object value = null;
	   HSSFRow row = null;
	   HSSFCell cell = null;
	  
	   for(int i = startRow;i<= sheet.getPhysicalNumberOfRows();i++){
	    row = sheet.getRow(i);
	    if (row == null) {
	     continue;
	    }
	    List<Object> linked = new LinkedList<Object>();
	    DecimalFormat df = new DecimalFormat("0");// 格式化 number String 字符
		   //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		     SimpleDateFormat sdfs = new SimpleDateFormat("yyyy年MM月");
		     SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		     SimpleDateFormat sdt = new SimpleDateFormat("yyyy-M-d");
		     DecimalFormat nf = new DecimalFormat("0.##");// 格式化数字
		     DecimalFormat nt = new DecimalFormat("##.##%");// 格式化数字
	    
	    for (int j = 0; j <= cellNum; j++) {
	     cell = row.getCell(j);
	     if (cell == null) {
	    	 value="";
	      //continue;
	     }else{
	 
		     switch (cell.getCellType()) {
			     case XSSFCell.CELL_TYPE_STRING:
			     // System.out.println(i+"行"+j+" 列 is String type");
			      value = cell.getStringCellValue();
			      break;
			     case XSSFCell.CELL_TYPE_NUMERIC:
			     // System.out.println(i+"行"+j+" 列 is Number type ---->"+cell.getCellStyle().getDataFormatString());
				      if("@".equals(cell.getCellStyle().getDataFormatString())){
				         value = df.format(cell.getNumericCellValue());
				      } else if("General".equals(cell.getCellStyle().getDataFormatString())){
				         value = Double.parseDouble(nf.format(cell.getNumericCellValue()));
				      }else if("0.0000%".equals(cell.getCellStyle().getDataFormatString())){
				    	  value = nf.format(cell.getNumericCellValue());
				      }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){
				    	  value=sd.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
				      }else if("0.00_);[Red]\\(0.00\\)".equals(cell.getCellStyle().getDataFormatString())){
				    	  value = Double.parseDouble(nf.format(cell.getNumericCellValue()));
				      }else if("0.00_);[红色](0.00)".equals(cell.getCellStyle().getDataFormatString())){
				    	  value = Double.parseDouble(nf.format(cell.getNumericCellValue()));
				      }else if("000000)".equals(cell.getCellStyle().getDataFormatString())){
				    	  value = nf.format(cell.getNumericCellValue());
				      }else if("0.00_ ".equals(cell.getCellStyle().getDataFormatString())){
				    	  value = nf.format(cell.getNumericCellValue());
				      }else if("yyyy-m-d".equals(cell.getCellStyle().getDataFormatString())){
				    	  value = sdt.format(cell.getNumericCellValue());
				      }else if("yyyy-mm-dd".equals(cell.getCellStyle().getDataFormatString())){
				    	  value = sd.format(cell.getNumericCellValue());
				      }else if("yyyy\"年\"m\"月\";@".equals(cell.getCellStyle().getDataFormatString())){
					    	value = sdfs.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
				      }else if("0.00%".equals(cell.getCellStyle().getDataFormatString())){
				    	  value = nt.format(cell.getNumericCellValue());
				      }else{
				    	  //System.out.println("---------------------->"+i+"行"+j+" 列 -->"+cell.getCellStyle().getDataFormatString());
				        //value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
				    	  value = sdfs.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
				      }
			      break;
			     case XSSFCell.CELL_TYPE_BOOLEAN:
			      System.out.println(i+"行"+j+" 列 is Boolean type");
			      value = cell.getBooleanCellValue();
			      break;
			     case XSSFCell.CELL_TYPE_BLANK:
			      System.out.println(i+"行"+j+" 列 is Blank type");
			      value = "";
			      break;
			     case XSSFCell.CELL_TYPE_FORMULA:
			    	 System.out.println(i+"行"+j+" 列 is FORMULA type");
			    	 //value=cell.getCellFormula();
			    	 value= String.valueOf(cell.getNumericCellValue());//获取公式值
			    	 
			     break;
			     default:
			      System.out.println(i+"行"+j+" 列 is default type");
			      value = cell.toString();
		     }
	    }
	   
	     linked.add(value);
	    
	   }
	    list.add(linked);
	   }
	  
	   return list;
	}
   
	/**
	 * 读取07版本的Excel
	 * @param file 读取的文件
	 * @param sheetName //sheet 名
	 * @param cellNum 需读取列数
	 * @param startRow 开始读取的行数 从0开始
	 * @return
	 * @throws IOException
	 */
	private static List<List<Object>> read2007Excel(File file,Object sheetName,int cellNum,int startRow) throws IOException {

	   List<List<Object>> list = new LinkedList<List<Object>>();
	   // 构造 XSSFWorkbook 对象，strPath 传入文件路径
	   XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
	   System.out.println("Read。。。");
	   // 读取第一章表格内容
	   XSSFSheet sheet = null;
	   if(sheetName instanceof String){
		   sheet=xwb.getSheet(sheetName.toString());
		}else if(sheetName instanceof Integer){
			sheet=xwb.getSheetAt((Integer)sheetName);
		}else {
			return null;
		}
	   Object value = null;
	   XSSFRow row = null;
	   XSSFCell cell = null;
	   
	   
	   for (int i = startRow; i <= sheet.getPhysicalNumberOfRows(); i++) {
	    row = sheet.getRow(i);
	    if (row == null) {
	     continue;
	    }
	    List<Object> linked = new LinkedList<Object>();
	    
	    DecimalFormat df = new DecimalFormat("0");// 格式化 number String 字符
		   //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		     SimpleDateFormat sdfs = new SimpleDateFormat("yyyy年MM月");
		     SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		     SimpleDateFormat sdt = new SimpleDateFormat("yyyy-M-d");
		     DecimalFormat nf = new DecimalFormat("0.##");// 格式化数字
		     DecimalFormat nt = new DecimalFormat("##.##%");// 格式化数字
		     
		     
		    // System.out.println(i+"行"+j+" 列 -->"+cell.getCellType());
	    
	    
	    for (int j = 0; j <= cellNum; j++) {
	     cell = row.getCell(j);
	     if (cell == null) {
	    	 value="";
	     }else{
		 
		   
		     switch (cell.getCellType()) {
		     case XSSFCell.CELL_TYPE_STRING:
		     // System.out.println(i+"行"+j+" 列 is String type");
		      value = cell.getStringCellValue();
		      break;
		     case XSSFCell.CELL_TYPE_NUMERIC:
		    	//if(i==132 && j==26)
		    	 //System.out.println(cell.getCellStyle().getDataFormatString()+cell.getNumericCellValue());
		      System.out.println(i+"行"+j+" 列 is Number type ; DateFormt:"+cell.getCellStyle().getDataFormatString());
		      if("@".equals(cell.getCellStyle().getDataFormatString())){
		        value = Double.parseDouble(df.format(cell.getNumericCellValue()));
		      } else if("General".equals(cell.getCellStyle().getDataFormatString())){
		        value = nf.format(cell.getNumericCellValue());
		      }else if("yyyy\"年\"m\"月\";@".equals(cell.getCellStyle().getDataFormatString())){
		    	value = sdfs.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
		      }else if("0.0000%".equals(cell.getCellStyle().getDataFormatString())){
		    	  value = nf.format(cell.getNumericCellValue());
		      }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){
		    	  value=sd.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
		      }else if("0.00_);[Red]\\(0.00\\)".equals(cell.getCellStyle().getDataFormatString())){
		    	  value = Double.parseDouble(nf.format(cell.getNumericCellValue()));
		      }else if("0.00_);[红色](0.00)".equals(cell.getCellStyle().getDataFormatString())){
		    	  value = nf.format(cell.getNumericCellValue());
		      }else if("000000)".equals(cell.getCellStyle().getDataFormatString())){
		    	  value = nf.format(cell.getNumericCellValue());
		      }else if("0.00_ ".equals(cell.getCellStyle().getDataFormatString())){
		    	  value = nf.format(cell.getNumericCellValue());
		      }else if("yyyy-m-d".equals(cell.getCellStyle().getDataFormatString())){
		    	  value = sdt.format(cell.getNumericCellValue());
		      }else if("yyyy-mm-dd".equals(cell.getCellStyle().getDataFormatString())){
		    	  value = sd.format(cell.getNumericCellValue());
		      }else if("0.00%".equals(cell.getCellStyle().getDataFormatString())){
		    	  value = nt.format(cell.getNumericCellValue());
		      }
		      else{
		    	  System.out.println("---------------------->"+i+"行"+j+" 列 -->"+cell.getCellStyle().getDataFormatString());
		       
		    	  value = sdfs.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
		    	 // value=cell.getNumericCellValue();
		      }
		      break;
		     case XSSFCell.CELL_TYPE_BOOLEAN:
		     // System.out.println(i+"行"+j+" 列 is Boolean type");
		      value = cell.getBooleanCellValue();
		      break;
		     case XSSFCell.CELL_TYPE_BLANK:
		    //  System.out.println(i+"行"+j+" 列 is Blank type");
		      value = " ";
		      break;
		     case XSSFCell.CELL_TYPE_FORMULA:
		    	// System.out.println(i+"行"+j+" 列 is FORMULA type");
		    	 value=cell.getCellFormula();
		     break;
		     default:
		     // System.out.println(i+"行"+j+" 列 is default type");
		      value = cell.toString();
		     }
	    }
	   
	     linked.add(value);
	    }
	    list.add(linked);
	   }
	   return list;
	}
  
	
}
