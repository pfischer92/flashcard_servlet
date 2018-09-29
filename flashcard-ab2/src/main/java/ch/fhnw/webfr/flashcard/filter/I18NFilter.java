package ch.fhnw.webfr.flashcard.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import ch.fhnw.webfr.flashcard.wrapper.ResponseWrapper;

@WebFilter(urlPatterns= {"/*"})
public class I18NFilter implements Filter{
	
	private static String DEFAULT_FILENAME = "messages.properties";
	private Properties i18nStrings;

	public void init(FilterConfig filterConfig) throws ServletException{
		// Get filename from servlet context
		String fileName = (String) filterConfig.getServletContext().getInitParameter("i18n");
		if(fileName == null) {
			fileName = DEFAULT_FILENAME;
		}
		
		// Load i18n messages from properties file
		i18nStrings = new Properties();
		try {
			i18nStrings.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
		}
		catch(IOException e){
			throw new ServletException(e.getMessage());
		}
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// INBOUND PATH
		
		// Initialize a ResponseWrapper to put into the chain, wrapping the original response
		ResponseWrapper rw = new ResponseWrapper((HttpServletResponse)response);
		
		// Put the ResponseWrapper into the chain
		chain.doFilter(request, rw);
		
		// OUTBOUND PATH
		
		// Read the response which should be updated
		BufferedReader rd = new BufferedReader(rw.getReader());
		String line = rd.readLine();
		StringBuffer sb = new StringBuffer();
		
		// Loop through each line of the response
		while(line != null) {
			// Check if a translation is necessary
			for(Entry<Object, Object> e : i18nStrings.entrySet()) {
				line = line.replace((String)e.getKey(), (String)e.getValue());
			}
			sb.append(line);
			line = rd.readLine();
		}
		
		// Write updated response into original response instance
		Writer wr = response.getWriter();
		wr.append(sb.toString());
		wr.flush();	
	}
	
	public void destroy() {
		
	}
}
