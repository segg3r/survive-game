package by.segg3r.upgradeclient.http.impl;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.exceptions.APIException;

@Component
public class SimpleHttpClient {

	@Autowired
	private HttpClient httpClient;

	public byte[] get(String address) throws APIException {
		try {
			HttpGet request = new HttpGet(address);
			HttpResponse response = httpClient.execute(request);
			InputStream in = response.getEntity().getContent();
			return IOUtils.toByteArray(in);
		} catch (Exception e) {
			throw new APIException(e);
		}
	}

}
