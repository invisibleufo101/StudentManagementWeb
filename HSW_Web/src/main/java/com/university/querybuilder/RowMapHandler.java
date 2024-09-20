package com.university.querybuilder;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.university.model.Model;

public class RowMapHandler {

	/**
	 * SELECT문을 실행하고 모델 자식 객체들이 가지고 있는 필드에 알맞는 데이터 타입을 배정해주기 위해 만든 static 클래스입니다.
	 */
	public RowMapHandler() {}
	 
	/**
	 * 데이터베이스 결과 값들을 가지고 있는 ResultSet 객체에 테이블 항목 이름과 데이터 값을 추출하고
	 * 모델 클래스의 setField() 메소드로 각 객체에 알맞는 필드 값을 지정해줍니다.
	 *  
	 * @param <T> 
	 * @param modelClass 모델 객체 
	 * @param rs 		 데이터베이스 결과 값을 가지고 있는 ResultSet 객체
	 * @return 			 결과 값들을 가지고 있는 모델 객체 배열
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Model> List<T> mapRows(Class<? extends Model> modelClass, ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		List<T> modelObjects = new ArrayList<>();
		
		while(rs.next()) {
			T modelObject = null;
			try {
				modelObject = (T) modelClass.getDeclaredConstructor().newInstance();
				for (int idx=1; idx<=columnCount; idx++) {
					String columnName = metaData.getColumnName(idx).toLowerCase();  
					Object columnValue = rs.getObject(idx);
										
					modelObject.setField(columnName, columnValue);
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | 
					InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			} 
			modelObjects.add(modelObject);
		}
		return modelObjects;
	}
	
	/**
	 * 위 메소드와 동일하지만 테이블 행 하나의 결과 값을 모델 객체에 지정해 반환합니다.
	 * 모델 클래스의 setField() 메소드로 각 객체에 알맞는 필드 값을 지정해줍니다.
	 *  
	 * @param <T> 
	 * @param modelClass 모델 객체 
	 * @param rs 		 데이터베이스 결과 값을 가지고 있는 ResultSet 객체
	 * @return 			 결과 값들을 가지고 있는 모델 객체 배열
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Model> T mapRow(Class<? extends Model> modelClass, ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		T modelObject = null;
		if (rs.next()) {				
			try {
				modelObject = (T) modelClass.getDeclaredConstructor().newInstance();
				for (int idx=1; idx<=columnCount; idx++) {
					String columnName = metaData.getColumnName(idx).toLowerCase();
					Object columnValue = rs.getObject(idx);
					modelObject.setField(columnName, columnValue);
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		return modelObject;
	}
}

