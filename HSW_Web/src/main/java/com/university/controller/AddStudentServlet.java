package com.university.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.university.model.impl.Student;
import com.university.querybuilder.QueryBuilder;

@WebServlet("/addStudent.do")
public class AddStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String studentId = request.getParameter("student_id");
		String name = request.getParameter("student_name");
		String major = request.getParameter("student_major");
		String phoneNumber = request.getParameter("student_tel");
		
		new QueryBuilder(Student.class).insert("studentId", "name", "major", "phoneNumber").values(studentId, name, major, phoneNumber).execute();
		
		response.sendRedirect("/browseStudents.do");
	}

}
