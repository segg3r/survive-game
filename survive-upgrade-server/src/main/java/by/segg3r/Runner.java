package by.segg3r;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import by.segg3r.config.ServerConfig;
import by.segg3r.util.LoggerUtil;

public class Runner {

	private Runner() {
	}

	public static void main(String[] args) {
		LoggerUtil.initializeLogger("upgrade-server.log");

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
