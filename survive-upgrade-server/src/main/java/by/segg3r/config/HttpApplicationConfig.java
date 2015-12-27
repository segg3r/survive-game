package by.segg3r.config;

import java.util.Arrays;

import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import by.segg3r.http.UpgradeServerJaxRsApplication;
import by.segg3r.http.services.UpgradeRestService;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Configuration
public class HttpApplicationConfig {

	@Bean(destroyMethod = "shutdown")
	public SpringBus cxf() {
		return new SpringBus();
	}

	@Bean @DependsOn( "cxf" )
	public Server jaxRsServer() {
		JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance()
				.createEndpoint(upgradeServerApplication(),
						JAXRSServerFactoryBean.class);
		factory.setServiceBeans(Arrays.<Object> asList(upgradeRestService()));
		factory.setProviders(Arrays.<Object> asList(jsonProvider()));
		return factory.create();
	}

	@Bean
	public UpgradeRestService upgradeRestService() {
		return new UpgradeRestService();
	}

	@Bean
	public UpgradeServerJaxRsApplication upgradeServerApplication() {
		return new UpgradeServerJaxRsApplication();
	}

	@Bean
	public JacksonJsonProvider jsonProvider() {
		return new JacksonJsonProvider();
	}

}
