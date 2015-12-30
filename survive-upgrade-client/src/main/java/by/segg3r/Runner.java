package by.segg3r;

import java.io.File;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import by.segg3r.config.UpgradeClientConfig;
import by.segg3r.upgradeclient.UpgradeRunner;

public class Runner {

	private Runner() {
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new RuntimeException(
					"Wrong number of arguments. <args>: [String root-path].");
		}

		String rootPath = args[0];
		File file = new File(rootPath);
		if (!file.isDirectory()) {
			throw new RuntimeException(
					"Specified root-path does not exist in file system.");
		}

		initializeLogger(rootPath);

		int upgradeResult;
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				UpgradeClientConfig.class)) {
			UpgradeRunner upgradeRunner = ctx.getBean(UpgradeRunner.class);
			upgradeResult = upgradeRunner.runUpgrade(rootPath);
		}

		System.exit(upgradeResult);
	}

	private static void initializeLogger(String rootPath) {
		ConsoleAppender console = new ConsoleAppender(); // create appender
		// configure the appender
		String PATTERN = "%d [%p|%c|%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.FATAL);
		console.activateOptions();
		// add appender to any Logger (here is root)
		Logger.getRootLogger().addAppender(console);

		FileAppender fa = new FileAppender();
		fa.setName("FileLogger");
		fa.setFile(rootPath + "/upgrade-client.log");
		fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
		fa.setThreshold(Level.ALL);
		fa.setAppend(true);
		fa.activateOptions();
		Logger.getRootLogger().addAppender(fa);
	}

}
