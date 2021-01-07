package com.iit.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BffConfig {

	@Bean(name ="bffRestTemplate")
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
