package com.university.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.university.validation.RegistrationValidator;

// /editStudent.do
@WebFilter(urlPatterns = "/addStudent.do")
public class StudentRegistrationFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

	public StudentRegistrationFilter() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		
		Map<String, String> registrationParams = new LinkedHashMap<>();
		
		String studentId = request.getParameter("student_id");
		String name = request.getParameter("student_name");
		String major = request.getParameter("student_major");
		String phoneNumber = request.getParameter("student_tel");
		
		registrationParams.put("major", major);
		registrationParams.put("phoneNumber", phoneNumber);
		registrationParams.put("studentId", studentId);
		registrationParams.put("name", name);
		
		RegistrationValidator validator = new RegistrationValidator();
		try {
			if (!validator.validate(registrationParams)) {
				Map<String, String> errors = validator.getErrorBag();
				showError(response, errors);
				return;
			}
		} catch (NoSuchFieldException | IOException e) {
			e.printStackTrace();
		}
		
		chain.doFilter(request, response);
	}
	
	// Show Errors via Javascript alert()
	private void showError(ServletResponse response, Map<String, String> errors) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    
		PrintWriter out = response.getWriter();
		out.println("<script>");
		for (Map.Entry<String, String> entry : errors.entrySet()) {
	        out.println("alert('" + entry.getValue() + "');");
	    }
		out.println("location='register_student.html';");
	    out.println("</script>");
		
		out.close();
	}
}
