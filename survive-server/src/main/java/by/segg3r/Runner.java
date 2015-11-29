package by.segg3r;

import org.apache.log4j.BasicConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import by.segg3r.config.InterceptorsConfig;
import by.segg3r.config.ListenersConfig;
import by.segg3r.config.MessageProcessorConfig;
import by.segg3r.config.ServerConfig;

public final class Runner {

	private Runner() {
	}

	public static void main(String[] args) {
		BasicConfigurator.configure();

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				ServerConfig.class, MessageProcessorConfig.class,
				ListenersConfig.class, InterceptorsConfig.class)) {
			Server server = ctx.getBean(Server.class);
			Thread serverThread = new Thread(server);
			serverThread.start();

			ctx.close();
		}
	}

}
