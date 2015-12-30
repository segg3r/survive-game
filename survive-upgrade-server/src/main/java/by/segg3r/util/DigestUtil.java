package by.segg3r.util;

import java.io.File;
import java.io.IOException;

public interface DigestUtil {

	String getFileDigest(File file) throws IOException;
	
}
