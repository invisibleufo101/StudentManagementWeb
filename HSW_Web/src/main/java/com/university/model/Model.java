package com.university.model;

public abstract class Model {
	
	protected String table;
	protected FieldHandler fieldHandler;
	
	// Case #1) The concrete class hasn't delcared its table name via constructor(super)
	public Model() {
		String lowerCaseClassName = this.getClass().getSimpleName();
		this.table = lowerCaseClassName.concat("s");
		this.fieldHandler = new FieldHandler(this);
	}
	
	// Case #2) Concrete class declared table name via constructor(super)
	public Model(String table) {
		this.table = table;
		this.fieldHandler = new FieldHandler(this);
	}
	
	public void setField(String column, Object value) {
		this.fieldHandler.setField(column, value);
	}
	
	public Object getField(String column) {
		return this.fieldHandler.getField(column);
	}
	
	public String getTable() {
		return table;
	}
}
