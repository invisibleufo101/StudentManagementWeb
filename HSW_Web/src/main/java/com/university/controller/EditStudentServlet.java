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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("EditStudnetServlet called!");
		
		String stringId = request.getParameter("id");
		Long id = Long.parseLong(stringId);
		String major = request.getParameter("student_major");
		String phoneNumber = request.getParameter("student_tel");
		
		Student updateStudent = new Student();
		updateStudent.setField("id", id);
		updateStudent.setField("major", major);
		updateStudent.setField("phoneNumber", phoneNumber);
		
		StudentService studentService = new StudentService();
		studentService.updateStudent(updateStudent);
		
		response.sendRedirect("/browseStudents.do");

	}
}
