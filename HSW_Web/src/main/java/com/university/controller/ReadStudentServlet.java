package com.university.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.university.model.impl.Student;
import com.university.service.StudentService;

@WebServlet("/readStudent.do")
public class ReadStudentServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private String stringId;
	

	/**
	 * 사용자의 요청에서 학생 ID를 추출하고
	 * 해당 id를 사용하여 해당 학생 정보를 조회합니다.
	 * 
	 * @param request  HttpServletRequest 객체
	 * @param response HttpServletResponse 객체
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stringId = request.getParameter("id");
		this.stringId = stringId;
		showContent(response);
	}
	
	/**
	 * 사용자를 위해 데이터베이스에 저장된 각 학생들의 정보를 보거나 수정할 수 있는 뷰를 만듭니다
	 * 
	 * @param response HttpServletResponse 객체
	 * @throws IOException
	 */
	private void showContent(HttpServletResponse response) throws IOException {
		Student student = getStudentInfo(stringId);
		PrintWriter out = response.getWriter();
		
		// Head
		out.println("<!DOCTYPE html>"); 
		out.println("<html>"); 
		out.println("<head>"); 
		out.println("<meta charset='UTF-8'>"); 
		out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>"); 
		out.println("<link rel='stylesheet' href='css/bootstrap.min.css' />"); 
		out.println("<link rel='stylesheet' href='css/style.css'/>"); 
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
		out.println("<!-- Menu Home -->"); 
		
		// 홈
		out.println("<li class='nav-item'>"); 
		out.println("<a href='/' class='nav-link text-white'>"); 
		out.println("<div class='d-flex align-items-center'>"); 
		out.println("<svg class='bi me-2' xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke-width='1.5' stroke='currentColor' width='16' height='16'>"); 
		out.println("<path stroke-linecap='round' stroke-linejoin='round' d='m2.25 12 8.954-8.955c.44-.439 1.152-.439 1.591 0L21.75 12M4.5 9.75v10.125c0 .621.504 1.125 1.125 1.125H9.75v-4.875c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125V21h4.125c.621 0 1.125-.504 1.125-1.125V9.75M8.25 21h8.25' />"); 
		out.println("</svg>"); 
		out.println("<p class='m-0'>홈</p>"); 
		out.println("</div>"); 
		out.println("</a>"); 
		out.println("</li>"); 
		
		// 학생 조회 및 검색
		out.println("<!-- List and Search Students -->"); 
		out.println("<li>"); 
		out.println("<a href='/browseStudents.do' class='nav-link text-white'>"); 
		out.println("<div class='d-flex align-items-center'>"); 
		out.println("<svg class='bi me-2' xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke-width='1.5' stroke='currentColor' width='16' height='16'>"); 
		out.println("<path stroke-linecap='round' stroke-linejoin='round' d='m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z' />"); 
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
		
		// 학생 정보 수정
		out.println("<section class='d-flex justify-content-center align-items-center' style='width: 75vw; height: auto; background-color: #ced9c7;'>"); 
		out.println("<!-- Start of Student Registration Form -->"); 
		out.println("<div class='card rounded-3 text-black border border-2 border-light-subtle'>"); 
		out.println("<div class='col-lg-12'>"); 
		out.println("<div class='card-body p-md-5 mx-md-4'>"); 
		out.println("<div class='text-center'>"); 
		out.println("<h4 class='w-100 mt-1 mb-5 pb-1'>학생 수정</h4>"); 
		out.println("</div>"); 
		
		// 정보 수정 폼
		out.println("<!-- Edit Form -->"); 
		out.println("<form action='/editStudent.do' method='POST'>"); 
		out.println("<input type='hidden' name='id' value='" + student.getField("id") + "'/>");
		
		// 학번
		out.println("<!-- Student ID -->"); 
		out.println("<div data-mdb-input-init class='form-outline mb-3'>"); 
		out.println("<span class='form-label text-muted'>학번</span>"); 
		out.println("<p>" + student.getField("studentId") + "</p>"); 
		out.println("</div>"); 
		
		// 이름
		out.println("<!-- Student Name -->"); 
		out.println("<div data-mdb-input-init class='form-outline mb-3'>"); 
		out.println("<span class='form-label text-muted'>이름</span>"); 
		out.println("<p>" + student.getField("name") + "</p>"); 
		out.println("</div>"); 
		
		// 전공
		out.println("<!-- Student Major Input -->"); 
		out.println("<div data-mdb-input-init class='form-outline mb-3'>"); 
		out.println("<label class='form-label text-muted' for='major'>학과</label>"); 
		out.println("<input type='text' id='major' class='form-control' name='student_major' value='" + student.getField("major") + "' />"); 
		out.println("</div>"); 
		
		// 전화번호
		out.println("<!-- Student Telephone Number Input -->"); 
		out.println("<div data-mdb-input-init class='form-outline mb-3'>"); 
		out.println("<label class='form-label text-muted' for='student_tel'>전화번호</label>"); 
		out.println("<input type='tel' id='student_tel' class='form-control' name='student_tel' value='" + student.getField("phoneNumber") + "' />"); 
		out.println("</div>"); 
		
		// 입력
		out.println("<!-- Submit -->"); 
		out.println("<div class='text-center pt-1 mb-5 pb-1'>"); 
		out.println("<div class='d-grid gap-2'>"); 
		out.println("<button data-mdb-button-init data-mdb-ripple-init class='btn btn-primary fa-lg gradient-custom-2 mb-3' type='submit'>수정</button>"); 
		out.println("</div>"); 
		out.println("</div>"); 
		out.println("</form>"); 
		out.println("</div>"); 
		out.println("</div>"); 
		out.println("</div>"); 
		out.println("<!-- End of Student Registration Form -->"); 
		out.println("</section>"); 
		out.println("</main>"); 
		out.println("</body>"); 
		out.println("</html>");
		
		out.close();
	}
	
	/**
	 * 각 학생의 정보를 가져오기 위해 StudentService 객체를 생성합니다.
	 * id 기본키 값으로 검색합니다.
	 * 
	 * @param stringId 사용자 요청에서 추출한 문자열 버젼의 기본키 값.
	 * @return		   해당 기본키 값을 가지고 있는 학생 정보
	 */
	private Student getStudentInfo(String stringId) {
		Long id = Long.parseLong(stringId);
		StudentService studentService = new StudentService();
		Student student = studentService.getStudent(id);
		return student;
	}
}