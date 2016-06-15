package by.segg3r;

import java.io.File;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import by.segg3r.config.UpgradeClientConfig;
import by.segg3r.upgradeclient.UpgradeRunner;
import by.segg3r.util.LoggerUtil;

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

		LoggerUtil.initializeLogger(rootPath, "upgrade-client.log");

		int upgradeResult;
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				UpgradeClientConfig.class)) {
			UpgradeRunner upgradeRunner = ctx.getBean(UpgradeRunner.class);
			upgradeResult = upgradeRunner.runUpgrade(rootPath);
		}

		System.exit(upgradeResult);
	}

}
