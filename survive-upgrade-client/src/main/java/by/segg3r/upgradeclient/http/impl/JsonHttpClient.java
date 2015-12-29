package by.segg3r.upgradeclient.http.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.exceptions.APIException;

import com.google.gson.Gson;

@Component
public class JsonHttpClient {

	@Autowired
	private HttpClient httpClient;
	@Autowired
	private Gson gson;

	public <T> T get(String address, Class<T> clazz) throws APIException {
		try {
			HttpGet request = new HttpGet(address);
			HttpResponse response = httpClient.execute(request);
			InputStream in = response.getEntity().getContent();
			String jsonString = IOUtils.toString(in);
			
			return gson.fromJson(jsonString, clazz);
		} catch (IOException e) {
			throw new APIException(e);
		}
	}

}
