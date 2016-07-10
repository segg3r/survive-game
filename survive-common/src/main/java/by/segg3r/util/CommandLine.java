package by.segg3r.util;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CommandLine {

	private static final Logger LOG = LogManager.getLogger(CommandLine.class);
	
	public static Process execute(String command, File workingDirectory) throws IOException {
		String fullCommand;
		if (isWindows()) {
			fullCommand = "cmd /c " + command;
		} else {
			fullCommand = command;
		}
	
		LOG.info("Executing command line : " + fullCommand);
		
		Runtime runtime = Runtime.getRuntime();
		return runtime.exec(fullCommand, null, workingDirectory);
	}
	
	private static boolean isWindows() {
		String operatingSystem = getOperatingSystemName();
		LOG.info("Current operating system is : " + operatingSystem);
		return operatingSystem.contains("win");
	}
	
	private static String getOperatingSystemName() {
		return System.getProperty("os.name").toLowerCase(Locale.US);
	}
	
}
