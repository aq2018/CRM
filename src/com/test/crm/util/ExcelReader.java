package com.test.crm.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.test.crm.dao.DeptDao;
import com.test.crm.dao.MarketDao;
import com.test.crm.domain.Clue;

public class ExcelReader<T> {
	public static void main(String[] args) throws Exception {
		String cls = "com.test.crm.domain.MarketActive";
		String cls2 = "com.test.crm.domain.Dept";
		MarketDao md = (MarketDao) SqlSessionUtil.getSqlSession().getMapper(MarketDao.class);
		DeptDao dd = (DeptDao) SqlSessionUtil.getSqlSession().getMapper(DeptDao.class);
		/*List<Dept> list2 = dd.getDeptList();
		List<Dept> list = new ExcelReader<Dept>().read(Dept.class,"D:/apache-tomcat-9.0.0.M10/webapps/fileupload/file/dept.xls");*/
		List<Clue> list3 = new ExcelReader<Clue>().read(Clue.class, "D:/Activity1532176354083.xls");
		System.out.println(list3.size());
		JSONUtil.getJson(list3);
	}

	

	/**
	 * 
	 * @param className xx.class
	 * @param path 文件的绝对路径
	 * @return
	 */
	public  List<T> read(Class<T> className,String filePath) {
		
		List<T> list = new ArrayList<>();
		HSSFWorkbook workbook = null;

		try {
			// 读取Excel文件
			InputStream inputStream = new FileInputStream(filePath);
			workbook = new HSSFWorkbook(inputStream);
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Field[] fields = className.getDeclaredFields();
		// 循环工作表
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = workbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				System.out.println("请检查路径");
				continue;
			}
			// 循环行
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if(hssfRow == null){
					continue;
				}
				// 将单元格中的内容存入集合
				try {
					T t = (T) className.newInstance();

					HSSFCell cell = null;
					for (int i = 0; i < fields.length; i++) {
						cell = hssfRow.getCell(i);
						if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							continue;
						}
						/*
						 * if(cell.getDateCellValue() == null ||
						 * "".equals(cell.getDateCellValue())){ continue; }
						 */
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String filedName = fields[i].getName();
						String methodName = "set" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
						Method method = className.getDeclaredMethod(methodName, String.class);
						method.invoke(t, cell.getStringCellValue());
					}

					list.add(t);

				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				
			}
		}
		return list;
	}
}