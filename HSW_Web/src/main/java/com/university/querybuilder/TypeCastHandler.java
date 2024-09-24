package com.university.querybuilder;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class TypeCastHandler {

	public TypeCastHandler() {
	}

	public static Object getValidFieldType(Object value) {
		if (value instanceof String) {
			return (String) value;
		} else if (value instanceof Short) {
			return (Short) value;
		} else if (value instanceof Byte) {
			return (Byte) value;
		} else if (value instanceof Character) {
			return (Character) value;
		} else if (value instanceof Boolean) {
			return (Boolean) value;
		} else if (value instanceof Integer) {
			return (Integer) value;
		} else if (value instanceof Double) {
			return (Double) value;
		} else if (value instanceof Float) {
			return (Float) value;
		} else if (value instanceof Long) {
			return (Long) value;
		} else if (value instanceof Date) { // (!) This is java.sql.Date
			return (Date) value;
		}

		return value;
	}

	// Converts the data type dervied from ResultSet (rs.getObject())
	public static Object convertToFieldType(Field field, Object value) {
		if (value instanceof BigDecimal) {
			if (field.getType() == int.class) {
				return ((BigDecimal) value).intValue();
			} else if (field.getType() == double.class) {
				return ((BigDecimal) value).doubleValue();
			} else if (field.getType() == long.class) {
				return ((BigDecimal) value).longValueExact();
			} else if (field.getType() == float.class) {
				return ((BigDecimal) value).floatValue();
			}
		} else if (value instanceof Timestamp && field.getType() == Date.class) {
			return new Date(((Timestamp) value).getTime());
		} else if (value instanceof Boolean && field.getType() == boolean.class) {
			return (Boolean) value;
		} else if (value instanceof String && field.getType() == String.class) {
			return (String) value;
		}

		return value;
	}
}
