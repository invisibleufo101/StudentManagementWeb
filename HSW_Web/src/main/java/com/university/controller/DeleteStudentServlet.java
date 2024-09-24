package com.university.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.university.service.StudentService;

@WebServlet("/deleteStudent.do")
public class DeleteStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 학생 명단에 있는 버튼을 통해 학생 정보 삭제를 요청합니다. StudentService 객체를 생성해서 해당 정보를 삭제합니다.
	 * 
	 * 작업이 끝난 뒤에 사용자를 다시 학생 명단 페이지로 보냅니다.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String stringId = request.getParameter("id");
		Long id = Long.parseLong(stringId);

		StudentService studentService = new StudentService();
		studentService.deleteStudent(id);

		response.sendRedirect("/browseStudents.do");
	}
}
