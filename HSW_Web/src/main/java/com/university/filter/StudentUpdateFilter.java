package com.university.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.university.validator.UpdateValidator;

@WebFilter(urlPatterns = "/editStudent.do")
public class StudentUpdateFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;

	public StudentUpdateFilter() {
	}

	/**
	 * 사용자가 학생 정보 수정을 했을 때 요청을 가로채고 해당 정보들을 UpdateValidator 객체에 보내서 예외 처리를 합니다.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");

		Map<String, Object> updateParams = new LinkedHashMap<>();

		String stringId = request.getParameter("id");
		Long id = Long.parseLong(stringId);
		String major = request.getParameter("student_major").replace(" ", "");
		String phoneNumber = request.getParameter("student_phone_number").replace(" ", "");

		updateParams.put("id", id);
		updateParams.put("major", major);
		updateParams.put("phoneNumber", phoneNumber);

		HttpSession session = ((HttpServletRequest) request).getSession();
		UpdateValidator updateValidator = new UpdateValidator();
		if (!updateValidator.validate(updateParams)) {
			Map<String, String> errors = updateValidator.getErrorBag();
			Boolean isValidUpdate = false;
			
			session.setAttribute("isValidUpdate", isValidUpdate);
			session.setAttribute("updateErrors", errors);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/readStudent.do");
			dispatcher.forward(request, response);			
			return;
		}
		chain.doFilter(request, response);
	}
}
