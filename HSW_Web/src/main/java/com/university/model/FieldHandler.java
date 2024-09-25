package com.university.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.university.querybuilder.TypeCastHandler;

public class FieldHandler {

	private Object model;
	private Map<String, Field> fieldMap;

	/**
	 * 
	 * 모델 객체를 기반으로 필드를 초기화하고 getFields()와 setFields()에 필요한 필드 맵을 구성합니다.
	 *
	 * @param model 필드를 처리할 모델 객체
	 */
	public FieldHandler(Object model) {
		this.model = model;
		this.fieldMap = getFieldMap(model);
	}

	/**
	 * 모델 객체에 필드에 값을 설정합니다. 필드 맵으로 해당 인자 값으로 받는 필드의 유무를 확인하며 없다면
	 * NoSuchFieldException 예외를 던집니다.
	 *
	 * @param 필드 이름
	 * @param 받을 값
	 */
	public void setField(String column, Object value) {
		try {
			column = convertToLowerCase(column);
			Field field = fieldMap.get(column);
			if (field == null) {
				throw new NoSuchFieldException("Field name: " + column + " does not exist!");
			}
			field.setAccessible(true);
			Object castedValue = TypeCastHandler.convertToFieldType(field, value);
			field.set(model, castedValue);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 모델 객체가 가지고 있는 필드로부터 해당하는 값을 가져옵니다.
	 *
	 * @param  필드 이름
	 * @return 필드에 저장된 값
	 */
	public Object getField(String column) {
		try {
			column = convertToLowerCase(column);
			Field field = fieldMap.get(column);
			if (field == null) {
				throw new NoSuchFieldException("Field name: " + column + "does not exist!");
			}
			field.setAccessible(true);
			return field.get(model);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 모델 객체의 선언된 필드를 가져와 필드 맵을 구성합니다.
	 * 
	 * @param model 필드를 처리할 모델 객체
	 * @return 필드 이름과 Field 객체가 Key-Value pair로 담긴 맵
	 */
	private Map<String, Field> getFieldMap(Object model) {
		Map<String, Field> fieldMap = new HashMap<>();
		Field[] fields = model.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			fieldMap.put(field.getName().toLowerCase(), field);
		}
		return fieldMap;
	}

	/**
	 * QueryBuilder 객체로부터 받는 테이블 항목 이름을 소문자로 변환하고 "_"를 제거합니다. 데이터베이스는 snake_case를
	 * 쓰지만 자바는 camelCase를 쓰기 때문에 데이터베이스로부터 값을 가져올 시 필드 이름을 한번 처리해야 됩니다.
	 * 
	 * @param columnName 변환할 컬럼 이름
	 * @return 변환된 소문자 컬럼 이름
	 */
	private String convertToLowerCase(String columnName) {
		String lowerCaseColumn = columnName.toLowerCase().replace("_", "");
		return lowerCaseColumn;
	}
}
