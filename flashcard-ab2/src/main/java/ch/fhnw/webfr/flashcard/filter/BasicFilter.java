package ch.fhnw.webfr.flashcard.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class BasicFilter implements Filter{
	
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("BasicFilter initialized");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		System.out.println("Request triggered: " + req.getRequestURI());
		
		chain.doFilter(request, response);
		
	}
	
	public void destroy() {
		
	}
}
