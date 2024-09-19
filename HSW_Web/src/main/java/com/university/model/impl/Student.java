package com.university.model.impl;

import com.university.model.Model;

public class Student extends Model {
	
	private Long id;
	private String studentId;
	private String name;
	private String major;
	private String phoneNumber;
	
	public Student() {
		super("student");
	}
}
