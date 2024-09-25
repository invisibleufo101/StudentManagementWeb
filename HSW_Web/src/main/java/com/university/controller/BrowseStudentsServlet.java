package com.university.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.university.model.impl.Student;
import com.university.service.StudentService;

@WebServlet("/browseStudents.do")
public class BrowseStudentsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자가 학 조회 및 검색 탭을 눌렀을 때 호출 되는 메소드입니다.
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		showContent(request, response);
	}

	private void deleteStudentsById(String...rowIds) {
		StudentService studentService = new StudentService();
		for (String rowId : rowIds) {
			Long id = Long.parseLong(rowId);
			studentService.deleteStudent(id);
		}
	}
	/**
	 * 사용자를 위해 데이터베이스에 저장된 모든 학생들의 정보를 볼 수 있는 페이지를 생성합니다. 테이블 위에 있는 검색 기능으로 특정한 조건에
	 * 맞는 학생들을 골라 볼 수 있습니다.
	 * 
	 * @param request  사용자 요청
	 * @param response 요청에 해당하는 학생 명단 페이
	 * @throws IOException
	 */
	private void showContent(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// Remember that there are two buttons in the same <form> tag
		// but their values are different:
		// "delete" to delete table rows
		// "search" to search for records
		
		// Mass Delete Student Rows 
		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		
		// If the button's value is "delete"
		// For search button, the value is "search" 
		if (action.equals("delete")) {			
			String[] rowIds = request.getParameterValues("rowIds");
			if (rowIds != null) {			
				deleteStudentsById(rowIds);
			}
		}
		
		// Get search Category & Keyword from session
		String searchCategory = request.getParameter("search_student_category");
		String searchKeyword = request.getParameter("search_student_keyword");
		
		// Set default value for both search category & keyword
		if (searchCategory == null) {
			searchCategory = "search_by_id";
		}
		
		if (searchKeyword == null) {
			searchKeyword = "";
		}

		HttpSession session = request.getSession();
		session.setAttribute("searchCategory", searchCategory);
		session.setAttribute("searchKeyword", searchKeyword);

		PrintWriter out = response.getWriter();

		// Head
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
		out.println("<link rel='stylesheet' href='css/bootstrap.min.css' />");
		out.println("<script defer src='js/bootstrap.bundle.min.js'></script>");
		out.println("<link rel='icon' href='images/portal_icon.svg'/>");
		out.println("<title>학생 관리 포털</title>");
		out.println("</head>");

		// Body
		out.println("<body>");
		out.println("<main class='d-flex flex-nowrap vh-100'>");

		// Navbar
		out.println("<!-- Start of Navbar -->");
		out.println("<nav class='d-flex flex-lg-column flex-md-column flex-row flex-shrink-0 p-3 text-bg-dark' style='width: 25vw;'>");
		out.println("<a href='/' class='d-flex justify-content-around align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none'>");
		out.println("<svg xmlns='http://www.w3.org/2000/svg' width='40' height='40' fill='currentColor' class='bi bi-mortarboard' viewBox='0 0 16 16'>");
		out.println("<path d='M8.211 2.047a.5.5 0 0 0-.422 0l-7.5 3.5a.5.5 0 0 0 .025.917l7.5 3a.5.5 0 0 0 .372 0L14 7.14V13a1 1 0 0 0-1 1v2h3v-2a1 1 0 0 0-1-1V6.739l.686-.275a.5.5 0 0 0 .025-.917zM8 8.46 1.758 5.965 8 3.052l6.242 2.913z'/>");
		out.println("<path d='M4.176 9.032a.5.5 0 0 0-.656.327l-.5 1.7a.5.5 0 0 0 .294.605l4.5 1.8a.5.5 0 0 0 .372 0l4.5-1.8a.5.5 0 0 0 .294-.605l-.5-1.7a.5.5 0 0 0-.656-.327L8 10.466zm-.068 1.873.22-.748 3.496 1.311a.5.5 0 0 0 .352 0l3.496-1.311.22.748L8 12.46z'/>");
		out.println("</svg>");
		out.println("<span class='ms-3' style='font-size: 1.5rem;'>학생 관리 포털</span>");
		out.println("</a>");
		out.println("<hr/>");
		out.println("<ul class='nav nav-pills flex-column mb-auto'>");

		// 홈
		out.println("<!-- Menu Home -->");
		out.println("<li class='nav-item'>");
		out.println("<a href='/' class='nav-link text-white'>");
		out.println("<div class='d-flex align-items-center'>");
		out.println("<svg class='bi me-2' xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke-width='1.5' stroke='currentColor' width='16' height='16'>");
		out.println("<path stroke-linecap='round' stroke-linejoin='round' d='m2.25 12 8.954-8.955c.44-.439 1.152-.439 1.591 0L21.75 12M4.5 9.75v10.125c0 .621.504 1.125 1.125 1.125H9.75v-4.875c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125V21h4.125c.621 0 1.125-.504 1.125-1.125V9.75M8.25 21h8.25' />");
		out.println("</svg>");
		out.println("<p class='m-0'>메뉴 홈</p>");
		out.println("</div>");
		out.println("</a>");
		out.println("</li>");

		// 학생 조회 및 검색
		out.println("<!-- List and Search Students -->");
		out.println("<li>");
		out.println("<a href='/browseStudents.do' class='nav-link active'>");
		out.println("<div class='d-flex align-items-center'>");
		out.println("<svg class='bi me-2' xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke-width='1.5' stroke='currentColor' width='16' height='16'>");
		out.println("<path stroke-linecap='round' stroke-linejoin='round' d='m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z'/>");
		out.println("</svg>");
		out.println("<p class='m-0'>학생 조회 및 검색</p>");
		out.println("</div>");
		out.println("</a>");
		out.println("</li>");

		// 학생 등록
		out.println("<!-- Register Student -->");
		out.println("<li>");
		out.println("<a href='/register_student.html' class='nav-link text-white'>");
		out.println("<div class='d-flex align-items-center'>");
		out.println("<svg class='bi me-2' xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke-width='1.5' stroke='currentColor' width='16' height='16'>");
		out.println("<path stroke-linecap='round' stroke-linejoin='round' d='m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10' />");
		out.println("</svg>");
		out.println("<p class='m-0'>학생 등록</p>");
		out.println("</div>");
		out.println("</a>");
		out.println("</li>");
		out.println("</ul>");
		out.println("<hr/>");
		out.println("</nav>");
		out.println("<!-- End of Navbar -->");

		// 학생 명단
		out.println("<section class='d-flex justify-content-center align-items-center' style='width: 75vw; background-color: #ced9c7;'>");
		out.println("<!-- Start of Student List -->");
		out.println("<div class='container mx-auto bg-white' style='max-height: 80vh; overflow-y: auto;'>");
		out.println("<h2 class='pt-5 text-center text-decoration-underline mb-3'>학생 명단</h2>");

		
		// 검색 창 (학번, 이름, 학과, 전화번호)
		out.println("<div class='p-5'>");
		out.println("<form action='/browseStudents.do' method='POST'>");
		out.println("<div class='row g-3 justify-content-end'>");
		
		// 학생 대량 삭제 버튼
		out.println("<div class='me-auto col-auto'>");		
		out.println("<button class='btn btn-danger d-flex align-items-center justify-content-between' type='submit' name='action' value='delete'>");
		out.println("<svg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke-width='1.5' stroke='currentColor' height='18' width='18'>");
		out.println("<path stroke-linecap='round' stroke-linejoin='round' d='m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0' />");
		out.println("</svg>");
		out.println("삭제</button>");
		
		out.println("</div>");
		
		out.println("<div class='col-auto'>");
		out.println("<div class='input-group'>");
		
		out.println("<div class='w-20'>");
		out.println("<select class='form-select border rounded-start border-secondary' style='border-radius: 0;' name='search_student_category'>");
		
		// Juggle selected attribute based on the current search category & keyword stored in session
		String category = (String) session.getAttribute("searchCategory");
		if (category.equals("search_by_id")) {
			out.println("<option value='search_by_id' selected> 학번 </option>");
		} else {
			out.println("<option value='search_by_id'> 학번 </option>");
		}
		
		if (category.equals("search_by_name")) {
			out.println("<option value='search_by_name' selected> 이름 </option>");
		} else {
			out.println("<option value='search_by_name'> 이름 </option>");
		}
		
		if (category.equals("search_by_major")) {			
			out.println("<option value='search_by_major' selected> 학과 </option>");
		} else {
			out.println("<option value='search_by_major'> 학과 </option>");
		}
		
		if (category.equals("search_by_phone_number")) {			
			out.println("<option value='search_by_phone_number' selected> 전화번호 </option>");
		} else {
			out.println("<option value='search_by_phone_number'> 전화번호 </option>");
		}
		
		out.println("</select>");
		out.println("</div>");
		
		out.println("<input type='search' class='form-control border border-secondary' id='search_student_keyword' name='search_student_keyword' placeholder='검색' value='"
						+ session.getAttribute("searchKeyword") + "'>");
		out.println("</div>");
		out.println("</div>");
		out.println("<div class='col-auto'>");
		out.println("<button type='submit' class='btn btn-primary mb-3' name='action' value='search'>검색</button>");
		out.println("</div>");
		out.println("</div>");
		out.println("<table class='table table-hover text-center w-100 overflow-y-hidden' style='table-layout: fixed;'> ");
		out.println("<thead class='table-dark sticky-top top-0 pt-3 bg-white'>");
		out.println("<tr>");
		out.println("<th scope='col'><input type='checkbox' class='form-check-input' onclick='checkAllRows(this)' /></th>");
		out.println("<th scope='col'>학번</th>");
		out.println("<th scope='col'>이름</th>");
		out.println("<th scope='col'>학과</th>");
		out.println("<th scope='col'>전화번호</th>");
		out.println("<th scope='col'>수정</th>");
		out.println("<th scope='col'>삭제</th>");
		out.println("</tr>");
		out.println("</thead>");
		out.println("<tbody>");

		// Show rows of Student record from the Database
		StudentService studentService = new StudentService();
		String keyword = (String) session.getAttribute("searchKeyword");
		
		List<Student> students = studentService.getAllStudents(category, keyword);
		// If the keyword is empty and there are still no results ==> then it's for sure empty
		// This is because ALL records of students are fetched via : SELECT * FROM student WHERE (searchCategory) LIKE (searchKeyword);  
		if (!keyword.equals("") && students.isEmpty()) {
			out.println("<tr>");
			out.println("<td class='p-5' colspan='6'>검색 결과에 해당하는 학생들이 없습니다</td>");
			out.println("</tr>");
		// If there are no REGISTERED students
		} else if (students.isEmpty()) {
			out.println("<tr>");
			out.println("<td class='p-5' colspan='6'>등록된 학생들이 없습니다</td>");
			out.println("</tr>");
		} else {
			for (Student student : students) {
				out.println("<tr>");
				out.println("<td><input type='checkbox' class='form-check-input row-checkbox' name='rowIds' value='" + student.getField("id") + "'/></td>");
				out.println("<td>" + student.getField("studentId") + "</td>");
				out.println("<td>" + student.getField("name") + "</td>");
				out.println("<td>" + student.getField("major") + "</td>");
				out.println("<td>" + student.getField("phoneNumber") + "</td>");
				out.println("<td>");
				out.println("<a href='readStudent.do?id=" + student.getField("id") + "' class='icon-link icon-link-hover text-primary'>");
				out.println("<svg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke-width='1.5' stroke='currentColor' width='18' height='18'>");
				out.println("<path stroke-linecap='round' stroke-linejoin='round' d='m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L6.832 19.82a4.5 4.5 0 0 1-1.897 1.13l-2.685.8.8-2.685a4.5 4.5 0 0 1 1.13-1.897L16.863 4.487Zm0 0L19.5 7.125' />");
				out.println("</svg>");
				out.println("</a>");
				out.println("</td>");
				out.println("<td>");
				out.println("<a href='deleteStudent.do?id=" + student.getField("id") + "' class='icon-link icon-link-hover text-danger'>");
				out.println("<svg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke-width='1.5' stroke='currentColor' width='18' height='18'>");
				out.println("<path stroke-linecap='round' stroke-linejoin='round' d='m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0' />");
				out.println("</svg>");
				out.println("</a>");
				out.println("</td>");
				out.println("</tr>");
			}
		}

		out.println("</tbody>");
		out.println("</table>");
		out.println("</div>");
		out.println("<!-- End of Student List -->");
		out.println("</form>");
		out.println("</section>");
		out.println("</main>");
		
		// Script to check ALL checkboxes in table rows
		// Code provided by chatGPT
		out.println("<script>");
		out.println("function checkAllRows(tableHeadCheckBox) {");
		out.println("let checkboxes = document.querySelectorAll('input[type=\"checkbox\"].row-checkbox');");
		out.println("checkboxes.forEach(checkbox => checkbox.checked = tableHeadCheckBox.checked);");    
		out.println("}");
		out.println("</script>");
		
		out.println("</body>");
		out.println("</html>");

		out.close();
	}
}