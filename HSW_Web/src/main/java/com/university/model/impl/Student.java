package com.university.model.impl;

import com.university.model.Model;

public class Student extends Model {
	
	private Long id;
	private String studentId;
	private String name;
	private String major;
	private String phoneNumber;
	
	/**
	 * 생성자를 통해 해당 객체가 어느 데이터베이스 테이블을 쓰고 있는지 명시해 줍니다.
	 */
	public Student() {
		super("student");
	}
}
