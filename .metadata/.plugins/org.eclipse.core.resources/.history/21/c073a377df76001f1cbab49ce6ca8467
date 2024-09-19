package com.university.filter;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.university.validation.UpdateValidator;

@WebFilter(urlPatterns = "/editStudent.do")
public class StudentUpdateFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

	public StudentUpdateFilter() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		
		Map<String, Object> updateParams = new LinkedHashMap();
		
		String stringId = request.getParameter("id");
		Long id = Long.parseLong(stringId);
		String major = request.getParameter("student_major");
		String phoneNumber = request.getParameter("student_tel");
		
		updateParams.put("id", id);
		updateParams.put("major", major);
		updateParams.put("phoneNumber", phoneNumber);
		
		UpdateValidator updateValidator = new UpdateValidator();
		if (!updateValidator.validate(updateParams)) {
			Map<String, String> errors = updateValidator.getErrorBag();
			showError(response, errors);
			return;
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
		out.println("location='/browseStudents.do';");
	    out.println("</script>");
		
		out.close();
	}
}
