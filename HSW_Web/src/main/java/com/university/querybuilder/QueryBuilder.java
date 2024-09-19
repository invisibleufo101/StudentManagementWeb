package com.university.querybuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.university.model.Model;

public class QueryBuilder {

	private Class<? extends Model> modelClass;
	private String table;
	private QueryExecutor queryExecutor;
	private StringBuilder query = new StringBuilder();
	private List<Object> parameters = new ArrayList<>();

	public QueryBuilder(Class<? extends Model> modelClass) {
		this.modelClass = modelClass;
		this.table = setTableName(modelClass);
		this.queryExecutor = new QueryExecutor();
	}

	// Extracts the table name from Model.class
	private String setTableName(Class<? extends Model> modelClass) {
		try {
			Object modelInstance = modelClass.getDeclaredConstructor().newInstance();
			Method getTableMethod = modelClass.getMethod("getTable");
			String tableName = (String) getTableMethod.invoke(modelInstance);
			return tableName;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public QueryBuilder select(String... columns) {
		columns = Arrays.stream(columns).map(this::camelCaseToSnakeCase).toArray(String[]::new);
		String selectColumns = String.join(", ", columns);

		this.query.append("SELECT ").append(selectColumns).append(" FROM ").append(this.table);

		return this;
	}

	public QueryBuilder select() {
		this.query.append("SELECT * FROM ").append(this.table);

		return this;
	}

	// Insert only certain columns
	public QueryBuilder insert(String... columns) {
		columns = Arrays.stream(columns).map(this::camelCaseToSnakeCase).toArray(String[]::new);
		String insertColumns = String.join(", ", columns);

		this.query.append("INSERT INTO ").append(this.table).append("( ").append(insertColumns).append(") ");

		return this;
	}

	// Insert ALL columns
	public QueryBuilder insert() {
		this.query.append("INSERT INTO ").append(this.table).append(" ");

		return this;
	}

	public QueryBuilder update() {
		this.query.append("UPDATE ").append(this.table).append(" ");

		return this;
	}

	public QueryBuilder delete() {
		this.query.append("DELETE FROM ").append(this.table).append(" ");

		return this;
	}

	// Made this becuase it might be useful for Subqueries and Joins
	// But idk.. Might delete this later
	public QueryBuilder from(String tableName) {
		this.query.append(" FROM ").append(tableName).append(" ");

		return this;
	}

	// Keyword for INSERT clause
	public QueryBuilder values(Object... values) {
		String valueParameters = String.join(", ", Collections.nCopies(values.length, "?"));

		this.query.append("VALUES (").append(valueParameters).append(")");

		this.parameters.addAll(Arrays.asList(values));

		return this;
	}

	// Keyword for UPDATE clause
	public QueryBuilder set(String column, Object value) {
		column = camelCaseToSnakeCase(column);
		// Check if SET clause was used before so that we can decide to add a comma or
		// not
		// ex) SET param = ?, param2 = ?
		if (this.query.toString().contains("SET")) {
			this.query.append(", ");
		} else {
			this.query.append("SET ");
		}

		this.query.append(column).append(" = ?");
		this.parameters.add(value);

		return this;
	}

	// Possible operands: =, <, >, >=, <=, !=, ^=, <>, etc
	// You get the point.
	// On a second thought I think you can also use this like: ("column", "IS",
	// NULL) ? <- need to test
	public QueryBuilder where(String column, String operand, Object value) {
		column = camelCaseToSnakeCase(column);
		this.query.append(" WHERE ").append(column).append(" ").append(operand).append(" ? ");

		this.parameters.add(value);

		return this;
	}

	// Overloaded WHERE clause method.
	// Assuming that the equals sign (=) is probably going to get used a lot.
	public QueryBuilder where(String column, Object value) {
		column = camelCaseToSnakeCase(column);
		this.query.append(" WHERE ").append(column).append(" = ?");

		this.parameters.add(value);
		return this;
	}

	public QueryBuilder whereNot(String column, Object value) {
		column = camelCaseToSnakeCase(column);
		this.query.append(" WHERE NOT ").append(column).append(" = ? ");

		this.parameters.add(value);
		return this;
	}

	public QueryBuilder whereLike(String column, String expression) {
		column = camelCaseToSnakeCase(column);
		this.query.append(" WHERE ").append(column).append(" LIKE '").append(expression).append("' ");

		return this;
	}

	public QueryBuilder whereIn(String column, Object... values) {
		column = camelCaseToSnakeCase(column);
		String valueParameters = String.join(", ", Collections.nCopies(values.length, "?"));

		this.query.append(" WHERE ").append(column).append(" IN (").append(valueParameters).append(") ");

		this.parameters.add(Arrays.asList(values));
		return this;
	}

	public QueryBuilder or(String column, String operand, Object value) throws SQLException {
		column = camelCaseToSnakeCase(column);
		this.query.append(" OR ").append(column).append(" ").append(operand).append(" ? ");

		this.parameters.add(value);

		return this;
	}

	public QueryBuilder or(String column, Object value) throws SQLException {
		column = camelCaseToSnakeCase(column);
		this.query.append(" OR ").append(column).append(" = ? ");

		this.parameters.add(value);

		return this;
	}

	public QueryBuilder and(String column, String operand, Object value) throws SQLException {
		column = camelCaseToSnakeCase(column);
		this.query.append(" AND ").append(column).append(" ").append(operand).append(" ? ");

		this.parameters.add(value);

		return this;
	}

	public QueryBuilder and(String column, Object value) throws SQLException {
		column = camelCaseToSnakeCase(column);
		this.query.append(" AND ").append(column).append(" = ? ");

		this.parameters.add(value);

		return this;
	}

	// Fetch ALL records
	@SuppressWarnings("unchecked")
	public <T extends Model> List<T> getAll() {
		String queryString = this.query.toString();
		List<Object> resultParameters = this.parameters;

		List<T> results = (List<T>) this.queryExecutor.fetchResults(queryString, resultParameters, this.modelClass);

		clearQuery();
		return results;
	}

	// Fetch a single record
	public <T extends Model> T get() {
		this.query.append(" FETCH FIRST 1 ROWS ONLY ");
		String queryString = this.query.toString();
		List<Object> resultParameters = this.parameters;

		T result = this.queryExecutor.fetchResult(queryString, resultParameters, this.modelClass);

		clearQuery();
		return result;
	}

	// Execute INSERT, UPDATE, DELETE commands
	public void execute() {
		String queryString = this.query.toString();
		List<Object> resultParameters = this.parameters;

		this.queryExecutor.execute(queryString, resultParameters, modelClass);

		clearQuery();
	}

	private void clearQuery() {
		this.query.setLength(0);
		this.parameters.clear();
	}
	
	
	private String camelCaseToSnakeCase(String camelCase) {
		// Convert camelCase strings into snake_case strings
        String snake_case = camelCase.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase(); 

        return snake_case;
    }

}
