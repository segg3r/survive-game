package by.segg3r;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import by.segg3r.config.ServerConfig;

public final class Runner {

	private Runner() {
	}

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				ServerConfig.class)) {
			Server server = ctx.getBean(Server.class);
			Thread serverThread = new Thread(server);
			serverThread.start();
			
			ctx.close();
		}
	}

}
