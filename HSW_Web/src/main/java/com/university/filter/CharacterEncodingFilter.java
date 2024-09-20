package com.university.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;

@WebFilter(urlPatterns = "*.do", initParams = @WebInitParam(name = "characterEncoding", value="UTF-8"))
public class CharacterEncodingFilter extends HttpFilter implements Filter {
      
    private static final long serialVersionUID = 1L;
    private String encoding;

	public CharacterEncodingFilter() {}

	/**
	 * 요청 및 응답에 대해 설정된 UTF-8 문자 인코딩을 적용합니다
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		response.setContentType("text/html;charset=" + encoding);
		chain.doFilter(request, response);
	}
	
	/**
	 * 필터 객체를 초기화하는 메서드입니다. 초기화 매개변수로부터 문자 인코딩 값을 가져옵니다.
	 */
	public void init(FilterConfig config) throws ServletException {
		encoding = config.getInitParameter("characterEncoding");
	}
}
