package com.tan.util;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

//泛型类
public class ExcelUtil<T> {

	/**
	 * 不用模板导出Excel
	 * @param list
	 * @param wb
	 * @param headers
	 * @param columns
	 */
	public static void fillExcelData(List<Map<String,Object>> list,Workbook wb,String[] headers,String[] columns){
		int rowIndex=0;
		Sheet sheet=wb.createSheet();
		
		//在第一行输入标题
		Row row=sheet.createRow(rowIndex++);
		for(int i=0;i<headers.length;i++){
			row.createCell(i).setCellValue(headers[i]);
		}
		
		//逐行添加数据
		for(int j=0;j<list.size();j++){
			row=sheet.createRow(rowIndex++);
			Map<String, Object> dataMap=list.get(j);
			for(int k=0;k<columns.length;k++){
				Cell cell=row.createCell(k);
				//按字段取值
				String columnName=columns[k];//取值的key
				cell.setCellValue(String.valueOf(dataMap.get(columnName)));
			}
		}	
	}
	
	/**
	 * 利用模板导出Excel
	 * @param list
	 * @param tempFileName
	 * @return
	 * @throws Exception
	 */
	public static Workbook fillExcelDataWithTemplate(List<Map<String,Object>> list,String tempFileName) throws Exception{
		//  "/" 代表的是bin,src文件夹和resources 文件夹下的的东西都会被加载到bin下面, 因为这两个文件被配置为了source
		InputStream inp=ExcelUtil.class.getResourceAsStream("/template/"+tempFileName);
		POIFSFileSystem fs=new POIFSFileSystem(inp);
		Workbook wb=new HSSFWorkbook(fs);
		Sheet sheet=wb.getSheetAt(0);
		//获取列数
		int cellNums=sheet.getRow(0).getLastCellNum();
		int rowIndex=1;//从第二行开始
		for(Map<String,Object> map:list){
			Row row=sheet.createRow(rowIndex++);
			Object[] textValues=map.values().toArray(new Object[cellNums]);
			int colNum=0;
			for(int i=0;i<textValues.length;i++){
				if(textValues[i]!=null){
					row.createCell(colNum++).setCellValue(textValues[i].toString());
				}
			}
		}
		return wb;
	}
	
	/**
	 * 读取Excel中的数据
	 * @param Mfile
	 * @param titleAndAttribute
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public List<T> getExcelInfo(MultipartFile Mfile,Map<String, String> titleAndAttribute,Class<T> clazz) throws Exception{
		//把spring文件上传的MultipartFile转换为File
		CommonsMultipartFile cf=(CommonsMultipartFile) Mfile;
		DiskFileItem fi=(DiskFileItem) cf.getFileItem();
		File file=fi.getStoreLocation();
		//初始化导入信息集合
		List<T> info=new ArrayList<T>();
		InputStream is=new FileInputStream(file);
		HSSFWorkbook wb=new HSSFWorkbook(is);
		HSSFSheet sheet=wb.getSheetAt(0);//获取第一个sheet页
		if(sheet!=null){
			//获取标题行
			HSSFRow titleRow=sheet.getRow(0);
			//属性名 attribute
			Map<Integer,String> attribute=new LinkedHashMap<Integer,String>();
			if(titleAndAttribute!=null){//如果传来的标题和属性名集合不为空
				for(int colNum=0;colNum<titleRow.getLastCellNum();colNum++){
					HSSFCell cell=titleRow.getCell(colNum);
					if(cell!=null){
						String key=cell.getStringCellValue();//获取每个单元格中的标题内容
						String value=titleAndAttribute.get(key);//获取标题对应的属性名
						attribute.put(Integer.valueOf(colNum), value);
					}
				}
			}
			//循环行，从第二行开始
			for(int rowNum=1;rowNum<=sheet.getLastRowNum();rowNum++){//小于等于
				HSSFRow row=sheet.getRow(rowNum);
				if(row==null){
					continue;
				}
				
				//添加一个判断
				boolean b=true;
				T t=clazz.newInstance();
				//循环列
				for(int c=0;c<row.getLastCellNum();c++){//小于
					HSSFCell cell=row.getCell(c);
					String value=getCellValue(cell);
					//单元格中的值等于null或等于"" 就放弃整行数据
					if(value==null||value.equals("")){
						b=false;
						break;
					}
					//利用反射获取类中的字段
					Field field=clazz.getDeclaredField(attribute.get(Integer.valueOf(c)));
            		String keyName=field.getName();
            		Object obj=null;
            		Class<?> fieldType=field.getType();
            		//用来判断set方法中对应的参数类型
            		if (fieldType.isAssignableFrom(Integer.class)) {
            			obj = Integer.valueOf(value);
                    } else if (fieldType.isAssignableFrom(Double.class)) {
                    	obj = Double.valueOf(value);
                    } else if (fieldType.isAssignableFrom(Float.class)) {
                    	obj = Float.valueOf(value);
                    } else if (fieldType.isAssignableFrom(Long.class)) {
                    	obj = Long.valueOf(value);
                    } else if (fieldType.isAssignableFrom(Date.class)) {
                    	obj = new SimpleDateFormat("yyyy-MM-dd").parse(value);
                    } else if (fieldType.isAssignableFrom(Boolean.class)) {
                    	obj = "Y".equals(value) || "1".equals(value);
                    } else if (fieldType.isAssignableFrom(String.class)) {
                    	obj = value;
                    }
            		
            		//创建一属性描述器
            		PropertyDescriptor pd = new PropertyDescriptor(keyName,clazz);
            		Method setMethod = pd.getWriteMethod();// 获得setter方法
            		setMethod.invoke(t,obj);// 执行setter方法
				}
				if(b) info.add(t);
				}
			}
		return info;
	}
	
	/**
	 * 格式化Excel数据
	 * @param hssfCell
	 * @return
	 */
	public static String formatCell(HSSFCell hssfCell){
		if(hssfCell==null){
			return "";
		}else{
			if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
				return String.valueOf(hssfCell.getBooleanCellValue());
			}else if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
				return String.valueOf(hssfCell.getNumericCellValue());
			}else{
				return String.valueOf(hssfCell.getStringCellValue());
			}
		}
	}
	
	/**
	 * 判断Excel导入的数据类型，转换为数据库可识别的数据类型
	 * @param cell
	 * @return
	 */
	public static String getCellValue(HSSFCell cell){
		DecimalFormat df=new DecimalFormat("#");
		if(cell==null){
			return "";
		}
		
		switch(cell.getCellType()){
		case HSSFCell.CELL_TYPE_NUMERIC:
			if(HSSFDateUtil.isCellDateFormatted(cell)){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
			}
			return df.format(cell.getNumericCellValue());
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case HSSFCell.CELL_TYPE_BLANK:
			return "";
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue()+"";
		case HSSFCell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue()+"";
			}
		return "";
	}
}
