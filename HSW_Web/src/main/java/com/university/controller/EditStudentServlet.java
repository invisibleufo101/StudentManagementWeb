package com.university.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.university.model.impl.Student;
import com.university.service.StudentService;

@WebServlet("/editStudent.do")
public class EditStudentServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 학생의 정보를 수정하려고 하는 사용자의 요청을 처리합니다.
	 * 해당 프로젝트에서 모든 예외처리는 Filter와 Validation 객체들이 담당합니다.
	 * 그러므로 이 메소드에서는 바로 사용자가 제공한 정보를 처리해도 문제가 없습니다.
	 * 
	 * StudentService 객체를 생성해서 학생 정보를 수정합니다.
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stringId = request.getParameter("id");
		Long id = Long.parseLong(stringId);
		String major = request.getParameter("student_major").replace(" ", "");
		String phoneNumber = request.getParameter("student_phone_number").replace(" ", "");
		
		Student updateStudent = new Student();
		updateStudent.setField("id", id);
		updateStudent.setField("major", major);
		updateStudent.setField("phoneNumber", phoneNumber);
		
		StudentService studentService = new StudentService();
		studentService.updateStudent(updateStudent);
		
		response.sendRedirect("/browseStudents.do");
	}
}
