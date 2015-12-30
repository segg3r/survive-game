package by.segg3r.util.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import by.segg3r.util.DigestUtil;

@Component
public class DigestUtilImpl implements DigestUtil {

	@Override
	public String getFileDigest(File file) throws IOException {
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			return DigestUtils.md5Hex(in);
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

}
