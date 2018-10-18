package com.test.crm.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;

import com.test.crm.dao.DeptDao;
import com.test.crm.dao.MarketDao;
import com.test.crm.domain.Dept;
import com.test.crm.domain.MarketActive;
import com.test.crm.domain.Page;

public class ExcelWriter<T> {
	public static void main(String[] args) {
		Class cls = MarketActive.class;
		Class cls2 = Dept.class;
		MarketDao md = (MarketDao) SqlSessionUtil.getSqlSession().getMapper(MarketDao.class);
		DeptDao dd = (DeptDao) SqlSessionUtil.getSqlSession().getMapper(DeptDao.class);
		Map<String,	Object> map = new HashMap<>();
		Page p = new Page();
		p.setPageNo((0));
		p.setPageSize(md.getMarketActiveCount(map).intValue());
		List<MarketActive> list = md.getAllActiveList(p);
		List<Dept> list2 = dd.getDeptList();//???????
		//泛型需要创建对象
		ExcelWriter<MarketActive> d = new ExcelWriter<>();
		HSSFWorkbook workbook = d.getWorkbook(cls,list,"部门表");
		
		
		// 保存Excel文件
		try {
			OutputStream outputStream = new FileOutputStream("D:/acts2.xls");
			workbook.write(outputStream);
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param className 完整类名
	 * @param dataList 需要导出的数据集合
	 * @param sheetName 表名
	 */
	public HSSFWorkbook getWorkbook(Class<T> className,List<T> dataList,String sheetName) {
		// 创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workbook.createSheet(sheetName);
		// 添加表头行
		HSSFRow hssfRow = sheet.createRow(0);
		// 设置单元格格式居中
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFCell headCell = null;
		HSSFCell cell = null;
		
			try {
				Field[] fields = className.getDeclaredFields();
				T obj =  (T) className.newInstance();
				// 添加表头内容
				for (int i = 0;i < fields.length;i++){
					headCell = hssfRow.createCell(i);
					String fieldName = fields[i].getName();
					headCell.setCellValue(fieldName);
					headCell.setCellStyle(cellStyle);
				}
				
				for (int i = 0; i < dataList.size(); i++) {
					hssfRow = sheet.createRow((int) i+1);
					obj = dataList.get(i);
					
					//反射get方法
					for(int j = 0;j < fields.length;j++){	
							try {
								String fieldName = fields[j].getName();
								String methodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
								cell = hssfRow.createCell(j);
								Method method = className.getDeclaredMethod(methodName);
								/**
								 * 为空输出 ""
								 */
								cell.setCellValue(method.invoke(obj) == null ? "" : String.valueOf(method.invoke(obj)));
								cell.setCellStyle(cellStyle);
							} catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
					}
					
				}
			} catch ( SecurityException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return workbook;
	}

}