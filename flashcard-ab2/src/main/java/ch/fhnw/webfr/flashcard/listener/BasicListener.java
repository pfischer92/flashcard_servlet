package ch.fhnw.webfr.flashcard.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import  javax.servlet.ServletContextListener;

import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;
import ch.fhnw.webfr.flashcard.util.QuestionnaireInitializer;

public class BasicListener implements ServletContextListener{

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext context = servletContextEvent.getServletContext();
		
		String mode = context.getInitParameter("mode");
		QuestionnaireRepository questionnaireRepository = null;
		
		if((mode != null)&& mode.equals("test")) {
			questionnaireRepository = new QuestionnaireInitializer().initRepoWithTestData();
		}
		else {
			questionnaireRepository = QuestionnaireRepository.getInstance();
		}
		
		context.setAttribute("questionnaireRepository", questionnaireRepository);
	}
	
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
	}
}
