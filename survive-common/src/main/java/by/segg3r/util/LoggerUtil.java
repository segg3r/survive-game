package by.segg3r.util;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class LoggerUtil {

	public static void initializeLogger(String fileName) {
		String rootPath = System.getProperty("user.dir");
		initializeLogger(rootPath, fileName);
	}
	
	public static void initializeLogger(String rootPath, String fileName) {
		ConsoleAppender console = new ConsoleAppender(); // create appender
		// configure the appender
		String PATTERN = "%d [%p|%c|%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.ERROR);
		console.activateOptions();
		// add appender to any Logger (here is root)
		Logger.getRootLogger().addAppender(console);

		FileAppender fa = new FileAppender();
		fa.setName("FileLogger");
		fa.setFile(rootPath + "/" + fileName);
		fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
		fa.setThreshold(Level.INFO);
		fa.setAppend(true);
		fa.activateOptions();
		Logger.getRootLogger().addAppender(fa);
	}
	
}
