package by.segg3r;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import by.segg3r.config.ServerConfig;

public class Runner {

	private Runner() {
	}

	public static void main(String[] args) {
		BasicConfigurator.configure();

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				ServerConfig.class)) {
			Server server = ctx.getBean(Server.class);
			ServletContextHandler httpApplicationServletContext = ctx
					.getBean(ServletContextHandler.class);
			
			server.setHandler(httpApplicationServletContext);
			server.start();
			server.join();

			ctx.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
